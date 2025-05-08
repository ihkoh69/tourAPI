<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="include/header.jsp" %>

<div class="container">
<div class="border  border-success rounded-3 p-5">

<form id="formClip" action="writeClip" method="get">
  <div class="mb-3 row">
    <label for="inpWriter" class="col-sm-1 col-form-label">글쓴이</label>
    <div class="col-sm-3">
      <input type="text" name="writer" class="form-control border-success-subtle" id="inpWriter">
    </div>
  </div>
	
  <div class="mb-3 row">
    <label for="inpTitle" class="col-sm-1 col-form-label">제목</label>
    <div class="col-sm-11">
      <input type="text" name="title" class="form-control border-success-subtle" id="inpTitle">
    </div>
  </div>
	
  <div class="mb-3 row">
    <label for="inpClipUrl" class="col-sm-1 col-form-label">영상URL</label>
    <div class="col-sm-11">
      <input type="text" name="clip_url" class="form-control border-success-subtle" id="inpClipUrl">
    </div>
  </div>

	<div class="row mt-3">
		<div class="d-flex col gap-2">
			<input type="button" value="저장" class="btn bg-primary text-white mt-3" onclick="formCheck()"/>
			
			<script>
				function formCheck(){
					
					if(inpWriter.value.trim() != "" && inpTitle.value.trim() != "" && inpClipUrl.value.trim() != ""){
					 
						formClip.submit()
						
					}  
					else {
						alert("작성을 꼼꼼히 해주세요")
					}
				}	
			</script>			
			
			<input type="button" value="취소" class="btn bg-primary text-white mt-3" onclick="moveToList()"/>
			<script>
				function moveToList(){
					let answer = confirm("작성을 취소할까요?")
					if(answer) {
						document.location.href="/listClip"
					}
				}	
			</script>
		</div>
	</div>

</form>

</div>
</div>


<%@ include file = "include/footer.jsp" %>