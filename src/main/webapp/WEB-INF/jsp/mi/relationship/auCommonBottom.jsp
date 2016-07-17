<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/x-handlebars-template" id="relationTable-template">
		{{#each this}}
		<tr>
			<td>{{name}}</td>
			<td>{{tableName}}</td>
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
		
		
	  	
		function getRelationshipData(){
			var relationship = {
				id:"",
				name:"",
				tableName:"",
				description:""
			};
		   for(var i in relationship){
		   		var value = $("[name="+i+"]").val();
		   		relationship[i]=value;
		   }
		   return relationship;
		}

		$("#submit").click(function(){
			var btn=$(this);
			var form = $(".form");
			var res = form.validate();
			if(res){
				var relationship = getRelationshipData();
				btn.attr("disabled","disabled");
				btn.addClass("disabled");

				   var relation = {adds:"",dels:''};
				   if(addRelation.length>0){
				   		relation.adds = addRelation.join("/")
				   }
				   if(delRelation.length>0){
				   		relation.dels = delRelation.join("/")
				   }
				   var newRelationTables = originalRelation.concat(addRelation);
				   for(var i=0;i<delRelation.length;i++){
						var index = $.inArray(delRelation[i],newRelationTables);
						index>-1&&newRelationTables.splice(index,1);
				   }
				   if(newRelationTables.length!=2){
					   alert("请选择两张表");
						btn.removeAttr("disabled");
						btn.removeClass("disabled");
					   return;
				   }
				   newRelationTables = newRelationTables.join("/");
				   
				$.ajax({
				   	type:"POST",
				   	url:saveRelationshipUrl,
				   	async:true,
// 				   	data:relationship,
				   	data:$.extend(relationship,{"newRelationTableIds":newRelationTables}),
				   	dataType:"json",
				   	success:function(data){
				   		if(data){
				   			if(!data.success){
				   				var name = data.field;
				   				if(name){
				   					var p = form.find("[name='"+name+"']");
				   					p.length>0&&(p.focus(),showerror(p,data.msg));
				   				}else{
				   					alert(data.msg);
				   				}
								$("body").scrollTop(0);
				   			}else{
				   				alert(data.msg);
				   				window.location.href=goBackUrl;
				   			}
				   		}
				   	},
				   	error:function(){
				   		alert(actionStr+"关系失败!");
						$("body").scrollTop(0);
				   	},
				   	complete:function(){
						btn.removeAttr("disabled");
						btn.removeClass("disabled");
				   	}
			   });
			}else{
				$("body").scrollTop(0);
			}
		});
		</script>
