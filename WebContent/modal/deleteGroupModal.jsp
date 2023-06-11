<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="groupDeleteModal" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form action="${pageContext.request.contextPath}/Group/Delete" method="POST">
				<div class="modal-header">
					<h5 class="modal-title">Delete Group</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<label class="mb-1 mt-1">Do you really want to delete group?</label> <input type="hidden" name="id" value="${param.id}" />
				</div>
				<div class="modal-footer">
					<input class="btn btn-primary" type="submit" value="Delete" />
					<button type="button" class="btn btn-secondary btn-danger" data-bs-dismiss="modal">Cancel</button>
				</div>
			</form>
		</div>
	</div>
</div>