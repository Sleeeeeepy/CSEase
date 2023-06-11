<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
<title>Group</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/asset/bootstrap/css/bootstrap.min.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="d-flex min-vh-100 align-items-center justify-content-center">
		<div class="col-md-6">
			<div class="h-100 w-100 p-5 text-white bg-success rounded-3">
				<h2 class="text-center"><c:out value="you make group successfully"/></h2>
				<p class="text-center"><c:out value="Invite other users to the group and share posts among users in the group."/></p>
				<div class="mt-5 text-center">
					<a class="btn btn-outline-light" type="button" href="${pageContext.request.contextPath}/Member/Profile?id=${sessionScope.user.getId()}">Go to my profile</a>
				</div>
			</div>
		</div>
	</div>
		<script src="${pageContext.request.contextPath}/asset/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>