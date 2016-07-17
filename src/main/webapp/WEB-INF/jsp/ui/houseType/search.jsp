<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headKeywords" value="户型"/>
<c:set var="headTitle" value="户型"/>
<%@include file="../inc/header.jsp" %>
</head>
<body>
	<div class="main">
		<%@include file="../inc/homeNav.jsp" %>
		<form class="search0620_new flexbox" name="wapSearchForm" action=""
			onsubmit="return false;" method="get" autocomplete="off">
			<div class="searbox_new">
				<div class="ipt" id="wapdsy_D01_09">
					<input type="search" name="searchKeyword" id="searchKeyword" value="${searchKeyword }"
						placeholder="户型名/楼盘名" autocomplete="off">
						<a href="javascript:;" class="off" style="display: none;"></a>
				</div>
				<a href="javascript:;" id="searchBtn" onclick="searchAction()" class="btn" rel="nofollow"><i></i></a>
			</div>
		</form>
		<c:if test="${hotSearchKeywords != null and fn:length(hotSearchKeywords) != 0 }">
		<div class="pd10">
			<div id="facilities" class="taplink">
			       		<c:forEach items="${hotSearchKeywords}" var="t" varStatus="status">
							<a class="active" href="javascript:" style="overflow:hidden; white-space:nowrap; text-overflow:ellipsis" onclick="searchAction('${t.name}')" >${t.name}</a>
						</c:forEach>
		    </div>
		</div>
		</c:if>

		<section class="whitebg bdt bdb">
			<div class="searchCriteria">
				<ul id="criteriaList" class="criteriaList">
					<li >
						<p>
							<span class="js-selected"  data-name="insideArea" data-condition=""> 不限 </span>
							<span class="js-sift-condition none" data-condition=""> 不限</span>
							<span class="js-sift-condition" data-condition="1:49"> 49或以下</span>
							<span class="js-sift-condition" data-condition="50:69"> 50-69 </span>
							<span class="js-sift-condition" data-condition="70:89"> 70-89 </span>
							<span class="js-sift-condition" data-condition="90:109"> 90-109 </span>
							<span class="js-sift-condition" data-condition="110:129"> 110-129</span>
							<span class="js-sift-condition" data-condition="130:149"> 130-149</span>
							<span class="js-sift-condition" data-condition="150:199"> 150-199</span>
							<span class="js-sift-condition" data-condition="200:10000"> 200或以上</span>
						</p> <strong>套内</strong> <em class="arrowDown"></em>
					</li>
					<li >
						<p>
							<span class="js-selected"  data-name="grossFloorArea" data-condition=""> 不限 </span>
							<span class="js-sift-condition none" data-condition=""> 不限</span>
							<span class="js-sift-condition" data-condition="1:49"> 49或以下</span>
							<span class="js-sift-condition" data-condition="50:69"> 50-69 </span>
							<span class="js-sift-condition" data-condition="70:89"> 70-89 </span>
							<span class="js-sift-condition" data-condition="90:109"> 90-109 </span>
							<span class="js-sift-condition" data-condition="110:129"> 110-129</span>
							<span class="js-sift-condition" data-condition="130:149"> 130-149</span>
							<span class="js-sift-condition" data-condition="150:199"> 150-199</span>
							<span class="js-sift-condition" data-condition="200:10000"> 200或以上</span>
						</p> <strong>建筑</strong> <em class="arrowDown"></em>
					</li>
					<li class="notImportant none">
						<p>
							<span class="js-selected"  data-name="roomNum" data-condition=""> 不限 </span>
							<span class="js-sift-condition none" data-condition=""> 不限</span>
							<span class="js-sift-condition" data-condition="1"> 一居 </span>
							<span class="js-sift-condition" data-condition="2"> 二居 </span>
							<span class="js-sift-condition" data-condition="3"> 三居 </span>
							<span class="js-sift-condition" data-condition="4"> 四居 </span>
							<span class="js-sift-condition" data-condition="5"> 五居 </span>
							<span class="js-sift-condition" data-condition="6"> 五居以上 </span>
						</p> <strong>户型</strong> <em class="arrowDown"></em>
					</li>
				</ul>

				<div id="listMore" class="down" onclick="spreadList()">
					<span class="icon"></span> <span class="txt">展开更多查询条件</span>
				</div>
			</div>
		</section>
		<div class="resultWarp whitebg mt10 bdt">
			<h5 id="search_result_num">
				<h5>共搜索到${total }个户型</h5>
			</h5>
		</div>
		<section class="homeList whitebg bdb">
			<ul id="itemList">
       			<c:forEach items="${results}" var="t" varStatus="status">
				<li><a id="${t.id}" href="${ctx}/houseType/view/${t.id }">
						<div class="img">
							<img
								src="${t.housePlanUrl }">
						</div>
						<div class="txt">
							<p class="x-intro">
							<h2>${t.name }</h2>
							<span class="new">${t.realEstateProject.name }</span>
							</p>
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
			</ul>
		</section>
		<c:choose>
			<c:when test="${total!=0}">
				<div id="drag" class="cenBtn">
					<a class="draginner" href="javascript:void(0);">查看更多户型</a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="searchNo">
			    	<p class="f14">暂未搜索到符合条件的户型。</p>
				</div>
			</c:otherwise>
		</c:choose>
		<%@include file="../inc/footer.jsp" %>
		<%@include file="../inc/goHead.jsp" %>
	</div>
	<input type="hidden" id="resultType" value="户型">
	<input type="hidden" id="total" value="${total }">
</body>
<%@include file="../inc/bottom.jsp" %>
<script>
var total=Math.ceil($("#total").val()/5);
var allowLoad = true;
var curp = 1;

	$(function() {
		initEvent();
		if("${roomNum}"){
			setItemValue("roomNum","${roomNum}");
		}
		if("${grossFloorArea}"){
			setItemValue("grossFloorArea","${grossFloorArea}");
		}
		if("${insideArea}"){
			setItemValue("insideArea","${insideArea}");
		}
	})
	
	function initEvent() {
		initScrollEvent();
		$("#criteriaList").children("li").children("em").click(function() {
			spreadItem(this);
		});
		$("#criteriaList").children("li").children("p").children(
				".js-sift-condition").click(function() {
			selectItem(this);
		})
		$("#search_condition_order").change(function(){
			searchAction();
		})
	}
	function initScrollEvent(){
		$("body").scrollTop(1);
		if(total<=1 || curp+1>=total){
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
	function spreadList() {
		var btn = $("#listMore");
		if (btn.hasClass("down")) {
			btn.removeClass("down");
			btn.addClass("up");
			btn.find(".txt").html("收起更多查询条件");
			$("#criteriaList").children("li.notImportant").removeClass("none");
		} else {
			btn.removeClass("up");
			btn.addClass("down");
			btn.find(".txt").html("展开更多查询条件");
			$("#criteriaList").children("li.notImportant").addClass("none");
		}
	}
	function spreadItem(ele) {
		var element = $(ele);
		var li = element.parents("li:first");
		if (element.hasClass("arrowDown")) {
			element.removeClass("arrowDown");
			element.addClass("arrowUp");
			li.addClass("spread");
		} else {
			element.addClass("arrowDown");
			element.removeClass("arrowUp");
			li.removeClass("spread");
		}
	}
	function selectItem(ele) {
		var element = $(ele);
		element.siblings(".js-sift-condition").removeClass("none");
		element.addClass("none");
		element.siblings(".js-selected").html(element.html());
		element.siblings(".js-selected").attr("data-condition",element.attr("data-condition"));
		searchAction();
	}
	function setItemValue(dataName,dataCondition){
		if(dataCondition){
			var element = $("[data-name='"+dataName+"']");
			element.siblings().each(function(){
				if($(this).attr("data-condition")==dataCondition){
					$(this).addClass("none");
					element.html($(this).html());
				}else{
					$(this).removeClass("none");
				}
			});
			element.attr("data-condition",dataCondition);
		}
	}
	function load(){
		var draginner=$('.draginner');
		if(draginner.hasClass("loading")){
			return;
		}
		if(curp+1>=total){
			allowLoad = false;
			$('#drag').hide();
			return;
		}
		allowLoad = false;
		draginner.css('padding-left','10px');
		draginner.addClass("loading");
		draginner.html("正在加载...");
		var url = "${ctx}/houseType/search/json/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-{targetPage}-{pageSize}-";
		url = bindSearchData(url);
		url = url.replace("{targetPage}",curp+1);
		url = url.replace("{pageSize}",5);
		$.ajax({
			url : url,
			dataType:'json',
			success: function(json){
				if(json.success){
					for(var i=0;i<json.results.length;i++){
						var result = json.results[i];
						var str = '<li><a id="{id}" href="${ctx}/houseType/view/{id}"> <div class="img"> <img src="{housePlanUrl}"> </div> <div class="txt"> <p class="x-intro"> <h2>{name}</h2> <span class="new">{realEstateProjectName}</span> </p> <p>{numMsgStr}</p>  <p>{areaMsgStr}</p> </div> </a></li>';
						var numMsgStr = "";
						if(result.roomNum && result.roomNum>0){
							numMsgStr+=result.roomNum+'室';
						}
						if(result.hallNum && result.hallNum>0){
							numMsgStr+=' '+result.hallNum+'厅';
						}
						if(result.toiletNum && result.toiletNum>0){
							numMsgStr+=' '+result.toiletNum+'卫';
						}
						if(result.kitchenNum && result.kitchenNum>0){
							numMsgStr+=' '+result.kitchenNum+'厨';
						}
						var areaMsgStr = "";
						if(result.grossFloorArea && result.grossFloorArea>0){
							areaMsgStr+=' 建筑约'+parseFloat(result.grossFloorArea).toFixed(2); +'㎡';
						}
						if(result.insideArea && result.insideArea>0){
							areaMsgStr+=' 套内约'+parseFloat(result.insideArea).toFixed(2); +'㎡';
						}
						str = str.replace(/{id}/g,result.id);
						str = str.replace(/{housePlanUrl}/g,result.housePlanUrl);
						str = str.replace(/{name}/g,result.name);
						str = str.replace(/{numMsgStr}/g,numMsgStr);
						str = str.replace(/{areaMsgStr}/g,areaMsgStr);
						str = str.replace(/{realEstateProjectName}/g,result.realEstateProject.name);
						$('#itemList').append(str);
					}
				}else if(json.msg){
					alert(json.msg);
				}
				curp=curp+1;
			},
			error:function(e){
				alert("搜索失败！");
			},
			complete:function(e){
				draginner.css('padding-left','0px');
				draginner.removeClass("loading");
				draginner.html("查看更多"+$("#resultType").val());
				
				allowLoad = true;
				if(curp+1>=total){
					$('#drag').hide();
					allowLoad = false;
				}
			}
		}); 
	}
	function searchAction(name){
		if(name){
			$("#searchKeyword").val(name);
		}
		var url = "${ctx}/houseType/search/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-";
		url = bindSearchData(url);
// 		top.location.href = url;
// 		top.location = "${ctx}/houseType/search/-"+$("#searchKeyword").val()+"-";
		top.location = url;
	}
	function bindSearchData(url){
		var searchKeyword = $("#searchKeyword").val();
		searchKeyword = searchKeyword.replace(/([^A-Za-z0-9\u4e00-\u9fa5\(\)_])+/g,"");
		url = url.replace("{searchKeyword}",searchKeyword);

		var roomNum = $("[data-name='roomNum']").attr("data-condition");
		var insideArea = $("[data-name='insideArea']").attr("data-condition");
		var grossFloorArea = $("[data-name='grossFloorArea']").attr("data-condition");
		url = url.replace("{roomNum}",roomNum);
		url = url.replace("{grossFloorArea}",grossFloorArea);
		url = url.replace("{insideArea}",insideArea);
		
		return url;
	}
</script>
</html>