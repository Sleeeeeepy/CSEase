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
import dao.MemberDAO;
import dao.MemberImpl;
import dto.GroupBean;
import dto.GroupInviteBean;
import dto.MemberBean;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class GroupInviteService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		if (request.getSession().getAttribute("user") != null) {
			Connection conn = null;
			GroupDAO gDao = null;
			GroupInviteDAO iDao = null;
			MemberDAO mDao = null;
			try {
				conn = ConnectionProvider.getConnection();
				gDao = new GroupImpl(conn);
				iDao = new GroupInviteImpl(conn);
				mDao = new MemberImpl(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			int groupId = 0;
			try {
				System.out.println(request.getParameter("groupId"));
				groupId = Integer.parseInt(request.getParameter("groupId"));
			} catch (NumberFormatException e) {
				throw new ServiceException("Invalid id.");
			}						

			int userId = 0;
			try {
				System.out.println(request.getParameter("userId"));
				userId = Integer.parseInt(request.getParameter("memberId"));
			} catch (NumberFormatException e) {
				throw new ServiceException("Invalid id.");
			}	
			GroupBean group = gDao.select(groupId);
			if (group == null) {
				throw new NotFoundException("The group could not be found.");
			}
			
			MemberBean admin = (MemberBean)request.getSession().getAttribute("user");
			if (admin == null) {
				throw new ServiceException("Invalid access.");
			} else if (admin.getId() != group.getAdminId()) {
				throw new ServiceException("Invalid access.");
			}
				
			MemberBean member = mDao.select(userId);
			if (member == null) {
				throw new NotFoundException("The user could not be found.");
			}
			
			if (gDao.isExist(groupId, userId)) {
				throw new ServiceException(member.getNickname() + " is already in this group.");
			}
			
			if (iDao.isExist(groupId, userId)) {
				throw new ServiceException("You have already invited this user.");
			}
			
			iDao.insert(new GroupInviteBean(0, userId, groupId, true));
		} else {
			throw new ServiceException("Invalid access.");
		}

	}

}
