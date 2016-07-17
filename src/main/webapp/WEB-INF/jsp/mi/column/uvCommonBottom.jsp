<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function openCloseColumnDetail(clazz){
	var ele = $("."+clazz);
	if(ele.is(':hidden')){
		ele.show();
	}else{
		ele.hide();
	}
	if(!ele.attr("first")){
		ele.attr("first",2);
		if("js-column-detail-container"==clazz){
			initColumnData();
		}
	}
}

function initColumnData(){
	$.ajax({
		type:"get",
		url:getColumnViewUrl,
		async:true,
		dataType:"json",
		success:function(data){
			if(data){
				var column = data.column;
				for(var i in column){
					var ele = $("[name="+i+"]");
					if(ele[0]){
						ele.val(column[i]);
					}
				}
				var dataType = column.dataType;
				if(dataType["id"]){
					$("[name=dataTypeId]").val(dataType["id"]);
				}
				if(dataType["name"]){
					$("[name=dataTypeName]").val(dataType["name"]);
				}
			}
		},
		error:function(){
			alert("获取列信息失败");
		}
	});
}
</script>