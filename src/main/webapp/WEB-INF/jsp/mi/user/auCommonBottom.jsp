<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script type="text/x-handlebars-template" id="role-template">
		{{#each this}}
		<tr>
			<td>{{name}}</td>
			<td>{{desciption}}</td>
			<td>
				<a class="btn btn-info btn-add-relation" href="javascript:;" data-id="{{id}}" data-name="{{name}}">
					<i class="icon-plus "></i>                                            
				</a>
			</td>
		</tr>
		{{/each}}
		</script>
	<%
	    request.setAttribute("encrypUrl", request.getContextPath()
						+ "/assets/tools/encryption");
	%>
	<script type="text/javascript" src="${encrypUrl}/RSA.js"></script>
	<script type="text/javascript" src="${encrypUrl}/BigInt.js"></script>
	<script type="text/javascript" src="${encrypUrl}/Barrett.js"></script>
	<script type="text/javascript" src="${encrypUrl}/md5.js"></script>
	<script>
		function RSAEncrypt(pwd) {
			var thisPwd = hex_md5(pwd);
			setMaxDigits(130);
			var publicExponent = document.getElementById("publicExponent").value;
			var modulus = document.getElementById("modulus").value;
			var key = new RSAKeyPair(publicExponent, "", modulus);
			result = encodeURIComponent(encryptedString(key, encodeURIComponent(thisPwd)));
			return result;
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

		/*
		 * 图片上传
		 */
		var uploading = !1;
		$(":file").change(function(){
			if(!!uploading){
				alert("图像正在上传，请稍后..");
				return ;
			}
			var errorStr = checkImgType(this);
			if(errorStr){
				alert(errorStr);
				return;
			}
			var formData = new FormData($("#uploadForm")[0]);	
			$(".uploader-loading").show();
			uploading =!0;
		    $.ajax({
		        type:'POST',
		        url:userHeadImgUploadUrl,
		        data: formData,
		        async: true,
		        cache: false,
				dataType : "json",
		        contentType: false,
		        processData: false,
		        success: function (data) {
					if(data.success){
						var final_url = data.imgPath;
						$("[name='headImgUrl']").val(final_url);
						$(".control-user-img").attr("src",final_url);
					}else{
						alert(data.msg);
					}
		        },
		        error: function (data) {
					alert("上传失败");
		        },
		        complete:function(){
					uploading =!1;
					$(".uploader-loading").hide();
		        }
		    });
		});

		/*
		 * 处理密码，密码加密，设置到password
		 */
		var resetPwd= false;
		$("#pwd").focus(function(){
			$(this).val("");
		})
		$("#pwd").change(function(){
			$("#password").val(RSAEncrypt($(this).val()));
			resetPwd = true;
		})
		
		/*
		 * 
		 * 新增关联
		 * 
		 */
		var addRelation = [];
		var delRelation = [];
		$(".datatable-table").on("click",".btn-add-relation",function(){
			var id = $(this).data("id");
			var name = $(this).data("name");
			var didx = $.inArray(id,delRelation);//不在已删除
			var oidx = $.inArray(id,originalRelation);
			didx>-1&&delRelation.splice(didx,1);
			if(oidx>-1&&didx==-1||$.inArray(id,addRelation)>-1){//原来有，未删除不加
				return;
			}
			addRelation.push(id);
			$(".relation").append(template("#relation-info-template",{"id":id,"name":name}))
			return false;
		})
		
		/*
		 * 删除关联
		 */
		$(".relation").on("click",".btn-remove-relation",function(){
			var id = $(this).data("id");
			var aidx = $.inArray(id,addRelation);//不在已删除
			aidx>-1&&addRelation.splice(aidx,1);
			if($.inArray(id,delRelation)==-1){//del中没有，则存入
				delRelation.push(id);
			}
			$(this).parent().parent().remove();
			return false;
		})
		
		/*
		 * 保存
		 * 
		 */
		
		$("#submit").click(function(){
			var btn = $(this);
			var form = $(".form");
			if(uploading){
				alert("图像正在上传，请稍后..");
				return ;
			}
			var res = form.validate();
			if(res){
				var user = {
					id:"",
					name:"",
					email:"",
					phoneNum:"",
					headImgUrl:"",
					password:"",
					locked:false,
					description:"",
					salt:""
				}
			   for(var i in user){
			   		var value = form.find("[name="+i+"]").val();
			   		user[i]=value;
			   }
			   if(user.name=="" && user.phoneNum =="" && user.email ==""){
			   		alert("姓名、邮箱和手机号码不能全部为空");
			   		return false;
			   }
			   var relation = {addroles:"",delroles:''};
			   if(addRelation.length>0){
			   		relation.addroles = addRelation.join("/")
			   }
			   if(delRelation.length>0){
			   		relation.delroles = delRelation.join("/")
			   }
			   var newRoles = originalRelation.concat(addRelation);
			   for(var i=0;i<delRelation.length;i++){
					var index = $.inArray(delRelation[i],newRoles);
					index>-1&&newRoles.splice(index,1);
			   }
			   newRoles = newRoles.join("/");
				var publicExponent = document.getElementById("publicExponent").value;
				var modulus = document.getElementById("modulus").value;
			   btn.attr("disabled","disabled");
			   btn.addClass("disabled");
			   $.ajax({
			   	type:"POST",
			   	url:saveUserUrl,
			   	async:true,
			   	dataType:"json",
			   	data:$.extend(user,{"newRoleIds":newRoles,"publicExponent":publicExponent,"modulus":modulus,"resetPwd":resetPwd}),
			   	success:function(data){
		   			var name = data.field;
		   			if(!data.success){
		   				if(name){
		   					var p = form.find("[name='"+name+"']");
		   					p.length>0&&(p.focus(),showerror(p,data.msg))
		   				}else{
		   					alert(data.msg)
		   				}
						btn.prop("disabled","false");
						btn.removeClass("disabled");
						$("body").scrollTop(0);
		   			}else{
		   				alert(data.msg);
		   				window.location.href=goBackUrl;
			   		}
			   	},
			   	error:function(){
			   		alert(actionStr+"用户失败!");
			   	},
			   	complete:function(){
					btn.prop("disabled","");
					btn.removeClass("disabled");
			   	}
			   });
			   
			}else{
				$("body").scrollTop(0);
			}
		})
		
		
		/*
		 *分页功能 
		 */
	  	var page = new Page({
	  			container:"#roleinfo",
	  			template:"#role-template",
	  			url:getRolesUrl,
	  			data:{"pageSize":10}
	  	})
	  	page.init();	  	
	  	$("#search-role").click(function(){
	  		var name = $("#searchbyname").val();
	  		page.setData({"name":name})
	  		page.init();
	  	})
</script>