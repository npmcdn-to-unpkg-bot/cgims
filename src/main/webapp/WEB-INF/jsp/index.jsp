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
    <script>
        window._LOGIN_USER__ = ${loginUserMap};
    </script>
    <%--<script>window.__BASE_PATH__ = 'http://www.chengongjiaju.cn'</script>--%>

    <link rel=stylesheet href=${ctx}/assets/css/admin.css>
    <link rel=stylesheet href=${ctx}/assets/font-awesome/css/font-awesome.css>
    <link href=${ctx}/assets/css/app.cd84a3c55dd1c8951027bec78c9f85ec.css rel=stylesheet>
    <%--<link rel=stylesheet href=${ctx}/assets/css/admin.css>--%>
    <%--<link rel=stylesheet href=${ctx}/assets/font-awesome/css/font-awesome.css>--%>
    <%--&lt;%&ndash;<link href=${ctx}/assets/css/app.bc873407d78efe89e343f14ed87ce9aa.css rel=stylesheet>&ndash;%&gt;--%>
    <%--<link href=${ctx}/assets/css/app.bc873407d78efe89e343f14ed87ce9aa.css rel=stylesheet>--%>
</head>
<body>
<app></app>
<script type=text/javascript src=${ctx}/assets/js/manifest.ee8cc8c43bd474137e98.js></script>
<script type=text/javascript src=${ctx}/assets/js/vendor.8aab4ae2181156315c55.js></script>
<script type=text/javascript src=${ctx}/assets/js/app.db998203743db3091ade.js></script>
<%--<script type=text/javascript src=${ctx}/assets/js/manifest.a308bc871126633fd20d.js></script>--%>
<%--<script type=text/javascript src=${ctx}/assets/js/vendor.c09ab21f34fb127763ef.js></script>--%>
<%--<script type=text/javascript src=${ctx}/assets/js/app.b91f624cdfec2aacbc42.js></script>--%>
<%--<script type=text/javascript src=${ctx}/assets/js/manifest.c712c139209b818c0554.js></script>--%>
<%--<script type=text/javascript src=${ctx}/assets/js/vendor.85403824a07bda6261d7.js></script>--%>
<%--<script type=text/javascript src=${ctx}/assets/js/app.1bed5544c5fb7f423b1e.js></script>--%>
</body>
</html>