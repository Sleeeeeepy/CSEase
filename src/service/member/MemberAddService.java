package service.member;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import mail.Gmail;
import mail.IMail;
import service.Service;
import service.ServiceException;

public class MemberAddService implements Service {

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
		
		String nickname = request.getParameter("nickname");
		
		Blob photo = null;
		try {
			Part part = request.getPart("photo");
			InputStream ins = part.getInputStream();
			byte byteArr[] = ins.readAllBytes();
			photo = new SerialBlob(byteArr);
		} catch (SQLException e) {
			throw new ServiceException("Failed to insert image into database.");
		} catch (IOException e) {
			throw new ServiceException("I/O operation failed.");
		} catch (ServletException e) {
			throw new ServiceException("Failed to receive parameters.");
		}
		
		String userId = request.getParameter("userId");
		String email = request.getParameter("email");
		String password = hash.crypto(request.getParameter("password"));
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		boolean phonePublic = Boolean.parseBoolean(request.getParameter("phonePublic"));
		boolean emailPublic = Boolean.parseBoolean(request.getParameter("emailPublic"));
		boolean namePublic = Boolean.parseBoolean(request.getParameter("namePublic"));

		if (dao.isExist(userId, email, nickname)) {
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.println("<script>");
			out.println("alert(\"E-mail, ID, or nickname already exists.\");");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return;
		}
		
		String key = hash.crypto("pA4q8$DI+q0N*eJsM83f8m-t92" + email);

		String title = "[CSease]Please complete the verification.";
		String address = request.getScheme() + "://" + request.getServerName() +":" + request.getServerPort() + request.getContextPath() + 
				"/Member/Verify?id=" + userId + "&key=" + key;
		String contents = String.format("<p>Click <a href=\"%s\">here</a> to complete email verification.</p><p>URL:%s</p>", address, address);
		IMail mail = new Gmail();
		
		if (validate(request, response)) {
			dao.insert(new MemberBean(0, nickname, photo, userId, email, password, phone, name, phonePublic, emailPublic, namePublic, false));
		} else {
			return;
		}
		
		try {
			mail.send(email, title, contents);
		} catch (AddressException e) {
			e.printStackTrace();
			throw new ServiceException("Mail transmission failed.");
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new ServiceException("Mail transmission failed.");
		}
		
		try {
			response.sendRedirect(request.getContextPath() + "/signupComplete.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean validate(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userId = request.getParameter("userId");
			if(userId == null || userId.length() < 5) {
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert(\"The id must be at least five characters long.\");");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				return false;
			}
			
			String nickname = request.getParameter("nickname");
			if(nickname == null || nickname.length() < 5) {
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert(\"The nickname must be at least five characters long.\");");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				return false;
			}
			
			String password = request.getParameter("password");
			if(password == null || request.getParameter("password").length() <= 7)
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
			if(again == null || !password.equals(again))
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
