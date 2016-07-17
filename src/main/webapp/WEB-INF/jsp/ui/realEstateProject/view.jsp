<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headTitle" value="${realEstateProject.name}"/>
<%@include file="../inc/header.jsp" %>
<script type="text/javascript"
	src="${ctx }/assets/js/jquery.event.drag-1.5.min.js"></script>
<script src="${ctx }/assets/js/jquery.touchSlider.js"></script>
</head>
<body>
	<div class="main">
		<%@include file="../inc/homeNav.jsp" %>
		
		<section class=" pd10 bdb">
			<div>
				<span class="f18 pd10">${realEstateProject.name }</span>
			</div>
		</section>
		
		<c:if test="${realEstateProject.houseTypes != null and fn:length(realEstateProject.houseTypes) != 0 }">
		<section class="mt10 homeList bdb">
			<ul>
				<c:if test="${oneRoomHouseTypes != null and fn:length(oneRoomHouseTypes) != 0 }">
				<h3 class="f999 f16 pdY5">一居</h3>
		       		<c:forEach items="${oneRoomHouseTypes}" var="t" varStatus="status">
						<li><a href="${ctx }/houseType/view/${t.id}">
								<div class="img">
									<img src="${t.housePlanUrl }">
								</div>
								<div class="txt">
									<p class="x-intro"></p>
									<h2>${t.name }</h2>
									<p><c:if test="${t.roomNum != null and t.roomNum != '0' }">
											${t.roomNum}室
										</c:if><c:if test="${t.hallNum != null and t.hallNum != '0' }">
											${t.hallNum}厅
										</c:if><c:if test="${t.toiletNum != null and t.toiletNum != '0' }">
											${t.toiletNum}卫
										</c:if><c:if test="${t.kitchenNum != null and t.kitchenNum != '0' }">
											${t.kitchenNum}厨
										</c:if></p>
									<p><c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											建筑约<fmt:formatNumber value="${t.grossFloorArea}" maxFractionDigits="2"/>  ㎡
										</c:if>&nbsp;<c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											套内约<fmt:formatNumber value="${t.insideArea}" maxFractionDigits="2"/> ㎡
										</c:if></p>
								</div>
						</a></li>
		       		</c:forEach>
				</c:if>
				<c:if test="${twoRoomHouseTypes != null and fn:length(twoRoomHouseTypes) != 0 }">
				<h3 class="f999 f16 pdY5">二居</h3>
		       		<c:forEach items="${twoRoomHouseTypes}" var="t" varStatus="status">
						<li><a href="${ctx }/houseType/view/${t.id}">
								<div class="img">
									<img src="${t.housePlanUrl }">
								</div>
								<div class="txt">
									<p class="x-intro"></p>
									<h2>${t.name }</h2>
									
									<p></p>
									<p><c:if test="${t.roomNum != null and t.roomNum != '0' }">
											${t.roomNum}室
										</c:if><c:if test="${t.hallNum != null and t.hallNum != '0' }">
											${t.hallNum}厅
										</c:if><c:if test="${t.toiletNum != null and t.toiletNum != '0' }">
											${t.toiletNum}卫
										</c:if><c:if test="${t.kitchenNum != null and t.kitchenNum != '0' }">
											${t.kitchenNum}厨
										</c:if></p>
									<p><c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											建筑约<fmt:formatNumber value="${t.grossFloorArea}" maxFractionDigits="2"/>  ㎡
										</c:if>&nbsp;<c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											套内约<fmt:formatNumber value="${t.insideArea}" maxFractionDigits="2"/> ㎡
										</c:if></p>
								</div>
						</a></li>
		       		</c:forEach>
				</c:if>
				<c:if test="${threeRoomHouseTypes != null and fn:length(threeRoomHouseTypes) != 0 }">
				<h3 class="f999 f16 pdY5">三居</h3>
		       		<c:forEach items="${threeRoomHouseTypes}" var="t" varStatus="status">
						<li><a href="${ctx }/houseType/view/${t.id}">
								<div class="img">
									<img src="${t.housePlanUrl }">
								</div>
								<div class="txt">
									<p class="x-intro"></p>
									<h2>${t.name }</h2>
									
									<p></p>
									<p><c:if test="${t.roomNum != null and t.roomNum != '0' }">
											${t.roomNum}室
										</c:if><c:if test="${t.hallNum != null and t.hallNum != '0' }">
											${t.hallNum}厅
										</c:if><c:if test="${t.toiletNum != null and t.toiletNum != '0' }">
											${t.toiletNum}卫
										</c:if><c:if test="${t.kitchenNum != null and t.kitchenNum != '0' }">
											${t.kitchenNum}厨
										</c:if></p>
									<p><c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											建筑约<fmt:formatNumber value="${t.grossFloorArea}" maxFractionDigits="2"/>  ㎡
										</c:if>&nbsp;<c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											套内约<fmt:formatNumber value="${t.insideArea}" maxFractionDigits="2"/> ㎡
										</c:if></p>
								</div>
						</a></li>
		       		</c:forEach>
				</c:if>
				<c:if test="${fourRoomHouseTypes != null and fn:length(fourRoomHouseTypes) != 0 }">
				<h3 class="f999 f16 pdY5">四居</h3>
		       		<c:forEach items="${fourRoomHouseTypes}" var="t" varStatus="status">
						<li><a href="${ctx }/houseType/view/${t.id}">
								<div class="img">
									<img src="${t.housePlanUrl }">
								</div>
								<div class="txt">
									<p class="x-intro"></p>
									<h2>${t.name }</h2>
									
									<p></p>
									<p><c:if test="${t.roomNum != null and t.roomNum != '0' }">
											${t.roomNum}室
										</c:if><c:if test="${t.hallNum != null and t.hallNum != '0' }">
											${t.hallNum}厅
										</c:if><c:if test="${t.toiletNum != null and t.toiletNum != '0' }">
											${t.toiletNum}卫
										</c:if><c:if test="${t.kitchenNum != null and t.kitchenNum != '0' }">
											${t.kitchenNum}厨
										</c:if></p>
									<p><c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											建筑约<fmt:formatNumber value="${t.grossFloorArea}" maxFractionDigits="2"/>  ㎡
										</c:if>&nbsp;<c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											套内约<fmt:formatNumber value="${t.insideArea}" maxFractionDigits="2"/> ㎡
										</c:if></p>
								</div>
						</a></li>
		       		</c:forEach>
				</c:if>
				<c:if test="${fiveRoomHouseTypes != null and fn:length(fiveRoomHouseTypes) != 0 }">
				<h3 class="f999 f16 pdY5">五居</h3>
		       		<c:forEach items="${fiveRoomHouseTypes}" var="t" varStatus="status">
						<li><a href="${ctx }/houseType/view/${t.id}">
								<div class="img">
									<img src="${t.housePlanUrl }">
								</div>
								<div class="txt">
									<p class="x-intro"></p>
									<h2>${t.name }</h2>
									
									<p></p>
									<p><c:if test="${t.roomNum != null and t.roomNum != '0' }">
											${t.roomNum}室
										</c:if><c:if test="${t.hallNum != null and t.hallNum != '0' }">
											${t.hallNum}厅
										</c:if><c:if test="${t.toiletNum != null and t.toiletNum != '0' }">
											${t.toiletNum}卫
										</c:if><c:if test="${t.kitchenNum != null and t.kitchenNum != '0' }">
											${t.kitchenNum}厨
										</c:if></p>
									<p><c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											建筑约<fmt:formatNumber value="${t.grossFloorArea}" maxFractionDigits="2"/>  ㎡
										</c:if>&nbsp;<c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											套内约<fmt:formatNumber value="${t.insideArea}" maxFractionDigits="2"/> ㎡
										</c:if></p>
								</div>
						</a></li>
		       		</c:forEach>
				</c:if>
				<c:if test="${overFiveRoomHouseTypes != null and fn:length(overFiveRoomHouseTypes) != 0 }">
				<h3 class="f999 f16 pdY5">五居以上</h3>
		       		<c:forEach items="${overFiveRoomHouseTypes}" var="t" varStatus="status">
						<li><a href="${ctx }/houseType/view/${t.id}">
								<div class="img">
									<img src="${t.housePlanUrl }">
								</div>
								<div class="txt">
									<p class="x-intro"></p>
									<h2>${t.name }</h2>
									
									<p></p>
									<p><c:if test="${t.roomNum != null and t.roomNum != '0' }">
											${t.roomNum}室
										</c:if><c:if test="${t.hallNum != null and t.hallNum != '0' }">
											${t.hallNum}厅
										</c:if><c:if test="${t.toiletNum != null and t.toiletNum != '0' }">
											${t.toiletNum}卫
										</c:if><c:if test="${t.kitchenNum != null and t.kitchenNum != '0' }">
											${t.kitchenNum}厨
										</c:if></p>
									<p><c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											建筑约<fmt:formatNumber value="${t.grossFloorArea}" maxFractionDigits="2"/>  ㎡
										</c:if>&nbsp;<c:if test="${t.grossFloorArea != null and t.grossFloorArea != '0' }">
											套内约<fmt:formatNumber value="${t.insideArea}" maxFractionDigits="2"/> ㎡
										</c:if></p>
								</div>
						</a></li>
		       		</c:forEach>
				</c:if>
			</ul>
		</section>
		</c:if>
		<%@include file="../inc/footer.jsp" %>
	</div>
</body>
<%@include file="../inc/bottom.jsp" %>
<script>
var total=2;
var allowLoad = true;
var curp = 1;
	$(function() {
		fitTextareaFmt();
		initScrollEvent();
		
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

	function initScrollEvent(){
		$("body").scrollTop(1);
		if(total<=1){
			$('#drag').hide();
		    allowLoad = false;
		}
		//滚动到页面底部时，自动加载更多
		var scrollFlag=false;
		window.addEventListener("scroll",function scrollHandler(){
			
			var scrollh = $(document).height();
			var bua = navigator.userAgent.toLowerCase();
			if(scrollFlag){
				if(bua.indexOf('iphone') != -1 || bua.indexOf('ios') != -1){
					scrollh = scrollh-140;
				}else{
					scrollh = scrollh-80;
				}
			}
			var c=document.documentElement.clientHeight || document.body.clientHeight, t=$(document).scrollTop();
			if(allowLoad != false && ($(document).scrollTop() + $(window).height()) >= scrollh){
				load();
			}
		},100); 
		$('#drag').click(function () {
			load();
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
	function load(){
		var draginner=$('.draginner');
		if(draginner.hasClass("loading")){
			return;
		}
		allowLoad = false;
		draginner.css('padding-left','10px');
		draginner.addClass("loading");
		draginner.html("正在加载...");
		setTimeout(function(){
			var str = ' <li ><a href="http://www.fang.com/news/2015-06-09/16194688.htm" id="wapdsy_D04_23"> <div class="bord"> <div class="lt"> <img data-original="http://imgs.soufun.com/viewimage/news/2015_06/09/news/1433810092437_000/120x120.jpg" alt="" src="http://www.pdfangchan.com/upload/house/2011-12-19/2011121915571257_120x90.jpg"> </div> <div class="rt"> <h3>京拟用闲置地建养老设施</h3> <p>北京拟利用国有企业闲置土地，建32个养老设施。</p> </div> </div> </a></li>';
// 			var str = '<li><a id="" href="/xf/bj/1010710185.htm"> <div class="img"> <img src="http://i3.sinaimg.cn/hs/2010/0901/S18375T1283345502659.jpg"> </div> <div class="txt"> <p class="x-intro"> <h2>一杯澜</h2> <span class="new">20000元/平</span> </p> <p>北戴河新区国际娱乐中心东3公里（原碧海蓝天度假村）</p> <div class="stag"> <span class="t1">楼盘：二居(<span class="t2">1</span>) 三居(<span class="t2">2</span>)</span></div></div></a></li>';
			$('#itemList').append(str);
			$('#itemList').append(str);
			$('#itemList').append(str);
			$('#itemList').append(str);
			$('#itemList').append(str);
			draginner.css('padding-left','0px');
			draginner.removeClass("loading");
			draginner.html("查看更多资讯");
			curp=curp+1;
			allowLoad = true;
			if(curp>total){
				$('#drag').hide();
				allowLoad = false;
			}
		},1000);
	}

</script>
</html>