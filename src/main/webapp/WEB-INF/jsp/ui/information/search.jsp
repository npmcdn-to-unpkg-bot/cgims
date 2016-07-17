<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headKeywords" value="资讯"/>
<c:set var="headTitle" value="资讯"/>
<%@include file="../inc/header.jsp" %>
<script type="text/javascript"
	src="${ctx }/assets/js/jquery.event.drag-1.5.min.js"></script>
<script src="${ctx }/assets/js/jquery.touchSlider.js"></script>
</head>
<body>
	<div class="main">
		<%@include file="../inc/homeNav.jsp" %>
		<form class="search0620_new flexbox" name="wapSearchForm" action=""
			onsubmit="return false;" method="get" autocomplete="off">
			<div class="searbox_new">
				<div class="ipt" id="wapdsy_D01_09">
					<input type="search" name="searchKeyword" id="searchKeyword" value="${searchKeyword }"
						placeholder="关键词" autocomplete="off">
						<a href="javascript:;" class="off" style="display: none;"></a>
				</div>
				<a href="javascript:;" id="searchBtn" onclick="searchAction()" class="btn" rel="nofollow"><i></i></a>
			</div>
		</form>
		<div class="resultWarp whitebg mt10 bdt">
			<h5 id="search_result_num">
				<h5>共搜索到${total }个资讯 </h5>
			</h5>
		</div>
		
		<section class="NewsList">
			<ul id="itemList">
       			<c:forEach items="${results}" var="t" varStatus="status">
					<li><a href="${ctx }/information/view/${t.id}" >
							<div class="bord">
								<div class="lt">
									<img src="${t.preViewUrl }">
								</div>
								<div class="rt">
									<h3>${t.name }</h3>
									<p>${t.summary }</p>
								</div>
							</div>
					</a></li>
       			</c:forEach>
			</ul>
		</section>

		<c:choose>
			<c:when test="${total!=0}">
				<div id="drag" class="cenBtn">
					<a class="draginner" href="javascript:void(0);">查看更多资讯</a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="searchNo">
			    	<p class="f14">暂未搜索到符合条件的资讯。</p>
				</div>
			</c:otherwise>
		</c:choose>
		<%@include file="../inc/footer.jsp" %>
		<%@include file="../inc/goHead.jsp" %>
	</div>
	<input type="hidden" id="infoType" value="${type }">
	<input type="hidden" id="total" value="${total }">
</body>
<%@include file="../inc/bottom.jsp" %>
<script>
var total=Math.ceil($("#total").val()/5);
var allowLoad = true;
var curp = 1;

	$(function() {
		initScrollEvent();
	})
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
		
		var url = "${ctx}/information/search/json/{searchKeyword}-{targetPage}-{pageSize}";
		url = url.replace("{targetPage}",curp+1);
		url = url.replace("{pageSize}",5);
		url = bindSearchData(url);
		$.ajax({
			url : url,
			dataType:'json',
			success: function(json){
				if(json.success){
					for(var i=0;i<json.results.length;i++){
						var result = json.results[i];
						var str = ' <li ><a href="${ctx}/information/view/{id}" > <div class="bord"> <div class="lt"> <img alt="" src="{preViewUrl}"> </div> <div class="rt"> <h3>{name}</h3> <p>{summary}</p> </div> </div> </a></li>';
						str = str.replace(/{id}/g,result.id);
						str = str.replace(/{preViewUrl}/g,result.preViewUrl);
						str = str.replace(/{name}/g,result.name);
						str = str.replace(/{summary}/g,result.summary);
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
				draginner.html("查看更多资讯");
				
				allowLoad = true;
				if(curp+1>=total){
					$('#drag').hide();
					allowLoad = false;
				}
			}
		});
		
	}
	function searchAction(){
		var url = "${ctx}/information/search/-{searchKeyword}-";
		url = bindSearchData(url);
// 		top.location.href = url;
		top.location = "${ctx}/information/search/-"+$("#searchKeyword").val()+"-";
	}
	function bindSearchData(url){
		var searchKeyword = $("#searchKeyword").val();
		searchKeyword = searchKeyword.replace(/([^A-Za-z0-9\u4e00-\u9fa5\(\)_])+/g,"");
		url = url.replace("{searchKeyword}",searchKeyword);
		return url;
	}

</script>
</html>