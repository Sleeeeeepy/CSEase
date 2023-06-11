package service.report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.ReportDAO;
import dao.ReportImpl;
import dto.CommentBean;
import dto.MemberBean;
import dto.Pagination;
import dto.TagBean;
import service.Service;
import service.ServiceException;

public class ReportListService implements Service{
	private ReportStatus status;
	
	public ReportListService(ReportStatus status) {
		this.status = status;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (request.getSession().getAttribute("user") != null) {
			MemberBean member = (MemberBean)request.getSession().getAttribute("user");
			if (member.getId() != 1) {
				throw new ServiceException("Invalid Access.");
			}
		} else {
			throw new ServiceException("Invalid Access.");
		}
		
		ReportDAO dao = null;
		try {
			dao = new ReportImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (status.equals(ReportStatus.NONE)) {
			request.setAttribute("rList", dao.selectList());
		} else if (status.equals(ReportStatus.CONFIRMED)) {
			request.setAttribute("rList", dao.selectList(true));
		} else if (status.equals(ReportStatus.NOT_CONFIRMED)) {
			request.setAttribute("rList", dao.selectList(false));
		} else {
			throw new ServiceException("Invalid Operation");
		}
		
		
		int current = 1;
		try {
			current = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			current = 1;
		}
		Pagination page = new Pagination(current, 10, 5, dao.getAllCount());

		List<String> pages = new ArrayList<>();
		for (int i = page.getPageStartsAt(); i < page.getPageEndsAt() + 1; i++) {
			pages.add(String.format("%s/Report/View?page=%d", request.getContextPath(), i));
		}
		
		//pagination
		request.setAttribute("pagination", page);
		request.setAttribute("nextBlockPage", String.format("%s/Report/View?page=%d", request.getContextPath(), page.getPageEndsAt()+1));
		request.setAttribute("prevBlockPage", String.format("%s/Report/View?page=%d", request.getContextPath(), page.getPageStartsAt()-1));
		request.setAttribute("pages", pages);
	}
	
}
