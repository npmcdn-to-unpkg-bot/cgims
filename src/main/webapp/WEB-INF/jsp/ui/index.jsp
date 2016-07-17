<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="inc/header.jsp" %>
<script type="text/javascript"
	src="${ctx }/assets/js/jquery.event.drag-1.5.min.js"></script>
<script src="${ctx }/assets/js/jquery.touchSlider.js"></script>
</head>
<body>
	<div class="main">
		<c:if test="${topBannerList != null and fn:length(topBannerList) != 0 }">
			<div class="imgTouchSlider">
				<div class="main_image">
					<ul>
			       		<c:forEach items="${topBannerList}" var="t" varStatus="status">
							<li><a href="${t.contentUrl}">
								<img src='${t.preViewUrl }'>
								</a></li>
						</c:forEach>
					</ul>
					<a href="javascript:void(0);" id="btn_prev" class="btn_prev"></a> <a
						href="javascript:void(0);" id="btn_next" class="btn_next"></a>
				</div>
				<div class="flicking_con">
					<div class="flicking_inner">
						<c:if test="${topBannerList != null and fn:length(topBannerList) != 0 }">
				       		<c:forEach items="${topBannerList}" var="t" varStatus="status">
				       			<a href=""></a>
				       		</c:forEach>
						</c:if>
					</div>
				</div>
			</div>
		</c:if>
		<section class="whitebg">
			<header class="homeHeader">
				<div class="cent mt10">
					<img width="200px" alt="" src="${ctx}/assets/img/ui/index_middle.png"
						style="display: inline;">
				</div>
<!-- 				<div id="wapdsy_D01_03" class="head-icon mt10"> -->
<!-- 					<a href="user/index" class="ico-my"> <span><i></i></span> -->
<!-- 					</a> -->
<!-- 				</div> -->
			</header>
		<form class="search0620_new flexbox" name="wapSearchForm" action=""
			onsubmit="return false;" method="get" autocomplete="off">
			<div class="searbox_new">
				<div class="ipt" >
					<input id="searchKeyword" type="search" name="searchKeyword" value=""
						placeholder="" autocomplete="off"><a
						href="javascript:void(0);" class="off" style="display: none;"></a>
				</div>
				<a href="javascript:search();" class="btn" rel="nofollow"><i></i></a>
			</div>
		</form>
			<div class="bigNav" style="display: block;">
				<div class="icons chooseNav">
					<div class="flexbox chooseNav1">
						<a href="${ctx }/houseType/search/-----" class="esf"><i></i><p>户型</p></a>
						<a href="${ctx }/designPanorama/search/-----" class="cfck"><i></i><p>装修方案</p></a>
						<a href="${ctx }/product/search/--" class="zf"><i></i> <p>商品</p></a> 
						<a href="${ctx }/shop/search/--" class="sp"><i></i><p>供货商</p></a>
<!-- 						<a href="javascript:spreadNav();" class="more gather"><i></i><p>更多</p></a> -->
<!-- 						<a href="javascript:gatherNav();" class="more spread none"><i></i><p>收起</p></a> -->
					</div>
<!-- 					<div class="flexbox chooseNav2 spread none"> -->
<%-- 						<a href="${ctx }/shop/search/--" class="sp"><i></i><p>供货商</p></a> --%>
<%-- 						<a href="${ctx }/pg" class="esfpg"><i></i><p>装修预算</p></a> --%>
<!-- 					</div> -->
				</div>
				<br style="display: inline;">
			</div>
		</section>
		
		<c:if test="${informationList != null and fn:length(informationList) != 0 }">
			<section class="NewsList mt10" id="information" >
				<div class="zd-tip" style="display: none;">
					<span></span>
				</div>
				<div class="homeTitle clearfix">
					<h1>资讯</h1>
				</div>
				<ul>
		       		<c:forEach items="${informationList}" var="t" varStatus="status">
						<li <c:if test="${status.index>4}">
							 class="none"
						</c:if>><a href="${ctx }/information/view/${t.id}" >
								<div class="bord">
									<div class="lt">
										<img alt="" src="${t.preViewUrl }" style="display: inline;">
									</div>
									<div class="rt">
										<h3>${t.name }</h3>
										<p>${t.summary }</p>
									</div>
								</div>
						</a></li>
					</c:forEach>
				</ul>
				<div class="homeOption flexbox">
					<a  href="javascript:nextPage('information');" class="huanhuan">换一换</a>
					<a href="${ctx }/information/search/--">进入资讯频道</a>
				</div>
			</section>
		</c:if>

		<%@include file="inc/footer.jsp" %>
		<%@include file="inc/goHead.jsp" %>
	</div>
</body>
<script type="text/javascript">
$(function() {
	$(".imgTouchSlider").each(function(){
		initSlider(this);
	})

	$(window).resize(function() {
		fitSize();
	});
	fitSize();
});
function fitSize() {
	var bw = $("body").width();
	if (bw > 640) {
		bw = 640;
	}
	$(".main_image").height(bw * 3 / 4);

	var fw = $(".flicking_inner").children("a").length * 21;
	$(".flicking_inner").css("left", (bw - fw) * 0.5 + "px");
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
	function spreadNav() {
		$(".spread").removeClass("none");
		$(".gather").addClass("none");
	}
	function gatherNav() {
		$(".spread").addClass("none");
		$(".gather").removeClass("none");
	}
	
	
	function nextPage(elementId){
		var element = $("#"+elementId);
		var list = element.children("ul").children("li");
		var pageSize = 5;
		var curPage = parseInt(element.attr("curPage"));
		var maxPage = Math.ceil(list.length/pageSize);
		var minPage = 1;
		if(!curPage){
			curPage = minPage;
		}
		var targetPage = curPage+1;
		if(targetPage>maxPage){
			targetPage = minPage;
		}
		list.each(function(index,ele){
			if(index<targetPage*5 && index>=(targetPage-1)*5){
				$(ele).removeClass("none");
			}else{
				$(ele).addClass("none");
			}
		})
		element.attr("curPage",targetPage);
	}
	
	function search(){
		var searchKeyword = $("#searchKeyword").val();
		searchKeyword = searchKeyword.replace(/([^A-Za-z0-9\u4e00-\u9fa5\(\)_])+/g,"");
		top.location = "${ctx}/search/-"+searchKeyword+"-";
	}
</script>
</html>