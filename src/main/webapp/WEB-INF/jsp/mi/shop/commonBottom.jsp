<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script>
		function template(id,data){
			var tpl = Handlebars.compile($(id).html());
			return tpl(data);
		}

		$(".cancle").on("click",function(){
			window.location.href = goBackUrl;
		});
		 </script>