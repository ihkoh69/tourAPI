<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="jakarta.tags.core" prefix="c" %>   


<%@ include file="include/header.jsp" %>


<div class="container">
<p>전체 글의 수(${totalCount})</p>
<table class="table table-striped table-hover">
<thead>
<tr>
<th>작성자</th>
<th>제목</th>
<th align="center">동영상 보기</th>
<th>작성일</th>
</tr>
</thead>
<tbody>
<c:forEach items="${clipLists}" var="dto">

<tr>
<td><a href="viewClip?id=${dto.id}">${dto.writer}</a></td>
<td><a href="viewClip?id=${dto.id}">${dto.title}</a></td>
<td align="center"><a href="viewClip?id=${dto.id}"><img src="images/youtube.png"></a></td>
<td>${dto.reg_date}</td>
</tr>
</c:forEach>
</tbody>
</table>

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


<%@ include file="include/clipWriteBtn.jsp" %>


</div>


<%@ include file="include/footer.jsp" %>