<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="box-cnt js-ht-rings-container" style="display:none">
	<div class="datatable" id="ringList">
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
				</select> <a class="btn btn-primary" href="javascript:;" onclick="ringBatchOperation(this);">批量操作</a>
				<a class="btn btn-primary" href="${toDesignRingAddUrl}">新增</a>
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
		var ringList = $("#ringList");
		var ringCheckList = ringList.find(".page-data-list");
		var ringDeling = false;
	  	//checkbox 全选
	  	ringList.find("#selectAll").on("change",function(){
	  		if($(this).is(":checked")){
	  			ringCheckList.find("input[type='checkbox']").prop("checked","checked");
	  		}else{
	  			ringCheckList.find("input[type='checkbox']").prop("checked",false);
	  		}
	  	});
	  	
	  	/*
	  	 * 批量操作
	  	 */
	  	function ringBatchOperation(e){
	  		var ringIds = "";
	  		ringCheckList.find("input[type='checkbox']").each(function(idx,item){
	  			if($(item).is(":checked")){
	  			ringIds==""?ringIds=$(item).val():ringIds+="/"+$(item).val();
	  			}
	  		});
	  		if(ringIds == ""){
	  			alert("请选择需要处理的三维");
				return ;
	  		}
	  		delRing(e,ringIds);
	  	}
	  	function delRing(e,ringIds){
	  		if(!window.confirm("确认删除?")){
	  			return ;
	  		}
	  		if(ringDeling){
	  			alert("正在删除三维,请稍后再操作");
	  			return;
	  		}
	  		ringDeling = true;
	  		var url = "${designRingDeleteBatchJsonUrl}";
	  		$.ajax({
	  			type:"post",
	  			data:{"designRingIds":ringIds},
	  			url:url,
	  			async:true,
	  			dataType:"json",
	  			success:function(data){
	  				if(data){
	  					if(data.success){
	  						alert(data.msg);
	  						ringPage.reloadPage();
	  					}else{
	  						alert(data.msg);
	  					}
	  				}
	  			},
	  			error:function(){
	  				alert("删除失败");
	  			},
	  			complete:function(){
	  				ringDeling = false;
	  			}
	  		});
	  	}
	  	
	  	
	  	/*
	  	 * 分页
	  	 * 
	  	 */
	  	var ringPage = new Page({
	  			container:"#ringList",
	  			template:"#ring-template",
	  			url:"${toDesignRingSearchPageJsonUrl}",
	  			data:{pageSize:10,houseTypeId:"${id}"}
	  	});
	  	
	  	ringList.find(".js-search-btn").click(function(){
	  		var name = ringList.find(".js-search-text").val();
	  		ringPage.setData({"name":name});
	  		ringPage.init();
	  	});
	</script>