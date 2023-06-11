package service.group;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dao.GroupInviteDAO;
import dao.GroupInviteImpl;
import dto.GroupInviteBean;
import dto.MemberBean;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class GroupInviteAcceptService implements Service {
	
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
		int groupId = invite.getGroupId();
		int userId = invite.getUserId();
		boolean isInvite = invite.isInvite();
		MemberBean member = (MemberBean) request.getSession().getAttribute("user");
		request.setAttribute("groupId", invite.getGroupId());
		request.setAttribute("memberId", invite.getUserId());
		
		if (isInvite == false) { //�ʴ�� ���� �ƴ϶� ���� ��û�� �Ͽ��ٸ�
			if (member != null) {
				if (gDao.select(groupId).getAdminId() == member.getId()) { //�����ڰ� �����Ѵ�.
					gDao.addMember(userId, groupId);
					iDao.delete(id);
				} else {
					throw new ServiceException("Invalid access.");
				}
			} else {
				throw new ServiceException("Invalid access.");
			}
		} else { //���Խ�û�� �� ���� �ƴ϶� �ʴ� �� ���̶��
			if (member != null) {
				if (userId == member.getId()) { //�ʴ���� ����� �����Ѵ�.
					isUserProfilePage = true;
					gDao.addMember(userId, groupId);
					iDao.delete(id);
				} else {
					throw new ServiceException("Invalid access.");
				}
			} else {
				throw new ServiceException("Invalid access.");
			}
		}
	}

	public boolean isUserProfilePage() {
		return isUserProfilePage;
	}

}
