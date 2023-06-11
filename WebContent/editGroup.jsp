<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Profile</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/asset/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/form.css">
</head>
<body>
	<jsp:include page="header.jsp"/>
	<div class="row min-vh-100 justify-content-center align-items-center">
		<div class="login-form">
			<div class="card shadow card-title border-bottom card-body">
				<form method="POST" action="${pageContext.request.contextPath}/Group/EditAction" enctype="multipart/form-data">
					<h1 class="h3 mb-3 fw-bolder text-center">Edit Profile</h1>
					<input type="hidden" name="id" value="${id}"/>
					<br>
					<div class="mt-2">
						<input type="file" name="photo" class="form-control mt-1" name="photo" accept="image/png, image/jpeg, image/jpg" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Upload group profile picture">
						<input type="text" name="explain" class="form-control mt-1" placeholder="Group Explain" required/>
						<div class="mt-1 d-grid gap-2">
							<input class="btn btn-primary" type="submit" value="Edit!">
						</div>
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
	<script src="${pageContext.request.contextPath}/asset/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/asset/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>