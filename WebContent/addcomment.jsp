<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="commentWirte">
	<p class="fw-bold">Write a comment</p>
	<form action="${pageContext.request.contextPath}/Comment/Write" method="POST">
		<div class="form-group row">
			<div class="col-sm-3">
			<c:if test="${empty sessionScope.user}"> 
				<input type="text" class="form-text" placeholder="user" value="Anonymous" readonly/>
				<input type="password" name="password" class="form-text" placeholder="password" />
			</c:if>
			<c:if test="${not empty sessionScope.user}">
				<input type="text" class="form-text" placeholder="user" value="<c:out value="${sessionScope.user.getNickname()}"/>" readonly/>
				<input type="password" name="password" class="form-text" placeholder="password" value="******" readonly/>
			</c:if>
			</div>
			<input type="hidden" name="postId" value="${post.getId()}"/>
			<c:if test="${empty sessionScope.user}"> 
				<input type="hidden" name="id" class="form-text" value="0"/>
			</c:if>
			<c:if test="${not empty sessionScope.user}">
				<input type="hidden" name="id" class="form-text" value="<c:out value="${sessionScope.user.getId()}"/>"/>
			</c:if>
			<div class="col-sm-9">
				<textarea name="comment" class="form-control" rows="3"></textarea>
			</div>
		</div>
		<input type="submit" class="btn btn-primary btn-success float-end mt-3" value="Wirte" />
	</form>
</div>