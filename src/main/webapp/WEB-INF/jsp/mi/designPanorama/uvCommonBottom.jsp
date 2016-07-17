<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function initPanoData(){
	$.ajax({
		type:"get",
		url:getPanoramaViewUrl,
		async:true,
		dataType:"json",
		success:function(data){
			if(data){
				var pano = data.designPanorama;
				
				for(var i in pano){
					var ele = $("[name="+i+"]");
					if(ele[0]){
						ele.val(pano[i]);
					}
				}
				var preViewUrl = pano["preViewUrl"];
				if(preViewUrl){
					$(".control-img").find("img").attr("src",preViewUrl);
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
			alert("获取效果全景信息失败");
		}
	});
}
initPanoData();
</script>