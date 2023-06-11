package service.member;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.MemberDAO;
import dao.MemberImpl;
import dto.MemberBean;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class PrepareMemberEditService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		MemberDAO dao = null;
		try {
			dao = new MemberImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid ID.");
		}

		if (id == 0 || id == 1) {
			throw new ServiceException("Invalid operation.");
		}
		
		MemberBean member = dao.select(id);
		MemberBean session = (MemberBean)request.getSession().getAttribute("user");
		if (member != null && session != null) {
			if (member.getId() == session.getId() || session.getId() == 1) {
				request.setAttribute("id", session.getId());
				request.setAttribute("member", member);
			} else {
				throw new ServiceException("Invalid access.");
			}
		} else {
			throw new NotFoundException("Member not found.");
		}
	}

}
