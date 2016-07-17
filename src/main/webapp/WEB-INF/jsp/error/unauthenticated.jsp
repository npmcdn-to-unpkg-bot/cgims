<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("ctx", request.getContextPath());%> 
<html>
<head><title>未登录</title></head>
<body>
	抱歉！您还没有登录。
    <a href="${ctx }/index">去首页看看</a><br/><br/>
</body>
</html>