<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="memberWithdrawModal" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form action="${pageContext.request.contextPath}/Member/Delete" method="POST">
				<div class="modal-header">
					<h5 class="modal-title">Membership withdrawal</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="mt-2">
						<label class="mb-1 mt-1"> When you withdraw as a member, the comments and posts you wrote and the group you manage are deleted. Do you really want to cancel your membership? <br> If you want to withdraw, please enter your username.</label>
						<input type="text" name="username" class="form-control" placeholder="Username" />
						<input type="hidden" name="id" value="${sessionScope.user.getId()}"/>
					</div>
				</div>
				<div class="modal-footer">
					<input class="btn btn-danger" type="submit" value="OK" />
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
				</div>
			</form>
		</div>
	</div>
</div>