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
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <link href="${ctx }/assets/css/ui.css" rel="stylesheet" type="text/css"/>
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
    <link href="${ctx }/assets/img/ui/favicon.ico" rel="icon" type="image/x-icon"/>
    <link href="${ctx }/assets/img/ui/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
</head>
<body style="zoom: 1;">
<div class="workmanSelfMain">
    <div class="line">
        <input type="button" class="logoutBtn" value="退出登录" onclick="logout()"/>
    <span class="title">
        我的资料
    </span>
    <span class="tips">
        完善资料可以为您带来更多机会
    </span>
    <span class="tips">
        工号：${workman.workmanNumber}
    </span>
    </div>
    <div class="line">
        <span class="head">
            个人信息
        </span>
        <input type="text" class="text" name="phoneNum" placeholder="手机号码" ${workman.phoneNum}/>
        <input type="hidden" class="text" name="id" placeholder="" value="${workman.id}"/>
        <input type="hidden" class="text" name="workmanNumber" placeholder="工号" value="${workman.workmanNumber}"/>
        <input type="text" class="text" name="name" placeholder="名称" value="${workman.name}"/>
        <input type="text" class="text" name="qq" placeholder="QQ" value="${workman.qq}"/>
        <div class="line">
            <label>收款方式：</label>
            <input type="radio" name="receiveType" id="receiveTypeZFB" value="0" class="radio" checked onchange="receiveTypeChange()"/><label
                for="receiveTypeZFB">支付宝</label>
            <input type="radio" name="receiveType" id="receiveTypeBank" value="1" class="radio" onchange="receiveTypeChange()"/><label
                for="receiveTypeBank">银行卡</label>
        </div>
        <input type="text" class="text" name="alipayAccount" placeholder="支付宝" value="${workman.alipayAccount}"/>
        <input type="text" class="text" name="bank" placeholder="银行名称" value="${workman.bank}"/>
        <input type="text" class="text" name="cardNum" placeholder="银行卡号" value="${workman.cardNum}"/>
        <div class="line">
            <input type="date" class="date"/><label>出生日期</label>
        </div>
        <div class="line">
            <label>所在地：</label>
            <select>
                <option value ="volvo">Volvo</option>
                <option value ="saab">Saab</option>
            </select>
            <select>
                <option value ="volvo">Volvo</option>
                <option value ="saab">Saab</option>
            </select>
            <select>
                <option value ="volvo">Volvo</option>
                <option value ="saab">Saab</option>
            </select>
        </div>
        <input type="text" class="text" name="address" placeholder="详细地址" value="${workman.address}"/>
        <input type="file" placeholder="身份证正面"/>
        <input type="file" placeholder="身份证背面"/>
        <%--<input type="text" class="text" name="" placeholder="" value="${workman.}"/>--%>
        <%--<input type="text" class="text" name="" placeholder="" value="${workman.}"/>--%>
        <%--<input type="text" class="text" name="" placeholder="" value="${workman.}"/>--%>
        <%--<input type="text" class="text" name="" placeholder="" value="${workman.}"/>--%>
        <%--<input type="text" class="text" name="" placeholder="" value="${workman.}"/>--%>
        <%--<input type="text" class="text" name="" placeholder="" value="${workman.}"/>--%>
        <%--<input type="text" class="text" name="" placeholder="" value="${workman.}"/>--%>
    </div>
    <div class="line">
        <span class="head">
            服务信息
        </span>

    </div>
    <div class="line">

        <span>服务类型</span>
        <input type="checkbox" id="psazCB"/><label for="psazCB">配送安装</label>
        <input type="checkbox" id="wxCB"/><label for="wxCB">维修</label>
        <span>服务类目</span>
    </div>
</div>
</body>
<script>
    function logout() {

        $.ajax({
//                url: "VerifyLoginServlet", // 进行二次验证
            url: "/workman/logout",
            type: "post",
            dataType: "json",
            success: function (json) {

            }
        });
    }
    function receiveTypeChange(){
        var type = $("input[name='receiveType']:checked").val();
        if(type==0){

        }
    }
    function init(){
        receiveTypeChange();
    }
    init();
</script>
</html>
