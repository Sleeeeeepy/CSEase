package service.group;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dto.GroupBean;
import dto.MemberBean;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class GroupListService implements Service {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotFoundException {
		Connection conn = null;
		GroupDAO gDao = null;
		try {
			conn = ConnectionProvider.getConnection();
			gDao = new GroupImpl(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		MemberBean member = null;
		if (request.getSession().getAttribute("user") != null) {
			member = (MemberBean)request.getSession().getAttribute("user");
		} else {
			throw new ServiceException("You must log in to use this service.");
		}
		
		List<GroupBean> groups = gDao.selectListByUser(member.getId());
		request.setAttribute("gList", groups);
	}
}
