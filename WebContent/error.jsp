<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
<title>Error</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/asset/bootstrap/css/bootstrap.min.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="d-flex min-vh-100 align-items-center justify-content-center">
		<div class="col-md-6">
			<div class="h-100 w-100 p-5 text-white bg-danger rounded-3">
				<h2 class="text-center">
					<c:out value="${code}" />
				</h2>
				<p>
					<c:out value="${message}" />
				</p>
				<div class="mt-5 text-center">
					<button class="btn btn-outline-light" type="button" onclick="javascript:history.back()">Back</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
	<script src="${pageContext.request.contextPath}/asset/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>