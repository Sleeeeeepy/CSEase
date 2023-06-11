package service.group;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dao.MemberDAO;
import dao.MemberImpl;
import dto.GroupBean;
import dto.MemberBean;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class GroupSearchService implements Service {

		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response) throws NotFoundException, ServiceException {
			Connection conn = null;
			GroupDAO gDao = null;

			try {
				conn = ConnectionProvider.getConnection();
				gDao = new GroupImpl(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String id = request.getParameter("groupName");

			GroupBean group = gDao.select(id);

			if (group == null) {
				throw new NotFoundException("Such group does not exist.");
			} 

			request.setAttribute("group", group);

	}

}
