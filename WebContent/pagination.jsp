<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav aria-label="..." class="d-flex justify-content-center">
	<ul class="pagination">
		<c:if test="${pagination.getPageStartsAt() > 0 && pagination.getPageEndsAt() > 0}">
			<c:if test="${!pagination.hasPrevBlock}">
				<li class="page-item disabled"><a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a></li>
			</c:if>
			<c:if test="${pagination.hasPrevBlock}">
				<li class="page-item"><a class="page-link" href='<c:out value="${prevBlockPage}"/>' tabindex="-1" aria-disabled="false">Previous</a></li>
			</c:if>
			<c:set var="j" value="0" scope="page" />
			<c:forEach var="i" begin="${pagination.getPageStartsAt()}" end="${pagination.getPageEndsAt()}">
				<c:if test="${i eq pagination.getCurrent()}">
					<li class="page-item active" aria-current="page"><a class="page-link" href="#">${i}</a></li>
				</c:if>
				<c:if test="${i ne pagination.getCurrent()}">
					<li class="page-item"><a class="page-link" href='<c:out value="${pages[j]}"/>'>${i}</a></li>
				</c:if>
				<c:set var="j" value="${j + 1}" scope="page" />
			</c:forEach>
			<c:if test="${pagination.hasNextBlock}">
				<li class="page-item"><a class="page-link" href='<c:out value="${nextBlockPage}"/>'>Next</a></li>
			</c:if>
			<c:if test="${!pagination.hasNextBlock}">
				<li class="page-item disabled"><a class="page-link" href="#" tabindex="-1" aria-disabled="true">Next</a></li>
			</c:if>
		</c:if>
	</ul>
</nav>