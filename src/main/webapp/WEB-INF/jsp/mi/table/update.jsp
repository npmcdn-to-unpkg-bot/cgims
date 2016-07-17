<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<c:set var="inUpdate" value="true"/>    
<c:set var="commonHeadUrl" value="/table/update"/> 

<c:set var="toColumnViewUrl" value="${ctx}/mi${commonHeadUrl }/column/view/${id}/{{id}}"/>    
<c:set var="columnSearchPageJsonUrl" value="${ctx}/mi${commonHeadUrl }/column/search/page/json/"/>    

<c:set var="toColumnAddUrl" value="${ctx}/mi${commonHeadUrl }/column/add/${id}"/> 
<c:set var="toColumnUpdateUrl" value="${ctx}/mi${commonHeadUrl }/column/update/${id}/{{id}}"/>   
<c:set var="columnDeleteBatchJsonUrl" value="${ctx}/mi${commonHeadUrl }/column/delete/batch/json"/>    



<c:set var="toRelationshipViewUrl" value="${ctx}/mi${commonHeadUrl }/relationship/view/${id}/{{id}}"/>    
<c:set var="relationshipSearchPageJsonUrl" value="${ctx}/mi${commonHeadUrl }/relationship/search/page/json/"/>    

<c:set var="toRelationshipAddUrl" value="${ctx}/mi${commonHeadUrl }/relationship/add/${id}"/> 
<c:set var="toRelationshipUpdateUrl" value="${ctx}/mi${commonHeadUrl }/relationship/update/${id}/{{id}}"/>   
<c:set var="relationshipDeleteBatchJsonUrl" value="${ctx}/mi${commonHeadUrl }/relationship/delete/batch/json"/>   
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
		var goBackUrl = "${ctx}/mi/table/search";

		var saveTableUrl = "${ctx}/mi/table/update/json";
		
		var getTableViewUrl = "${ctx}/mi${commonHeadUrl }/view/json/${id}";
		
		var actionStr = "更新";
	</script>
	<%@include file="commonBottom.jsp" %>
	<%@include file="auCommonBottom.jsp" %>
	<%@include file="uvCommonBottom.jsp" %>
</html>