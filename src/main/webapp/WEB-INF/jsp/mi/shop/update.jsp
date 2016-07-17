<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<c:set var="inUpdate" value="true"/>    
<c:set var="commonHeadUrl" value="/shop/update"/> 

<c:set var="toProductViewUrl" value="${ctx}/mi${commonHeadUrl }/product/view/${id}/{{id}}"/>    
<c:set var="productSearchPageJsonUrl" value="${ctx}/mi${commonHeadUrl }/product/search/page/json/"/>    

<c:set var="toProductAddUrl" value="${ctx}/mi${commonHeadUrl }/product/add/${id}"/> 
<c:set var="toProductUpdateUrl" value="${ctx}/mi${commonHeadUrl }/product/update/${id}/{{id}}"/>   
<c:set var="productDeleteBatchJsonUrl" value="${ctx}/mi${commonHeadUrl }/product/delete/batch/json"/>    
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
			<%@include file="commonBody.jsp" %>
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
		var goBackUrl = "${ctx}/mi/shop/search";

		var shopPreViewUploadUrl = "${ctx}/mi${commonHeadUrl}/uploadPreView";
		var saveShopUrl = "${ctx}/mi/shop/update/json";
		
		var getShopViewUrl = "${ctx}/mi${commonHeadUrl }/view/json/${id}";
		
		var actionStr = "更新";
	</script>
	<%@include file="commonBottom.jsp" %>
	<%@include file="auCommonBottom.jsp" %>
	<%@include file="uvCommonBottom.jsp" %>
</html>