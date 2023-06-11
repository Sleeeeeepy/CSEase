package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.PostBean;
import service.ListMode;
import service.NotFoundException;
import service.Service;
import service.ServiceException;
import service.post.PostAddService;
import service.post.PostDeleteService;
import service.post.PostFindService;
import service.post.PostListService;
import service.post.PostRecommendService;
import service.post.PreparePostAddService;

/**
 * Servlet implementation class Post
 */
@WebServlet("/Post/*")
public class PostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostController() {
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
		
		try {
			Service service = null;
			if (command.equals("/Post/View")) {
				service = new PostFindService();
				service.execute(request, response);
				RequestDispatcher rd = null;
				if ((boolean)request.getAttribute("private") == true) {
					if ((boolean)request.getAttribute("auth") == false) {
						response.sendRedirect(String.format("%s/Post/Private?id=%s", contextPath, request.getParameter("id")));
					} else {
						rd = request.getRequestDispatcher("/post.jsp");
						rd.forward(request, response);
					}
				} else {
					rd = request.getRequestDispatcher("/post.jsp");
					rd.forward(request, response);
				}
				
			} else if (command.equals("/Post/Write")) {
				service = new PreparePostAddService();
				service.execute(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("/addpost.jsp");
				rd.forward(request, response);
			} else if (command.equals("/Post/WriteAction")) {
				service = new PostAddService();
				service.execute(request, response);
				int postId = (Integer)request.getAttribute("postId");
				RequestDispatcher rd = request.getRequestDispatcher("/Post/View?id=" + postId);
				rd.forward(request, response);
			} else if (command.equals("/Post/Modify")) {
				service = new PostAddService();
				service.execute(request, response);
				String id = (String) request.getAttribute("pid");
				RequestDispatcher rd = request.getRequestDispatcher(String.format("/Post/View?id={%s}", id)); //TODO: ?id={id}
				rd.forward(request, response);
			} else if (command.equals("/Post/Delete")) {
				service = new PostDeleteService();
				service.execute(request, response);
				response.sendRedirect(String.format("%s/", contextPath));
			} else if (command.equals("/Post/Private")) {
				request.setAttribute("id", request.getParameter("id"));
				RequestDispatcher rd = request.getRequestDispatcher("/postauth.jsp");
				rd.forward(request, response);
			} else if (command.equals("/Post/Recommend")) {
				service = new PostRecommendService();
				service.execute(request, response);
				response.sendRedirect(String.format("%s/Post/View?id=%s", contextPath, request.getParameter("id")));
			}
		} catch (ServiceException e) {
			request.setAttribute("code", "401");
			request.setAttribute("message", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		} catch (NotFoundException e) {
			request.setAttribute("code", "404");
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
