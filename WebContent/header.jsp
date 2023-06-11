<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="py-2 bg-light border-bottom">
	<div class="container d-flex flex-wrap">
		<ul class="nav me-auto">
			<li class="nav-item"><a href="${pageContext.request.contextPath}/index.jsp" class="nav-link link-dark px-2 active">Home</a></li>
		</ul>
		<ul class="nav">
			<c:if test="${empty sessionScope.user}">
				<li class="nav-item"><a href="${pageContext.request.contextPath}/Member/Login" class="nav-link link-dark px-2">Login</a></li>
				<li class="nav-item"><a href="${pageContext.request.contextPath}/Member/Signup" class="nav-link link-dark px-2">Sign up</a></li>
			</c:if>
			<li class="nav-item"><a href="${pageContext.request.contextPath}/Post/Write" class="nav-link link-dark px-2">Post</a></li>
			<c:if test="${not empty sessionScope.user}">
				<li class="nav-item"><a href="${pageContext.request.contextPath}/Member/Profile?id=${sessionScope.user.getId()}" class="nav-link link-dark px-2">My Profile</a></li>
				<li class="nav-item"><a href="${pageContext.request.contextPath}/Member/LogoutAction" class="nav-link link-dark px-2">Log out</a></li>
			</c:if>
			<c:if test="${sessionScope.user.getId() eq 1 }">
			<li class="nav-item"><a href="${pageContext.request.contextPath}/Report/View" class="nav-link link-dark px-2">Report</a></li>
			</c:if>
		</ul>
	</div>
</nav>
<header class="py-3 mb-4 border-bottom bg-white">
	<div class="container d-flex flex-wrap justify-content-center">
		<a href="${pageContext.request.contextPath}/index.jsp" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none"> <!--<svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"/></svg>--> <span class="fs-4">CSEase</span>
		</a>
	</div>
</header>
