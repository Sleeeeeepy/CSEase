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
import service.comment.CommentAddService;
import service.comment.CommentDeleteService;
import service.comment.CommentEditService;

/**
 * Servlet implementation class CommentController
 */
@WebServlet("/Comment/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		Service service = null;
		try {
			if (command.equals("/Comment/Write")) {
				service = new CommentAddService();
				service.execute(request, response);
				String id = request.getParameter("postId");
				response.sendRedirect(String.format("%s/Post/View?id=%s", contextPath, id));
			} else if (command.equals("/Comment/Delete")) {
				service = new CommentDeleteService();
				service.execute(request, response);
				String id = request.getParameter("postId");
				response.sendRedirect(String.format("%s/Post/View?id=%s", contextPath, id));
			} else if (command.equals("/Comment/Edit")) {
				service = new CommentEditService();
				service.execute(request, response);
				String id = request.getParameter("postId");
				response.sendRedirect(String.format("%s/Post/View?id=%s", contextPath, id));
			}
		} catch (ServiceException e) {
			request.setAttribute("code", "401");
			request.setAttribute("message", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		} catch (NotFoundException e) {
			request.setAttribute("code", "401");
			request.setAttribute("message", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
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
