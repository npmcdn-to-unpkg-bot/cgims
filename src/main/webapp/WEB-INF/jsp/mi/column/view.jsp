<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<c:set var="inView" value="true"/>  
<c:choose> 
  <c:when test="${fn:contains(uri,'/mi/table/view') }">
		<c:set var="parentHeadUrl" value="/table/view"/>
  </c:when> 
  <c:when test="${fn:contains(uri,'/mi/table/update') }">   
		<c:set var="parentHeadUrl" value="/table/update"/>
  </c:when> 
  <c:otherwise>   
		<c:set var="parentHeadUrl" value=""/>
   </c:otherwise> 
</c:choose> 
<c:set var="commonHeadUrl" value="${parentHeadUrl }/column/view"/>

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
						<div class="box-hd" onclick="openCloseColumnDetail('js-column-detail-container')">
							<h2>列详情</h2>
						</div>
						<%@include file="commonBody.jsp" %>
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
		
		var goBackUrl = "${ctx}/mi${parentHeadUrl}/${tableId}";

		var getColumnViewUrl = "${ctx}/mi${commonHeadUrl}/json/${id}";
	</script>
	<%@include file="commonBottom.jsp" %>
	<%@include file="uvCommonBottom.jsp" %>
	<script>
		openCloseColumnDetail("js-column-detail-container");
	</script>
</html>