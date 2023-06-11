<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>New Paste</title>
<!-- compiled bootstrap5 css -->
<link rel="stylesheet" href="../asset/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../asset/tagify/tagify.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-lg-10 col-xl-8 offset-lg-1 offset-xl-2">
				<form action="WriteAction" method="POST" onsubmit="return Submit()">
					<label class="mb-1 mt-1">Title <span class="text-danger">*</span></label> <input name="title" type="text" class="form-control" required>
					<label class="mb-1 mt-1">Contents <span class="text-danger">*</span></label>
					<textarea id="textarea" name="contents" class="form-control" rows="15" required></textarea>
					<label class="mb-1 mt-1">Explain <span class="text-danger">*</span></label>
					<textarea name="explain" class="form-control" rows="3"></textarea>
					<label class="mb-1 mt-1">Expire <span class="text-danger">*</span></label> <select name="expire" class="form-select">
						<option value="forever">Forever</option>
						<option value="86400">1 day</option>
						<option value="43200">12 hour</option>
						<option value="21600">6 hour</option>
						<option value="3600">1 hour</option>
						<option value="1800">30 minute</option>
						<option value="300">5 minute</option>
						<option value="burn">Immediately</option>
					</select>
					<c:if test="${empty sessionScope.user}">
						<label class="mb-1 mt-1">Password <span class="text-danger">*</span></label>
						<input name="password" type="password" class="form-control" required>
					</c:if>
					<label class="mb-1 mt-1">Text Type <span class="text-danger">*</span></label> <select name="type" class="form-select">
						<option value="plain-text" selected>Plain Text</option>
						<option value="pl">Programming Language</option>
					</select> <label class="mb-1 mt-1">Open Range <span class="text-danger">*</span></label> <select id="openRange" name="openRange" class="form-select">
						<option value="public" selected>Public</option>
						<option value="private">Private</option>
						<c:if test="${not empty sessionScope.user}">
							<option value="group">Group Only</option>
							<!-- <option disabled>──────────</option> -->
						</c:if>
					</select> <label class="mb-1 mt-1">Tags</label> <input type="text" id="tags" name="tags" class="form-control" />
					<c:if test="${not empty sessionScope.user}">
						<label class="mb-1 mt-1">Group</label>
						<select id="group-select" name="group" class="form-select">
							<option value="null">None</option>
							<c:forEach var="g" items="${gList}">
								<option value="${g.getId()}"><c:out value="${g.getName()}" /></option>
							</c:forEach>
						</select>
					</c:if>
					<div class="float-end mt-2">
						<input type="submit" class="btn btn-primary" value="Write">
					</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"/>
	<script src="../asset/tagify/tagify.min.js"></script>
		<!-- compiled bootstrap5 js -->
	<script src="../asset/bootstrap/js/bootstrap.min.js"></script>
	<script>
	var inputElement = document.getElementById("tags");
	var tagify = new Tagify(inputElement);
	document.getElementById('textarea').addEventListener('keydown', function(event) {
		  if (event.key == 'Tab' ) {
		    event.preventDefault();
		    var start = this.selectionStart;
		    var end = this.selectionEnd;

		    this.value = this.value.substring(0, start) +
		      "\t" + this.value.substring(end);

		    this.selectionStart = this.selectionEnd = start + 1;
		  }
		});

	var formSubmitting = false;
	var Submit = function() {
		if (document.getElementById("group-select") != null && document.getElementById("openRange").value == "group" && document.getElementById("group-select").value == "null") {
			alert("Please select a group.");
			return false;
		}
		formSubmitting = true;
		return true;
	};
	
	window.onbeforeunload = function (e) {
        if (formSubmitting != true) {
            return "Would you like to leave? if you leave, your post will not be saved.";
        }
    };
	</script>
</body>
</html>