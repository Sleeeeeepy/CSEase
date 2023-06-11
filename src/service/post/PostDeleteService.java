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
		if (post == null) { //����Ʈ�� �������� ������
			throw new NotFoundException("The post with id "+ id +" does not exist.");
		}
		
		if (request.getSession().getAttribute("user") == null) { //������ ����ִٸ�
			if (post.getUserId() == 0) { //�͸����� �ø� �Խù��̸� ���� ���� �ƴϸ� �Ұ���
				passwordCheck(post, request, response);
			} else {
				throw new ServiceException("This post was written by another user."); 
			}
		} else { //������ ���� �ʾҴٸ�
			MemberBean member = (MemberBean)request.getSession().getAttribute("user"); //cast
			if (post.getUserId() == 0) { //�͸����� �ø� �Խù��̸� ���� ���� �ƴϸ� �Ұ���
				passwordCheck(post, request, response);
			} else if (member.getId() != post.getUserId()) { //���� �Խù��� �ƴϸ�
				throw new ServiceException("This post was written by another user."); 
			}
		}
		
		pDao.delete(id);
	}
	
	private void passwordCheck(PostBean post, HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		String password = request.getParameter("password"); //��й�ȣ �˻�
		if (password != null) {
			IHash hash = new SHA1();
			password = hash.crypto(password);
			if (!password.equals(post.getPassword())) { //��й�ȣ�� �ٸ���
				throw new ServiceException("Incorrect password."); //���ܹ߻�
			}
		} else {
			throw new ServiceException("Incorrect password.");
		}
	}

}
