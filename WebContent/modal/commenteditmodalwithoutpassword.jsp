<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="commentEditModalWithoutPassword" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
              <form action="${pageContext.request.contextPath}/Comment/Edit" name="comment_edit_without_pw" method="POST">
              <div class="modal-header">
                <h5 class="modal-title">Edit Comment</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
              	  <input type="hidden" name="postId" value="${post.getId()}"/>
              	  <input type="hidden" name="commentId" value=""/>
                  <div class="mt-2">
                  <textarea name="contents" class="form-control" rows="3" placeholder="Comment"></textarea>
                  </div>
              </div>
              <div class="modal-footer">
                <input class="btn btn-primary" type="submit" onsubmit="javascript:setValue()" value="OK"/>
                <button type="button" class="btn btn-secondary btn-danger" data-bs-dismiss="modal">Cancel</button>
              </div>
            </form>
		</div>
	</div>
</div>