<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headTitle" value="${designRing.name}"/>
<%@include file="../inc/header.jsp" %>

<script src="${ctx }/assets/tools/ring/jquery.timer.js"></script>
<link rel="stylesheet" href="${ctx }/assets/tools/ring/mobilePage.css">
<script src="${ctx }/assets/tools/ring/BevHorizontalHtml5Object.js"></script>
<script src="${ctx }/assets/tools/ring/jquery.mousewheel.min.js"></script>
		<style type="text/css" title="Default">
			body, div, h1, h2, h3, span, p {
				font-family: Verdana,Arial,Helvetica,sans-serif;
				color: #000000; 
			}
			/* fullscreen */
			html {
				height:100%;
			}
			body {
				height:100%;
				margin: 0px;
				overflow:hidden; /* disable scrollbars */
			}
			iframe{
				height:100%;
				width:100%;
			}
			.rotate90 {
			    filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=1);
			    -moz-transform: rotate(90deg);
			    -o-transform: rotate(90deg);
			    -webkit-transform: rotate(90deg);
			    transform: rotate(90deg);
			 }
		</style>
</head>
<body class="mainContent">
<div data-role="page" id="pageone" class="mainContent">
		<div data-role="content" class="mainContent" id="mainContent">
		</div>
	</div>

	<script>
	
	$(function() {

	var data = new Object();

	data.imgUrls = eval('${imgUrls}');
	data.bigImgUrls = eval('${bigImgUrls}');
	data.imgCount = data.imgUrls.length;
	data.clockwiseImg = "${clockwise}"=="false"?false:true;

	var bev = new BevHorizontalHtml5Object("mainContent");
	bev.initData(data);

	});
	</script>
</body>
</html>