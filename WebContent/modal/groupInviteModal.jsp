<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="inviteUserModal" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
              <form action="${pageContext.request.contextPath}/Member/Find" name="member-invite" method="POST">
              <div class="modal-header">
                <h5 class="modal-title">Invite User</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                  <h1 class="h3 mb-3 fw-bolder">User ID</h1>
                  <input type="hidden" name="groupId" value="${param.id}"/>
                  <div class="mt-2">
                    <input type="text" name="memberName" class="form-control" placeholder="User ID"/>
                  </div>
              </div>
              <div class="modal-footer">
                <input class="btn btn-primary" type="submit" value="Join"/>
                <button type="button" class="btn btn-secondary btn-danger" data-bs-dismiss="modal">Cancel</button>
              </div>
            </form>
		</div>
	</div>
</div>