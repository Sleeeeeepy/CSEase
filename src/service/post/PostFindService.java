package service.post;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDAO;
import dao.CommentImpl;
import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dao.MemberDAO;
import dao.MemberImpl;
import dao.PostDAO;
import dao.PostImpl;
import dao.TagDAO;
import dao.TagImpl;
import dto.CommentBean;
import dto.MemberBean;
import dto.OpenRange;
import dto.Pagination;
import dto.PostBean;
import dto.TagBean;
import hash.IHash;
import hash.SHA1;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class PostFindService implements Service {

	private boolean burn = false;
	private boolean _private = false;
	private boolean auth = false;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		Connection conn = null;
		PostDAO pDao = null;
		MemberDAO mDao = null;
		TagDAO tDao = null;
		CommentDAO cDao = null;
		GroupDAO gDao = null;
		Date now = new Date();

		try {
			conn = ConnectionProvider.getConnection();
			pDao = new PostImpl(conn);
			mDao = new MemberImpl(conn);
			tDao = new TagImpl(conn);
			cDao = new CommentImpl(conn);
			gDao = new GroupImpl(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new NotFoundException("Invalid id.");
		}

		PostBean post = pDao.select(id);
		if (post == null) { // 포스트가 존재하지 않으면
			throw new NotFoundException("The post with id " + id + " does not exist.");
		}
		
		MemberBean member = (MemberBean) request.getSession().getAttribute("user");
		if (post.getOpenRange().equals(OpenRange.PRIVATE)) {
			_private = true;

			if (member != null) {
				if (member.getId() == post.getUserId() && post.getUserId() != 0) {
					auth = true;
				} else {
					if (passwordCheck(post, request, response)) {
						auth = true;
					}
				}
			} else {
				if (passwordCheck(post, request, response)) {
					auth = true;
				}
			}
		} else if (post.getOpenRange().equals(OpenRange.MEMBER_ONLY)) {
			if (member != null) {
				if (gDao.isExist(post.getGroupId(), member.getId())) { //그룹에 존재하는 유저인지
					auth = true;
				} else {
					throw new ServiceException("This post can only be accessed by certain groups of users.");
				}
			} else {
				throw new ServiceException("This post can only be accessed by certain groups of users.");
			}
		}

		if (post.isBurn() == true) { // burn이 true이면
			burn = true;
			pDao.delete(id);
			throw new ServiceException("This post has already been read.");
		} else if (post.getExpiredDate() != null
				&& post.getExpiredDate().compareTo(new java.sql.Date(now.getTime())) < 0) { // 기간이 지났으면
			burn = true;
			pDao.delete(id);
			throw new ServiceException("This post is out of date.");
		}

		int current = 1;
		try {
			current = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			current = 1;
		}
		Pagination page = new Pagination(current, 10, 5, cDao.getAllCount(id));

		MemberBean member2 = mDao.select(post.getUserId());
		List<TagBean> tList = tDao.selectListByPost(post.getId());
		List<CommentBean> cList = cDao.selectListByPost(page, post.getId());
		List<MemberBean> mList = new ArrayList<>();
		for (CommentBean c : cList) {
			mList.add(mDao.select(c.getUserId()));
		}

		List<String> pages = new ArrayList<>();
		for (int i = page.getPageStartsAt(); i < page.getPageEndsAt() + 1; i++) {
			pages.add(String.format("%s/Post/View?id=%d&page=%d", request.getContextPath(), id, i));
		}

		request.setAttribute("post", post);
		request.setAttribute("member", member2);
		request.setAttribute("tList", tList);
		request.setAttribute("cList", cList);
		request.setAttribute("mList", mList);
		request.setAttribute("private", _private);
		request.setAttribute("auth", auth);
		// pagination
		request.setAttribute("pagination", page);
		request.setAttribute("nextBlockPage", String.format("%s/Post/View?id=%d&page=%d", request.getContextPath(), id, page.getPageEndsAt() + 1));
		request.setAttribute("prevBlockPage", String.format("%s/Post/View?id=%d&page=%d", request.getContextPath(), id, page.getPageStartsAt() - 1));
		request.setAttribute("pages", pages);

	}

	public boolean isBurn() {
		return burn;
	}

	private boolean passwordCheck(PostBean post, HttpServletRequest request, HttpServletResponse response)
			throws ServiceException {
		String password = request.getParameter("password");
		if (password != null) {
			IHash hash = new SHA1();
			password = hash.crypto(password);
			if (password.equals(post.getPassword())) { // 비밀번호가 같으면
				return true;
			}
		}
		return false;
	}

}
