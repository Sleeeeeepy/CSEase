package service.report;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.ReportDAO;
import dao.ReportImpl;
import dto.MemberBean;
import service.Service;
import service.ServiceException;

public class ReportDeleteService implements Service {

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
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid id");
		}
		dao.delete(id);
	}

}
