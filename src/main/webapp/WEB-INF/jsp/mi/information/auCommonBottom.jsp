<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script>
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
			        url:informationPreViewUploadUrl,
			        data: formData,
			        async: true,
			        cache: false,
					dataType : "json",
			        contentType: false,
			        processData: false,
			        success: function (data) {
						if(data.success){
							var final_url = data.imgPath;
							$("[name='preViewUrl']").val(final_url);
							$(".control-img").find("img").attr("src",final_url);
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
		
		function getInformationData(){
			var information = {
				id:"",
				name:"",
				preViewUrl:"",
				author:"",
				summary:"",
				content:"",
				contentText:"",
				description:"",
				priority:"",
			};
		   for(var i in information){
		   		var value = $("[name="+i+"]").val();
		   		if(i=="content"){
		   			value = UE.getEditor('UEContainer').getContent();
		   		}
		   		if(i=="contentText"){
		   			value = UE.getEditor('UEContainer').getContentTxt();
		   		}
		   		information[i]=value;
		   }
		   return information;
		}

		$("#submit").click(function(){
			if(!!uploading){
				alert("图像正在上传，请稍后..");
				return ;
			}
			var btn=$(this);
			var form = $(".form");
			var res = form.validate();
			if(res){
				var information = getInformationData();
				btn.attr("disabled","disabled");
				btn.addClass("disabled");
				$.ajax({
				   	type:"POST",
				   	url:saveInformationUrl,
				   	async:true,
				   	data:information,
				   	dataType:"json",
				   	success:function(data){
				   		if(data){
				   			if(!data.success){
				   				var name = data.field;
				   				if(name){
				   					var p = form.find("[name='"+name+"']");
				   					p.length>0&&(p.focus(),showerror(p,data.msg));
				   				}else{
				   					alert(data.msg);
				   				}
								$("body").scrollTop(0);
				   			}else{
				   				alert(data.msg);
				   				window.location.href=goBackUrl;
				   			}
				   		}
				   	},
				   	error:function(){
				   		alert(actionStr+"资讯失败!");
						$("body").scrollTop(0);
				   	},
				   	complete:function(){
						btn.removeAttr("disabled");
						btn.removeClass("disabled");
				   	}
			   });
			}else{
				$("body").scrollTop(0);
			}
		});
		</script>
