<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Report Management</title>
<!-- compiled bootstrap 5 css -->
<link rel="stylesheet" href="../asset/bootstrap/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="header.jsp"/>
	<div class="container">
		<h3>Report Management</h3>
		<div style="padding-top: 50px">
			<table class="table table-hover">
				<tr>
					<th>ID</th>
					<th>UserId</th>
					<th>PostId</th>
					<th>Explain</th>
					<th></th>
				</tr>
				<c:forEach var="report" items="${rList}">
					<tr>
						<td>${report.getId()}</td>
						<td>${report.getUserId()}</td>
						<td>${report.getPostId()}</td>
						<td><c:out value="${report.getReason()}"/></td>
						<td>
						<a href="${pageContext.request.contextPath}/Post/View?id=${report.getPostId()}" class="btn btn-primary" role="button">View</a>
						<a href="#" onclick="deleteConfirm(${report.getId()})" class="btn btn-danger" role="button">Delete</a>
						<a href="#" onclick="ignoreConfirm(${report.getId()})" class="btn btn-secondary" role="button">Ignore</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<jsp:include page="pagination.jsp"/>
		</div>
	</div>

	<script>
		function deleteConfirm(id) {
			if (confirm("Do you want to remove the post?") == true)
				location.href = "/Csease/Report/Delete?id=" + id;
			else
				return;
		}

		function ignoreConfirm(id) {
			if (confirm("Do you want to ignore the report?") == true)
				location.href = "/Csease/Report/Ignore?id=" + id;
			else
				return;
		}
	</script>
	<!-- compiled bootstrap 5 javascript -->
	<script src="../asset/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>