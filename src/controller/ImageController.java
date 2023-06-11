package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dao.MemberDAO;
import dao.MemberImpl;
import dto.GroupBean;
import dto.MemberBean;

/**
 * Servlet implementation class ImageController
 */
@WebServlet("/Image/*")
public class ImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		
		Connection conn;
		try {
			conn = ConnectionProvider.getConnection();
		} catch (SQLException e) {
			return;
		}
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			return;
		}
		
		if (command.equals("/Image/Member")) {
			MemberDAO dao = new MemberImpl(conn);
			ServletOutputStream out = response.getOutputStream();
			MemberBean member = dao.select(id);
			if (member.getPhoto() == null) return;
			try {
				out.write(member.getPhoto().getBytes(1, (int) member.getPhoto().length()));
			} catch (IOException e) {
				throw new ServletException();
			} catch (SQLException e) {
				throw new ServletException();
			} finally {
				out.flush();
				out.close();
			}
		} else if (command.equals("/Image/Group")) {
			GroupDAO dao = new GroupImpl(conn);
			OutputStream out = response.getOutputStream();
			GroupBean group = dao.select(id);
			if (group.getPhoto() == null) return;
			try {
				out.write(group.getPhoto().getBytes(1, (int) group.getPhoto().length()));
			} catch (IOException e) {
				return;
			} catch (SQLException e) {
				return;
			} finally {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
