<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a class="d-flex align-items-center text-dark text-decoration-none"> <span class="fs-4"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
			<path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" /></svg> Search</span>
</a>
<hr>
<form method="GET" action="Search">
	<div class="mt-4 d-grid gap-2">
		<input name="q" type="search" value="<c:out value="${param.q}"/>" class="form-control"> <label class="form-label fs-6 mt-2">Search by...</label> <select name="type" class="form-select">
			<c:if test="${param.type eq 'TAG' }">
				<option value="TAG" selected>Tag</option>
			</c:if>
			<c:if test="${param.type ne 'TAG' }">
				<option value="TAG">Tag</option>
			</c:if>
			<c:if test="${param.type eq 'TITLE' }">
				<option value="TITLE" selected>Title</option>
			</c:if>
			<c:if test="${param.type ne 'TITLE' }">
				<option value="TITLE">Title</option>
			</c:if>
			<c:if test="${param.type eq 'USER' }">
				<option value="USER" selected>User</option>
			</c:if>
			<c:if test="${param.type ne 'USER' }">
				<option value="USER">User</option>
			</c:if>
			<c:if test="${param.type eq 'GROUP' }">
				<option value="GROUP" selected>Group</option>
			</c:if>
			<c:if test="${param.type ne 'GROUP' }">
				<option value="GROUP">Group</option>
			</c:if>
		</select> <label class="form-label fs-6 mt-2">Order by</label> <select name="order" class="form-control">
			<!--option value="MATCH">Match</option-->
			<c:if test="${param.order eq 'LATEST' }">
				<option value="LATEST" selected>Latest</option>
			</c:if>
			<c:if test="${param.order ne 'LATEST' }">
				<option value="LATEST">Latest</option>
			</c:if>
			<c:if test="${param.order eq 'HIT' }">
				<option value="HIT" selected>View</option>
			</c:if>
			<c:if test="${param.order ne 'HIT' }">
				<option value="HIT">View</option>
			</c:if>
			<c:if test="${param.order eq 'RECOMMEND' }">
				<option value="RECOMMEND" selected>Recommend</option>
			</c:if>
			<c:if test="${param.order ne 'RECOMMEND' }">
				<option value="RECOMMEND">Recommend</option>
			</c:if>
		</select> <input type="submit" class="btn btn-primary" value="search" readonly="readonly" />
	</div>
</form>