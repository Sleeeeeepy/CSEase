<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>View Profile</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/asset/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/list.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-sm-10">
				<h1>
					<c:out value="${group.getName()}" />
				</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-3">
				<div class="text-center">
					<img src="${pageContext.request.contextPath}/Image/Group?id=${group.getId()}" class="avatar img-thumbnail" onerror="this.src='${pageContext.request.contextPath}/asset/jpg/blank_profile.png';" alt="profile">
				</div>
				<br>
				<div class="card">
					<div class="card-header">Explain</div>
					<ul class="list-group list-group-flush">
						<li class="list-group-item"><c:out value="${group.getExplain()}" /></li>
					</ul>
				</div>
				<div class="mt-3"></div>
				<div class="card">
					<div class="card-header">Activity</div>
					<ul class="list-group list-group-flush">
						<li class="list-group-item"><span class="float-start">Post</span><span class="float-end">${postNo}</span></li>
					</ul>
				</div>
				<c:if test="${belong eq true}">
					<div class="mt-3"></div>
					<div class="card">
						<div class="card-header">User</div>
						<ul class="list-group list-group-flush">
						<c:if test="${group.getAdminId() eq sessionScope.user.getId()}">
							<li class="list-group-item"><a class="text-decoration-none" href="${pageContext.request.contextPath}/Group/Edit?id=${group.getId()}">Edit profile</a></li>
							<li class="list-group-item"><a class="text-decoration-none" href="#" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#inviteUserModal">Invite user</a></li>
							<li class="list-group-item"><a class="text-danger text-decoration-none" href="#" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#groupDeleteModal">Delete group</a></li>
						</c:if>
						
							<c:if test="${group.getAdminId() ne sessionScope.user.getId()}">
								<li class="list-group-item"><a class="text-decoration-none" href="#" onclick="leave(${group.getId()})">Leave this group</a></li>
							</c:if>
						
						</ul>
					</div>
					</c:if>
				
			</div>
			<div class="col-sm-9 mt-3">
				<nav>
					<div class="nav nav-tabs" id="nav-tab" role="tablist">
						<button class="nav-link active" id="nav-post-tab" data-bs-toggle="tab" data-bs-target="#nav-post" type="button" role="tab" aria-controls="nav-post" aria-selected="true">Post</button>
						<c:if test="${group.getAdminId() eq sessionScope.user.getId()}">
						<button class="nav-link" id="nav-group-invite-tab" data-bs-toggle="tab" data-bs-target="#nav-group-invite" type="button" role="tab" aria-controls="nav-invite-group" aria-selected="false">User Join</button>
						</c:if>
					</div>
				</nav>
				<div class="tab-content">
					<div class="tab-pane fade show active" id="nav-post" role="tabpanel" aria-labelledby="nav-post-tab"><jsp:include page="searchbody.jsp" /><jsp:include page="pagination.jsp" /></div>
					<c:if test="${group.getAdminId() eq sessionScope.user.getId()}">
						<div class="tab-pane fade" id="nav-group-invite" role="tabpanel">
							<table class="table table-hover">
								<tr>
									<th>Nickname</th>
									<th>User ID</th>
									<th></th>
								</tr>
								<c:forEach var="invite" items="${iList}" varStatus="i">
									<tr>
										<td>${miList.get(i.index).getNickname()}</td>
										<td>${miList.get(i.index).getUserId()}
										<td>
										<c:if test="${invite.isInvite() eq false }">
											<a href="#" onclick="accept(${invite.getId()})" class="btn btn-success" role="button">Accept</a>
										</c:if> <a href="#" onclick="ignore(${invite.getId()})" class="btn btn-secondary" role="button">Delete</a></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="modal/groupInviteModal.jsp" />
	<jsp:include page="modal/deleteGroupModal.jsp" />
	<jsp:include page="footer.jsp"/>
	<script>
		var tabEl = document.querySelector('button[data-bs-toggle="tab"]')
		tabEl.addEventListener('shown.bs.tab', function(event) {
			event.target // newly activated tab
			event.relatedTarget // previous active tab
		})
		
		function accept(id) {
			if (confirm("Do you want to accept the user?") == true)
				location.href = "/Csease/Group/AcceptInvite?id=" + id;
			else
				return;
		}
		
		function ignore(id) {
			if (confirm("Do you want to remove the user join request?") == true)
				location.href = "/Csease/Group/Reject?id=" + id;
			else
				return;
		}
		
		function leave(id) {
			if (confirm("Do you want to leave this group?") == true)
				location.href = "/Csease/Group/Leave?id=" + id;
			else
				return;
		}
	</script>
	<script src="${pageContext.request.contextPath}/asset/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/asset/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>