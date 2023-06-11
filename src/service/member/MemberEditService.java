package service.member;

import java.sql.Connection;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;

import dao.ConnectionProvider;
import dao.MemberDAO;
import dao.MemberImpl;
import dto.MemberBean;
import hash.IHash;
import hash.SHA1;
import service.Service;
import service.ServiceException;

public class MemberEditService implements Service {

	private boolean password_change = false;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		Connection conn = null;
		MemberDAO dao = null;
		IHash hash = new SHA1();

		try {
			conn = ConnectionProvider.getConnection();
			dao = new MemberImpl(conn);
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
		
		Blob photo = null;
		try {
			Part part = request.getPart("photo");
			InputStream ins = part.getInputStream();
			byte byteArr[] = ins.readAllBytes();
			photo = new SerialBlob(byteArr);
		} catch (SQLException e) {
			throw new ServiceException("Failed to fetch image from database.");
		} catch (IOException e) {
			throw new ServiceException("I/O operation failed.");
		} catch (ServletException e) {
			throw new ServiceException("Failed to receive parameters.");
		}
		
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		boolean phonePublic = Boolean.parseBoolean(request.getParameter("phonePublic"));
		boolean emailPublic = Boolean.parseBoolean(request.getParameter("emailPublic"));
		boolean namePublic = Boolean.parseBoolean(request.getParameter("namePublic"));

		MemberBean member = (MemberBean)request.getSession().getAttribute("user");
		
		if (member == null) { //세션이 비어있다면
			throw new ServiceException("This is ilegal operation.");
		} else { //세션이 비지 않았다면
			String password = request.getParameter("password");
			if (!(password == null || password.isEmpty() || password.isBlank())) {
				password_change = true;
				member.setPassword(hash.crypto(password));
			}
			member.setEmailPublic(emailPublic);
			member.setPhonePublic(phonePublic);
			member.setNamePublic(namePublic);
			member.setPhone(phone);
			member.setName(name);
			try {
				if (photo != null && photo.length() != 0) {
					member.setPhoto(photo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (member.getUserId().equals("admin")) { //관리자면 수정가능
				if (validate(request, response)) {
					dao.update(member);
				} else {
					return;
				}
			} else if (member.getId() == id) { //본인이면 수정가능
				if (validate(request, response)) {
					dao.update(member);
				} else {
					return;
				}
			} else { //둘 다 아니면 수정 불가
				throw new ServiceException("This is ilegal operation.");
			}
			
			try {
				response.sendRedirect(request.getContextPath() + "/Member/Profile?id=" + request.getParameter("id"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	private boolean validate(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!password_change) {
				return true;
			}
			
			String password = request.getParameter("password");
			if(password != null && request.getParameter("password").length() <= 7)
			{
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert(\"The password must be at least 8 characters long.\");");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				return false;
			}
			
			String again = request.getParameter("password-again");
			if((password != null && again != null) && !password.equals(again))
			{
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert(\"The password entered again does not match the password.\");");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				return false;
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
