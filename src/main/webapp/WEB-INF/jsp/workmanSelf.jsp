<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/2
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>师傅信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
</head>
<body>
${workmanLoginId}
<input type="button" value="logout" onclick="logout()"/>
</body>
<script>
function logout(){

    $.ajax({
//                url: "VerifyLoginServlet", // 进行二次验证
        url:"/workman/logout",
        type: "post",
        dataType: "json",
        success: function (data) {
        }
    });
}
</script>
</html>
