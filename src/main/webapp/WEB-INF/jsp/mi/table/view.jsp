<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<c:set var="inView" value="true"/>
<c:set var="commonHeadUrl" value="/table/view"/>

<c:set var="toColumnViewUrl" value="${ctx}/mi${commonHeadUrl }/column/view/${id}/{{id}}"/>    
<c:set var="columnSearchPageJsonUrl" value="${ctx}/mi${commonHeadUrl }/column/search/page/json/"/>    

<c:set var="toRelationshipViewUrl" value="${ctx}/mi${commonHeadUrl }/relationship/view/${id}/{{id}}"/>    
<c:set var="relationshipSearchPageJsonUrl" value="${ctx}/mi${commonHeadUrl }/relationship/search/page/json/"/>    
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
		var form = $(".form");
		form.find("input").prop("disabled","disabled");
		form.find("textarea").prop("disabled","disabled");
		
		var goBackUrl = "${ctx}/mi/table/search";

		var getTableViewUrl = "${ctx}/mi/table/view/json/${id}";
		
	</script>
	<%@include file="commonBottom.jsp" %>
	<%@include file="uvCommonBottom.jsp" %>
	<script>
		openCloseTableDetail("js-table-detail-container");
	</script>
</html>