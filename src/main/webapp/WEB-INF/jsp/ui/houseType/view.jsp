<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headTitle" value="${houseType.name}"/>
<%@include file="../inc/header.jsp" %>
<script type="text/javascript"
	src="${ctx }/assets/js/jquery.event.drag-1.5.min.js"></script>
<script src="${ctx }/assets/js/jquery.touchSlider.js"></script>
</head>
<body>
	<div class="main">
		<%@include file="../inc/homeNav.jsp" %>
		
		<c:if test="${houseType.housePlanUrl != null}">
			<a href="${ctx }/houseType/view/housePlan/${houseType.id}"><img src='${houseType.housePlanUrl }' style="width:100%"></a>
		</c:if>
		<section class=" pd10 f14 mt10 bdt bdb lh105">
			<div class="detailMsg">
				<p>
					楼盘：<a href="${ctx }/realEstateProject/view/${houseType.realEstateProject.id}"><span >${houseType.realEstateProject.name }</span></a>
				</p>
				<p>
					户型：<span class="f999">
					<c:if test="${houseType.roomNum != null and houseType.roomNum != '0' }">
											${houseType.roomNum}室
										</c:if><c:if test="${houseType.hallNum != null and houseType.hallNum != '0' }">
											${houseType.hallNum}厅
										</c:if><c:if test="${houseType.toiletNum != null and houseType.toiletNum != '0' }">
											${houseType.toiletNum}卫
										</c:if><c:if test="${houseType.kitchenNum != null and houseType.kitchenNum != '0' }">
											${houseType.kitchenNum}厨
										</c:if>
										</span>
				</p>
				<p>
					建筑面积：<span class="f999">约<fmt:formatNumber value="${houseType.grossFloorArea }" maxFractionDigits="2"/>㎡</span>
				</p>
				<p>
					套内面积：<span class="f999">约<fmt:formatNumber value="${houseType.insideArea}" maxFractionDigits="2"/>㎡</span>
				</p>
			</div>
		</section>
		
		<c:if test="${houseType.designImages != null and fn:length(houseType.designImages) != 0 }">
		<section class="mt10 bdt bdb">
			<h3 class="pd10 f16">效果图片</h3>
			<div class="imgTouchSlider">
				<div class="main_image">
					<ul>
		       			<c:forEach items="${houseType.designImages}" var="t" varStatus="status">
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
			       		<c:forEach items="${houseType.designImages}" var="t" varStatus="status">
			       			<a href=""></a>
			       		</c:forEach>
					</div>
				</div>
			</div>
		</section>
		</c:if>
		
		<c:if test="${houseType.designRings != null and fn:length(houseType.designRings) != 0 }">
		<section class="mt10 bdt bdb">
			<h3 class="pd10 f16">效果三维</h3>
			<div class="imgTouchSlider">
				<div class="main_image">
					<ul>
		       			<c:forEach items="${houseType.designRings}" var="t" varStatus="status">
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
			       		<c:forEach items="${houseType.designRings}" var="t" varStatus="status">
			       			<a href=""></a>
			       		</c:forEach>
					</div>
				</div>
			</div>
		</section>
		</c:if>
		
		<c:if test="${houseType.designPanoramas != null and fn:length(houseType.designPanoramas) != 0 }">
		<section class="mt10 bdt bdb">
			<h3 class="pd10 f16">效果全景</h3>
			<div class="imgTouchSlider">
				<div class="main_image">
					<ul>
		       			<c:forEach items="${houseType.designPanoramas}" var="t" varStatus="status">
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
			       		<c:forEach items="${houseType.designPanoramas}" var="t" varStatus="status">
			       			<a href=""></a>
			       		</c:forEach>
					</div>
				</div>
			</div>
		</section>
		</c:if>
		<%@include file="../inc/footer.jsp" %>
	</div>
</body>
<%@include file="../inc/bottom.jsp" %>
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