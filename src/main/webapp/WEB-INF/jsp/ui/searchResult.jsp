<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headKeywords" value="搜索"/>
<c:set var="headTitle" value="搜索"/>
<%@include file="inc/header.jsp" %>
</head>
<body>
	<div class="main whitebg">
		<%@include file="inc/homeNav.jsp" %>

		<form class="search0620_new flexbox" name="wapSearchForm" action=""
			onsubmit="return false;" method="get" autocomplete="off">
			<div class="searbox_new">
				<div class="ipt" >
					<input id="searchKeyword" type="search" name="searchKeyword" value="${searchKeyword }"
						placeholder="楼盘名/户型名/地名等" autocomplete="off"><a
						href="javascript:void(0);" class="off" style="display: none;"></a>
				</div>
				<a href="javascript:search();" class="btn" rel="nofollow"><i></i></a>
			</div>
		</form>
		<div class="searList"  >
			<ul>
				<div class="searTips">共${totalNum }个搜索结果，请选择类别查看</div>
				<li><a href="${ctx }/information/search/-${searchKeyword }-"><span class="flor f999">${informationNum }条</span><span
						class="searchListName" >资讯</span></a></li>
				<li><a href="${ctx }/houseType/search/-${searchKeyword }----"><span class="flor f999">${houseTypeNum }条</span><span
						class="searchListName" >户型</span></a></li>
				<li><a href="${ctx }/designPanorama/search/-${searchKeyword }----"><span class="flor f999">${designPanoramaNum }条</span><span
						class="searchListName" >效果全景</span></a></li>
				<li><a href="${ctx }/designRing/search/-${searchKeyword }----"><span class="flor f999">${designRingNum }条</span><span
						class="searchListName" >效果三维</span></a></li>
				<li><a href="${ctx }/designImage/search/-${searchKeyword }----"><span class="flor f999">${designImageNum }条</span><span
						class="searchListName" >效果图片</span></a></li>
				<li><a href="${ctx }/shop/search/-${searchKeyword }-"><span class="flor f999">${shopNum }条</span><span
						class="searchListName" >商铺</span></a></li>
				<li><a href="${ctx }/product/search/-${searchKeyword }-"><span class="flor f999">${productNum }条</span><span
						class="searchListName" >商品</span></a></li>
			</ul>
		</div>
	</div>
</body>
<script>
	function search(){
		var searchKeyword = $("#searchKeyword").val();
		searchKeyword = searchKeyword.replace(/([^A-Za-z0-9\u4e00-\u9fa5\(\)_])+/g,"");
		top.location = "${ctx}/search/-"+searchKeyword+"-";
	}
</script>
</html>