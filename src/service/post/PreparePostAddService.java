package service.post;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dao.MemberDAO;
import dao.MemberImpl;
import dao.PostDAO;
import dao.PostImpl;
import dao.TagDAO;
import dao.TagImpl;
import dto.GroupBean;
import dto.MemberBean;
import service.Service;

public class PreparePostAddService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		GroupDAO gDao = null;
		try {
			gDao = new GroupImpl(ConnectionProvider.getConnection());
		} catch(SQLException e) {
			e.printStackTrace();
		}
		MemberBean member = (MemberBean)request.getSession().getAttribute("user");
		if (member != null) {
			List<GroupBean> gList = gDao.selectListByUser(member.getId());
			request.setAttribute("gList", gList);
		}
	}
}
