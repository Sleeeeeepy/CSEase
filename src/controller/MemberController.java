package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.NotFoundException;
import service.Service;
import service.ServiceException;
import service.member.MemberAddService;
import service.member.MemberDeleteService;
import service.member.MemberEditService;
import service.member.MemberEmailVerifyService;
import service.member.MemberFindService;
import service.member.MemberLoginService;
import service.member.MemberPasswordFindService;
import service.member.MemberSearchService;
import service.member.PrepareMemberEditService;

/**
 * Servlet implementation class Login
 */
@MultipartConfig
@WebServlet("/Member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
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
			if (command.equals("/Member/Login")) {
				RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
				rd.forward(request, response);
			} else if (command.equals("/Member/LoginAction")) {
				service = new MemberLoginService("login");
				service.execute(request, response);
				response.sendRedirect(contextPath + "/index.jsp");
			} else if (command.equals("/Member/LogoutAction")) {
				service = new MemberLoginService("logout");
				service.execute(request, response);
				response.sendRedirect(contextPath + "/index.jsp");
			} else if (command.equals("/Member/Profile")) {
				service = new MemberFindService();
				service.execute(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("/userprofile.jsp");
				rd.forward(request, response);
			} else if (command.equals("/Member/Delete")) {
				service = new MemberDeleteService();
				service.execute(request, response);
				response.sendRedirect(contextPath+ "/index.jsp");
			} else if (command.equals("/Member/Signup")) {
				RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
				rd.forward(request, response);
			} else if (command.equals("/Member/SignupAction")) {
				service = new MemberAddService();
				service.execute(request, response);
			} else if (command.equals("/Member/Verify")){
				service = new MemberEmailVerifyService();
				service.execute(request, response);
				response.sendRedirect(contextPath + "/emailVerify.jsp");
			} else if (command.equals("/Member/PasswordFind")) {
				service = new MemberPasswordFindService();
				service.execute(request, response);
				response.sendRedirect(contextPath + "/passwordFindComplete.jsp");
			} else if (command.equals("/Member/EditAction")) {
				service = new MemberEditService();
				service.execute(request, response);
			} else if (command.equals("/Member/Edit")) {
				service = new PrepareMemberEditService();
				service.execute(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("/editMember.jsp");
				rd.forward(request, response);
			} else if (command.equals("/Member/Find")){
				service = new MemberSearchService();
				service.execute(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("/memberfind.jsp");
				rd.forward(request, response);
			} else {
				System.out.println(command +" Not Exist.");
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
