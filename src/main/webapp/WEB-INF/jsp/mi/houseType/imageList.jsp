<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="box-cnt js-ht-images-container" style="display:none">
	<div class="datatable" id="imageList">
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
				</select> <a class="btn btn-primary" href="javascript:;" onclick="imageBatchOperation(this);">批量操作</a>
				<a class="btn btn-primary" href="${toDesignImageAddUrl}">新增</a>
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
		var imageList = $("#imageList");
		var imageCheckList = imageList.find(".page-data-list");
		var imageDeling = false;
	  	//checkbox 全选
	  	imageList.find("#selectAll").on("change",function(){
	  		if($(this).is(":checked")){
	  			imageCheckList.find("input[type='checkbox']").prop("checked","checked");
	  		}else{
	  			imageCheckList.find("input[type='checkbox']").prop("checked",false);
	  		}
	  	});
	  	
	  	/*
	  	 * 批量操作
	  	 */
	  	function imageBatchOperation(e){
	  		var imageIds = "";
	  		imageCheckList.find("input[type='checkbox']").each(function(idx,item){
	  			if($(item).is(":checked")){
	  			imageIds==""?imageIds=$(item).val():imageIds+="/"+$(item).val();
	  			}
	  		});
	  		if(imageIds == ""){
	  			alert("请选择需要处理的图片");
				return ;
	  		}
	  		delImage(e,imageIds);
	  	}
	  	function delImage(e,imageIds){
	  		if(!window.confirm("确认删除?")){
	  			return ;
	  		}
	  		if(imageDeling){
	  			alert("正在删除图片,请稍后再操作");
	  			return;
	  		}
	  		imageDeling = true;
	  		var url = "${designImageDeleteBatchJsonUrl}";
	  		$.ajax({
	  			type:"post",
	  			data:{"designImageIds":imageIds},
	  			url:url,
	  			async:true,
	  			dataType:"json",
	  			success:function(data){
	  				if(data){
	  					if(data.success){
	  						alert(data.msg);
	  						imagePage.reloadPage();
	  					}else{
	  						alert(data.msg);
	  					}
	  				}
	  			},
	  			error:function(){
	  				alert("删除失败");
	  			},
	  			complete:function(){
	  				imageDeling = false;
	  			}
	  		});
	  	}
	  	
	  	
	  	/*
	  	 * 分页
	  	 * 
	  	 */
	  	var imagePage = new Page({
	  			container:"#imageList",
	  			template:"#image-template",
	  			url:"${toDesignImageSearchPageJsonUrl}",
	  			data:{pageSize:10,houseTypeId:"${id}"}
	  	});
	  	
	  	imageList.find(".js-search-btn").click(function(){
	  		var name = imageList.find(".js-search-text").val();
	  		imagePage.setData({"name":name});
	  		imagePage.init();
	  	});
	</script>