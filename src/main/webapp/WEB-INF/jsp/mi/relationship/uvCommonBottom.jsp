<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function openCloseRelationshipDetail(clazz){
	var ele = $("."+clazz);
	if(ele.is(':hidden')){
		ele.show();
	}else{
		ele.hide();
	}
	if(!ele.attr("first")){
		ele.attr("first",2);
		if("js-relationship-detail-container"==clazz){
			initRelationshipData();
		}else if("js-relationship-product-container"==clazz){
		  	htPage.init();
		}
	}
}

function initRelationshipData(){
	$.ajax({
		type:"get",
		url:getRelationshipViewUrl,
		async:true,
		dataType:"json",
		success:function(data){
			if(data){
				var relationship = data.relationship;
				var relations = data.relations;
				for(var i in relationship){
					var ele = $("[name="+i+"]");
					if(relationship[i]==null){
						continue;
					}
					if(ele[0]){
						ele.val(relationship[i]);
					}
				}
				if(relations){
					for(var i=0,l = relations.length;i<l;i++){
						originalRelation.push(relations[i].id);
					}
					$(".relation").append(template("#relation-info-template",relations))
				}
			}
		},
		error:function(){
			alert("获取关系信息失败");
		}
	});
}
</script>