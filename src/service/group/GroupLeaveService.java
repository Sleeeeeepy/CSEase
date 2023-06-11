package service.group;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dto.GroupBean;
import dto.MemberBean;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class GroupLeaveService implements Service {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
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

		MemberBean member = (MemberBean)request.getSession().getAttribute("user");
		if (member == null) {
			throw new ServiceException("invalid access.");
		}
		
		int userId = member.getId();

		GroupBean group = gDao.select(id);
		if (group.getAdminId() == userId) {
			throw new ServiceException("Group administrator cannot leave the group.");
		}
		
		if (request.getSession().getAttribute("user") != null) {
			if (member.getId() == group.getAdminId()) {
				gDao.leaveMember(userId, group.getId());
			} else if (member.getId() == userId) {
				gDao.leaveMember(userId, group.getId());
			}
		} else {
			throw new ServiceException("Invalid Access.");
		}
	}
}
