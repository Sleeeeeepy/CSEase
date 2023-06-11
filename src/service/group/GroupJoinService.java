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

public class GroupJoinService implements Service {
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
			//System.out.println(request.getParameter("memberId"));
			//System.out.println(request.getParameter("groupId"));
			int userId = 0;
			try {
				userId = Integer.parseInt(request.getParameter("memberId"));
			} catch (NumberFormatException e) {
				throw new ServiceException("Invalid id.");
			}						

			int groupId = 0;
			try {
				groupId = Integer.parseInt(request.getParameter("groupId"));
			} catch (NumberFormatException e) {
				throw new ServiceException("Invalid id.");
			}
			
			GroupBean group = gDao.select(groupId);
			if (group == null) {
				throw new NotFoundException("The group could not be found.");
			}
			
			MemberBean member = (MemberBean)request.getSession().getAttribute("user");
			if (member == null) {
				throw new ServiceException("Invalid access.");
			} else if (member.getId() != userId) {
				throw new ServiceException("Invalid access.");
			}
			
			if (gDao.isExist(group.getId(), userId)) {
				throw new ServiceException(" you are already in this group.");
			}
			
			if (iDao.isExist(group.getId(), userId)) {
				throw new ServiceException("You have already invited this user.");
			}
			
			iDao.insert(new GroupInviteBean(0, userId, group.getId(), false));
		} else {
			throw new ServiceException("Invalid access.");
		}

	}
}
