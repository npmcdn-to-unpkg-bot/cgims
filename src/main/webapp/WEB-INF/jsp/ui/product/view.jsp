<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headTitle" value="${product.name}"/>
<%@include file="../inc/header.jsp" %>
<script type="text/javascript"
	src="${ctx }/assets/js/jquery.event.drag-1.5.min.js"></script>
<script src="${ctx }/assets/js/jquery.touchSlider.js"></script>
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
		<a href="${ctx }/product/view/preView/${product.id}"><img src="${product.preViewUrl }" style="width:100%"/></a>
		<section class=" pd10 f14 mt10 bdt bdb lh105">
			<div class="detailMsg">
				<p>
					商铺：<a href="${ctx }/shop/view/${product.shop.id}"><span >${product.shop.name }</span></a>
				</p>
				<div class="l"></div>
				<h3>商品介绍</h3>
				<p class="f999 js-textarea-content">${product.introduction }</p>
			</div>
		</section>
		<c:if test="${product.designImages != null and fn:length(product.designImages) != 0 }">
		<section class="mt10 bdt bdb">
			<h3 class="pd10 f16">效果图片</h3>
			<div class="imgTouchSlider">
				<div class="main_image">
					<ul>
		       			<c:forEach items="${product.designImages}" var="t" varStatus="status">
								<li><a href="${ctx }/designImage/view/${t.id}">
								<div class="play360Btn"></div><img
								src='${t.contentUrl }'>
								</a></li>
		       			</c:forEach>
					</ul>
					<a href="javascript:void(0);" id="btn_prev" class="btn_prev"></a> <a
						href="javascript:void(0);" id="btn_next" class="btn_next"></a>
				</div>
				<div class="flicking_con">
					<div class="flicking_inner">
			       		<c:forEach items="${product.designImages}" var="t" varStatus="status">
			       			<a href=""></a>
			       		</c:forEach>
					</div>
				</div>
			</div>
		</section>
		</c:if>
		
		<c:if test="${product.designRings != null and fn:length(product.designRings) != 0 }">
		<section class="mt10 bdt bdb">
			<h3 class="pd10 f16">效果三维</h3>
			<div class="imgTouchSlider">
				<div class="main_image">
					<ul>
		       			<c:forEach items="${product.designRings}" var="t" varStatus="status">
								<li><a href="${ctx }/designRing/view/${t.id}">
								<div class="play360Btn"></div><img
								src='${t.preViewUrl }'>
								</a></li>
		       			</c:forEach>
					</ul>
					<a href="javascript:void(0);" id="btn_prev" class="btn_prev"></a> <a
						href="javascript:void(0);" id="btn_next" class="btn_next"></a>
				</div>
				<div class="flicking_con">
					<div class="flicking_inner">
			       		<c:forEach items="${product.designRings}" var="t" varStatus="status">
			       			<a href=""></a>
			       		</c:forEach>
					</div>
				</div>
			</div>
		</section>
		</c:if>
		
		<c:if test="${product.designPanoramas != null and fn:length(product.designPanoramas) != 0 }">
		<section class="mt10 bdt bdb">
			<h3 class="pd10 f16">效果全景</h3>
			<div class="imgTouchSlider">
				<div class="main_image">
					<ul>
		       			<c:forEach items="${product.designPanoramas}" var="t" varStatus="status">
								<li><a href="${ctx}/designPanorama/view/${t.id}">
								<div class="play360Btn"></div><img
								src='${t.preViewUrl }'>
								</a></li>
		       			</c:forEach>
					</ul>
					<a href="javascript:void(0);" id="btn_prev" class="btn_prev"></a> <a
						href="javascript:void(0);" id="btn_next" class="btn_next"></a>
				</div>
				<div class="flicking_con">
					<div class="flicking_inner">
			       		<c:forEach items="${product.designPanoramas}" var="t" varStatus="status">
			       			<a href=""></a>
			       		</c:forEach>
					</div>
				</div>
			</div>
		</section>
		</c:if>
		</div>
</body>
<script>

$(function() {
	fitTextareaFmt();
	
	$(".imgTouchSlider").each(function(){
		initSlider(this);
	})

	$(window).resize(function() {
		fitSize();
	});
	fitSize();
});

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

function initSlider(element){

	var sliderObj = $(element).children(".main_image");
	var flickObj = $(element).find(".flicking_con a");
	
	var dragBln = false;
	sliderObj.touchSlider(
			{
				flexible : true,
				speed : 200,
				paging : flickObj,
				counter : function(e) {
					flickObj.removeClass("on")
							.eq(e.current - 1).addClass("on");
				}
			});
	sliderObj.bind("mousedown", function() {
		dragBln = false;
	})
	sliderObj.bind("dragstart", function() {
		dragBln = true;
	})
	sliderObj.click(function() {
		if (dragBln) {
			return false;
		}
	})
	var timer = setInterval(function() {
		sliderObj[0].animate(-1,true);
	}, 5000);
	$(element).hover(function() {
		clearInterval(timer);
	}, function() {
		timer = setInterval(function() {
			sliderObj[0].animate(-1,true);
		}, 5000);
	})
	sliderObj.bind("touchstart", function() {
		clearInterval(timer);
	}).bind("touchend", function() {
		timer = setInterval(function() {
			sliderObj[0].animate(-1,true);
		}, 5000);
	})
}
function fitSize() {
	var bw = $("body").width();
	if (bw > 640) {
		bw = 640;
	}
	$(".main_image").height(bw * 0.75);

	$(".imgTouchSlider").each(function(){
		var fw = $(this).find(".flicking_inner a").length*21;
		$(this).find(".flicking_inner").css("left", (bw - fw) * 0.5 + "px");
	})
}
</script>
</html>