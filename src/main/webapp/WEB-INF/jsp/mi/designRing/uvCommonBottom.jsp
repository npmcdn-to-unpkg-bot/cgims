<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function initRingData(){
	$.ajax({
		type:"get",
		url:getRingViewUrl,
		async:true,
		dataType:"json",
		success:function(data){
			if(data){
				var ring = data.designRing;
				for(var i in ring){
					var ele = $("[name="+i+"]");
					if(ele[0]){
						ele.val(ring[i]);
					}
				}
				var preViewUrl = ring["preViewUrl"];
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
			alert("获取效果三维信息失败");
		}
	});
}
initRingData();
</script>