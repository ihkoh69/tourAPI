<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="include/header.jsp" %>
<%@ include file="include/bbsHeader.jsp" %>

<div class="container viewBody">

	<div class="border  border-success rounded-3 p-5">
	
	<fieldset disabled>
	<form action="write" method="get">
	  <div class="mb-3 row">
	    <label for="inpWriter" class="col-sm-1 col-form-label">글쓴이</label>
	    <div class="col-sm-3">
	      <input type="text" class="form-control border-success-subtle" id="inpWriter" value="${record.writer}">
	    </div>
	  </div>
		
	  <div class="mb-3 row">
	    <label for="inpTitle" class="col-sm-1 col-form-label">제목</label>
	    <div class="col-sm-11">
	      <input type="text" class="form-control border-success-subtle" id="inpTitle" value="${record.title}">
	    </div>
	  </div>
		
		<div class="row mt-2">
		  <label for="inpContent" class="form-label">내용</label>
		  <div class="form-control border-success-subtle bg-success-subtle contentArea" id="inpContent">${record.content}</div>
		</div>	

	
	</form>
	</fieldset>

   	<div class="buttons d-flex justify-content-between align-items-center mt-3 mb-5 gap-2 pr-3">
		<div class="d-flex justify-content-start align-items-center gap-2">
			<div>
				<a href="writeForm"><button type="button" class="btn btn-primary">글쓰기</button></a>
			</div>
			<div>
				<a href="list"><button type="button" class="btn btn-primary" >목록</button></a>
			</div>
		</div>
		<div class="d-flex justify-content-end align-items-center">
			<a href="javascript:void(0)"><button type="button" class="btn btn-danger" onclick="delCheck()">삭제</button></a>
			<script>
				function delCheck(){
					let answer = confirm(`게시물 ID(${record.id})를 정말 삭제하시겠습니까?`)
					if(answer) {
						document.location.href=`delete?id=${record.id}`
					}
				}	
			</script>
		</div>
	</div>

	</div>


</div>

<%@ include file="include/footer.jsp" %>