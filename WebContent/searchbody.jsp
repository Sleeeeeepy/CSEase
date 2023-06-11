<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="mt-2">
	<ul class="flex-column">
		<c:forEach var="post" items="${pList}" varStatus="i">
			<li class="list-group-item">
				<div class="p-2">
					<div class="d-flex pt-3">
						<div class="pb-3 mb-0 small w-100">
							<div class="d-flex">
								<a class="text-gray-dark fs-3 text-decoration-none" href="${pageContext.request.contextPath}/Post/View?id=${post.getId()}"><c:out value="${post.getTitle()}" />
								<c:if test="${post.getOpenRange().toString() eq 'PRIVATE'}">
								<span class="fs-12px fw-blod text-danger"> (private)</span>
								</c:if>
								<c:if test="${post.getOpenRange().toString() eq 'MEMBER_ONLY' }">
								<span class="fs-12px fw-blod text-danger"> (Group Only)</span>
								</c:if>
								</a>
							</div>
							<span class="d-block">${post.getExplain()}</span> <br>
							<c:forEach var="tag" items="${tMap.get(i.index)}">
									<a class="tag border rounded-pill me-1 text-decoration-none" href="#"><span>#</span> <c:out value="${tag.getTagName()}" /></a>
							</c:forEach>
							<p class="fs-6 user-text">by <c:out value="${mList.get(i.index).getNickname()}"/></p>
						</div>
						<p class="border align-items-center fs-2 text-center">
							<span>${post.getRecommend()}</span> <span class="fs-6">vote</span>
						</p>
					</div>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>