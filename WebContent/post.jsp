<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://csease.com/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${post.getTitle()}" /></title>
<!-- compiled bootstrap 5 css -->
<link rel="stylesheet" href="../asset/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../asset/css/post.css">
<!-- highlight.js style -->
<link rel="stylesheet" href="../asset/highlight/styles/vs2015.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-lg-10 col-xl-8 offset-lg-1 offset-xl-2">
				<div class="title">
					<h1 class="text-center fw-light">
						<c:out value="${post.getTitle()}" />
					</h1>
					<br> <br>
					<div class="d-flex fw-light">
						<div class="me-auto">
							by<a class="dropdown-toggle link-dark px-2" href="#" data-bs-toggle="dropdown" aria-expanded="false"><c:out value="${member.getNickname()}" /></a>
							<ul class="dropdown-menu">
								<c:if test="${member.getId() ne 0}">
									<li><a class="dropdown-item" href="${pageContext.request.contextPath}/Member/Profile?id=${member.getId()}">View Profile</a></li>
								</c:if>
								<c:if test="${member.getId() eq 0}">
									<li><a href="#" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#postDeleteModal">Delete</a></li>
									<!-- <li><a href="#" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#postEditModal">Edit</a></li> -->
								</c:if>
								<c:if test="${member.getId() ne 0}">
									<c:if test="${not empty sessionScope.user}">
										<c:if test="${sessionScope.user.getId() eq member.getId() }">
											<li><a href="#" onclick="deletePost(${post.getId()})" class="dropdown-item">Delete</a></li>
											<!-- <li><a href="${pageContext.request.contextPath}/Post/Edit?id=${post.getId()}" class="dropdown-item">Edit</a></li> -->
										</c:if>
									</c:if>
								</c:if>
								<li><a href="#" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#postReportModal">Report post</a></li>
							</ul>
						</div>
						<div>
							<span class="link-dark px-2"><c:out value="${post.getPostDate()}" /></span>
						</div>
					</div>
				</div>
				<div class="paste line-break">
					<p class="fw-bold">
						<span><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-font" viewBox="0 0 16 16">
                            <path d="M10.943 6H5.057L5 8h.5c.18-1.096.356-1.192 1.694-1.235l.293-.01v5.09c0 .47-.1.582-.898.655v.5H9.41v-.5c-.803-.073-.903-.184-.903-.654V6.755l.298.01c1.338.043 1.514.14 1.694 1.235h.5l-.057-2z" />
                            <path d="M14 4.5V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h5.5L14 4.5zm-3 0A1.5 1.5 0 0 1 9.5 3V1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h-2z" />
                          </svg></span> Text
					</p>
					<c:if test="${post.getType() == 'plain-text'}">
						<pre>
						<code class="plain-text">
<c:out value="${post.getContents()}" />
						</code>
						</pre>
					</c:if>
					<c:if test="${post.getType() !=  'plain-text'}">
						<pre>
						<code>
<c:out value="${post.getContents()}" />
						</code>
						</pre>
					</c:if>
				</div>
				<div class="tags">
					<p class="fw-bold">
						<span><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-card-text" viewBox="0 0 16 16">
                            <path d="M14.5 3a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h13zm-13-1A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h13a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-13z" />
                            <path d="M3 5.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3 8a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 8zm0 2.5a.5.5 0 0 1 .5-.5h6a.5.5 0 0 1 0 1h-6a.5.5 0 0 1-.5-.5z" />
                          </svg></span> Tag
					</p>
					<!-- 아래 부분을 forEach로 표현하세요 -->
					<c:forEach var="tag" items="${tList}">
						<a class="me-1 text-decoration-none" href="${pageContext.request.contextPath}/Search?&q=${sf:encode(tag.getTagName())}&type=TAG&order=LATEST"><c:out value="${tag.getTagName()}" /> </a>
					</c:forEach>
				</div>
				<br>
				<div class="explain">
					<p class="fw-bold">
						<span><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-card-text" viewBox="0 0 16 16">
                            <path d="M14.5 3a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h13zm-13-1A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h13a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-13z" />
                            <path d="M3 5.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3 8a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 8zm0 2.5a.5.5 0 0 1 .5-.5h6a.5.5 0 0 1 0 1h-6a.5.5 0 0 1-.5-.5z" />
                          </svg></span> Explain
					</p>
					<p class="line-break">
						<c:out value="${post.getExplain()}" />
					</p>
				</div>
				<div class="recommend-box">
					<div class="text-center">
						<button onclick="location.href='${pageContext.request.contextPath}/Post/Recommend?id=${post.getId()}'" type="button" class="btn btn-primary btn-circle btn-xl bg-dark">
							<svg class="recommend-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-up" viewBox="0 0 16 16">
                                <path
									d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z" />
                              </svg>
						</button>
					</div>
					<div class="text-center mt-1 mb-2">
						<h3 id="recommend">
							<c:out value="${post.getRecommend()}" />
						</h3>
					</div>
				</div>
					<div class="comment">
						<p class="fw-bold">
							<span><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-card-text" viewBox="0 0 16 16">
                            <path d="M14.5 3a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h13zm-13-1A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h13a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-13z" />
                            <path d="M3 5.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3 8a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 8zm0 2.5a.5.5 0 0 1 .5-.5h6a.5.5 0 0 1 0 1h-6a.5.5 0 0 1-.5-.5z" />
                          </svg></span> Comment
						</p>
						<c:forEach var="cMember" items="${mList}" varStatus="i">
							<div class="d-flex fw-light">
								<!-- 여기부터 -->
								<div class="me-auto">
									<a class="dropdown-toggle link-dark px-2" href="#" data-bs-toggle="dropdown" aria-expanded="false"><c:out value="${cMember.getNickname()}" /></a>
									<ul class="dropdown-menu">
										<c:if test="${cMember.getId() ne 0}">
											<li><a class="dropdown-item" href="${pageContext.request.contextPath}/Member/Profile?id=${cMember.getId()}">View Profile</a></li>
										</c:if>
										<c:if test="${cMember.getId() eq 0}">
											<li><a href="#" onclick="javascript:commentId=${cList[i.index].getId()}; setValue();" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#commentDeleteModal">Delete</a></li>
											<li><a href="#" onclick="javascript:commentId=${cList[i.index].getId()}; setValue();" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#commentEditModal">Edit</a></li>
										</c:if>
										<c:if test="${cMember.getId() ne 0}">
											<c:if test="${not empty sessionScope.user}">
												<c:if test="${sessionScope.user.getId() eq cMember.getId()}">
													<li><a href="${pageContext.request.contextPath}/Comment/Delete?commentId=${cList[i.index].getId()}&postId=${post.getId()}" class="dropdown-item">Delete</a></li>
													<li><a href="#" onclick="javascript:commentId=${cList[i.index].getId()}; setValue();" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#commentEditModalWithoutPassword">Edit</a></li>
													<!-- <li><a href="#" class="dropdown-item">Edit</a></li>-->
												</c:if>
											</c:if>
										</c:if>
									</ul>
								</div>
								<div>
									<span class="link-dark px-2"><c:out value="${cList[i.index].getDate()}" /></span>
								</div>
							</div>
							<p class="line-break px-2">
								<c:out value="${cList[i.index].getComment()}" />
							</p>
							<hr>
							<!-- 여기까지를 forEach로 반복해주세요-->
						</c:forEach>
					</div>
					<jsp:include page="pagination.jsp" />
					<jsp:include page="addcomment.jsp" />
					<jsp:include page="modal/commentdeletemodal.jsp" />
					<jsp:include page="modal/postdeletemodal.jsp" />
					<jsp:include page="modal/commenteditmodal.jsp" />
					<jsp:include page="modal/posteditmodal.jsp" />
					<jsp:include page="modal/commenteditmodalwithoutpassword.jsp" />
					<jsp:include page="modal/postReportModal.jsp"/>
			</div>
		</div>
	</div>
	<!-- compiled highlight.js -->
	<script src="../asset/highlight/highlight.pack.js"></script>
	<script>
		hljs.highlightAll();
		var commentId = -1;
		function getCommentId() {
			return commentId;
		}

		function setValue() {
			document.comment_edit.commentId.value = commentId;
			document.comment_delete.commentId.value = commentId;
			document.comment_edit_without_pw.commentId.value = commentId;
			return true;
		}
		
		function deletePost(id) {
			if (confirm("Do you want to remove the post?") == true)
				location.href = "${pageContext.request.contextPath}/Post/Delete?id=${post.getId()}";
			else
				return;
		}
	</script>
	<!-- compiled bootstrap 5 javascript -->
	<script src="../asset/bootstrap/js/bootstrap.min.js"></script>
	<!-- compiled bootstrap 5 bundle javascript -->
	<script src="../asset/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>