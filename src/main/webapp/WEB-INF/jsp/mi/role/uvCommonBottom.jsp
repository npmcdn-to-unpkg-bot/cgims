<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
/*
 * 编辑页面，渲染页面数据
 * 
 */
$.ajax({
	type:"get",
	url:getRoleViewUrl,
	async:true,
	dataType:"json",
	success:function(data){
		if(data){
			var role = data.role;
			var relations = data.relations;
			
			var form = $(".form");
			for(var i in role){
				var e = form.find("[name="+i+"]");
				e.length>0&&e.val(role[i]);
			}
			var d = form.find("textarea[name='description']");
			d.length>0&&d.val(role['description']);
			if(relations){
				for(var i=0,l = relations.length;i<l;i++){
					originalRelation.push(relations[i].id);
				}
				$(".relation").append(template("#relation-info-template",relations))
			}
		}
	},
	error:function(){
		
	}
});
</script>