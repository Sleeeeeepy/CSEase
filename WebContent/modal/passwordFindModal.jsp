<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="passwordFindModal" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form action="${pageContext.request.contextPath}/Member/PasswordFind" method="POST">
				<div class="modal-header">
					<h5 class="modal-title">Find Password</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="mt-2">
						<input type="text" name="userId" class="form-control" placeholder="ID" />
					</div>
					<div class="mt-2">
						<input type="email" name="email" class="form-control" placeholder="E-Mail" />
					</div>
				</div>
				<div class="modal-footer">
					<input class="btn btn-primary" type="submit" value="OK" />
					<button type="button" class="btn btn-secondary btn-danger" data-bs-dismiss="modal">Cancel</button>
				</div>
			</form>
		</div>
	</div>
</div>