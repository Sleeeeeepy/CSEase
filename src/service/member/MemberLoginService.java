package service.member;

import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.ServiceException;
import dao.MemberDAO;
import dao.MemberImpl;
import dto.MemberBean;
import hash.IHash;
import hash.SHA1;
import dao.ConnectionProvider;
import service.Service;

public class MemberLoginService implements Service {

	private String mode;

	public MemberLoginService(String mode) {
		this.mode = mode;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		System.out.println("MemberLoginService");
		HttpSession session = request.getSession();
		if (mode.equals("login")) {
			IHash hash = new SHA1();
			String id = request.getParameter("id");
			String password = hash.crypto(request.getParameter("password"));
			String saveId = request.getParameter("saveId");
			
			System.out.println(id);
			System.out.println(password);

			if (login(id, password, session)) { // 로그인에 성공했다면
				if (saveId != null && saveId.equals("save")) { // saveId 파라미터가 save라면
					sendCookie("saveId", id, response); // 쿠키를 보낸다
				} else { // save가 아니라면
					String recv = recvCookie("saveId", request);
					if (recv == null || recv.isEmpty()) { // 쿠키를 받고 그것이 null, empty라면
						return; // 리턴
					} else {
						removeCookie("saveId", request, response); // 쿠키를 삭제한다.
					}
				}
			} else {
				System.out.println("로그인 실패");
				throw new ServiceException("login fail.");
			}
		} else if (mode.equals("logout")) {
			logout(session);
		}
	}

	private boolean login(String id, String password, HttpSession session) {
		MemberDAO dao = null;
		try {
			dao = new MemberImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		MemberBean member = dao.select(id);
		if (member != null && member.getPassword().equals(password)) {
			session.setAttribute("user", member); // 세션에 추가
			return true;
		}
		return false;
	}

	private void logout(HttpSession session) {
		session.invalidate(); // 세션에서 삭제
	}

	private void sendCookie(String name, String value, HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		response.addCookie(cookie);
	}

	private String recvCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies) {
			if (c.getName().equals(name)) {
				return c.getValue();
			}
		}
		return "";
	}

	private void removeCookie(String name, HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies) {
			if (c.getName().equals(name)) {
				c.setMaxAge(0);
				response.addCookie(c);
			}
		}
	}

}
