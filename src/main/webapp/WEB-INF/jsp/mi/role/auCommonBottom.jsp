<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script type="text/x-handlebars-template" id="permission-template">
		{{#each this}}
		<tr>
			<td>{{name}}</td>
			<td>{{code}}</td>
			<td>{{desciption}}</td>
			<td>
				<a class="btn btn-info btn-add-relation" href="javascript:;" data-id="{{id}}" data-name="{{name}}">
					<i class="icon-plus "></i>                                            
				</a>
			</td>
		</tr>
		{{/each}}
		</script>
<script>
		/*
		 * 
		 * 新增关联
		 * 
		 */
		var addRelation = [];
		var delRelation = [];
		$(".datatable-table").on("click",".btn-add-relation",function(){
			var id = $(this).data("id");
			var name = $(this).data("name");
			var didx = $.inArray(id,delRelation);//不在已删除
			var oidx = $.inArray(id,originalRelation);
			didx>-1&&delRelation.splice(didx,1);
			if(oidx>-1&&didx==-1||$.inArray(id,addRelation)>-1){//原来有，未删除不加
				return;
			}
			addRelation.push(id);
			console.log(delRelation);
			console.log(addRelation);
			$(".relation").append(template("#relation-info-template",{"id":id,"name":name}))
			return false;
		})
		
		/*
		 * 删除关联
		 */
		$(".relation").on("click",".btn-remove-relation",function(){
			var id = $(this).data("id");
			var aidx = $.inArray(id,addRelation);//不在已删除
			aidx>-1&&addRelation.splice(aidx,1);
			if($.inArray(id,delRelation)==-1){//del中没有，则存入
				delRelation.push(id);
			}
			console.log(delRelation);
			console.log(addRelation);
			$(this).parent().parent().remove();
			return false;
		})
		
		
		/*
		 *分页功能 
		 */
	  	var page = new Page({
	  			container:"#permission",
	  			template:"#permission-template",
	  			url:getPermissionsUrl,
	  			data:{"pageSize":10}
	  	})
	  	page.init();
	  	
	  	$("#search-btn").click(function(){
	  		var name = $("#searchbyname").val();
	  		var code = $("#searchbycode").val();
	  		page.setData({"name":name,"code":code})
	  		page.init();
	  	})
		
/*
		 * 保存
		 * 
		 */
		
		$("#submit").click(function(){
			var btn=$(this);
			var form = $(".form");
			var res = form.validate();
			if(res){
				var role = {
					id:"",
					name:"",
					description:""
				}
			   for(var i in role){
			   		var value = form.find("[name="+i+"]").val();
			   		role[i]=value;
			   }
			   var relation = {adds:"",dels:''};
			   if(addRelation.length>0){
			   		relation.adds = addRelation.join("/")
			   }
			   if(delRelation.length>0){
			   		relation.dels = delRelation.join("/")
			   }
			   var newPermissions = originalRelation.concat(addRelation);
			   for(var i=0;i<delRelation.length;i++){
					var index = $.inArray(delRelation[i],newPermissions);
					index>-1&&newPermissions.splice(index,1);
			   }
			   newPermissions = newPermissions.join("/");
			btn.attr("disabled","disabled");
			   $.ajax({
			   	type:"POST",
			   	url:saveRoleUrl,
			   	async:true,
			   	data:$.extend(role,{"newPermissionIds":newPermissions}),
			   	dataType:"json",
			   	success:function(data){
			   		if(data){
			   			if(!data.success){
			   				var name = data.field;
			   				if(name){
			   					var p = form.find("[name='"+name+"']");
			   					p.length>0&&(p.focus(),showerror(p,data.msg))
			   				}else{
			   					alert(data.msg)
			   				}
							$("body").scrollTop(0);
			   			}else{
			   				alert(data.msg)
			   				window.location.href=goBackUrl;
			   			}
			   		}
			   	},
			   	error:function(){
			   		alert(actionStr+"角色失败!");
			   	},
			   	complete:function(){
			   		btn.removeAttr("disabled");
			   	}
			   });
			   
			}else{
				$("body").scrollTop(0);
			}
		})
		
	</script>