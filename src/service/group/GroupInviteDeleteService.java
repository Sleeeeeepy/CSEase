package service.group;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dao.GroupInviteDAO;
import dao.GroupInviteImpl;
import dto.GroupBean;
import dto.GroupInviteBean;
import dto.MemberBean;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class GroupInviteDeleteService implements Service {
	
	private boolean isUserProfilePage = false;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		Connection conn = null;
		GroupDAO gDao = null;
		GroupInviteDAO iDao = null;
		try {
			conn = ConnectionProvider.getConnection();
			gDao = new GroupImpl(conn);
			iDao = new GroupInviteImpl(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid id.");
		}
		
		GroupInviteBean invite = iDao.select(id);
		GroupBean group = gDao.select(invite.getGroupId());
		request.setAttribute("groupId", invite.getGroupId());
		request.setAttribute("memberId", invite.getUserId());
		MemberBean member = (MemberBean) request.getSession().getAttribute("user");
		if (member != null) {
			if (member.getId() == invite.getUserId()) {
				isUserProfilePage = true;
				iDao.delete(id);
			} else if (member.getId() == group.getAdminId()) {
				iDao.delete(id);
			} else {
				throw new ServiceException("Invalid access.");
			}
		} else {
			throw new ServiceException("Invalid access.");
		}
	}

	public boolean isUserProfilePage() {
		return isUserProfilePage;
	}

}
