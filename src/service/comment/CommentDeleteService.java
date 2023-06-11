package service.comment;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.CommentDAO;
import dao.CommentImpl;
import dto.CommentBean;
import dto.MemberBean;
import dto.PostBean;
import hash.IHash;
import hash.SHA1;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class CommentDeleteService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		CommentDAO cDao = null;
		try {
			cDao = new CommentImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("id=" + request.getParameter("commentId"));
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("commentId"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid id.");
		}
		
		CommentBean comment = cDao.select(id);
		if (comment == null) { //����� �������� ������
			throw new NotFoundException("The comment with id "+ id +" does not exist.");
		}
		
		if (request.getSession().getAttribute("user") == null) { //������ ����ִٸ�
			if (comment.getUserId() == 0) { //�͸����� �ø� ����̸�
				passwordCheck(comment, request, response);
			} else {
				throw new ServiceException("This comment was written by another user."); 
			}
		} else { //������ ���� �ʾҴٸ�
			MemberBean member = (MemberBean)request.getSession().getAttribute("user"); //cast
			if (comment.getUserId() == 0) { //�͸����� �ø� ����̸�
				passwordCheck(comment, request, response);
			} else if (member.getId() != comment.getUserId()) { //���� ����� �ƴϸ�
				throw new ServiceException("This comment was written by another user."); 
			}
		}
		
		cDao.delete(id);
	}
	
	private void passwordCheck(CommentBean comment, HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		String password = request.getParameter("password"); //��й�ȣ �˻�
		if (password != null) {
			IHash hash = new SHA1();
			password = hash.crypto(password);
			if (!password.equals(comment.getPassword())) { //��й�ȣ�� �ٸ���
				throw new ServiceException("Incorrect password."); //���ܹ߻�
			}
		} else {
			throw new ServiceException("Incorrect password.");
		}
	}

}
