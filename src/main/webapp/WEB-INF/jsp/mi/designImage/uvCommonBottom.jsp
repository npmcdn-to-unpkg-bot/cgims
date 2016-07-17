<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function initImageData(){
	$.ajax({
		type:"get",
		url:getImageViewUrl,
		async:true,
		dataType:"json",
		success:function(data){
			if(data){
				var image = data.designImage;
				for(var i in image){
					var ele = $("[name="+i+"]");
					if(ele[0]){
						ele.val(image[i]);
					}
				}
				var contentUrl = image["contentUrl"];
				if(contentUrl){
					$(".control-img").find("img").attr("src",contentUrl);
				}
				
				var relationProducts = data.relationProducts;
				if(relationProducts){
					for(var i=0,l = relationProducts.length;i<l;i++){
						originalRelation.push(relationProducts[i].id);
					}
					$(".relation").append(template("#relation-product-template",relationProducts));
				}
			}
		},
		error:function(){
			alert("获取效果图片信息失败");
		}
	});
}
initImageData();
</script>