<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="jakarta.tags.core" prefix="c" %>    


<%@ include file="include/header.jsp" %>
<%@ include file="include/mainMiddle.jsp" %>





<div class="container bbsBoards">
<div class="row">
<div class="col-6">
<table class="table">
<thead>
<tr>
<th class="bbsBigFont">
<a href="list?size=10">
<span class="text-danger">언론보도+</span>(${totalBbsCount})
</a>
</th>
</tr>
</thead>
<tbody>

	<c:forEach items="${bbsLists}" var="bbs_dto">
	

<tr>
<td>
<a href="view?id=${bbs_dto.id}" target="_self">
<div class="bbsTitle text-black" style="font-size:16px !important;">${bbs_dto.title}</div>
${bbs_dto.formattedDate} / ${bbs_dto.writer} / <span class="bbsStrong text-danger">심플BBS 기사</span>
</a>
</td>
</tr>
</c:forEach>

</tbody>
</table>
</div>
<div class="col-6">
<table class="table">
<thead>
<tr>
<th class="bbsBigFont">
<a href="listClip?size=10">
<span class="text-danger">공지사항+</span>(${totalClipCount})
</a>
</th>
</tr>
</thead>
<tbody>

	<c:forEach items="${clipLists}" var="clip_dto">
	
<tr>
<td>
<a href="viewClip?id="${clip_dto.id}  target="_self">
<div class="bbsTitle text-black" style="font-size:16px !important;">${clip_dto.title}</div> 
${clip_dto.formattedDate} / <img src="images/youtube.png"> / <span class="bbsStrong text-danger">${clip_dto.writer}</span>
</a>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</div>

</div>





<%@ include file="include/mainScripts.jsp" %>
<%@ include file="include/footer.jsp" %>