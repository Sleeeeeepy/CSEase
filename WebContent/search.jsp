<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search</title>
<!-- compiled bootstrap5 css -->
<link rel="stylesheet" href="asset/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="asset/css/list.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-sm-3">
				<jsp:include page="sidebar.jsp">
					<jsp:param name="q" value="${param.q}"/>
					<jsp:param name="order" value="${param.order}" />
					<jsp:param name="type" value="${param.type}" />
				</jsp:include>
			</div>
			<div class="col-sm-1"></div>
			<div class="col-sm-8">
				<div class="bg-white" style="min-width: 350px;">
					<a class="text-dark text-decoration-none"> <span class="fs-4">Search Result</span>
					</a>
					<hr>
					<jsp:include page="searchbody.jsp" />
				</div>
				<jsp:include page="pagination.jsp" />
			</div>
		</div>
	</div>
</body>
</html>