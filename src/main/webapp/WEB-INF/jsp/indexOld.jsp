<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%request.setAttribute("ctx", request.getContextPath());%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
    <link href="${ctx }/assets/img/ui/favicon.ico" rel="icon" type="image/x-icon"/>
    <link href="${ctx }/assets/img/ui/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
    <style>
        .text{
            width:300px;
        }
    </style>
</head>
<body>
${userName}
<br/>
<form method="POST" action="${ctx}/order/upload/logistics" ENCTYPE="multipart/form-data">
    <input type="file" name="theFile" />
    <input type="submit" value="uploadLogisticsImg" />
</form>
id:<input type="text" id="keyword1" class="text"/><br/>
v1:<input type="text" id="keyword2" class="text"/><br/>
v2:<input type="text" id="keyword3" class="text"/><br/>
v3:<input type="text" id="keyword4" class="text"/><br/>
v4:<input type="text" id="keyword5" class="text"/><br/>
<input type="button" value="testGET" onclick="test('GET')"/>
<input type="button" value="testPOST" onclick="test('POST')"/>
<input type="button" value="testPUT" onclick="test('PUT')"/>
<input type="button" value="testPATCH" onclick="test('PATCH')"/>
<input type="button" value="testDELETE" onclick="test('DELETE')"/>
<br/>
<input type="button" value="queryPermission" onclick="queryPermission()"/>
<input type="button" value="test2" onclick="test2()"/>
<br/>
<input type="button" value="queryRole" onclick="queryRole()"/>
<input type="button" value="getRole" onclick="getRole()"/>
<input type="button" value="addRole" onclick="addRole()"/>
<input type="button" value="updateRole" onclick="updateRole()"/>
<input type="button" value="deleteRole" onclick="deleteRole()"/>
<br/>
<input type="button" value="queryUser" onclick="queryUser()"/>
<input type="button" value="getUser" onclick="getUser()"/>
<input type="button" value="addUser" onclick="addUser()"/>
<input type="button" value="updateUser" onclick="updateUser()"/>
<input type="button" value="deleteUser" onclick="deleteUser()"/>
<input type="button" value="batchUser" onclick="batch('user')"/>
<input type="button" value="login" onclick="login()"/>
<input type="button" value="logout" onclick="logout()"/>
<br/>
<input type="button" value="queryWorkman" onclick="query('workman')"/>
<input type="button" value="getWorkman" onclick="get('workman')"/>
<input type="button" value="addWorkman" onclick="add('workman')"/>
<input type="button" value="updateWorkman" onclick="update('workman')"/>
<input type="button" value="deleteWorkman" onclick="del('workman')"/>
<input type="button" value="batchWorkman" onclick="batch('workman')"/>
<br/>
<input type="button" value="queryOrder" onclick="query('order')"/>
<input type="button" value="getOrder" onclick="get('order')"/>
<input type="button" value="addOrder" onclick="add('order')"/>
<input type="button" value="updateOrder" onclick="update('order')"/>
<input type="button" value="deleteOrder" onclick="del('order')"/>
<input type="button" value="batchOrder" onclick="batch('order')"/>
<br/>
<input type="button" value="analysisOrderCount" onclick="analysis('orderCount')"/>
<input type="button" value="analysisIncome" onclick="analysis('income')"/>
<input type="button" value="analysisExpenditure" onclick="analysis('expenditure')"/>
<input type="button" value="analysisProfit" onclick="analysis('profit')"/>
<input type="button" value="analysisProfitMargin" onclick="analysis('profitMargin')"/>
<br/>
<span id="content">Hello World!wawa</span>
</body>
</html>
<script>
    function analysis(name){
        var url = "${ctx}/analysis/"+name;
        var data = {"creatorId": getKeyword(),"serviceType":getKeyword2(),"beginTime":getKeyword3(),"endTime":getKeyword4()};
        var type = "GET";
        normalAjax(url, type, data);
    }
    function query(obj){
        var url = "${ctx}/"+obj;
        var data = {"searchKeyword": getKeyword(), targetPage: 1, pageSize: 10};
        var type = "GET";
        normalAjax(url, type, data);
    }
    function get(obj){
        var url = "${ctx}/"+obj+"/"+getKeyword();
        var type = "GET";
        normalAjax(url, type, null);
    }
    function add(obj){
        var url = "${ctx}/"+obj;
        var data = {};
        switch (obj){
            case "user":
                data.loginName = getKeyword();
                data.password = getKeyword2();
                data.roleIds = getKeyword3();
                data.description = "测试用户描述";
                break;
            case "order":
                data.orderNumber = getKeyword2();
                data.workmanId = getKeyword3();
        }
        var type = "POST";
        normalAjax(url, type, data);
    }
    function update(obj){
        var url = "${ctx}/"+obj+"/"+getKeyword2();
        var data = {};
        data.id = getKeyword2();
        data.name = getKeyword();
        data.description = "测试角色描述3";
        data.permissionIds = getKeyword3();
        var type = "PATCH";
        normalAjax(url, type, data);
    }
    function del(obj){
        var url = "${ctx}/"+obj+"/"+getKeyword();
        var type = "DELETE";
        normalAjax(url, type, null);
    }
    function batch(obj){
        var url = "${ctx}/"+obj+"/batch";
        var type = "POST";
        var data = {};
        data.ids = "aa,bb";
        normalAjax(url, type, data);
    }
    function login(){
        var url = "${ctx}/user/login";
        var data = {};
        data.loginName = getKeyword();
        data.password = getKeyword2();
        var success = function(json){
            if(json.status){
                self.location.href = "${ctx}/html/index";
            }else{
                showContent(json.msg);
            }
        }
        commonAjax(url,"POST",data,success);
    }
    function logout(){
        var url = "${ctx}/user/logout";
        commonAjax(url,"POST");
    }
    function queryPermission() {
        var url = "${ctx}/permission";
        commonQuery(url);
    }
    function queryRole() {
        var url = "${ctx}/role";
        commonQuery(url);
    }
    function queryUser() {
        var url = "${ctx}/user";
        commonQuery(url);
    }
    function getRole() {
        var url = "${ctx}/role/"+getKeyword();
        commonGet(url);
    }
    function getUser() {
        var url = "${ctx}/user/"+getKeyword();
        commonGet(url);
    }
    function addRole() {
        var url = "${ctx}/role";
        var data = {};
        data.name = getKeyword();
        data.permissionIds = getKeyword3();
        data.description = "测试角色描述";
        commonAdd(url, data);
    }
    function addUser() {
        var url = "${ctx}/user";
        var data = {};
        data.loginName = getKeyword();
        data.password = getKeyword2();
        data.roleIds = getKeyword3();
        data.description = "测试用户描述";
        commonAdd(url, data);
    }
    function deleteRole() {
        var url = "${ctx}/role/"+getKeyword();
        commonDelete(url);
    }
    function deleteUser() {
        var url = "${ctx}/user/"+getKeyword();
        commonDelete(url);
    }
    function updateRole() {
        var url = "${ctx}/role/"+getKeyword2();
        var data = {};
        data.id = getKeyword2();
        data.name = getKeyword();
        data.description = "测试角色描述3";
        data.permissionIds = getKeyword3();
        commonUpdate(url, data);
    }
    function updateUser() {
        var url = "${ctx}/user/"+getKeyword2();
        var data = {};
        data.id = getKeyword2();
        data.password = getKeyword();
        data.description = "测试用户描述3";
        data.roleIds = getKeyword3();
        commonUpdate(url, data);
    }
    function commonGet(url) {
        var type = "GET";
        normalAjax(url, type, null);
    }
    function commonAdd(url, data) {
        var type = "POST";
        normalAjax(url, type, data);
    }
    function commonUpdate(url, data) {
        var type = "PATCH";
        normalAjax(url, type, data);
    }
    function commonDelete(url) {
        var type = "DELETE";
        normalAjax(url, type, null);
    }
    function commonQuery(url) {
        var data = {"searchKeyword": getKeyword(), targetPage: 1, pageSize: 10};
        var type = "GET";
        normalAjax(url, type, data);
    }
    function test2() {
        var url = "${ctx}/role/4028c42255f18e710155f18eb8740000";
        var data = {};
        data.id = "4028c42255f18e710155f18eb8740000";
        data.name = "测试角色3";
//        data.permissions = [{"id":"4028c42255f184ec0155f184f4c90002"}];
        data.permissionIds = "4028c42255f184ec0155f184f4c90002,4028c42255f184ec0155f184f4af0000";
        data.description = "测试角色描述3";
//        data = {"role":data};
        data.kk = "sdfsdf";

        var type = "PATCH";
//        if(type!="GET" && type !="get"){
//            data._method = type;
//            type = "POST";
////            data = [{"_method":type},JSON.stringify(data)];
//            data = JSON.stringify(data);
//        }
        $.ajax({
            url: url,
            type: type,
            dataType: 'json',
//            contentType:"application/json",
            data: data,
            success: function (json) {
                if (json.status) {
                    showContent(json.result);
                } else {
                    alert(json.msg);
                }
            },
            error: function () {

            },
            complete: function () {

            }
        });
    }

    function test(type) {
        commonAjax("${ctx}/index/test", type, {"searchKeyword": $("#searchKeyword").val()}, function (json) {
            $("#content").html(json.status + json.msg);
        }, function (XMLHttpRequest, textStatus, errorThrown) {
            var result = JSON.parse(XMLHttpRequest.responseText);
            $("#content").html(result.status + result.msg + XMLHttpRequest.status);
        });
    }

    function normalAjax(url, type, data) {
        var success = function (json) {
            if (json.status) {
                showContent(json.result);
            } else {
                alert(json.msg);
            }
        }
        var error = function (XMLHttpRequest) {
            var result = JSON.parse(XMLHttpRequest.responseText);
            $("#content").html(result.status + result.msg + XMLHttpRequest.status);
        }
        commonAjax(url, type, data, success, error);
    }

    function commonAjax(url, type, data, success, error, complete) {
        if (!data) {
            data = {};
        }
//        if(type!="GET" && type !="get"){
//            data._method = type;
//            type = "POST";
////            data = [{"_method":type},JSON.stringify(data)];
////            data = JSON.stringify(data);
//        }
        $.ajax({
            url: url,
            type: type,
            dataType: 'json',
//            contentType:"application/json",
            data: data,
            success: success,
            error: error,
            complete: complete
        });
    }
    function getKeyword() {
        return $("#keyword1").val();
    }
    function getKeyword2() {
        return $("#keyword2").val();
    }
    function getKeyword3() {
        return $("#keyword3").val();
    }
    function getKeyword4() {
        return $("#keyword4").val();
    }
    function getKeyword5() {
        return $("#keyword5").val();
    }
    function showContent(obj) {
        $("#content").html(obj2string(obj));
    }
    function obj2string(o) {
        if (o == null) {
            return "";
        }
        var r = [];
        if (typeof o == "string") {
            return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
        }
        if (typeof o == "object") {
            if (!o.sort) {
                for (var i in o) {
                    r.push(i + ":" + obj2string(o[i]));
                }
                if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
                    r.push("toString:" + o.toString.toString());
                }
                r = "{" + r.join() + "}";
            } else {
                for (var i = 0; i < o.length; i++) {
                    r.push(obj2string(o[i]))
                }
                r = "[" + r.join() + "]";
            }
            return r;
        }
        return o.toString();
    }

</script>
