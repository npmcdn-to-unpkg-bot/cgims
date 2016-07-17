<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<c:set var="inView" value="true"/>  
<c:choose> 
  <c:when test="${fn:contains(uri,'/mi/realEstateProject/view') }"> 
		<c:set var="parentHeadUrl" value="/realEstateProject/view"/>
  </c:when> 
  <c:when test="${fn:contains(uri,'/mi/realEstateProject/update') }">   
		<c:set var="parentHeadUrl" value="/realEstateProject/update"/>
  </c:when> 
  <c:otherwise>   
		<c:set var="parentHeadUrl" value=""/>
   </c:otherwise> 
</c:choose> 
<c:set var="commonHeadUrl" value="${parentHeadUrl }/houseType/view"/>

<c:set var="toDesignImageSearchPageJsonUrl" value="${ctx}/mi${commonHeadUrl }/designImage/search/page/json/"/>   
<c:set var="toDesignPanoramaSearchPageJsonUrl" value="${ctx}/mi${commonHeadUrl }/designPanorama/search/page/json/"/>   
<c:set var="toDesignRingSearchPageJsonUrl" value="${ctx}/mi${commonHeadUrl }/designRing/search/page/json/"/>   
 
<c:set var="toDesignImageViewUrl" value="${ctx}/mi${commonHeadUrl }/designImage/view/${realEstateProjectId }/${id }/{{id}}"/>   
<c:set var="toDesignPanoramaViewUrl" value="${ctx}/mi${commonHeadUrl }/designPanorama/view/${realEstateProjectId }/${id }/{{id}}"/>   
<c:set var="toDesignRingViewUrl" value="${ctx}/mi${commonHeadUrl }/designRing/view/${realEstateProjectId }/${id }/{{id}}"/>   

<!DOCTYPE html>
<html>
	<head>
         <%@include file="../inc/header.jsp" %>
		<title>后台管理</title>
	</head>
	<body>
		
		<!-- 头部导航条开始     -->
		<%@include file="../inc/nav.jsp" %>
		<!-- 头部导航条结束     -->
		<div class="clearfix"></div>
		
		<div class="container">
			<!-- 右边内容区域开始     -->
			<div class="main skin">
				<div class="content">
					<div class="box">
						<div class="box-hd" onclick="openCloseHTDetail('js-ht-detail-container')">
							<h2>户型详情</h2>
						</div>
						<%@include file="commonBody.jsp" %>
					</div>
					<div class="box">
						<div class="box-hd" onclick="openCloseHTDetail('js-ht-images-container')">
							<h2>效果图片</h2>
						</div>
						<%@include file="imageList.jsp" %>
					</div>
					<div class="box">
						<div class="box-hd" onclick="openCloseHTDetail('js-ht-panos-container')">
							<h2>效果全景</h2>
						</div>
						<%@include file="panoList.jsp" %>
					</div>
					<div class="box">
						<div class="box-hd" onclick="openCloseHTDetail('js-ht-rings-container')">
							<h2>效果三维</h2>
						</div>
						<%@include file="ringList.jsp" %>
					</div>
					<div class="form-actions">
			  			<button type="reset" class="btn cancle" >返回</button>
					</div>
				</div>
			</div>
			<!-- 右边内容区域结束     -->
			
			<!-- 左边侧边栏区域开始     -->
			<div class="slider skin">
				<div class="clearfix">&nbsp;</div>
				<div class="clearfix">&nbsp;</div>
				<%@include file="../inc/left.jsp" %>
			</div>
			
			<!-- 左边侧边栏区域结束     -->
		</div>
		<!-- 底部区域开始     -->
		<%@include file="../inc/footer.jsp" %>
		<!-- 底部区域结束     -->
		
	</body>
	<script type="text/javascript">
		var form = $(".form");
		form.find("input").prop("disabled","disabled");
		form.find("textarea").prop("disabled","disabled");
		
		var goBackUrl = "${ctx}/mi${parentHeadUrl}/${realEstateProjectId}";

		var getHouseTypeViewUrl = "${ctx}/mi${commonHeadUrl}/json/${id}";
	</script>
	<%@include file="commonBottom.jsp" %>
	<%@include file="uvCommonBottom.jsp" %>
	<script>
		openCloseHTDetail("js-ht-detail-container");
	</script>
</html>