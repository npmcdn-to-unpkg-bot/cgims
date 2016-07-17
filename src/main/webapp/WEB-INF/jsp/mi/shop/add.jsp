<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<c:set var="inAdd" value="true"/>  
<c:set var="commonHeadUrl" value="/shop/add"/> 
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
		var saveShopUrl = "${ctx}/mi/shop/add/json";
		
		var getShopViewUrl = "${ctx}/mi${commonHeadUrl}/view/json/${id}";
		
		var actionStr = "添加";
	</script>
	<%@include file="commonBottom.jsp" %>
	<%@include file="auCommonBottom.jsp" %>
	<script>
		$(".js-shop-detail-container").show();
	</script>
</html>