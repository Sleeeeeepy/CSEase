<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign up</title>
<link rel="stylesheet" href="../asset/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../asset/css/form.css">
</head>
<body>
	<jsp:include page="header.jsp"/>
	<div class="row min-vh-100 justify-content-center align-items-center">
		<div class="login-form">
			<div class="card shadow card-title border-bottom card-body">
				<form method="POST" action="${pageContext.request.contextPath}/Member/SignupAction" enctype="multipart/form-data">
					<h1 class="h3 mb-3 fw-bolder text-center">Create an account</h1>
					<br>
					<div class="mt-2">
						<input type="text" name="userId" class="form-control mt-1" placeholder="ID" data-bs-toggle="tooltip" data-bs-placement="bottom" title="" required>
						<input type="text" name="nickname" class="form-control mt-1" placeholder="Nickname" data-bs-toggle="tooltip" data-bs-placement="bottom" title="" required>
						<input type="password" name="password" class="form-control mt-1" placeholder="Password" data-bs-toggle="tooltip" data-bs-placement="bottom" title="" required>
						<input type="password" name="password-again" class="form-control mt-1" placeholder="Password Again" data-bs-toggle="tooltip" data-bs-placement="bottom" title="" required>
						<input type="text" name="name" class="form-control mt-1" placeholder="Name" required>
						<input type="email" name="email" class="form-control mt-1" placeholder="abc@host.com" required>
						<input type="tel" name="phone" class="form-control mt-1" placeholder="000-0000-0000" required> 
						<input type="file" name="photo" class="form-control mt-1" name="photo" accept="image/png, image/jpeg, image/jpg" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Upload your profile picture">
						<div>
							<label class="mt-2"> Keep my name public </label>
							<br> 
							<input type="radio" name="namePublic" value="true"> Yes 
							<input type="radio" name="namePublic" value="false" checked> No
						</div>
						<div>
							<label class="mt-2"> Keep my email address public </label><br>
							<input type="radio" name="emailPublic" value="true"> Yes
							<input type="radio" name="emailPublic" value="false" checked> No
						</div>
						<div>
							<label class="mt-2"> Keep my phone number public </label><br>
							<input type="radio" name="phonePublic" value="true"> Yes
							<input type="radio" name="phonePublic" value="false" checked> No
						</div>
						<br>
					</div>
					<div class="mt-1 d-grid gap-2">
						<input class="btn btn-primary" type="submit" value="Sign up!">
					</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"/>
	<script>
		document.addEventListener("DOMContentLoaded", function(event) {
			var tooltipTriggerList = [].slice.call(document
					.querySelectorAll('[data-bs-toggle="tooltip"]'))
			var tooltipList = tooltipTriggerList
					.map(function(tooltipTriggerEl) {
						return new bootstrap.Tooltip(tooltipTriggerEl)
					});
		});
	</script>
	<script src="../asset/bootstrap/js/bootstrap.min.js"></script>
	<script src="../asset/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>