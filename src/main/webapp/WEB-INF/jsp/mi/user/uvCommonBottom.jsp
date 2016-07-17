<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
/*
 * 渲染页面数据
 * 
 */
$.ajax({
	type:"get",
	url:getUserViewUrl,
	async:true,
	dataType:"json",
	success:function(data){
		if(data){
			var user = data.user;
			var relationroles = data.relationroles;
			
			var form = $(".form");
			for(var i in user){
				if(i=="locked"){
					form.find("[name="+i+"]").length>0&&form.find("[name="+i+"]").val(String(user[i]));
					continue;
				}
				var e = form.find("[name="+i+"]");
				e.length>0&&e.val(user[i]);
				if(i=="name" && e.val()=="admin"){
					e.attr("readonly","readonly");
				}
			}
			if(user.headImgUrl){
				$(".control-user-img").attr("src",user.headImgUrl)
			}
			if(relationroles){
				for(var i=0,l = relationroles.length;i<l;i++){
					originalRelation.push(relationroles[i].id);
				}
				$(".relation").append(template("#relation-info-template",relationroles))
			}
		}
	},
	error:function(){
		
	}
});
</script>