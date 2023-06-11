<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CSEase</title>
<link rel="stylesheet" href="asset/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="asset/css/index.style.css">
</head>
<body>
	<div class="overlay"></div>
	<video playsinline="playsinline" autoplay="autoplay" loop="loop" muted="muted" class="video">
		<source src="asset/video/index.mp4" type="video/mp4">
	</video>
	<div class="main-header w-100">
		<jsp:include page="header.jsp" />
	</div>
	<div class="d-flex align-items-center index-form">
		<div class="cover-container d-flex h-100 justify-content-center p-3 mx-auto flex-column main-bg">
			<main class="px-3 text-center my-auto">
				<h1 class="text-white">Share Your Text</h1>
				<p class="lead text-white">Search</p>
				<form method="GET" action="Search">
					<input type="search" name="q" class="form-control form-text" id="q" placeholder="Search..." style="min-width: 400px; max-width: 500px" onkeyup="search()"/>
					<input type="hidden" name="type" value="TITLE"/>
					<input type="hidden" name="order" value="RECOMMEND"/>
				</form>
			</main>
		</div>
	</div>
	<script src="asset/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>