<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="postReportModal" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form action="${pageContext.request.contextPath}/Report/ReportPost" method="POST">
				<div class="modal-header">
					<h5 class="modal-title">Report Post</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
				<input type="hidden" name="postId" value="${post.getId()}"/>
				<c:if test="${not empty sessionScope.user}">
					<input type="hidden" name="userId" value="${sessionScope.user.getId()}"/>
				</c:if>
				<c:if test="${empty sessionScope.user}">
					<input type="hidden" name="userId" value="0"/>
				</c:if>
					<div class="mt-2">
						<label class="mb-1 mt-1">Reason <span class="text-danger">*</span></label>
						<textarea name="explain" class="form-control" rows="6"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<input class="btn btn-danger" type="submit" value="OK" />
					<button type="button" class="btn btn-secondary btn-danger" data-bs-dismiss="modal">Cancel</button>
				</div>
			</form>
		</div>
	</div>
</div>