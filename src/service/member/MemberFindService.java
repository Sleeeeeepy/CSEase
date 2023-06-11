package service.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import dto.GroupBean;
import dto.GroupInviteBean;
import dto.MemberBean;
import dto.Pagination;
import dto.PostBean;
import dto.TagBean;
import service.ListMode;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class MemberFindService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws NotFoundException, ServiceException {
		MemberDAO mDao = null;
		PostDAO pDao = null;
		TagDAO tDao = null;
		GroupDAO gDao = null;
		GroupInviteDAO iDao = null;
		try {
			mDao = new MemberImpl(ConnectionProvider.getConnection());
			pDao = new PostImpl(ConnectionProvider.getConnection());
			tDao = new TagImpl(ConnectionProvider.getConnection());
			gDao = new GroupImpl(ConnectionProvider.getConnection());
			iDao = new GroupInviteImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid ID.");
		}

		if (id == 0 || id == 1) {
			throw new ServiceException("Invalid operation.");
		}

		MemberBean member = mDao.select(id);
		if (member == null) {
			throw new NotFoundException("Such user does not exist.");
		}

		int current = 1;
		try {
			current = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			current = 1;
		}

		List<PostBean> pList = new ArrayList<>();
		List<MemberBean> mList = new ArrayList<>();
		HashMap<Integer, List<TagBean>> tMap = new HashMap<>();
		Pagination page = new Pagination(current, 10, 5, pDao.getAllCount(id));
		pList = pDao.selectList(page, id);
		for (int i = 0; i < pList.size(); i++) {
			mList.add(mDao.select(pList.get(i).getUserId()));
			tMap.put(i, tDao.selectListByPost(pList.get(i).getId()));
		}
		
		List<String> pages = new ArrayList<>();
		for (int i = page.getPageStartsAt(); i < page.getPageEndsAt() + 1; i++) {
			pages.add(String.format("%s/Member/Profile?id=%d&page=%d", request.getContextPath(), id, i));
		}
		
		MemberBean smember = (MemberBean) request.getSession().getAttribute("user");
		if (smember != null) {
			if (smember.getId() == 1 || smember.getId() == member.getId()) {
				List<GroupInviteBean> invite = iDao.selectListByUser(id);
				List<GroupBean> giList = new ArrayList<>();
				for (GroupInviteBean i : invite) {
					giList.add(gDao.select(i.getGroupId()));
				}
				request.setAttribute("giList", giList);
				request.setAttribute("iList", invite);
			}
		}
		
		List<GroupBean> group = gDao.selectListByUser(id);
		request.setAttribute("member", member);
		request.setAttribute("postNo", page.getTotalRow());
		request.setAttribute("groupNo", group.size());
		request.setAttribute("gList", group);
		request.setAttribute("pList", pList);
		request.setAttribute("mList", mList);
		request.setAttribute("tMap", tMap);
		request.setAttribute("pagination", page);
		request.setAttribute("nextBlockPage", String.format("%s/Member/Profile?id=%d&page=%d", request.getContextPath(), id, page.getPageEndsAt()+1));
		request.setAttribute("prevBlockPage", String.format("%s/Member/Profile?id=%d&page=%d", request.getContextPath(), id, page.getPageStartsAt()-1));
		request.setAttribute("pages", pages);
	}

}
