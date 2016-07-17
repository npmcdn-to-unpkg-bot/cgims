<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headTitle" value="${designRing.name}"/>
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
			iframe{
				height:100%;
				width:100%;
			}
		</style>
</head>
<body>
	<div class="main">
		<%@include file="../inc/backNav.jsp" %>
	<iframe src="${ctx }/designRing/content/${designRing.id }" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" id="ifm" name="ifm" width="100%"></iframe>
		<section class=" pd10 bdb">
				<span class="cent"><a href="${ctx }/designRing/content/${designRing.id}">全屏浏览</a></span>
		</section>
		<section class=" pd10 f14 mt10 bdt bdb lh105">
			<div class="detailMsg">
				<p>
					楼盘：<a href="${ctx }/realEstateProject/view/${designRing.houseType.realEstateProject.id}"><span >${designRing.houseType.realEstateProject.name }</span></a>
				</p>
				<p>
					户型名称：<a href="${ctx }/houseType/view/${designRing.houseType.id}"><span >${designRing.houseType.name }</span></a>
				</p>
				<p>
					户型：<span class="f999">
					<c:if test="${designRing.houseType.roomNum != null and designRing.houseType.roomNum != '0' }">
											${designRing.houseType.roomNum}室
										</c:if><c:if test="${designRing.houseType.hallNum != null and designRing.houseType.hallNum != '0' }">
											${designRing.houseType.hallNum}厅
										</c:if><c:if test="${designRing.houseType.toiletNum != null and designRing.houseType.toiletNum != '0' }">
											${designRing.houseType.toiletNum}卫
										</c:if><c:if test="${designRing.houseType.kitchenNum != null and designRing.houseType.kitchenNum != '0' }">
											${designRing.houseType.kitchenNum}厨
										</c:if>
										</span>
				</p>
				<p>
					建筑面积：<span class="f999">约<fmt:formatNumber value="${designRing.houseType.grossFloorArea }" maxFractionDigits="2"/>㎡</span>
				</p>
				<p>
					套内面积：<span class="f999">约<fmt:formatNumber value="${designRing.houseType.insideArea}" maxFractionDigits="2"/>㎡</span>
				</p>
			</div>
		</section>
		
		<c:if test="${designRing.products != null and fn:length(designRing.products) != 0 }">
		<section class="mt10 homeList bdb">
			<ul>
				<c:if test="${designRing.products != null and fn:length(designRing.products) != 0 }">
				<h3 class="f999 f16 pdY5">商品</h3>
		       		<c:forEach items="${designRing.products}" var="t" varStatus="status">
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

$(function() {
	$(window).resize(function() {
		var dw = $(".main").width();
		$("iframe").each(function() {
			var fitH = dw*0.5;
			$(this).css("height", fitH + "px");
		});
	});

	$(window).resize();
})
</script>
</html>