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
//        安徽省
//        澳门特别行政区
//        北京市
//        重庆市
//        福建省
//        甘肃省
//        广东省
//        广西壮族自治区
//        贵州省
//        海南省
//        河北省
//        河南省
//        黑龙江省
//        湖北省
//        湖南省
//        吉林省
//        江苏省
//        江西省
//        辽宁省
//        内蒙古自治区
//        宁夏回族自治区
//        青海省
//        山东省
//        山西省
//        陕西省
//        上海市
//        四川省
//        台湾省
//        天津市
//        西藏自治区
//        香港特别行政区
//        新疆维吾尔自治区
//        云南省
//        浙江省
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