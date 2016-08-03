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

    <!-- 引入封装了failback的接口--initGeetest -->
    <script src="http://static.geetest.com/static/tools/gt.js"></script>
    <style>
        .text{
            width:300px;
        }
    </style>
</head>
<body>
电话:<input type="text" id="phoneNum" class="text"/><br/>
<div class="captcha">
    <div id="embed-captcha"></div>
    <input type="text" id="captchaText"/>
    <input type="button" id="jsGetPhoneCaptchaBtn" class="formbtn01" value="获取验证码"/>
    <input type="button" id="submitBtn" value="登录"/>
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

    <%--var smoothCaptchObject = new Object();--%>
    <%--smoothCaptchObject.captchaReady = false;--%>
    <%--function gt_custom_ajax(result, selector) {--%>
        <%--if (result) {--%>
            <%--smoothCaptchObject.captchaReady = true;--%>
            <%--$("#captchaerror").css("display","none");--%>
            <%--smoothCaptchObject.challenge = selector(".geetest_challenge").value;--%>
            <%--smoothCaptchObject.validate = selector(".geetest_validate").value;--%>
            <%--smoothCaptchObject.seccode = selector(".geetest_seccode").value;--%>
            <%--$("[name='geetest_challenge']").val(smoothCaptchObject.challenge);--%>
            <%--$("[name='geetest_validate']").val(smoothCaptchObject.validate);--%>
            <%--$("[name='geetest_seccode']").val(smoothCaptchObject.seccode);--%>
        <%--}else{--%>
            <%--smoothCaptchObject.captchaReady = false;--%>
        <%--}--%>
    <%--}--%>
    <%--function getPhoneCaptcha(element) {--%>
        <%--if(smoothCaptchObject.captchaReady){--%>
            <%--var url = "${ctx}/workman/phoneCaptcha";--%>
            <%--var data = {phoneNum:getKeyword(),geetest_challenge:smoothCaptchObject.challenge,geetest_validate:smoothCaptchObject.validate,geetest_seccode:smoothCaptchObject.seccode};--%>
            <%--var method = "GET";--%>
            <%--normalAjax(url,method,data)--%>
        <%--}else{--%>
            <%--alert(smoothCaptchObject.captchaReady);--%>
        <%--}--%>

        <%--return;--%>
        <%--if($(element).hasClass("disabled")){--%>
            <%--return false;--%>
        <%--}--%>
        <%--var phoneNumReady = checkElement(document.getElementById(PHONE_ID));--%>
        <%--if(phoneNumReady && smoothCaptchObject.captchaReady){--%>
            <%--document.getElementById(errorMap[CAPTCHA_ID]).innerHTML = "";--%>
            <%--if(phoneNumCheckObject.accessValue != document.getElementById(PHONE_ID).value){--%>
                <%--phoneNumCheckObject.toCaptcha = true;--%>
                <%--phoneNumValid();--%>
                <%--return false;--%>
            <%--}--%>
            <%--var errorElement = document.getElementById(errorMap[CAPTCHA_ID]);--%>
            <%--$.ajax({--%>
                <%--type: "GET",--%>
                <%--async: true,--%>
                <%--url: GET_PHONE_CAPTCHA_URL,--%>
                <%--data: {phoneNum:phoneNumCheckObject.accessValue,geetest_challenge:smoothCaptchObject.challenge,geetest_validate:smoothCaptchObject.validate,geetest_seccode:smoothCaptchObject.seccode},--%>
                <%--dataType: "json",--%>
                <%--success: function (data) {--%>
                    <%--if(!data.success){--%>
                        <%--errorElement.innerHTML = data.msg;--%>
                    <%--}--%>
                <%--},--%>
                <%--error: function (data) {--%>
                    <%--errorElement.innerHTML = "验证码发送失败，请稍后尝试！";--%>
                <%--}--%>
            <%--});--%>
            <%--captchaTimeSpan(element);--%>
            <%--return true;--%>
        <%--}else{--%>
            <%--var errStr = "";--%>
            <%--if(!phoneNumReady && !smoothCaptchObject.captchaReady){--%>
                <%--errStr = "请输入正确的电话号码和滑动验证图片";--%>
            <%--}else if(!phoneNumReady && smoothCaptchObject.captchaReady){--%>
                <%--errStr = "请输入正确的电话号码";--%>
            <%--}else if(phoneNumReady && !smoothCaptchObject.captchaReady){--%>
                <%--errStr = "请滑动验证图片";--%>
            <%--}--%>
            <%--document.getElementById(errorMap[CAPTCHA_ID]).innerHTML = errStr;--%>
        <%--}--%>
        <%--return false;--%>
    <%--}--%>


//http://www.geetest.com/install/sections/idx-client-sdk.html#api
    var handlerEmbed = function (captchaObj) {

        $("#jsGetPhoneCaptchaBtn").click(function (e) {
            var validate = captchaObj.getValidate();
            if (!validate) {
                alert('请先完成验证！');
                return;
            }
            $.ajax({
//                url: "VerifyLoginServlet", // 进行二次验证
                url:"/workman/phoneCaptcha",
                type: "post",
                dataType: "json",
                data: {
                    // 二次验证所需的三个值
                    phoneNum:getKeyword(),
                    geetest_challenge: validate.geetest_challenge,
                    geetest_validate: validate.geetest_validate,
                    geetest_seccode: validate.geetest_seccode
                },
                success: function (data) {
                    showContent(data);
                    captchaObj.refresh();
                }
            });
        });

        $("#submitBtn").click(function(){
            var captchaText = $("#captchaText").val();
            $.ajax({
//                url: "VerifyLoginServlet", // 进行二次验证
                url:"/workman/login",
                type: "post",
                dataType: "json",
                data: {
                    // 二次验证所需的三个值
                    phoneNum:getKeyword(),
                    captchaText:captchaText
                },
                success: function (data) {
                    if(data.status){
                        var id = data.result;
                        self.location.href = "${ctx}/html/workman/self/"+id;
                    }else{
                        showContent(data);
                        captchaObj.refresh();
                    }
                }
            });
        })

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
        url:"${ctx}/workman/initCaptcha",
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
