<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../bottom.jsp" %>
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
		if("${enName }"){
			setItemValue("resultType","${enName}");
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
		var url = "${ctx}/{resultType}/search/json/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-{targetPage}-{pageSize}-";
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
						var str = '<li><a id="{id}" href="${ctx}/${enName}/view/{id}"> <div class="img"> <img src="{preViewUrl}"> </div> <div class="txt"> <p class="x-intro"> <h2>{name}</h2></p> <p>{numMsgStr}</p>  <p>{areaMsgStr}</p> </div> </a></li>';
						
						var numMsgStr = "";
						if(result.houseType.roomNum && result.houseType.roomNum>0){
							numMsgStr+=result.houseType.roomNum+'室';
						}
						if(result.houseType.hallNum && result.houseType.hallNum>0){
							numMsgStr+=' '+result.houseType.hallNum+'厅';
						}
						if(result.houseType.toiletNum && result.houseType.toiletNum>0){
							numMsgStr+=' '+result.houseType.toiletNum+'卫';
						}
						if(result.houseType.kitchenNum && result.houseType.kitchenNum>0){
							numMsgStr+=' '+result.houseType.kitchenNum+'厨';
						}
						var areaMsgStr = "";
						if(result.houseType.grossFloorArea && result.houseType.grossFloorArea>0){
							areaMsgStr+=' 建筑约'+parseFloat(result.houseType.grossFloorArea).toFixed(2) +'㎡';
						}
						if(result.houseType.insideArea && result.houseType.insideArea>0){
							areaMsgStr+=' 套内约'+parseFloat(result.houseType.insideArea).toFixed(2) +'㎡';
						}
						
						var preViewUrl = "";
						if(result.contentUrl){
							preViewUrl = result.contentUrl;
						}
						if(result.preViewUrl){
							preViewUrl = result.preViewUrl;
						}
						str = str.replace(/{id}/g,result.id);
						str = str.replace(/{preViewUrl}/g,preViewUrl);
						str = str.replace(/{name}/g,result.name);
						str = str.replace(/{numMsgStr}/g,numMsgStr);
						str = str.replace(/{areaMsgStr}/g,areaMsgStr);
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
	function searchAction(){
		var url = "${ctx}/{resultType}/search/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-";
		url = bindSearchData(url);
		top.location = url;
	}
	function bindSearchData(url){
		var searchKeyword = $("#searchKeyword").val();
		searchKeyword = searchKeyword.replace(/([^A-Za-z0-9\u4e00-\u9fa5\(\)_])+/g,"");
		url = url.replace("{searchKeyword}",searchKeyword);

		var roomNum = $("[data-name='roomNum']").attr("data-condition");
		var insideArea = $("[data-name='insideArea']").attr("data-condition");
		var grossFloorArea = $("[data-name='grossFloorArea']").attr("data-condition");
		var resultType = $("[data-name='resultType']").attr("data-condition");
		url = url.replace("{roomNum}",roomNum);
		url = url.replace("{grossFloorArea}",grossFloorArea);
		url = url.replace("{insideArea}",insideArea);
		url = url.replace("{resultType}",resultType);
		
		return url;
	}
</script>