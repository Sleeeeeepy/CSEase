package service.member;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.MemberDAO;
import dao.MemberImpl;
import dto.MemberBean;
import hash.IHash;
import hash.SHA1;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class MemberEmailVerifyService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		String userId = request.getParameter("id");
		String key = request.getParameter("key");
		
		if (userId == null || key == null) {
			throw new ServiceException("Invalid id or invalid key");
		}
		
		MemberDAO dao = null;
		try {
			dao = new MemberImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		MemberBean member = dao.select(userId);
		if (member == null) {
			throw new NotFoundException("The user does not exist.");
		}
		
		if (member.isVerify() == true) {
			throw new ServiceException("You have already completed email verification.");
		}
		
		IHash hash = new SHA1();
		String compare = hash.crypto("pA4q8$DI+q0N*eJsM83f8m-t92" + member.getEmail()).trim(); //공백문자 때문에 인증이 안되는 오류 발생
		if (compare.equals(key)) {
			member.setVerify(true);
			request.setAttribute("result", true);
			dao.update(member);
		} else {
			throw new ServiceException("Email verification failed.");
		}
	}

}
