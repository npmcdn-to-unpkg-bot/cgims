<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<c:set var="inSelf" value="true"/>  
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
						<div class="box-hd">
							<h2>修改个人信息</h2>
						</div>
						<%@include file="commonBody.jsp" %>
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
	<script>
		var goBackUrl = "${ctx}/mi/index";
		
		var saveUserUrl = "${ctx}/mi/self/update/json";
		var userHeadImgUploadUrl = "${ctx}/mi/self/update/uploadHeadImg";
		var getRolesUrl = "${ctx}/mi/self/update/role/search/page/json/";

		var getUserViewUrl = "${ctx}/mi/self/update/view/json/${id}";
		
		var actionStr = "更新";
	</script>
	<%@include file="commonBottom.jsp" %>
	<%@include file="auCommonBottom.jsp" %>
	<%@include file="uvCommonBottom.jsp" %>
</html>
