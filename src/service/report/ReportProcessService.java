package service.report;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import dto.MemberBean;
import dto.PostReport;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class ReportProcessService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		if (request.getSession().getAttribute("user") != null) {
			MemberBean member = (MemberBean)request.getSession().getAttribute("user");
			if (member.getId() != 1) {
				throw new ServiceException("Invalid Access.");
			}
		} else {
			throw new ServiceException("Invalid Access.");
		}
		
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PostDAO pDao = new PostImpl(conn);
		ReportDAO rDao = new ReportImpl(conn);
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid id.");
		}
		
		PostReport bean = rDao.select(id);
		if (bean != null) {
			pDao.delete(bean.getPostId());
			bean.setConfirm(true);
			rDao.update(bean);
		} else {
			throw new NotFoundException("Invalid Access.");
		}
	}
	
}
