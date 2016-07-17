<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script type="text/x-handlebars" id="column-template">
			{{#each this}}
			<tr>
<c:if test="${inUpdate}">
				<td>
					<input type="checkbox" value="{{id}}"/>
				</td>
</c:if>
				<td>{{name}}</td>
				<td>{{columnName}}</td>
				<td>{{description}}</td>
				<td>{{dateformat updateDate 3}}</td>
				<td>
					<a class="btn btn-info" href="${toColumnViewUrl}">
						<i class="icon-zoom-in "></i>                                            
					</a>
<c:if test="${inUpdate}">
					<a class="btn btn-info" href="${toColumnUpdateUrl}">
						<i class="icon-edit "></i>                                            
					</a>
					<a class="btn btn-danger" href="javascript:;" onclick="delColumn(this,'{{id}}');return false;" data-id="{{id}}">
						<i class="icon-trash "></i> 
					</a>
</c:if>
				</td>
			</tr>
			{{/each}}
		</script>
		<script type="text/x-handlebars" id="relationship-template">
			{{#each this}}
			<tr>
<c:if test="${inUpdate}">
				<td>
					<input type="checkbox" value="{{id}}"/>
				</td>
</c:if>
				<td>{{name}}</td>
				<td>{{tableName}}</td>
				<td>{{description}}</td>
				<td>{{dateformat updateDate 3}}</td>
				<td>
					<a class="btn btn-info" href="${toRelationshipViewUrl}">
						<i class="icon-zoom-in "></i>                                            
					</a>
				</td>
			</tr>
			{{/each}}
		</script>
<script>
var columnList = $("#columnList");
var columnCheckList = columnList.find(".page-data-list");
var columnDeling = false;
	//checkbox 全选
	columnList.find("#selectAll").on("change",function(){
		if($(this).is(":checked")){
			columnCheckList.find("input[type='checkbox']").prop("checked","checked");
		}else{
			columnCheckList.find("input[type='checkbox']").prop("checked",false);
		}
	});
	
	/*
	 * 批量操作
	 */
	function columnBatchOperation(e){
		var columnIds = "";
		columnCheckList.find("input[type='checkbox']").each(function(idx,item){
			if($(item).is(":checked")){
			columnIds==""?columnIds=$(item).val():columnIds+="/"+$(item).val();
			}
		});
		if(columnIds == ""){
			alert("请选择需要处理的列");
		return ;
		}
		delColumn(e,columnIds);
	}
	function delColumn(e,columnIds){
		if(!window.confirm("确认删除?")){
			return ;
		}
		if(columnDeling){
			alert("正在删除列,请稍后再操作");
			return;
		}
		columnDeling = true;
		var url = "${columnDeleteBatchJsonUrl}";
		$.ajax({
			type:"post",
			data:{"columnIds":columnIds},
			url:url,
			async:true,
			dataType:"json",
			success:function(data){
				if(data){
					if(data.success){
						alert(data.msg);
						columnPage.reloadPage();
					}else{
						alert(data.msg);
					}
				}
			},
			error:function(){
				alert("删除失败");
			},
			complete:function(){
				columnDeling = false;
			}
		});
	}
	
	
	/*
	 * 分页
	 * 
	 */
	var columnPage = new Page({
			container:"#columnList",
			template:"#column-template",
			url:"${columnSearchPageJsonUrl}",
			data:{"pageSize":10,"tableId":"${id}"}
	});
	
	columnList.find(".js-search-btn").click(function(){
		var name = columnList.find(".js-search-text").val();
		columnPage.setData({"name":name});
		columnPage.init();
	});
	
	
	
	var relationshipList = $("#relationshipList");
	var relationshipCheckList = relationshipList.find(".page-data-list");
	var relationshipDeling = false;
		//checkbox 全选
	relationshipList.find("#selectAll").on("change",function(){
		if($(this).is(":checked")){
			relationshipCheckList.find("input[type='checkbox']").prop("checked","checked");
		}else{
			relationshipCheckList.find("input[type='checkbox']").prop("checked",false);
		}
	});
	
	/*
	 * 批量操作
	 */
	function relationshipBatchOperation(e){
		var relationshipIds = "";
		relationshipCheckList.find("input[type='checkbox']").each(function(idx,item){
			if($(item).is(":checked")){
			relationshipIds==""?relationshipIds=$(item).val():relationshipIds+="/"+$(item).val();
			}
		});
		if(relationshipIds == ""){
			alert("请选择需要处理的列");
		return ;
		}
		delRelationship(e,relationshipIds);
	}
	function delRelationship(e,relationshipIds){
		if(!window.confirm("确认删除?")){
			return ;
		}
		if(relationshipDeling){
			alert("正在删除列,请稍后再操作");
			return;
		}
		relationshipDeling = true;
		var url = "${relationshipDeleteBatchJsonUrl}";
		$.ajax({
			type:"post",
			data:{"relationshipIds":relationshipIds},
			url:url,
			async:true,
			dataType:"json",
			success:function(data){
				if(data){
					if(data.success){
						alert(data.msg);
						relationshipPage.reloadPage();
					}else{
						alert(data.msg);
					}
				}
			},
			error:function(){
				alert("删除失败");
			},
			complete:function(){
				relationshipDeling = false;
			}
		});
	}
	
	
	/*
	 * 分页
	 * 
	 */
	var relationshipPage = new Page({
			container:"#relationshipList",
			template:"#relationship-template",
			url:"${relationshipSearchPageJsonUrl}",
			data:{"pageSize":10,"tableId":"${id}"}
	});
	
	relationshipList.find(".js-search-btn").click(function(){
		var name = relationshipList.find(".js-search-text").val();
		relationshipPage.setData({"name":name});
		relationshipPage.init();
	});
		
function openCloseTableDetail(clazz){
	var ele = $("."+clazz);
	if(ele.is(':hidden')){
		ele.show();
	}else{
		ele.hide();
	}
	if(!ele.attr("first")){
		ele.attr("first",2);
		if("js-table-detail-container"==clazz){
			initTableData();
		}else if("js-table-column-container"==clazz){
		  	columnPage.init();
		}else if("js-table-relationship-container"==clazz){
			relationshipPage.init();
		}
	}
}

function initTableData(){
	$.ajax({
		type:"get",
		url:getTableViewUrl,
		async:true,
		dataType:"json",
		success:function(data){
			if(data){
				var table = data.table;
				for(var i in table){
					var ele = $("[name="+i+"]");
					if(table[i]==null){
						continue;
					}
					if(ele[0]){
						ele.val(table[i]);
					}
				}
			}
		},
		error:function(){
			alert("获取库表信息失败");
		}
	});
}
</script>