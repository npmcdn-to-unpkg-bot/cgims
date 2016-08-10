<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%request.setAttribute("ctx", request.getContextPath());%>
<!DOCTYPE html>
<html>
<head>
    <meta charset=utf-8>
    <title>vue-cgims</title>
    <script>window.__BASE_PATH__ = 'http://localhost:8080'</script>
    <%--<script>window.__BASE_PATH__ = 'http://www.chengongjiaju.cn'</script>--%>
    <script>
        window.__LOGIN_USER__ = ${loginUserMap};
        window.__PROVINCES__ = ${provinces};
        window.__PROVINCE_NAMES__ = "${provinceNames}";

    </script>

    <link rel=stylesheet href=${ctx}/assets/css/admin.css>
    <link rel=stylesheet href=${ctx}/assets/font-awesome/css/font-awesome.css>
    <link href=${ctx}/assets/css/app.css rel=stylesheet>
</head>
<body>
<app></app>
<script type=text/javascript src=${ctx}/assets/js/manifest.js></script>
<script type=text/javascript src=${ctx}/assets/js/vendor.js></script>
<script type=text/javascript src=${ctx}/assets/js/app.js></script>
</body>
<script src="assets/my97datepicker/WdatePicker.js"></script>
</html>