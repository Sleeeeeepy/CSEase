package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CommonErrorController
 */
@WebServlet("/Error/*")
public class CommonErrorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommonErrorController() {
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
		
		if (command.equals("/Error/400")) {
			request.setAttribute("code", "400 Bed Request");
			request.setAttribute("message", "The server cannot process the request due to an apparent client error.");
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		} else if (command.equals("/Error/403")) {
			request.setAttribute("code", "403 Forbidden");
			request.setAttribute("message", "The request contained valid data and was understood by the server, but the server is refusing action.");
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		} else if (command.equals("/Error/404")) {
			request.setAttribute("code", "404 Not Found");
			request.setAttribute("message", "The requested resource could not be found.");
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		} else if (command.equals("/Error/405")) {
			request.setAttribute("code", "405 Method Not Allowed");
			request.setAttribute("message", "A request method is not supported for the requested resource.");
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		} else if (command.equals("/Error/500")) {
			request.setAttribute("code", "500 Internal Server Error");
			request.setAttribute("message", "The server either does not recognize the request method, or it lacks the ability to fulfil the request.");
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		} else if (command.equals("/Error/503")) {
			request.setAttribute("code", "503 Service Unavailable");
			request.setAttribute("message", "The server cannot handle the request.");
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
