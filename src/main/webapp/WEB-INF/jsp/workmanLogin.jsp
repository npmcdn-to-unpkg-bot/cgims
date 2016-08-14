<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%request.setAttribute("ctx", request.getContextPath());%>
<html>
<head>
    <title>师傅登录</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <link href="${ctx }/assets/css/ui.css" rel="stylesheet" type="text/css"/>
    <%@include file="inc/icoImport.jsp" %>
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
    <link href="${ctx }/assets/img/ui/favicon.ico" rel="icon" type="image/x-icon"/>
    <link href="${ctx }/assets/img/ui/favicon.ico" rel="shortcut icon" type="image/x-icon"/>

    <!-- 引入封装了failback的接口--initGeetest -->
    <script src="http://static.geetest.com/static/tools/gt.js"></script>
</head>
<body style="zoom: 1;">
<div class="workmanLoginMain">
    <header>
        <span class="title">
            师傅登录
        </span>
    </header>
    <div class="line">
        <div class="captcha" id="embed-captcha"></div>
    </div>
    <div class="line">
        <div class="form">

            <input type="text" id="phoneNum" name="phoneNum" placeholder="请填写您的手机号" class="ipt-text mt10"
                   maxlength="11"style="color: #999999;">
        </div>
    </div>
    <div class="line">
        <div class="form">
            <input type="text" id="captchaText" name="captcha" placeholder="请输入手机验证码" class="ipt-text shortText" maxlength="6" style="color: #999999;">
            <input type="button" id="jsGetPhoneCaptchaBtn" class="formbtn01"
                   value="获取验证码">
        </div>
    </div>
    <div class="line">
        <input type="button" class="btn" id="submitBtn" value="登录"/>
    </div>
    <div class="line">
        <img class="logo" src="${ctx}/assets/img/logo.png"/>
    </div>
</div>
</body>
</html>
<script>
    function getKeyword() {
        return $("#phoneNum").val();
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

    var waiting = false;
    var timer = null;
    const DEFAULT_LIMIT = 120;
    const DEFAULT_TEXT = "获取验证码";
    var limit = DEFAULT_LIMIT;
    function beginWait(){
        waiting = true;
        if(!timer){
            timer = setInterval(function(){
                if(--limit>0){
                    $("#jsGetPhoneCaptchaBtn").attr("disabled","disabled");
                    $("#jsGetPhoneCaptchaBtn").val(limit+"秒");
                }else{
                    endWait();
                }
            },1000);
        }
    }
    function endWait(){
        if(timer){
            window.clearInterval(timer);
            timer = null;
        }
        $("#jsGetPhoneCaptchaBtn").val(DEFAULT_TEXT);
        $("#jsGetPhoneCaptchaBtn").removeAttr("disabled");
        waiting = false;
    }
    //http://www.geetest.com/install/sections/idx-client-sdk.html#api
    var handlerEmbed = function (captchaObj) {

        $("#jsGetPhoneCaptchaBtn").click(function (e) {
            if(waiting){
                return;
            }
            var validate = captchaObj.getValidate();
            if (!validate) {
                alert('请先完成验证！');
                return;
            }
            if(!getKeyword()){
                alert("手机号码不能为空");
                return;
            }
            beginWait();
            $.ajax({
//                url: "VerifyLoginServlet", // 进行二次验证
                url: "/workman/phoneCaptcha",
                type: "post",
                dataType: "json",
                data: {
                    // 二次验证所需的三个值
                    phoneNum: getKeyword(),
                    geetest_challenge: validate.geetest_challenge,
                    geetest_validate: validate.geetest_validate,
                    geetest_seccode: validate.geetest_seccode
                },
                success: function (json) {
                    if(json.status){
                        showContent(json);
                    }else{
                        alert(json.msg);
                        captchaObj.refresh();
                    }
                }
            });
        });
        var submitClick = function () {
            var captchaText = $("#captchaText").val();
            if(!captchaText || !getKeyword()){
                alert("手机号码和验证码不能为空");
                return;
            }
            $.ajax({
//                url: "VerifyLoginServlet", // 进行二次验证
                url: "/workman/login",
                type: "post",
                dataType: "json",
                data: {
                    // 二次验证所需的三个值
                    phoneNum: getKeyword(),
                    captchaText: captchaText
                },
                success: function (json) {
                    if (json.status) {
                        self.location.href = "${ctx}/html/workman/self";
                    } else {
                        alert(json.msg);
                        captchaObj.refresh();
                    }
                }
            });
        };
        $(".logo").click(submitClick);
        $("#submitBtn").click(submitClick)
        // 将验证码加到id为captcha的元素里
        captchaObj.appendTo("#embed-captcha");

        captchaObj.onReady(function () {
//            $("#wait")[0].className = "hide";
        });

        // 更多接口参考：http://www.geetest.com/install/sections/idx-client-sdk.html
    };
    $.ajax({
        // 获取id，challenge，success（是否启用failback）
//        url: "StartCaptchaServlet",
        url: "${ctx}/workman/initCaptcha",
        type: "get",
        dataType: "json",
        success: function (data) {

            // 使用initGeetest接口
            // 参数1：配置参数
            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它做appendTo之类的事件
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                product: "embed", // 产品形式，包括：float，embed，popup。注意只对PC版验证码有效
                offline: !data.success // 表示用户后台检测极验服务器是否宕机，一般不需要关注
            }, handlerEmbed);
        }
    });
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    }
</script>
