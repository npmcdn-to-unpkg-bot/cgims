<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script type="text/x-handlebars" id="pano-template">
			{{#each this}}
			<tr>
<c:if test="${inUpdate}">
				<td>
					<input type="checkbox" value="{{id}}"/>
				</td>
</c:if>
				<td><img src="{{preViewUrl}}" style="width:100px"></td>
				<td>{{name}}</td>
				<td>{{description}}</td>
				<td>
					<a class="btn btn-info" href="${toDesignPanoramaViewUrl}">
						<i class="icon-zoom-in "></i>                                            
					</a>
<c:if test="${inUpdate}">
					<a class="btn btn-info" href="${toDesignPanoramaUpdateUrl}">
						<i class="icon-edit "></i>                                            
					</a>
					<a class="btn btn-danger" href="javascript:;" onclick="delPano(this,'{{id}}');return false;" data-id="{{id}}">
						<i class="icon-trash "></i> 
					</a>
</c:if>
				</td>
			</tr>
			{{/each}}
		</script>
		<script type="text/x-handlebars" id="image-template">
			{{#each this}}
			<tr>
<c:if test="${inUpdate}">
				<td>
					<input type="checkbox" value="{{id}}"/>
				</td>
</c:if>
				<td><img src="{{contentUrl}}" style="width:100px"></td>
				<td>{{name}}</td>
				<td>{{description}}</td>
				<td>
					<a class="btn btn-info" href="${toDesignImageViewUrl}">
						<i class="icon-zoom-in "></i>                                            
					</a>
<c:if test="${inUpdate}">
					<a class="btn btn-info" href="${toDesignImageUpdateUrl}">
						<i class="icon-edit "></i>                                            
					</a>
					<a class="btn btn-danger" href="javascript:;" onclick="delImage(this,'{{id}}');return false;" data-id="{{id}}">
						<i class="icon-trash "></i> 
					</a>
</c:if>
				</td>
			</tr>
			{{/each}}
		</script>
		<script type="text/x-handlebars" id="ring-template">
			{{#each this}}
			<tr>
<c:if test="${inUpdate}">
				<td>
					<input type="checkbox" value="{{id}}"/>
				</td>
</c:if>
				<td><img src="{{preViewUrl}}" style="width:100px"></td>
				<td>{{name}}</td>
				<td>{{description}}</td>
				<td>
					<a class="btn btn-info" href="${toDesignRingViewUrl}">
						<i class="icon-zoom-in "></i>                                            
					</a>
<c:if test="${inUpdate}">
					<a class="btn btn-info" href="${toDesignRingUpdateUrl}">
						<i class="icon-edit "></i>                                            
					</a>
					<a class="btn btn-danger" href="javascript:;" onclick="delRing(this,'{{id}}');return false;" data-id="{{id}}">
						<i class="icon-trash "></i> 
					</a>
</c:if>
				</td>
			</tr>
			{{/each}}
		</script>
<script>
function openCloseProductDetail(clazz){
	var ele = $("."+clazz);
	if(ele.is(':hidden')){
		ele.show();
	}else{
		ele.hide();
	}
	if(!ele.attr("first")){
		ele.attr("first",2);
		if("js-product-detail-container"==clazz){
			initProductData();
		}else if("js-product-panos-container"==clazz){
			panoPage.init();
		}else if("js-product-images-container"==clazz){
		  	imagePage.init();
		}else if("js-product-rings-container"==clazz){
		  	ringPage.init();
		}
	}
}

function initProductData(){
	$.ajax({
		type:"get",
		url:getProductViewUrl,
		async:true,
		dataType:"json",
		success:function(data){
			if(data){
				var product = data.product;
				for(var i in product){
					var ele = $("[name="+i+"]");
					if(ele[0]){
						ele.val(product[i]);
					}
				}
				var preViewUrl = product["preViewUrl"];
				if(preViewUrl){
					$(".control-img").find("img").attr("src",preViewUrl);
				}
			}
		},
		error:function(){
			alert("获取产品信息失败");
		}
	});
}
</script>