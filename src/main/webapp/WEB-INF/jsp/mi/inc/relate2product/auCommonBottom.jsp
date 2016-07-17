<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script type="text/x-handlebars-template" id="product-template">
		{{#each this}}
		<tr>
			<td>{{name}}</td>
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
	$(".relation").append(template("#relation-product-template",{"id":id,"name":name}))
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
	$(this).parent().parent().remove();
	return false;
})


/*
 *分页功能 
 */
	var page = new Page({
			container:"#productInfo",
			template:"#product-template",
			url:getProductsUrl,
			data:{"pageSize":10}
	})
	page.init();	  	
	$("#search-product-btn").click(function(){
		var name = $("#search-product-text").val();
		page.setData({"name":name})
		page.init();
	})
</script>