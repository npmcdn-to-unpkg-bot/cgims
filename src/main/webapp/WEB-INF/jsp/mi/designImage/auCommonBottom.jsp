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
		$("#imageFile").change(function(){
			if(!!uploading){
				alert("正在上传，请稍后..");
				return ;
			}
			var errorStr = checkImgType(this);
			if(errorStr){
				alert(errorStr);
				return;
			}
			var formData = new FormData($("#uploadImageForm")[0]);	
			$(".uploader-image-loading").show();
			uploading =!0;		
		    $.ajax({
		        type:'POST',
		        url:designImageContentUploadUrl,
		        data: formData,
		        async: true,
		        cache: false,
				dataType : "json",
		        contentType: false,
		        processData: false,
		        success: function (data) {
					if(data.success){
						var final_url = data.imgPath;
						$("[name='contentUrl']").val(final_url);
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
					$(".uploader-image-loading").hide();
				}
		    });
		});
		function getImageData(){
			var image = {
				id:"",
				name:"",
				description:"",
				priority:"",
				contentUrl:"",
				outLinkUrl:"",
				houseTypeId:"",
				realEstateProjectId:""
			};
		   for(var i in image){
		   		var value = $("[name="+i+"]").val();
		   		image[i]=value;
		   }
		   return image;
		}

		$("#submit").click(function(){
			if(!!uploading){
				alert("正在上传，请稍后..");
				return ;
			}
			var btn=$(this);
			var form = $(".form");
			console.log($(".form").children())
			var res = form.validate();
			if(res){
				var image = getImageData();	
//				if(!image.housePlanUrl){
//					$(".uploade-img-error").html("户型缩略图不能为空");
//					return;
//				}else{
//					$(".uploade-img-error").html("");
//				}	
				   var newProductIds = originalRelation.concat(addRelation);
				   for(var i=0;i<delRelation.length;i++){
						var index = $.inArray(delRelation[i],newProductIds);
						index>-1&&newProductIds.splice(index,1);
				   }
				   newProductIds = newProductIds.join("/");
			btn.attr("disabled","disabled");
			btn.addClass("disabled");
			   $.ajax({
			   	type:"POST",
			   	url:saveDesignImageUrl,
			   	async:true,
// 			   	data:$.extend(image,{"realEstateProjectId":"${realEstateProjectId}"),
// 			   	data:image,
			   	data:$.extend(image,{"newProductIds":newProductIds}),
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
// 							window.history.back(-1);
			   			}
			   		}
			   	},
			   	error:function(){
					alert(actionStr+"效果图片失败!");
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
		<%@include file="../inc/relate2product/auCommonBottom.jsp" %>