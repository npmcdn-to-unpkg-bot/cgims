<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headTitle" value="${information.name}"/>
<%@include file="../inc/header.jsp" %>
<script type="text/javascript"
	src="${ctx }/assets/js/jquery.event.drag-1.5.min.js"></script>
<script src="${ctx }/assets/js/jquery.touchSlider.js"></script>
</head>
<body>
	<div class="main">
		<%@include file="../inc/homeNav.jsp" %>
		
		<section class="bdb">
			<div class="conTitle">
			<h1>${information.name}</h1>
			<p><span class="time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${information.updateDate}" /></span> ${information.author } </p>
			</div>
			<div class="conWord">
			${information.content }
			</div>
		</section>
		<%@include file="../inc/footer.jsp" %>
		<%@include file="../inc/goHead.jsp" %>
	</div>
</body>
<script>

	$(function() {
		initScrollEvent();
	})
	
	function initScrollEvent(){
		$("body").scrollTop(1);
		window.addEventListener("scroll",function scrollHandler(){
			var backTopBtn = $("#showgohead");
			if(backTopBtn[0]){
				window.pageYOffset>window.innerHeight*2-60?backTopBtn.removeClass("none"):backTopBtn.addClass("none");
			}
		},100); 
	}

</script>
</html>