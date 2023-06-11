<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Private Post</title>
<link rel="stylesheet" href="../asset/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../asset/css/form.css">
</head>
<body>
	<jsp:include page="header.jsp"/>
	<div class="row min-vh-100 justify-content-center align-items-center">
		<div class="login-form">
			<div class="card shadow card-title text-center border-bottom card-body">
				<form action="${pageContext.request.contextPath}/Post/View?id=${id}" method="POST">
					<h1 class="h3 mb-3 fw-bolder">Private Post</h1>
					<div class="mt-2">
						<input type="password" name="password" class="form-control" placeholder="Password"/>
					</div>
					<div class="mt-2 d-grid gap-2">
						<input class="btn btn-primary" type="submit" value="Auth"/>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>