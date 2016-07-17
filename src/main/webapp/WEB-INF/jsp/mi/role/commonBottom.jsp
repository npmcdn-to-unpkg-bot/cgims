<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script type="text/x-handlebars-template" id="relation-info-template">
			{{#this}}
			<li>
				<div class="relation-info">
					<span>
						{{name}}
					</span>
<c:if test="${inAdd or inUpdate}">
					<a class="btn btn-rel btn-remove-relation"  data-id="{{id}}">
						<i class="icon-remove"></i>
					</a>
</c:if>
				</div>
			</li>
			{{/this}}
		</script>
		<script>
		var originalRelation = [];
		function template(id,data){
			var tpl = Handlebars.compile($(id).html());
			return tpl(data);
		}
		
		/*
		 * 返回
		 */
		$(".cancle").on("click",function(){
			window.location.href = goBackUrl;
		})
		</script>