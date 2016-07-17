<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headTitle" value="${designImage.name}"/>
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
		<%@include file="../inc/backNav.jsp" %>
		<a href="${ctx }/designImage/content/${designImage.id}"><img src="${designImage.contentUrl }" style="width:100%"/></a>
		<section class=" pd10 bdb">
				<span class="cent"><a href="${ctx }/designImage/content/${designImage.id}">全屏浏览</a></span>
		</section>
		<section class=" pd10 f14 mt10 bdt bdb lh105">
			<div class="detailMsg">
				<p>
					楼盘：<a href="${ctx }/realEstateProject/view/${designImage.houseType.realEstateProject.id}"><span >${designImage.houseType.realEstateProject.name }</span></a>
				</p>
				<p>
					户型名称：<a href="${ctx }/houseType/view/${designImage.houseType.id}"><span >${designImage.houseType.name }</span></a>
				</p>
				<p>
					户型：<span class="f999">
					<c:if test="${designImage.houseType.roomNum != null and designImage.houseType.roomNum != '0' }">
											${designImage.houseType.roomNum}室
										</c:if><c:if test="${designImage.houseType.hallNum != null and designImage.houseType.hallNum != '0' }">
											${designImage.houseType.hallNum}厅
										</c:if><c:if test="${designImage.houseType.toiletNum != null and designImage.houseType.toiletNum != '0' }">
											${designImage.houseType.toiletNum}卫
										</c:if><c:if test="${designImage.houseType.kitchenNum != null and designImage.houseType.kitchenNum != '0' }">
											${designImage.houseType.kitchenNum}厨
										</c:if>
										</span>
				</p>
				<p>
					建筑面积：<span class="f999">约<fmt:formatNumber value="${designImage.houseType.grossFloorArea }" maxFractionDigits="2"/>㎡</span>
				</p>
				<p>
					套内面积：<span class="f999">约<fmt:formatNumber value="${designImage.houseType.insideArea}" maxFractionDigits="2"/>㎡</span>
				</p>
			</div>
		</section>
		
		<c:if test="${designImage.products != null and fn:length(designImage.products) != 0 }">
		<section class="mt10 homeList bdb">
			<ul>
				<c:if test="${designImage.products != null and fn:length(designImage.products) != 0 }">
				<h3 class="f999 f16 pdY5">商品</h3>
		       		<c:forEach items="${designImage.products}" var="t" varStatus="status">
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
</html>