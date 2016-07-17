<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function initInformationData(){
	$.ajax({
		type:"get",
		url:getInformationViewUrl,
		async:true,
		dataType:"json",
		success:function(data){
			if(data){
				var information = data.information;
				for(var i in information){
					var ele = $("[name="+i+"]");
					if(information[i]==null){
						continue;
					}
					if(ele[0]){
						if(i=="content"){
							ele.html(information[i]);
						}else{
							ele.val(information[i]);
						}
					}
					if(i=="preViewUrl"){
						$(".control-img").find("img").attr("src",information[i]);
					}
				}
				if($("#UEContainer")[0]){
		   			if(ue.isReady){
			   			ue.setContent(information["content"]);
		   			}else{
				        ue.ready(function() {
				            ue.setContent(information["content"]);
				        });
		   			}
				}
			}
		},
		error:function(){
			alert("获取资讯信息失败");
		}
	});
}
initInformationData();
</script>