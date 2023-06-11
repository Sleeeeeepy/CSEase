package service.member;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.MemberDAO;
import dao.MemberImpl;
import dto.MemberBean;
import service.NotFoundException;
import service.Service;
import service.ServiceException;

public class MemberSearchService implements Service {

		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response) throws NotFoundException, ServiceException {
			MemberDAO mDao = null;

			try {
				mDao = new MemberImpl(ConnectionProvider.getConnection());
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String id = request.getParameter("memberName");

			MemberBean member = mDao.select(id);

			if (member == null) {
				throw new NotFoundException("Such user does not exist.");
			} else if (member.getId() == 0 || member.getId() == 1) {
				throw new ServiceException("Invalid operation.");
			}

			request.setAttribute("member", member);

	}

}
