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
							<h2>用户管理</h2>
						</div>
						<div class="box-cnt">
							<div class="datatable" id="userinfo">
								<div class="datatabls-filter">
									<!--搜索：-->
									<input type="text" id="searchbyname" />
									<input type="button" class="btn btn-primary" id="search-user" value="搜索" />
								</div>
								<table class="datatable-table">
									<thead>
		    							<c:if test="${fn:contains(loginedUserPermissions,'mi:user:delete') and fn:contains(loginedUserPermissions,'mi:user:update') }">
										<th class="checkarea">
											<input type="checkbox"  id="selectAll"/>
										</th>
										</c:if>
										<th class="name">名称</th>
										<th>邮箱</th>
										<th>手机号码</th>
										<th>状态</th>
<!-- 										<th class="description">描述</th> -->
										<!--<th>创建者</th>-->
										<!--<th>上次编辑</th>
										<th>创建日期</th>-->
										<th class="time">最后修改时间</th>
										<th class="operation">操作</th>
									</thead>
									<tbody class="page-data-list">
									</tbody>
								</table>
								<div class="datatable-toolbar disabled">
									<div class="toolbar">
		    							<c:if test="${fn:contains(loginedUserPermissions,'mi:user:update') or fn:contains(loginedUserPermissions,'mi:user:delete') }">
										<select id="batch_option">
								    		<c:if test="${fn:contains(loginedUserPermissions,'mi:user:delete') }">
											<option value="del" selected="selected">删除</option>
											</c:if>
								    		<c:if test="${fn:contains(loginedUserPermissions,'mi:user:update') }">
											<option value="unlock">解冻</option>
											<option value="lock">锁定</option>
											</c:if>
										</select>
										<a class="btn btn-primary" href="javascript:;" onclick="batchOperation(this);">批量操作</a>
										</c:if>
							    		<c:if test="${fn:contains(loginedUserPermissions,'mi:user:add') }">
										<a class="btn btn-primary" href="${ctx}/mi/user/add">新增</a>
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
		<script type="text/x-handlebars" id = "user-info-template">
			{{#each this}}
			<tr>
		    	<c:if test="${fn:contains(loginedUserPermissions,'mi:user:delete') or fn:contains(loginedUserPermissions,'mi:user:update') }">
				<td>
					<input type="checkbox" value="{{id}}"/>
				</td>
				</c:if>
				<td>{{name}}</td>
				<td>{{email}}</td>
				<td>{{phoneNum}}</td>
				{{#if locked}}<td>锁定{{else}}<td>正常</td>{{/if}}
				<!--<td>{{description}}</td>
				<td>{{creater}}</td>
				<td>{{create}}</td>
				<td>{{lastEditor}}</td>
				<td>{{createDate}}</td>-->
				<td>{{dateformat updateDate 3}}</td>
				<td>
		    		<c:if test="${fn:contains(loginedUserPermissions,'mi:user:view') }">
					<a class="btn btn-info" href="${ctx}/mi/user/view/{{id}}">
						<i class="icon-zoom-in "></i>  
					</a>
					</c:if>
		    		<c:if test="${fn:contains(loginedUserPermissions,'mi:user:update') }">
					<a class="btn btn-info" href="${ctx}/mi/user/update/{{id}}">
						<i class="icon-edit "></i>                                            
					</a>
					</c:if>
		    		<c:if test="${fn:contains(loginedUserPermissions,'mi:user:delete') }">
					<a class="btn btn-danger" href="javascript:;" onclick="delUser(this);return false;" data-id="{{id}}">
						<i class="icon-trash "></i> 
					</a>
					</c:if>
				</td>
			</tr>
			{{/each}}
		</script>
	</body>
	<script>
	  	//checkbox 全选
	  	$("#selectAll").on("change",function(){
	  		if($(this).is(":checked")){
	  			$(".page-data-list").find("input[type='checkbox']").prop("checked","checked");
	  		}else{
	  			$(".page-data-list").find("input[type='checkbox']").prop("checked",false);
	  		}
	  	})
	  	
	  	/*
	  	 * 批量操作
	  	 */
	  	function batchOperation(e){
	  		var option = $("#batch_option").val();
	  		var userids = "";
	  		$(".page-data-list").find("input[type='checkbox']").each(function(idx,item){
	  			if($(item).is(":checked")){
	  			userids==""?userids=$(item).val():userids+="/"+$(item).val();
	  			}
	  		})
	  		if(userids == ""){
	  			alert("请选择需要更新的用户");
	  			return ;
	  		}
			var user = {};
			var msg = "确认更新用户?";
	  		if(option =="del"){
	  			user.delFlag = true;
	  			msg = "确认删除用户?";
	  		}else if(option == "unlock"){
	  			user.locked = false;
	  		}else if(option == "lock"){
	  			user.locked = true;
	  		}
	  		if(!window.confirm(msg)){
	  			return ;
	  		}
	  		if(deling){
	  			alert("正在更新用户,请稍后再操作");
	  			return;
	  		}
	  		if(option =="del"){
	  			deleteUsers(e,userids);
	  		}else{
		  		updateUsers(e,userids,user);
	  		}
	  	}
	  	function delUser(e){
	  		var id = $(e).data("id");
	  		if(!window.confirm("确认删除用户?")){
	  			return ;
	  		}
	  		if(deling){
	  			alert("正在删除用户,请稍后再操作");
	  			return;
	  		}
	  		var url = "${ctx}/mi/user/delete/json/"+id;
	  		$.ajax({
	  			type:"post",
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
	  				alert("操作失败")
	  			},
	  			complete:function(){
	  				 deling = false;
	  			}
	  		});
	  	}
	  	var deling = false;
	  	function updateUsers(e,userIds,user){
	  		var $e = $(this);
	  		var url = "${ctx}/mi/user/update/batch/json";
	  		$.ajax({
	  			type:"post",
	  			data:$.extend({"userIds":userIds},user),
	  			url:url,
	  			async:true,
	  			dataType:"json",
	  			success:function(data){
	  				if(data){
	  					if(data.success){
	  						alert(data.msg);
	  						page.reloadPage()
	  					}else{
	  						alert(data.msg);
	  					}
	  				}
	  			},
	  			error:function(){
	  				alert("操作失败")
	  			},
	  			complete:function(){
	  				 deling = false;
	  			}
	  		});
	  	}

	  	function deleteUsers(e,userIds,user){
	  		var $e = $(this);
	  		var url = "${ctx}/mi/user/delete/batch/json";
	  		$.ajax({
	  			type:"post",
	  			data:{"userIds":userIds},
	  			url:url,
	  			async:true,
	  			dataType:"json",
	  			success:function(data){
	  				if(data){
	  					if(data.success){
	  						alert(data.msg);
	  						page.reloadPage()
	  					}else{
	  						alert(data.msg);
	  					}
	  				}
	  			},
	  			error:function(){
	  				alert("操作失败")
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
	  			container:"#userinfo",
	  			template:"#user-info-template",
	  			url:"${ctx}/mi/user/search/page/json/",
	  			data:{"pageSize":10}
	  	})
	  	page.init();
	  	
	  	$("#search-user").click(function(){
	  		var name = $("#searchbyname").val();
	  		page.setData({"name":name})
	  		page.init();
	  	})
	  		</script>
</html>
