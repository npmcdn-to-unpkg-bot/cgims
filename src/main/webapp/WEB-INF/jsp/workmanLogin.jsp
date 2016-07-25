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
电话:<input type="text" id="phoneNum" class="text"/><br/>
<div class="captcha">
    <script type="text/javascript"
            src="http://api.geetest.com/get.php?gt=${geetestId }&product=embed"></script>
    <input type="button" id="jsGetPhoneCaptchaBtn" class="formbtn01" onclick="getPhoneCaptcha(this);" value="获取验证码">
</div>
<br/>
<span id="content">Hello World!wawa</span>
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

    var smoothCaptchObject = new Object();
    smoothCaptchObject.captchaReady = false;
    function gt_custom_ajax(result, selector) {
        if (result) {
            smoothCaptchObject.captchaReady = true;
            $("#captchaerror").css("display","none");
            smoothCaptchObject.challenge = selector(".geetest_challenge").value;
            smoothCaptchObject.validate = selector(".geetest_validate").value;
            smoothCaptchObject.seccode = selector(".geetest_seccode").value;
            $("[name='geetest_challenge']").val(smoothCaptchObject.challenge);
            $("[name='geetest_validate']").val(smoothCaptchObject.validate);
            $("[name='geetest_seccode']").val(smoothCaptchObject.seccode);
        }else{
            smoothCaptchObject.captchaReady = false;
        }
    }
    function getPhoneCaptcha(element) {
        if(smoothCaptchObject.captchaReady){
            var url = "${ctx}/workman/phoneCaptcha";
            var data = {phoneNum:getKeyword(),geetest_challenge:smoothCaptchObject.challenge,geetest_validate:smoothCaptchObject.validate,geetest_seccode:smoothCaptchObject.seccode};
            var method = "GET";
            normalAjax(url,method,data)
        }else{
            alert(smoothCaptchObject.captchaReady);
        }

        return;
        if($(element).hasClass("disabled")){
            return false;
        }
        var phoneNumReady = checkElement(document.getElementById(PHONE_ID));
        if(phoneNumReady && smoothCaptchObject.captchaReady){
            document.getElementById(errorMap[CAPTCHA_ID]).innerHTML = "";
            if(phoneNumCheckObject.accessValue != document.getElementById(PHONE_ID).value){
                phoneNumCheckObject.toCaptcha = true;
                phoneNumValid();
                return false;
            }
            var errorElement = document.getElementById(errorMap[CAPTCHA_ID]);
            $.ajax({
                type: "GET",
                async: true,
                url: GET_PHONE_CAPTCHA_URL,
                data: {phoneNum:phoneNumCheckObject.accessValue,geetest_challenge:smoothCaptchObject.challenge,geetest_validate:smoothCaptchObject.validate,geetest_seccode:smoothCaptchObject.seccode},
                dataType: "json",
                success: function (data) {
                    if(!data.success){
                        errorElement.innerHTML = data.msg;
                    }
                },
                error: function (data) {
                    errorElement.innerHTML = "验证码发送失败，请稍后尝试！";
                }
            });
            captchaTimeSpan(element);
            return true;
        }else{
            var errStr = "";
            if(!phoneNumReady && !smoothCaptchObject.captchaReady){
                errStr = "请输入正确的电话号码和滑动验证图片";
            }else if(!phoneNumReady && smoothCaptchObject.captchaReady){
                errStr = "请输入正确的电话号码";
            }else if(phoneNumReady && !smoothCaptchObject.captchaReady){
                errStr = "请滑动验证图片";
            }
            document.getElementById(errorMap[CAPTCHA_ID]).innerHTML = errStr;
        }
        return false;
    }
</script>
