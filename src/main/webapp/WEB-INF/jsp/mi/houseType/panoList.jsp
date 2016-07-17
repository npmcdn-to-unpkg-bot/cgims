<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="box-cnt js-ht-panos-container" style="display:none">
	<div class="datatable" id="panoList">
		<div class="datatabls-filter">
			<!--搜索：--> 
			<input type="text" class="js-search-text"  placeholder="名称"/>
			<input type="button" class="btn js-search-btn" value="搜索"/>
		</div>
		<table class="datatable-table">
			<thead>
<c:if test="${inUpdate}">
				<th class="checkarea"><input type="checkbox" id="selectAll" /></th>
</c:if>
				<th>预览</th>
				<th class="name">名称</th>
				<th class="description">描述</th>
				<th class="operation">操作</th>
			</thead>
			<tbody class="page-data-list">
			</tbody>
		</table>
<c:if test="${inUpdate}">
		<div class="datatable-toolbar">
			<div class="toolbar">
				<select id="batch_option">
					<option value="del" selected="selected">删除</option>
				</select> <a class="btn btn-primary" href="javascript:;" onclick="panoBatchOperation(this);">批量操作</a>
				<a class="btn btn-primary" href="${toDesignPanoramaAddUrl}">新增</a>
			</div>
		</div>
</c:if>
		<div class="datatable-footer">
			<div class="datatable-info">共0条</div>
			<div class="center">
				<div class="datatable-pagination">
					<ul class="pagination">
	
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
		<script>
		var panoList = $("#panoList");
		var panoCheckList = panoList.find(".page-data-list");
		var panoDeling = false;
	  	//checkbox 全选
	  	panoList.find("#selectAll").on("change",function(){
	  		if($(this).is(":checked")){
	  			panoCheckList.find("input[type='checkbox']").prop("checked","checked");
	  		}else{
	  			panoCheckList.find("input[type='checkbox']").prop("checked",false);
	  		}
	  	});
	  	
	  	/*
	  	 * 批量操作
	  	 */
	  	function panoBatchOperation(e){
	  		var panoIds = "";
	  		panoCheckList.find("input[type='checkbox']").each(function(idx,item){
	  			if($(item).is(":checked")){
	  			panoIds==""?panoIds=$(item).val():panoIds+="/"+$(item).val();
	  			}
	  		});
	  		if(panoIds == ""){
	  			alert("请选择需要处理的全景");
				return ;
	  		}
	  		delPano(e,panoIds);
	  	}
	  	function delPano(e,panoIds){
	  		if(!window.confirm("确认删除?")){
	  			return ;
	  		}
	  		if(panoDeling){
	  			alert("正在删除全景,请稍后再操作");
	  			return;
	  		}
	  		panoDeling = true;
	  		var url = "${designPanoramaDeleteBatchJsonUrl}";
	  		$.ajax({
	  			type:"post",
	  			data:{"designPanoramaIds":panoIds},
	  			url:url,
	  			async:true,
	  			dataType:"json",
	  			success:function(data){
	  				if(data){
	  					if(data.success){
	  						alert(data.msg);
	  						panoPage.reloadPage();
	  					}else{
	  						alert(data.msg);
	  					}
	  				}
	  			},
	  			error:function(){
	  				alert("删除失败");
	  			},
	  			complete:function(){
	  				panoDeling = false;
	  			}
	  		});
	  	}
	  	
	  	
	  	/*
	  	 * 分页
	  	 * 
	  	 */
	  	var panoPage = new Page({
	  			container:"#panoList",
	  			template:"#pano-template",
	  			url:"${toDesignPanoramaSearchPageJsonUrl}",
	  			data:{pageSize:10,houseTypeId:"${id}"}
	  	});
	  	
	  	panoList.find(".js-search-btn").click(function(){
	  		var name = panoList.find(".js-search-text").val();
	  		panoPage.setData({"name":name});
	  		panoPage.init();
	  	});
	</script>