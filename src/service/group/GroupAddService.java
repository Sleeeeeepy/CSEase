package service.group;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;

import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dao.MemberDAO;
import dao.MemberImpl;
import dto.GroupBean;
import dto.MemberBean;
import service.Service;
import service.ServiceException;

public class GroupAddService implements Service {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		Connection conn = null;
		GroupDAO dao = null;
		try {
			conn = ConnectionProvider.getConnection();
			dao = new GroupImpl(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Blob photo = null;
		try {
			Part part = request.getPart("photo");
			InputStream ins = part.getInputStream();
			byte barr[] = ins.readAllBytes();
			photo = new SerialBlob(barr);
		} catch (SQLException e) {
			throw new ServiceException("Failed to insert image into database.");
		} catch (IOException e) {
			throw new ServiceException("I/O operation failed.");
		} catch (ServletException e) {
			throw new ServiceException("Failed to receive parameters.");
		}

		String name = request.getParameter("name");
		MemberBean admin = (MemberBean) request.getSession().getAttribute("user");
		if (admin == null) {
			throw new ServiceException("You must log in to make a group.");
		}
		try {
			if (dao.select(name) == null) {

				if (request.getParameter("name") == null) {
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert(\"group name has not been entered.\");");
					out.println("history.back();");
					out.println("</script>");
					out.close();
					return;
				}
			} else {
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert(\"This group already exists.\");");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				return;
			}
		} catch (IOException e) {
			throw new ServiceException("IO Error");
		}
		String explain = request.getParameter("explain");
		dao.insert(new GroupBean(0, name, photo, admin.getId(), explain));
		RequestDispatcher rd = request.getRequestDispatcher("/groupmakeComplete.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
