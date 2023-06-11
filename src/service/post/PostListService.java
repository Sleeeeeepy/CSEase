package service.post;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import dto.*;
import service.ListMode;
import service.Service;
import service.SortMode;

public class PostListService implements Service {

	private ListMode mode = ListMode.NONE;
	public PostListService(ListMode mode) {
		this.mode = mode;
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		PostDAO pDao = null;
		MemberDAO mDao = null;
		TagDAO tDao = null;
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			pDao = new PostImpl(conn);
			mDao = new MemberImpl(conn);
			tDao = new TagImpl(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String q = request.getParameter("q");
		//특수문자 escape
		String regex = util.RegexEscape.escape(q);
		String[] temp = regex.split(" ");
		String regexp = "";
		if (temp.length > 1) {
			for (int i = 0; i < temp.length - 1; i++) {
				regexp += temp[i];
				regexp += "|";
			}
			regexp += temp[temp.length - 1];
		} else if (temp.length == 1) {
			regexp = temp[0];
		} else {
			regexp = "";
		}
		
		
		String order = request.getParameter("order");
		String type = request.getParameter("type");
		SortMode sort = null;
		ListMode mode = null;
		try {
			sort = SortMode.valueOf(order);
			mode = ListMode.valueOf(type);
		} catch (Exception e) {
			e.printStackTrace();
			sort = SortMode.HIT;
			mode = ListMode.NONE;
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
		Pagination page = null;
		if (mode.equals(ListMode.NONE)) {
			page = new Pagination(current, 10, 5, pDao.getAllCount());
			pList = pDao.selectList(page, sort);
		} else {
			page = new Pagination(current, 10, 5, pDao.searchCount(mode, regexp));
			pList = pDao.searchList(page, sort, mode, regexp);
		}
		
		for (int i = 0; i < pList.size(); i++) {
			mList.add(mDao.select(pList.get(i).getUserId()));
			tMap.put(i, tDao.selectListByPost(pList.get(i).getId()));
			for (TagBean t : tDao.selectListByPost(pList.get(i).getId())) {
				System.out.println(t.getTagName());
			}
		}
		
		
		List<String> pages = new ArrayList<String>();
		for (int i = page.getPageStartsAt(); i < page.getPageEndsAt() + 1; i++) {
			pages.add(String.format("%s/Search?q=%s&type=%s&order=%s&page=%d", request.getContextPath(), q, type, order, i));
		}

		request.setAttribute("pList", pList);
		request.setAttribute("mList", mList);
		request.setAttribute("tMap", tMap);
		request.setAttribute("pagination", page);
		request.setAttribute("nextBlockPage", String.format("%s/Search?q=%s&type=%s&order=%s&page=%d", request.getContextPath(), q, type, order, page.getPageEndsAt()+1));
		request.setAttribute("prevBlockPage", String.format("%s/Search?q=%s&type=%s&order=%s&page=%d", request.getContextPath(), q, type, order, page.getPageStartsAt()-1));
		request.setAttribute("pages", pages);
		
	}

	

}
