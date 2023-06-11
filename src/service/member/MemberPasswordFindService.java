package service.member;

import java.security.SecureRandom;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.MemberDAO;
import dao.MemberImpl;
import dto.MemberBean;
import hash.IHash;
import hash.SHA1;
import mail.Gmail;
import mail.IMail;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class MemberPasswordFindService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		String userId = request.getParameter("userId");
		String email = request.getParameter("email");
		
		MemberDAO dao = null;
		try {
			dao = new MemberImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		IHash hash = new SHA1();
		String password = generatePassword(8);
		String hashedPw = hash.crypto(password);
		MemberBean member = dao.select(userId);
		if (member == null) {
			throw new ServiceException("Database Error.");
		}
		member.setPassword(hashedPw);
		
		if (!member.getEmail().equals(email)) {
			throw new ServiceException("Mail does not match.");
		}
		
		if (member.isVerify() == false) {
			throw new ServiceException("Your mail is not verified.");
		}
		
		String title = "[CSease]Your password has been reset.";
		String address = request.getScheme() + "://" + request.getServerName() +":" + request.getServerPort() + request.getContextPath();
		String contents = String.format("<p>Your password has been changed to <br>%s</p>Login <a href=\"%s\">here</a>", password, address);
		IMail mail = new Gmail();
		try {
			mail.send(member.getEmail(), title, contents);
		} catch (AddressException e) {
			e.printStackTrace();
			throw new ServiceException("Mail transmission failed.");
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new ServiceException("Mail transmission failed.");
		}
		
		dao.update(member);
	}
	
	private String generatePassword(int length) {
		if (length < 6) throw new IllegalArgumentException("too short password.");
		SecureRandom rand = new SecureRandom();
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(rand.nextInt(chars.length())));
		}
		return sb.toString();
	}

}
