package service.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import dto.MemberBean;
import service.NotFoundException;
import service.Service;
import service.ServiceException;
public class MemberDeleteService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws NotFoundException, ServiceException {
		MemberDAO dao = null;
		try {
			dao = new MemberImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(request.getParameter("id"));
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new NotFoundException("The user does not exist.");
		}
		
		if (id == 0 || id == 1) {
			throw new ServiceException("Invalid operation.");
		}
		
		MemberBean member = (MemberBean)request.getSession().getAttribute("user");
		
		if (member == null) { //세션이 비어있다면
			throw new ServiceException("This is ilegal operation.");
		} else { //세션이 비지 않았다면
			if (member.getId() == 1) { //관리자면 삭제가능
				dao.delete(id);
				return;
			}
			
			if (validate(member.getUserId(), request, response)) {
				if (member.getId() == id) { //본인이면 삭제가능
					dao.delete(id);
					return;
				}
				return;
			}
		}	
	}

	private boolean validate(String username, HttpServletRequest request, HttpServletResponse response) {
		String _username = request.getParameter("username");
		if(username == null || _username == null || !username.equals(_username)) {
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.println("<script>");
			out.println("alert(\"Membership withdrawal has been cancelled.\");");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return false;
		}
		return true;
	}
}
