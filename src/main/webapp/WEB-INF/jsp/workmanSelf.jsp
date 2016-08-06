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
        <input type="text" class="text" id="alipayAccount" name="alipayAccount" placeholder="支付宝" value="${workman.alipayAccount}"/>
        <input type="text" class="text" id="bank" name="bank" placeholder="银行名称" value="${workman.bank}"/>
        <input type="text" class="text" id="cardNum" name="cardNum" placeholder="银行卡号" value="${workman.cardNum}"/>
        <div class="line">
            <input type="date" class="date"/><label>出生日期</label>
        </div>
        <div class="line szd">
            <select id="province">
                <option value ="">所在省</option>
            </select>
            <select id="city">
                <option value ="">所在市</option>
            </select>
            <select id="area">
                <option value ="">所在区</option>
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
    <%--var provinces = ${provinces};--%>
//    alert(provinces.length);
//    var aToStr = JSON.stringify(provinces);
//    alert(aToStr.length);
//    var aToObj = JSON.parse(provinces);
//    alert(aToObj);
    function logout() {

        $.ajax({
//                url: "VerifyLoginServlet", // 进行二次验证
            url: "/workman/logout",
            type: "post",
            dataType: "json",
            success: function (json) {
                self.location.href = "${ctx}/html/workman/login";
            }
        });
    }
    function receiveTypeChange(){
        var type = $("input[name='receiveType']:checked").val();

        if(type==0){
            $("#alipayAccount").show();
            $("#bank").hide();
            $("#cardNum").hide();
        }else{
            $("#alipayAccount").hide();
            $("#bank").show();
            $("#cardNum").show();
        }
    }
    var provinces = ${provinces2};
    function init(){
        receiveTypeChange();
        $('#cardNum').bind('focus',filter_time);

        for(var key in provinces){
            $("#province").append('<option value ="'+key+'">'+key+'</option>');
        }
        $("#province").change(function(){
            refreshCity();
            refreshArea();
        })
        $("#city").change(function(){
            refreshArea();
        })
    }
    function refreshCity(){
       var val =  $("#province").val();
        $("#city").html('<option value ="">所在市</option>');
        if(val){
            for(var key in provinces[val]){
                $("#city").append('<option value ="'+key+'">'+key+'</option>');
            }
        }
    }
    function refreshArea(){
        var val =  $("#city").val();
        $("#area").html('<option value ="">所在区</option>');
        if(val){
            var valP =  $("#province").val();
            var list = provinces[valP][val];
            for( var i=0;i<list.length;i++){
                $("#area").append('<option value ="'+list[i]+'">'+list[i]+'</option>');
            }
//            for(var key in provinces[valP][val]){
//                $("#area").append('<option value ="'+key+'">'+key+'</option>');
//            }
        }
    }

    $(function () {
        init();
    });

    filter_time = function(){
        var time = setInterval(filter_staff_from_exist, 100);
        $(this).bind('blur',function(){
            clearInterval(time);
        });
    };

    filter_staff_from_exist = function(){
        var now = $.trim($('#cardNum').val());
        now = insertSpace(now,4);
        now = insertSpace(now,9);
        now = insertSpace(now,14);
        $("#cardNum").val(now);
    }
    function insertSpace(str,position){
        if(str.length>position){
            if(str.charAt(position)!=" "){
                str = str.substr(0,position)+" "+str.substring(position,str.length);
            }
        }
        return str;
    }
</script>
</html>
