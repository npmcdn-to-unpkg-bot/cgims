<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	
	<script>
	  	var dataTypePage = new Page({
	  			container:"#dataTypeInfo",
	  			template:"#dataType-template",
	  			url:searchDataTypeUrl,
	  			data:{"pageSize":10}
	  	});
	  	dataTypePage.init();	  	
	  	$("#search-dataType-btn").click(function(){
// 	  		var name = encodeURIComponent($("#search-rc-text").val());
	  		var name = $("#search-dataType-text").val();
	  		dataTypePage.setData({"name":name});
	  		dataTypePage.init();
	  	});

		$("#dataTypeInfo").find(".datatable-table").on("click",".btn-add-relation",function(){
			var id = $(this).data("id");
			var name = $(this).data("name");
			$("[name=dataTypeId]").val(id);
			$("[name=dataTypeName]").val(name);
			return false;
		});
	</script>
		<script type="text/x-handlebars-template" id="dataType-template">
		{{#each this}}
		<tr>
			<td>{{name}}</td>
			<td>{{code}}</td>
			<td>{{description}}</td>
			<td>
				<a class="btn btn-info btn-add-relation" href="javascript:;" data-id="{{id}}" data-name="{{name}}">
					<i class="icon-plus "></i>                                            
				</a>
			</td>
		</tr>
		{{/each}}
		</script>
		
		<script>
		function getColumnData(){
			var column = {
				id:"",
				name:"",
				description:"",
				dataTypeExtension:"",
				dataTypeId:"",
				tableId:"",
				columnName:""
			};
		   for(var i in column){
		   		var value = $("[name="+i+"]").val();
		   		column[i]=value;
		   }
		   return column;
		}

		$("#submit").click(function(){
			var btn=$(this);
			var form = $(".form");
			console.log($(".form").children())
			var res = form.validate();
			if(res){
				var column = getColumnData();	
//				if(!column.preViewUrl){
//					$(".uploade-img-error").html("列缩略图不能为空");
//					return;
//				}else{
//					$(".uploade-img-error").html("");
//				}	
			btn.attr("disabled","disabled");
			btn.addClass("disabled");
			   $.ajax({
			   	type:"POST",
			   	url:saveColumnUrl,
			   	async:true,
// 			   	data:$.extend(column,{"tableId":"${tableId}"),
			   	data:column,
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
// 							window.history.back(-1);
			   			}
			   		}
			   	},
			   	error:function(){
					alert(actionStr+"列失败!");
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