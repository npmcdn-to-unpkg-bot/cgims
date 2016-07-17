<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headTitle" value="${shop.name}"/>
<%@include file="../inc/header.jsp" %>
<style type="text/css" title="Default">
			/* fullscreen */
			html {
				height:100%;
			}
			body {
				height:100%;
				margin: 0px;
			}
		</style>
</head>
<body>
	<div class="main">
		<%@include file="../inc/homeNav.jsp" %>
		<a href="${ctx }/shop/view/preView/${shop.id}"><img src="${shop.preViewUrl }" style="width:100%"/></a>
		<section class=" pd10 f14 mt10 bdt bdb lh105">
			<div class="detailMsg">
				<h3>介绍</h3>
				<p class="f999 js-textarea-content">${shop.introduction }</p>
			</div>
		</section>
		
		<c:if test="${shop.products != null and fn:length(shop.products) != 0 }">
		<section class="mt10 homeList bdb">
			<ul>
				<c:if test="${shop.products != null and fn:length(shop.products) != 0 }">
				<h3 class="f999 f16 pdY5">商品</h3>
		       		<c:forEach items="${shop.products}" var="t" varStatus="status">
						<li><a href="${ctx }/product/view/${t.id}">
								<div class="img">
									<img src="${t.preViewUrl }">
								</div>
								<div class="txt">
									<p class="x-intro"></p>
									<h2>${t.name }</h2>
									<p>${t.introduction }</p>
								</div>
						</a></li>
		       		</c:forEach>
				</c:if>
			</ul>
		</section>
		</c:if>
		</div>
</body>
<script>

fitTextareaFmt();
function fitTextareaFmt(){
	$(".js-textarea-content").each(function(){
		var ttc = this.innerHTML;
		if(ttc){
			ttc = ttc.replace(/\n/g,"<br/>");
			ttc = ttc.replace(/ /g,"&nbsp;");
			this.innerHTML = ttc;
		}
	});
}
</script>
</html>