<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<c:set var="inAdd" value="true"/>  
<c:set var="commonHeadUrl" value="/relationship/add"/> 
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
			<%@include file="commonBody.jsp" %>
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
		
	</body>
	<script type="text/javascript">
		var goBackUrl = "${ctx}/mi/relationship/search";

		var relationshipPreViewUploadUrl = "${ctx}/mi${commonHeadUrl}/uploadPreView";
		var saveRelationshipUrl = "${ctx}/mi/relationship/add/json";
		var getRelationTablesUrl = "${ctx}/mi${commonHeadUrl}/table/search/page/json/";
		
		var getRelationshipViewUrl = "${ctx}/mi${commonHeadUrl}/view/json/${id}";
		
		var actionStr = "添加";
	</script>
	<%@include file="commonBottom.jsp" %>
	<%@include file="auCommonBottom.jsp" %>
	<script>
		$(".js-relationship-detail-container").show();

		$(".datatable-table").on("click",".btn-add-relation",function(){

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
			   if(newRelationTables.length>=2){
				   alert("只能选择两张表");
				   return;
			   }
			   
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
	  			container:"#relationTable",
	  			template:"#relationTable-template",
	  			url:getRelationTablesUrl,
	  			data:{"pageSize":10}
	  	})
	  	page.init();
	  	
	  	$("#search-btn").click(function(){
	  		var name = $("#searchbyname").val();
	  		var code = $("#searchbycode").val();
	  		page.setData({"name":name,"tableName":code})
	  		page.init();
	  	})
	</script>
</html>