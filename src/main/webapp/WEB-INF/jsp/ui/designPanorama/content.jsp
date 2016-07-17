<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headTitle" value="${information.name}"/>
<%@include file="../inc/header.jsp" %>
<style type="text/css" title="Default">
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
		</style>
</head>
<body>
<c:choose> 
  <c:when test="${designPanorama.outLinkUrl != null and designPanorama.outLinkUrl != ''}">   
<iframe src="${designPanorama.outLinkUrl }" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" id="ifm" name="ifm" width="100%"></iframe>
  </c:when> 
  <c:when test="${designPanorama.contentUrl != null and designPanorama.contentUrl != ''}">   
<iframe src="${designPanorama.contentUrl }" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" id="ifm" name="ifm" width="100%"></iframe>
  </c:when> 
  <c:otherwise>   
  	未放置内容。
  </c:otherwise> 
</c:choose> 
</body>
<script>

</script>
</html>