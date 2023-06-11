package service.comment;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDAO;
import dao.CommentImpl;
import dao.ConnectionProvider;
import dto.CommentBean;
import hash.IHash;
import hash.SHA1;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class CommentAddService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		int postId = 0;
		try {
			postId = Integer.parseInt(request.getParameter("postId"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid post id.");
		}
		
		String contents = request.getParameter("comment");
		Date now = new Date();
		Timestamp date = new Timestamp(now.getTime());
		String password = request.getParameter("password");
		
		int userId = 0;
		try {
			userId = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid user id.");
		}
		
		if (password != null) {
			IHash hash = new SHA1();
			password = hash.crypto(password);
		} else if (userId == 0) {
			throw new ServiceException("The comment password can not be null.");
		}
		
		CommentDAO dao = null; 
		try {
			dao = new CommentImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			throw new ServiceException("Database error.");
		}
		CommentBean comment = new CommentBean(0, postId, userId, contents, date, password);
		dao.insert(comment);
	}

}
