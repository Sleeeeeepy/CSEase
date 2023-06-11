<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign in</title>
<link rel="stylesheet" href="../asset/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../asset/css/form.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="row min-vh-100 justify-content-center align-items-center">
		<div class="login-form">
			<div class="card shadow card-title text-center border-bottom card-body">
				<form action="${pageContext.request.contextPath}/Member/LoginAction" method="POST">
					<h1 class="h3 mb-3 fw-bolder">Sign in</h1>
					<div class="mt-2">
						<c:if test="${empty cookie.saveId.value}">
							<input type="text" name="id" id="id" class="form-control" placeholder="User">
						</c:if>
						<c:if test="${not empty cookie.saveId.value}">
							<input type="text" name="id" id="id" class="form-control" placeholder="User" value="<c:out value="${cookie.saveId.value}"/>">
						</c:if>
					</div>
					<div class="mt-2">
						<input type="password" name="password" id="password" class="form-control" placeholder="Password" />
					</div>
					<div class="mt-2">
						<c:if test="${empty cookie.saveId.value}">
							<input type="checkbox" name="saveId" value="save" /> Remember me
						</c:if>
						<c:if test="${not empty cookie.saveId.value}">
							<input type="checkbox" name="saveId" value="save" checked /> Remember me
						</c:if>
					</div>
					<div class="mt-2 d-grid gap-2">
						<input class="btn btn-primary" type="submit" value="Login" />
					</div>
					<div class="mt-2">
						<a href="#" class="fs-6 fst-italic" data-bs-toggle="modal" data-bs-target="#passwordFindModal">forgot password?</a>
					</div>
					<div class="mt-0">
						<a class="fs-6" href="${pageContext.request.contextPath}/Member/Signup">Create new account</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="modal/passwordFindModal.jsp" />
	<jsp:include page="footer.jsp"/>
	<script src="../asset/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="../asset/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>