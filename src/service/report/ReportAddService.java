package service.report;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import dto.*;
import service.Service;
import service.ServiceException;

public class ReportAddService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		
		ReportDAO dao = null;
		try {
			dao = new ReportImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int postId = 0;
		try {
			postId = Integer.parseInt(request.getParameter("postId"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid post id.");
		}

		int userId = 0;
		try {
			userId = Integer.parseInt(request.getParameter("userId"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid user id.");
		}
		
		Timestamp date = new Timestamp(new Date().getTime());
		String reason = request.getParameter("explain");
		boolean confirm = false;
		
		if (dao.selectByPostId(postId) == null) {
			dao.insert(new PostReport(0, date, postId, userId, reason, confirm));
		} else {
			throw new ServiceException("This post has already been reported.");	
		}
		
	}

}
