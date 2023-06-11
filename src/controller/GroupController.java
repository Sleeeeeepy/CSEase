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
import service.group.*;

/**
 * Servlet implementation class Group
 */
@MultipartConfig
@WebServlet("/Group/*")
public class GroupController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());

		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");

		try {
			Service service = null;
			if (command.equals("/Group/Profile")) {
				service = new GroupFindService();
				service.execute(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("/groupprofile.jsp");
				rd.forward(request, response);
			} else if (command.equals("/Group/Add")) {
				service = new GroupAddService();
				service.execute(request, response);
			} else if (command.equals("/Group/Delete")) {
				service = new GroupDeleteService();
				service.execute(request, response);
				response.sendRedirect(contextPath+ "/index.jsp");
			} else if (command.equals("/Group/Leave")) {
				service = new GroupLeaveService();
				service.execute(request, response);
				response.sendRedirect(contextPath+ "/index.jsp");
			} else if (command.equals("/Group/Edit")) {
				request.setAttribute("id", request.getParameter("id"));
				RequestDispatcher rd = request.getRequestDispatcher("/editGroup.jsp");
				rd.forward(request, response);
			} else if (command.equals("/Group/EditAction")) {
				service = new GroupEditService();
				service.execute(request, response);
				response.sendRedirect(contextPath + "/Group/Profile?id=" + request.getParameter("id"));
			} else if (command.equals("/Group/AcceptInvite")) {//신청자가 받은 초대를 수용
				service = new GroupInviteAcceptService();
				service.execute(request, response);
				GroupInviteAcceptService temp = (GroupInviteAcceptService) service;
				if (temp.isUserProfilePage() == true) {
					response.sendRedirect(contextPath+ "/Member/Profile?id=" + request.getAttribute("memberId"));
				} else {
					response.sendRedirect(contextPath+ "/Group/Profile?id=" + request.getAttribute("groupId"));
				}

			} else if (command.equals("/Group/Invite")) { //그룹 관리자가 유저를 초대
				service = new GroupInviteService();
				service.execute(request, response);
				response.sendRedirect(contextPath+ "/Group/Profile?id=" + request.getParameter("groupId"));
			} else if (command.equals("/Group/Join")) { //유저가 그룹에 가입 신청
				service = new GroupJoinService();
				service.execute(request, response);
				response.sendRedirect(contextPath+ "/Member/Profile?id=" + request.getParameter("memberId"));
			} else if (command.equals("/Group/Reject")) { //초대 거절, 가입신청 삭제
				service = new GroupInviteDeleteService();
				service.execute(request, response);
				GroupInviteDeleteService temp = (GroupInviteDeleteService) service;
				if (temp.isUserProfilePage() == true) {
					response.sendRedirect(contextPath+ "/Member/Profile?id=" + request.getAttribute("memberId"));
				} else {
					response.sendRedirect(contextPath+ "/Group/Profile?id=" + request.getAttribute("groupId"));
				}
			} else if (command.equals("/Group/Find")) {
				service = new GroupSearchService();
				service.execute(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("/groupfind.jsp");
				rd.forward(request, response);
			}
		} catch (ServiceException e) {
			request.setAttribute("code", "401");
			request.setAttribute("message", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
			e.printStackTrace();
		} catch (NotFoundException e) {
			request.setAttribute("code", "404");
			request.setAttribute("message", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
			e.printStackTrace();
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
