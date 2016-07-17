<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script>
		
		function getREPData(){
			var rep = {
				id:"",
				name:"",
				description:"",
				priority:"",
			};
		   for(var i in rep){
		   		var value = $("[name="+i+"]").val();
		   		rep[i]=value;
		   }
		   return rep;
		}

		$("#submit").click(function(){
			var btn=$(this);
			var form = $(".form");
			var res = form.validate();
			if(res){
				var rep = getREPData();
				btn.attr("disabled","disabled");
				btn.addClass("disabled");
				$.ajax({
				   	type:"POST",
				   	url:saveRealEstateProjectUrl,
				   	async:true,
				   	data:rep,
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
				   		alert(actionStr+"楼盘失败!");
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
