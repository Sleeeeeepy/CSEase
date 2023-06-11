<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
<title>Verify</title>
<link rel="stylesheet" href="asset/bootstrap/css/bootstrap.min.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="d-flex min-vh-100 align-items-center justify-content-center">
		<div class="col-md-6">
			<div class="h-100 w-100 p-5 text-white rounded-3 bg-success">
				<h2 class="text-center">
					<c:out value="Verification" />
				</h2>
				<p class="text-center">
					<c:out value="Email verification success. Registration is complete." />
				</p>
				<div class="mt-5 text-center">
					<a class="btn btn-outline-light" type="button" href="index.jsp">Go to main page</a>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
	<script src="asset/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>