<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>    
    

<%@ include file="include/header.jsp" %>		
	
	
	
	
	<div class="container">
	<p>전체 글의 수(${totalCount})</p>	
	<table class="table table-striped table-hover">
	
			<tr>
		<th>순번</th>
		<th>글번호</th>
		<th>글쓴이</th>
		<th>제목</th>
		<th>작성일시</th>
		
		</tr>
	
	
	<c:forEach items="${bbsLists}" var="dto">
	

	
		<tr>
			
				<td><a href="view?id=${dto.id}">순번</a></td>
	            <td><a href="view?id=${dto.id}">${dto.id}</a></td>
				<td><a href="view?id=${dto.id}">${dto.writer}</a></td>
				<td><a href="view?id=${dto.id}">${dto.title}</a></td>
				<td>${dto.formattedDate}
			

		</tr>
	</c:forEach></table>	

		<div class="d-flex justify-content-center">
			<ul class="pagination">
				<c:if test="${currentPage > 1 }">
			
				<li class="page-item">
					<a class="page-link" href="list?page=${currentPage -1}&size=${size}">Previous</a>
				</li>
				</c:if>
				
				<c:forEach begin="1" end="${totalPages }" var="page">
				<li class="page-item ${page == currentPage ? 'active':''}" >
					<a class="page-link" href="list?page=${page}&size=${size}">${page}</a>
				</li>
				</c:forEach>

				<c:if test="${currentPage < totalPages}">
				
				<li class="page-item"><a class="page-link" href="list?page=${currentPage + 1}&size=${size}">Next</a></li>
				</c:if>
			</ul>
		</div>


<%@ include file="include/bbsWriteBtn.jsp" %>		
	</div>
	

<%@ include file="include/footer.jsp" %>	

