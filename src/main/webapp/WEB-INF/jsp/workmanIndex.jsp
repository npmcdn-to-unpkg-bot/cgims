<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%request.setAttribute("ctx", request.getContextPath());%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>师傅入口</title>
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <%@include file="inc/icoImport.jsp" %>
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
<iframe  frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" id="ifm" name="ifm" width="100%" src="${ctx}/assets/index/html/CGJJ.html"/>
</body>
</html>
