package service.post;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.PostDAO;
import dao.PostImpl;
import dto.MemberBean;
import dto.PostBean;
import hash.IHash;
import hash.SHA1;
import service.NotFoundException;
import service.Service;
import service.ServiceException;


public class PostDeleteService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws NotFoundException, ServiceException {
		PostDAO pDao = null;
		
		try {
			pDao = new PostImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid id.");
		}
		
		PostBean post = pDao.select(id);
		if (post == null) { //포스트가 존재하지 않으면
			throw new NotFoundException("The post with id "+ id +" does not exist.");
		}
		
		if (request.getSession().getAttribute("user") == null) { //세션이 비어있다면
			if (post.getUserId() == 0) { //익명으로 올린 게시물이면 삭제 가능 아니면 불가능
				passwordCheck(post, request, response);
			} else {
				throw new ServiceException("This post was written by another user."); 
			}
		} else { //세션이 비지 않았다면
			MemberBean member = (MemberBean)request.getSession().getAttribute("user"); //cast
			if (post.getUserId() == 0) { //익명으로 올린 게시물이면 삭제 가능 아니면 불가능
				passwordCheck(post, request, response);
			} else if (member.getId() != post.getUserId()) { //본인 게시물이 아니면
				throw new ServiceException("This post was written by another user."); 
			}
		}
		
		pDao.delete(id);
	}
	
	private void passwordCheck(PostBean post, HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		String password = request.getParameter("password"); //비밀번호 검사
		if (password != null) {
			IHash hash = new SHA1();
			password = hash.crypto(password);
			if (!password.equals(post.getPassword())) { //비밀번호가 다르면
				throw new ServiceException("Incorrect password."); //예외발생
			}
		} else {
			throw new ServiceException("Incorrect password.");
		}
	}

}
