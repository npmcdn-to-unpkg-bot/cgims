<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script type="text/x-handlebars" id="ht-template">
			{{#each this}}
			<tr>
<c:if test="${inUpdate}">
				<td>
					<input type="checkbox" value="{{id}}"/>
				</td>
</c:if>
				<td>{{name}}</td>
				<td>{{priority}}</td>
				<td>{{dateformat updateDate 3}}</td>
				<td>
					<a class="btn btn-info" href="${toHouseTypeViewUrl}">
						<i class="icon-zoom-in "></i>                                            
					</a>
<c:if test="${inUpdate}">
					<a class="btn btn-info" href="${toHouseTypeUpdateUrl}">
						<i class="icon-edit "></i>                                            
					</a>
					<a class="btn btn-danger" href="javascript:;" onclick="delHt(this,'{{id}}');return false;" data-id="{{id}}">
						<i class="icon-trash "></i> 
					</a>
</c:if>
				</td>
			</tr>
			{{/each}}
		</script>
<script>
var htList = $("#htList");
var htCheckList = htList.find(".page-data-list");
var htDeling = false;
	//checkbox 全选
	htList.find("#selectAll").on("change",function(){
		if($(this).is(":checked")){
			htCheckList.find("input[type='checkbox']").prop("checked","checked");
		}else{
			htCheckList.find("input[type='checkbox']").prop("checked",false);
		}
	});
	
	/*
	 * 批量操作
	 */
	function htBatchOperation(e){
		var htIds = "";
		htCheckList.find("input[type='checkbox']").each(function(idx,item){
			if($(item).is(":checked")){
			htIds==""?htIds=$(item).val():htIds+="/"+$(item).val();
			}
		});
		if(htIds == ""){
			alert("请选择需要处理的户型");
		return ;
		}
		delHt(e,htIds);
	}
	function delHt(e,htIds){
		if(!window.confirm("确认删除?")){
			return ;
		}
		if(htDeling){
			alert("正在删除户型,请稍后再操作");
			return;
		}
		htDeling = true;
		var url = "${houseTypeDeleteBatchJsonUrl}";
		$.ajax({
			type:"post",
			data:{"houseTypeIds":htIds},
			url:url,
			async:true,
			dataType:"json",
			success:function(data){
				if(data){
					if(data.success){
						alert(data.msg);
						htPage.reloadPage();
					}else{
						alert(data.msg);
					}
				}
			},
			error:function(){
				alert("删除失败");
			},
			complete:function(){
				htDeling = false;
			}
		});
	}
	
	
	/*
	 * 分页
	 * 
	 */
	var htPage = new Page({
			container:"#htList",
			template:"#ht-template",
			url:"${houseTypeSearchPageJsonUrl}",
			data:{"pageSize":10,"realEstateProjectId":"${id}"}
	});
	
	htList.find(".js-search-btn").click(function(){
		var name = htList.find(".js-search-text").val();
		htPage.setData({"name":name});
		htPage.init();
	});
function openCloseREPDetail(clazz){
	var ele = $("."+clazz);
	if(ele.is(':hidden')){
		ele.show();
	}else{
		ele.hide();
	}
	if(!ele.attr("first")){
		ele.attr("first",2);
		if("js-rep-detail-container"==clazz){
			initREPData();
		}else if("js-rep-ht-container"==clazz){
		  	htPage.init();
		}
	}
}

function initREPData(){
	$.ajax({
		type:"get",
		url:getRealEstateProjectViewUrl,
		async:true,
		dataType:"json",
		success:function(data){
			if(data){
				var rep = data.realEstateProject;
				for(var i in rep){
					var ele = $("[name="+i+"]");
					if(rep[i]==null){
						continue;
					}
					if(ele[0]){
						ele.val(rep[i]);
					}
				}
			}
		},
		error:function(){
			alert("获取楼盘信息失败");
		}
	});
}
</script>