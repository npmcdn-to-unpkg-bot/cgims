<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
	<head>
         <%@include file="../inc/header.jsp" %>
		<title>后台管理</title>
		<title>登录</title>
	</head>
	<body>
		<div class="login theme">
			<h2 style="text-align: center;">登录</h2>
			<form class="login-form" action="${ctx}/mi/login" method="post">
				<fieldset>
					<c:if test="${error  != null }"><div id="error" class="help-inline">${error }</div></c:if>
					<input type="hidden" name="publicExponent" id="publicExponent" value="${publicExponent }" />
					<input type="hidden" name="modulus" id="modulus" value="${modulus }"  />
					
					<input type="hidden" name="geetest_challenge"> 
					<input type="hidden" name="geetest_validate"> 
					<input type="hidden" name="geetest_seccode"> 
					<input type="text" id="txtname" name="loginName"  error="只能输入用户名、邮箱或手机号码" style="color:black" 
					patterns = "^([a-z]([a-zA-Z0-9_]){3,31})|(([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+)|(1[0-9]{10})$" require="require" require_msg ="请输入用户名、邮箱或手机号码"  placeholder="请输入用户名、邮箱或手机号码" value="${loginName }"/>
					<input type="password" style="color:black"  id="txtpsw" name="pwd" max="32" min="6"  error="密码长度6~32只能包含大小写字母、数字、部分特殊符号 !@#$%^&*()" 
					require="require" require_msg ="密码不能为空" patterns = "^[A-Za-z0-9\!\@\#\$\%\^\&\*\(\)]*$" placeholder="输入密码" />
					<input type="hidden" name="password" id="password"/>
					<div class="captcha">
					<script type="text/javascript"
						src="http://api.geetest.com/get.php?gt=${geetestId }&product=embed"></script>
					</div>
					<div class="help-inline" style="display: none;" id="captchaerror"></div>
					<div class="clearfix"></div>
					<label for="remember" class="login-form-rememver">
<!-- 						<div class="checker"> -->
<!-- 							<span><input type="checkbox" id="rememberMe" name="rememberMe" /> -->
<!-- 							</span> -->
<!-- 						</div> -->
<!-- 						记住密码? -->
					</label>

					<button id="submitBtn" type="button" class="btn btn-primary" onclick="submitForm()">登录</button>
				</fieldset>
			</form>
		</div>
	</body>
	<%
	    request.setAttribute("encrypUrl", request.getContextPath()
						+ "/assets/tools/encryption");
	%>
	<script type="text/javascript" src="${encrypUrl}/RSA.js"></script>
	<script type="text/javascript" src="${encrypUrl}/BigInt.js"></script>
	<script type="text/javascript" src="${encrypUrl}/Barrett.js"></script>
	<script type="text/javascript" src="${encrypUrl}/md5.js"></script>
	<script>
// 		var storePwd = util.cookie("password");
		var storeName = getCookie("miName");
		var storePwd = getCookie("miPassword");
		if(storeName){
			$("#txtname").val(storeName);
			if(storePwd){
				$("#txtpsw").val("111111");
				$("#rememberMe").prop("checked","true");
			}
		}
// 		$("#txtpsw").on("change",function(){
// 			storePwd = null;
// // 			RSAEncrypt($(this).val());
// // 			$("#password").val(hex_md5($(this).val()))
// 		});
		$("#txtpsw").on("keyup",function(){
			storePwd = null;
		});
		
		function RSAEncrypt(pwd) {
// 			var thisPwd = hex_md5(pwd);
			var thisPwd = pwd;
			setMaxDigits(130);
			var publicExponent = document.getElementById("publicExponent").value;
			var modulus = document.getElementById("modulus").value;
			var key = new RSAKeyPair(publicExponent, "", modulus);
			var result = encodeURIComponent(encryptedString(key, encodeURIComponent(thisPwd)));
			return result;
		}
		function refreshSubmitBtn(){
			if($(".btn").attr("disabled") == false){
				$(".btn").addClass("disabled").attr("disabled",true)
			}else{
				$(".btn").removeClass("disabled").attr("disabled",false)
			}
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
			}
		}
		function submitForm(){
			refreshSubmitBtn();
			var r = $(".login-form").validate();
			if(r){
				if(smoothCaptchObject.captchaReady==true){
// 				if(smoothCaptchObject.captchaReady==true || true){
					var md5_pwd;
					if(storePwd){
						md5_pwd = storePwd;
					}else{
						md5_pwd = hex_md5($("#txtpsw").val());
					}
					$("#submitBtn").attr("disabled","disabled");
					if($("#rememberMe").is(":checked")){
						addCookie("miPassword",md5_pwd,30);
						addCookie("miName",$("#txtname").val(),30);
// 						util.cookie("password",$("#password").val());
					}
					$("#password").val(RSAEncrypt(md5_pwd));
					$("#txtpsw").val("");
					$("#error").length>0&&$("#error").html("");
					$(".login-form").submit();
				}else{
					$("#captchaerror").css("display","block").html("请输入验证码");
				}
			}
		}
	</script>
</html>
