package service.group;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dao.GroupInviteDAO;
import dao.GroupInviteImpl;
import dao.MemberDAO;
import dao.MemberImpl;
import dao.PostDAO;
import dao.PostImpl;
import dao.TagDAO;
import dao.TagImpl;
import dto.GroupBean;
import dto.GroupInviteBean;
import dto.MemberBean;
import dto.Pagination;
import dto.PostBean;
import dto.TagBean;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class GroupFindService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		Connection conn = null;
		GroupDAO gDao = null;
		GroupInviteDAO iDao = null;
		MemberDAO mDao = null;
		PostDAO pDao = null;
		TagDAO tDao = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			gDao = new GroupImpl(conn);
			iDao = new GroupInviteImpl(conn);
			mDao = new MemberImpl(conn);
			pDao = new PostImpl(conn);
			tDao = new TagImpl(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		GroupBean group = null;
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid id.");
		}
		
		group = gDao.select(id);
		if (group == null) {
			throw new NotFoundException("That group doesn't exist.");
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
		Pagination page = new Pagination(current, 10, 5, pDao.getAllCountByGroup(id));
		pList = pDao.selectListByGroup(page, id);
		for (int i = 0; i < pList.size(); i++) {
			mList.add(mDao.select(pList.get(i).getUserId()));
			tMap.put(i, tDao.selectListByPost(pList.get(i).getId()));
		}
		
		List<String> pages = new ArrayList<>();
		for (int i = page.getPageStartsAt(); i < page.getPageEndsAt() + 1; i++) {
			pages.add(String.format("%s/Group/Profile?id=%d&page=%d", request.getContextPath(), id, i));
		}
		
		MemberBean smember = (MemberBean) request.getSession().getAttribute("user");
		if (smember != null) {
			if (smember.getId() == 1 || smember.getId() == group.getAdminId()) {
				List<GroupInviteBean> invite = iDao.selectList(group.getId());
				List<MemberBean> miList = new ArrayList<>();
				for (GroupInviteBean i : invite) {
					miList.add(mDao.select(i.getUserId()));
				}
				request.setAttribute("miList", miList);
				request.setAttribute("iList", invite);
			}
		}
		
		MemberBean member = (MemberBean) request.getSession().getAttribute("user");
		if (member != null) {
			request.setAttribute("belong", gDao.isExist(id, member.getId()));
		} else {
			request.setAttribute("belong", false);
		}
		
		request.setAttribute("group", group);
		request.setAttribute("postNo", page.getTotalRow());
		request.setAttribute("pList", pList);
		request.setAttribute("mList", mList);
		request.setAttribute("tMap", tMap);
		request.setAttribute("pagination", page);
		request.setAttribute("nextBlockPage", String.format("%s/Group/Profile?id=%d&page=%d", request.getContextPath(), id, page.getPageEndsAt()+1));
		request.setAttribute("prevBlockPage", String.format("%s/Group/Profile?id=%d&page=%d", request.getContextPath(), id, page.getPageStartsAt()-1));
		request.setAttribute("pages", pages);
	}
}
