<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<c:set var="inUpdate" value="true"/>  
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
<c:set var="commonHeadUrl" value="${parentHeadUrl }/houseType/update"/>

<c:set var="toDesignImageSearchPageJsonUrl" value="${ctx}/mi${commonHeadUrl }/designImage/search/page/json/"/>   
<c:set var="toDesignPanoramaSearchPageJsonUrl" value="${ctx}/mi${commonHeadUrl }/designPanorama/search/page/json/"/>   
<c:set var="toDesignRingSearchPageJsonUrl" value="${ctx}/mi${commonHeadUrl }/designRing/search/page/json/"/>   
 
<c:set var="toDesignImageViewUrl" value="${ctx}/mi${commonHeadUrl }/designImage/view/${realEstateProjectId }/${id }/{{id}}"/>   
<c:set var="toDesignPanoramaViewUrl" value="${ctx}/mi${commonHeadUrl }/designPanorama/view/${realEstateProjectId }/${id }/{{id}}"/>   
<c:set var="toDesignRingViewUrl" value="${ctx}/mi${commonHeadUrl }/designRing/view/${realEstateProjectId }/${id }/{{id}}"/>   

<c:set var="toDesignImageAddUrl" value="${ctx}/mi${commonHeadUrl }/designImage/add/${realEstateProjectId }/${id }"/>   
<c:set var="toDesignPanoramaAddUrl" value="${ctx}/mi${commonHeadUrl }/designPanorama/add/${realEstateProjectId }/${id }"/>   
<c:set var="toDesignRingAddUrl" value="${ctx}/mi${commonHeadUrl }/designRing/add/${realEstateProjectId }/${id }"/>  

<c:set var="toDesignImageUpdateUrl" value="${ctx}/mi${commonHeadUrl }/designImage/update/${realEstateProjectId }/${id }/{{id}}"/>   
<c:set var="toDesignPanoramaUpdateUrl" value="${ctx}/mi${commonHeadUrl }/designPanorama/update/${realEstateProjectId }/${id }/{{id}}"/>   
<c:set var="toDesignRingUpdateUrl" value="${ctx}/mi${commonHeadUrl }/designRing/update/${realEstateProjectId }/${id }/{{id}}"/> 

<c:set var="designImageDeleteBatchJsonUrl" value="${ctx}/mi${commonHeadUrl }/designImage/delete/batch/json"/>    
<c:set var="designPanoramaDeleteBatchJsonUrl" value="${ctx}/mi${commonHeadUrl }/designPanorama/delete/batch/json"/>    
<c:set var="designRingDeleteBatchJsonUrl" value="${ctx}/mi${commonHeadUrl }/designRing/delete/batch/json"/>    

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
							<h2>编辑户型</h2>
						</div>
						<%@include file="commonBody.jsp" %>
					</div>
					<div class="box">
						<div class="box-hd" onclick="openCloseHTDetail('js-ht-images-container')">
							<h2>管理户型图片</h2>
						</div>
						<%@include file="imageList.jsp" %>
					</div>
					<div class="box">
						<div class="box-hd" onclick="openCloseHTDetail('js-ht-panos-container')">
							<h2>管理户型全景</h2>
						</div>
						<%@include file="panoList.jsp" %>
					</div>
					<div class="box">
						<div class="box-hd" onclick="openCloseHTDetail('js-ht-rings-container')">
							<h2>管理户型三维</h2>
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
		var goBackUrl = "${ctx}/mi${parentHeadUrl}/${realEstateProjectId}";
		
		var houseTypeHousePlanUploadUrl = "${ctx}/mi${commonHeadUrl}/uploadHousePlan";
		var saveHouseTypeUrl = "${ctx}/mi${commonHeadUrl}/json";

		var getHouseTypeViewUrl = "${ctx}/mi${commonHeadUrl}/view/json/${id}";
		
		var actionStr = "更新";
	</script>
	<%@include file="commonBottom.jsp" %>
	<%@include file="auCommonBottom.jsp" %>
	<%@include file="uvCommonBottom.jsp" %>
</html>