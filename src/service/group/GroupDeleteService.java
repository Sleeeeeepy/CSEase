package service.group;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dao.MemberDAO;
import dao.MemberImpl;
import dto.GroupBean;
import dto.MemberBean;
import service.Service;
import service.ServiceException;

public class GroupDeleteService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		Connection conn = null;
		GroupDAO gDao = null;
		try {
			conn = ConnectionProvider.getConnection();
			gDao = new GroupImpl(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid id.");
		}
		
		GroupBean group = gDao.select(id);
		if (request.getSession().getAttribute("user") != null) {
			MemberBean member = (MemberBean)request.getSession().getAttribute("user");
			if (member.getId() != group.getAdminId() && member.getId() != 1) {
				throw new ServiceException("Invalid Access.");
			}
		} else {
			throw new ServiceException("Invalid Access.");
		}
		
		gDao.delete(id);
	}

}
