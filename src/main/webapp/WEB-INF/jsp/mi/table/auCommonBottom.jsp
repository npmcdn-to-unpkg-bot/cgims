<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script>

		function getTableData(){
			var table = {
				id:"",
				name:"",
				description:"",
				tableName:""
			};
		   for(var i in table){
		   		var value = $("[name="+i+"]").val();
		   		table[i]=value;
		   }
		   return table;
		}

		$("#submit").click(function(){
			var btn=$(this);
			var form = $(".form");
			var res = form.validate();
			if(res){
				var table = getTableData();
				btn.attr("disabled","disabled");
				btn.addClass("disabled");
				$.ajax({
				   	type:"POST",
				   	url:saveTableUrl,
				   	async:true,
				   	data:table,
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
				   		alert(actionStr+"库表失败!");
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
