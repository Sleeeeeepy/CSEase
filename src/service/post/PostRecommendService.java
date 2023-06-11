package service.post;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.PostRecommendDAO;
import dao.PostRecommendImpl;
import dto.MemberBean;
import dto.PostRecommendBean;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class PostRecommendService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		PostRecommendDAO dao = null;
		try {
			dao = new PostRecommendImpl(ConnectionProvider.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int postId = 0;
		try {
			postId = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid post id.");
		}
		
		String ip = getClientIP(request);
		if (ip == null || ip.length() == 0) {
			throw new ServiceException("Could not get IP address.");
		}
		
		int userId = 0;
		if (request.getAttribute("user") != null) {
			MemberBean member = (MemberBean)request.getAttribute("user");
			userId = member.getId();
		}
		
		if (dao.select(ip, postId) == null) {
			dao.insert(new PostRecommendBean(0, postId, userId, ip));
		} else {
			throw new ServiceException("You can only recommend a post once.");
		}
	}
	
	private String getClientIP(HttpServletRequest request) {
	    String ip = request.getHeader("X-Forwarded-For");

	    if (ip == null || ip.length() == 0) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0) {
	        ip = request.getRemoteAddr();
	    }
	    
	    return ip;
	}
}
