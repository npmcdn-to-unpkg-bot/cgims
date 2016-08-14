<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%request.setAttribute("ctx", request.getContextPath());%>
<html>
<head>
    <title>师傅信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <%@include file="inc/icoImport.jsp" %>
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
        <input type="text" class="text" name="phoneNum" placeholder="手机号码" readonly="readonly" value="${workman.phoneNum}"/>
        <input type="hidden" class="text" name="id" placeholder="" value="${workman.id}"/>
        <input type="hidden" class="text" name="workmanNumber" placeholder="工号" value="${workman.workmanNumber}"/>
        <input type="text" class="text" name="name" placeholder="名称" value="${workman.name}"/>
        <input type="text" class="text" name="qq" placeholder="QQ" value="${workman.qq}"/>
        <div class="line">
            <label>收款方式：</label>
            <input type="radio" name="receiveType" id="receiveTypeZFB" value="0" class="radio" checked
                   onchange="receiveTypeChange()"/><label
                for="receiveTypeZFB">支付宝</label>
            <input type="radio" name="receiveType" id="receiveTypeBank" value="1" class="radio"
                   onchange="receiveTypeChange()"/><label
                for="receiveTypeBank">银行卡</label>
        </div>
        <input type="text" class="text" id="alipayAccount" name="alipayAccount" placeholder="支付宝"
               value="${workman.alipayAccount}"/>
        <input type="text" class="text" id="bank" name="bank" placeholder="银行名称" value="${workman.bank}"/>
        <input type="text" class="text" id="cardNum" name="cardNum" placeholder="银行卡号" value="${workman.cardNum}"/>
        <div class="line">
            <input type="date" name="birthday" class="date" value='<fmt:formatDate value="${workman.birthday}" pattern="yyyy-MM-dd"/>'/><label>出生日期</label>
        </div>
        <div class="line szd">
            <select id="province" name="province">
                <option value="">所在省</option>
            </select>
            <select id="city" name="city">
                <option value="">所在市</option>
            </select>
            <select id="area" name="area">
                <option value="">所在区</option>
            </select>
        </div>
        <input type="text" class="text" name="address" placeholder="详细地址" value="${workman.address}"/>
    </div>
    <div class="line">
        <form id="uploadHeadImgFaceForm" action="${ctx}/workman/self/upload/headImg" class="loadForm" method= "post" enctype ="multipart/form-data">
            <div class="btn">头像</div>
            <input type="file"  name="theFile" id="theHeadImgFile" accept="image/*"
                   class="upload-input">
        </form>
        <img name="headImg" src="${workman.headImg}" class="idHeadImgClass"/>
    </div>
    <div class="line">
        <form id="uploadIdCardFaceForm" action="${ctx}/workman/self/upload/idCardFace" class="loadForm" method= "post" enctype ="multipart/form-data">
            <div class="btn">身份证正面</div>
            <input type="file"  name="theFile" id="theIdCardFaceFile" accept="image/*"
                   class="upload-input">
        </form>
        <img name="idCardFace" src="${workman.idCardFace}" class="idCardImgClass"/>
    </div>
    <div class="line">
        <form id="uploadIdCardBackForm" action="${ctx}/workman/self/upload/idCardBack" class="loadForm" method= "post" enctype ="multipart/form-data">
            <div class="btn">身份证背面</div>
            <input type="file" name="theFile" id="theIdCardBackFile" accept="image/*"
                   class="upload-input">
        </form>
        <img name="idCardBack" src="${workmna.idCardBack}" class="idCardImgClass"/>
    </div>
    <div class="line">
        <span class="head">
            服务信息
        </span>

    </div>
    <div class="line">
        <div class="serviceItem" id="serviceTypeForm">
        </div>
    </div>
    <div class="line">
        <div class="serviceItem" id="serviceAreaForm">
        </div>
    </div>
    <div class="line">
        <span class="sTitle">服务类目</span>
        <div class="serviceItem" id="jjTypeForm">
        </div>
        <div class="serviceItem" id="djTypeForm">
        </div>
        <div class="serviceItem" id="wyTypeForm">
        </div>
        <div class="serviceItem" id="jdTypeForm">
        </div>
        <div class="serviceItem" id="wjTypeForm">
        </div>
    </div>
    <div class="line">
        <input type="text" class="text" name="teamPeopleNum" placeholder="团队人数" value="${workman.teamPeopleNum}"/>
        <input type="text" class="text" name="truckNum" placeholder="货车数量" value="${workman.truckNum}"/>
        <input type="text" class="text" name="tonnage" placeholder="货车吨位" value="${workman.tonnage}"/>
        <input type="text" class="text" name="willingPickAddress" placeholder="推荐提货点"
               value="${workman.willingPickAddress}"/>
        <input type="text" class="text" name="logistics" placeholder="推荐物流" value="${workman.logistics}"/>
        <textarea class="text textarea" name="strength" placeholder="优势" >${workman.strength}</textarea>
    </div>
    <div class="line">
        <input type="button" class="submitBtn" value="确认保存" onclick="submitUpdate()"/>
    </div>
</div>
</body>
<script>
    var serviceTypeFormat = {"服务类型": ["配送安装", "维修"]};
    var jjTypeFormat = {"家具类": ["民用家具", "办公家具", "定制家具（衣橱类）", "其他"]}
    var djTypeFormat = {"灯具类": ["灯具安装", "灯具检测维修", "电路检测维修", "其他"]}
    var wyTypeFormat = {"卫浴类": ["马桶", "花洒", "淋浴屏", "浴室柜", "水龙头", "储物架", "水盆类", "其他"]}
    var jdTypeFormat = {"家电类": ["电视", "空调", "净水器", "热水器", "油烟机", "吊扇", "浴霸", "小家电", "其他"]}
    var wjTypeFormat = {"门窗五金类": ["晾衣架", "内门", "纱窗", "开锁换锁", "各种墙体挂板", "其他"]}
    var formatList = [serviceTypeFormat, jjTypeFormat, djTypeFormat, wyTypeFormat, jdTypeFormat, wjTypeFormat];
    var idTagList = ["serviceTypeIdTag", "jjTypeIdTag", "djTypeIdTag", "wyTypeIdTag", "jdTypeIdTag", "wjTypeIdTag"];
    var targetIdList = ["serviceTypeForm", "jjTypeForm", "djTypeForm", "wyTypeForm", "jdTypeForm", "wjTypeForm"];

    function buildCheckBoxForm(idTag, targetId, format, data) {
        var obj = $("#" + targetId);
        for (var key in format) {
            var dataList;
            if (data && data[key]) {
                dataList = data[key].split(',');
            } else {
                dataList = [];
            }
            obj.append('<span class="tTitle">' + key + '</span>');
            var list = format[key];
            var allBtn = $(buildCheckBoxDiv(idTag + "all", "全部"));
            obj.append(allBtn);
            for (var i = 0; i < list.length; i++) {
                var btn = $(buildCheckBoxDiv(idTag + i, list[i]));
                obj.append(btn);
                if (list[i] == "其他" && dataList.length > 0) {
                    btn.find("input[type=checkbox]").prop("checked", "checked");
                    btn.find("input[type=text]").val(dataList[0]);
                    dataList.splice(0, 1);
                } else {
                    for (var j = 0; j < dataList.length; j++) {
                        if (list[i] == dataList[j]) {
                            btn.find("input[type=checkbox]").prop("checked", "checked");
                            dataList.splice(j, 1);
                            break;
                        }
                    }
                }
                btn.find("input[type=checkbox]").change(function () {
                    if (!this.checked) {
                        allBtn.find("input[type=checkbox]").removeAttr("checked");
                    } else {
                        var countTag = 0;
                        obj.find("input[type=checkbox]").each(function () {
                            if (!this.checked) {
                                countTag++;
                            }
                        })
                        if (countTag == 1) {
                            allBtn.find("input[type=checkbox]").prop("checked", "checked");
                        }
                    }
                })
            }
            allBtn.find("input[type=checkbox]").change(function () {
                if (this.checked) {
                    obj.find("input[type=checkbox]").prop("checked", "checked");
                } else {
                    obj.find("input[type=checkbox]").removeProp("checked");
                }
            })
        }
    }
    function buildCheckBoxDiv(id, text) {
        if (text == "其他") {
            return '<div class="checkBoxForm"><input type="checkbox" id="' + id + '" /><label for="' + id + '">' + text + '</label><input type="text" placeholder="多项服务请用“；”隔开"/> </div>';
        }
        return '<div class="checkBoxForm"><input type="checkbox" id="' + id + '" /><label for="' + id + '">' + text + '</label></div>';
    }
    function getServiceItems() {
        var data = {};
        for (var i = 1; i < targetIdList.length; i++) {
            var obj = $("#" + targetIdList[i]);
            var key = obj.find(".tTitle").html();
            var list = getItemValue(obj);
            data[key] = list;
        }
        return JSON.stringify(data);
    }
    function getItemValue(obj) {
        var list = "";
        obj.find(".checkBoxForm").each(function () {
            if($(this).find("input[type=checkbox]").prop("checked")){
                var value = $(this).find("label").html();
                if (value == "其他") {
                    value = $(this).find("input[type=text]").val();
                } else if (value == "全部") {
                    value = "";
                }
                if (value) {
                    if (list) {
                        list += ",";
                    }
                    list += value;
                }
            }
        })
        return list;
    }
    function refreshServiceArea(data) {
        var val = $("#city").val();
        var obj = $("#serviceAreaForm");
        obj.empty();
        if (val) {
            var valP = $("#province").val();
            var list = provinces[valP][val];
            var format = {"服务区域": list};
            buildCheckBoxForm("serviceAreaIdTag", "serviceAreaForm", format, data);
        }
    }

    <%--var provinces = ${provinces};--%>
    //    alert(provinces.length);
    //    var aToStr = JSON.stringify(provinces);
    //    alert(aToStr.length);
    //    var aToObj = JSON.parse(provinces);
    //    alert(aToObj);
    function logout() {
        $.ajax({
            url: "/workman/logout",
            type: "post",
            dataType: "json",
            success: function (json) {
                self.location.href = "${ctx}/html/workman/login";
            }
        });
    }
    function receiveTypeChange() {
        var type = $("input[name='receiveType']:checked").val();

        if (type == 0) {
            $("#alipayAccount").show();
            $("#bank").hide();
            $("#cardNum").hide();
        } else {
            $("#alipayAccount").hide();
            $("#bank").show();
            $("#cardNum").show();
        }
    }
    var provinces = ${provinces};
    var provinceNames = "${provinceNames}";
    provinceNames = provinceNames.split(",");
    function init() {
        receiveTypeChange();
        $('#cardNum').bind('focus', filter_time);

        for(var i=0;i<provinceNames.length;i++){
            var key = provinceNames[i];
            $("#province").append('<option value ="' + key + '">' + key + '</option>');
        }
//        for (var key in provinces) {
//            $("#province").append('<option value ="' + key + '">' + key + '</option>');
//        }
        $("#province").change(function () {
            refreshCity();
            refreshArea();
        })
        $("#city").change(function () {
            refreshArea();
        })

        var provinceVal = "${workman.province}";
        var cityVal = "${workman.city}";
        var areaVal = "${workman.area}";
        if(provinceVal){
            $("#province").val(provinceVal);
            refreshCity();
        }
        if(cityVal){
            $("#city").val(cityVal);
            var serviceArea = {"服务区域":"${workman.serviceArea}"};
            refreshArea(serviceArea);
        }
        if(areaVal){
            $("#area").val(areaVal);
        }
        var data = '${workman.serviceItems}';
        if(data){
            data = JSON.parse(data);
        }else{
            data = {};
        }
        data["服务类型"] = "${workman.serviceType}";
        <%--var servictTypeData = {"服务类目":"${workman.servictType}"};--%>
        for (var i = 0; i < formatList.length; i++) {
//            var data = {
//                "家具类": "办公家具,定制家具（衣橱类）",
//                "灯具类": "阿里山的风景阿拉山口大就发生的",
//                "卫浴类": "马桶,淋浴屏,浴室柜,水盆类,阿里山的风景阿拉山口大就发生的",
//                "门窗五金": "",
//                "家电": ""
//            };
            buildCheckBoxForm(idTagList[i], targetIdList[i], formatList[i], data);
            <%--buildCheckBoxForm(idTagList[0], targetIdList[0], formatList[0], servictTypeData);--%>
        }
        $("img").each(function(){
            if($(this).attr("src")){
                $(this).show();
            }
        })

        filter_staff_from_exist();
    }
    function refreshCity() {
        var val = $("#province").val();
        $("#city").html('<option value ="">所在市</option>');
        if (val) {
            for (var key in provinces[val]) {
                $("#city").append('<option value ="' + key + '">' + key + '</option>');
            }
        }
    }
    function refreshArea(data) {
        var val = $("#city").val();
        $("#area").html('<option value ="">所在区</option>');
        if (val) {
            var valP = $("#province").val();
            var list = provinces[valP][val];
            for (var i = 0; i < list.length; i++) {
                $("#area").append('<option value ="' + list[i] + '">' + list[i] + '</option>');
            }
//            for(var key in provinces[valP][val]){
//                $("#area").append('<option value ="'+key+'">'+key+'</option>');
//            }
        }
        refreshServiceArea(data);
    }

    $(function () {
        init();
    });

    filter_time = function () {
        var time = setInterval(filter_staff_from_exist, 100);
        $(this).bind('blur', function () {
            clearInterval(time);
        });
    };

    filter_staff_from_exist = function () {
        var now = $.trim($('#cardNum').val());
        now = insertSpace(now, 4);
        now = insertSpace(now, 9);
        now = insertSpace(now, 14);
        $("#cardNum").val(now);
    }
    function insertSpace(str, position) {
        if (str.length > position) {
            if (str.charAt(position) != " ") {
                str = str.substr(0, position) + " " + str.substring(position, str.length);
            }
        }
        return str;
    }

    function checkImgType(element){
        var filePath=$(element).val();
        var extStart=filePath.lastIndexOf(".");
        var ext=filePath.substring(extStart,filePath.length).toUpperCase();
        if(ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
            return "图片限于png,gif,jpg,jpeg格式";
        }else{
            if(element.files[0].size>20*1024*1024){
                return "图片最大支持20M";
            }
        }
        return null;
    }
    $(':file').change(function(){
        var errorStr = checkImgType(this);
        if(errorStr){
            alert(errorStr);
            return;
        }
        var fileObj = $(this);
        var formObj = fileObj.parent();
        var btn = formObj.find(".btn");
        <%--var url = "${ctx}/workman/self/upload/idCardBack";--%>
                //action="${ctx}/workman/self/upload/idCardBack"
        var url = formObj.attr("action");
        btn.attr("name-data",btn.html());
        btn.html("正在上传...");
        fileObj.attr("disabled","disabled");
        var parent = formObj.parent();
//        var formData = new FormData(formObj[0]);
        var formData=new FormData( );
        formData.append("theFile" , fileObj[0].files[0]);//获取文件法二
        $.ajax({
            type:'POST',
            url:url,
            data: formData,
            async: true,
            cache: false,
            dataType : "json",
            contentType: false,
            processData: false,
            success: function (json) {
                if(json.status){
                    parent.find("img").attr("src",json.result);
                    parent.find("img").show();
                }else{
                    alert(json.msg);
                }
            },
            error: function (data) {
                alert("上传失败");
            },
            complete:function(){
                fileObj.removeAttr("disabled");
                btn.html(btn.attr("name-data"));
            }
        });
    });
    function submitUpdate(){
        var  data = {};
        $(".text,.date,select").each(function(){
            var value = $(this).val();
            if(value){
                if($(this).attr("id")=="cardNum"){
                    value = value.replace(/\s+/g,"");
                }else{
                    value = value.replace(/(^\s*)|(\s*$)/g, "");
                }
            }
            data[$(this).attr("name")] = value;
        });
        $("img").each(function(){
            data[$(this).attr("name")] = $(this).attr("src");
        });
        data.receiveType = $("input[name='receiveType']:checked").val();
        data.serviceItems = getServiceItems();
        data.serviceType = getItemValue($("#serviceTypeForm"));
        data.serviceArea = getItemValue($("#serviceAreaForm"));
        $.ajax({
            url: "/workman/self",
            type: "post",
            dataType: "json",
            data: data,
            success: function (json) {
                if (json.status==1) {
                    alert("修改成功");
                } else {
                    alert(json.msg);
                }
            }
        });
    }
</script>
</html>
