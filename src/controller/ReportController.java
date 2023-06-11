package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.NotFoundException;
import service.Service;
import service.ServiceException;
import service.report.*;

/**
 * Servlet implementation class ReportController
 */
@WebServlet("/Report/*")
public class ReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());

		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");

		try {
			Service service = null;
			if (command.equals("/Report/View")) {
				service = new ReportListService(ReportStatus.NOT_CONFIRMED);
				service.execute(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("/admin.jsp");
				rd.forward(request, response);
			} else if (command.equals("/Report/ReportPost")) {
				service = new ReportAddService();
				service.execute(request, response);
				int postId = 0;
				try {
					postId = Integer.parseInt(request.getParameter("postId"));
				} catch (NumberFormatException e) {
					throw new ServiceException("Invalid post id.");
				}
				response.sendRedirect(contextPath + "/Post/View?id=" + postId);
			} else if (command.equals("/Report/Ignore")) {
				service = new ReportDeleteService();
				service.execute(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("/Report/View");
				rd.forward(request, response);
			} else if (command.equals("/Report/Delete")) {
				service = new ReportProcessService();
				service.execute(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("/Report/View");
				rd.forward(request, response);
				
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		} catch (NotFoundException e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
