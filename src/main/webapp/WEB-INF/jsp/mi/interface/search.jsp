<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
	<head>
         <%@include file="../inc/header.jsp" %>
		<title>后台管理</title>
	</head>
	<body>
		
		<!-- 头部导航条开始     -->
		<%@include file="../inc/nav.jsp" %>
		<!-- 头部导航条结束     -->
		<div class="clearfix"></div>
		
		<div class="container">
			<!-- 右边内容区域开始     -->
			<div class="main skin">
				<div class="content">
					<div class="box">
						<div class="box-hd">
							<h2>接口管理</h2>
						</div>
						<div class="box-cnt">
							<div class="datatable" id="interfaceList">
								<div class="datatabls-filter">
									<!--搜索：-->
									<input type="text" id="searchbyname" />
									<input type="button" class="btn btn-primary" id="search-interface" value="搜索" />
								</div>
								<table class="datatable-table">
									<thead>
										<c:if test="${fn:contains(loginedUserPermissions,'mi:interface:delete') }">
										<th class="checkarea">
											<input type="checkbox"  id="selectAll"/>
										</th>
										</c:if>
										<th>ID</th>
										<th class="name">名称</th>
										<th class="description">描述</th>
										<th class="time">最后修改时间</th>
										<th class="operation">操作</th>
									</thead>
									<tbody class="page-data-list">
									</tbody>
								</table>
								<div class="datatable-toolbar disabled">
									<div class="toolbar">
										<c:if test="${fn:contains(loginedUserPermissions,'mi:interface:delete') }">
										<select id="batch_option">
											<option value="del" selected="selected">删除</option>
										</select>
										<a class="btn btn-primary" href="javascript:;" onclick="batchOperation(this);">批量操作</a>
										</c:if>
										
										<c:if test="${fn:contains(loginedUserPermissions,'mi:interface:add') }">
										<a class="btn btn-primary" href="${ctx}/mi/interface/add">新增</a>
										</c:if>
									</div>
								</div>
								<div class="datatable-footer">
									<div class="datatable-info">
										共32条 当前展示第1条到第10条
									</div>
									<div class="center">
										<div class="datatable-pagination">
											<ul class="pagination">
												
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 右边内容区域结束     -->
			
			
			
			<!-- 左边侧边栏区域开始     -->
			<div class="slider skin">
				<div class="clearfix">&nbsp;</div>
				<div class="clearfix">&nbsp;</div>
				<%@include file="../inc/left.jsp" %>
			</div>
			
			<!-- 左边侧边栏区域结束     -->
		</div>
		
		<!-- 底部区域开始     -->
		<%@include file="../inc/footer.jsp" %>
		<!-- 底部区域结束     -->	
		<script type="text/x-handlebars" id = "interface-template">
			{{#each this}}
			<tr>
				<c:if test="${fn:contains(loginedUserPermissions,'mi:interface:delete') }">
				<td>
					<input type="checkbox" value="{{id}}"/>
				</td>
				</c:if>
				<td>{{id}}</td>
				<td>{{name}}</td>
				<td>{{description}}</td>
				<td>{{dateformat updateDate 3}}</td>
				<td>
					<c:if test="${fn:contains(loginedUserPermissions,'mi:interface:view') }">
					<a class="btn btn-info" href="${ctx}/mi/interface/view/{{id}}">
						<i class="icon-zoom-in "></i>                                            
					</a>
					</c:if>
					<c:if test="${fn:contains(loginedUserPermissions,'mi:interface:update') }">
					<a class="btn btn-info" href="${ctx}/mi/interface/update/{{id}}">
						<i class="icon-edit "></i>                                            
					</a>
					</c:if>
					<c:if test="${fn:contains(loginedUserPermissions,'mi:interface:delete') }">
					<a class="btn btn-danger" href="javascript:;" onclick="delRep(this,'{{id}}');return false;" data-id="{{id}}">
						<i class="icon-trash "></i> 
					</a>
					</c:if>
				</td>
			</tr>
			{{/each}}
		</script>
	</body>
	<script>
		var deling = false;
	  	//checkbox 全选
	  	$("#selectAll").on("change",function(){
	  		if($(this).is(":checked")){
	  			$(".page-data-list").find("input[type='checkbox']").prop("checked","checked");
	  		}else{
	  			$(".page-data-list").find("input[type='checkbox']").prop("checked",false);
	  		}
	  	});
	  	
	  	/*
	  	 * 批量操作
	  	 */
	  	function batchOperation(e){
	  		var interfaceIds = "";
	  		$(".page-data-list").find("input[type='checkbox']").each(function(idx,item){
	  			if($(item).is(":checked")){
	  			interfaceIds==""?interfaceIds=$(item).val():interfaceIds+="/"+$(item).val();
	  			}
	  		});
	  		if(interfaceIds == ""){
	  			alert("请选择需要处理的接口");
				return ;
	  		}
	  		delRep(e,interfaceIds);
	  	}
	  	function delRep(e,interfaceIds){	  		
			if(!window.confirm("确认删除?")){
	  			return ;
	  		}
	  		if(deling){
	  			alert("正在删除接口,请稍后再操作");
	  			return;
	  		}
	  		deling = true;
	  		var url = "${ctx}/mi/interface/delete/batch/json";
	  		$.ajax({
	  			type:"post",
	  			data:{"interfaceIds":interfaceIds},
	  			url:url,
	  			async:true,
	  			dataType:"json",
	  			success:function(data){
	  				if(data){
	  					if(data.success){
	  						alert(data.msg);
	  						page.reloadPage();
	  					}else{
	  						alert(data.msg);
	  					}
	  				}
	  			},
	  			error:function(){
	  				alert("删除失败");
	  			},
	  			complete:function(){
	  				deling = false;
	  			}
	  		});
	  	}
	  	
	  	
	  	/*
	  	 * 分页
	  	 * 
	  	 */
	  	var page = new Page({
	  			container:"#interfaceList",
	  			template:"#interface-template",
	  			url:"${ctx}/mi/interface/search/page/json/",
	  			data:{"pageSize":10}
	  	});
	  	page.init();
	  	
	  	$("#search-interface").click(function(){
	  		var name = $("#searchbyname").val();
	  		page.setData({"name":name});
	  		page.init();
	  	});
	</script>
</html>
