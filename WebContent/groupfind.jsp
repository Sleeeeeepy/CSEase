<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Group Find</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/asset/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/form.css">
</head>
<body>
	<div class="row min-vh-100 justify-content-center align-items-center">
		<div class="login-form">
		<div class="card shadow card-title border-bottom card-body">
			<form method="POST" action="${pageContext.request.contextPath}/Group/Join">
				<h1 class="h3 mb-3 fw-bolder text-center">Find Result</h1>
				<img src="${pageContext.request.contextPath}/Image/Group?id=${group.getId()}" class="avatar img-thumbnail" onerror="this.src='${pageContext.request.contextPath}/asset/jpg/blank_profile.png'" alt="profile">
				<br>
				<p class="text-center"><c:out value="${group.getName()}"/></p>
				<p class="text-center"><c:out value="${group.getExplain()}"/></p>
				<div class="mt-2">
					<input type="hidden" name="groupId" value="${group.getId()}"/>
					<input type="hidden" name="memberId" value="${sessionScope.user.getId()}"/>
				</div>
				<div class="mt-1 d-grid gap-2">
					<input class="btn btn-primary" type="submit" value="Join">
				</div>
			</form>
		</div>
		</div>
	</div>
</body>
</html>