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
function openCloseHTDetail(clazz){
	var ele = $("."+clazz);
	if(ele.is(':hidden')){
		ele.show();
	}else{
		ele.hide();
	}
	if(!ele.attr("first")){
		ele.attr("first",2);
		if("js-ht-detail-container"==clazz){
			initHTData();
		}else if("js-ht-panos-container"==clazz){
			panoPage.init();
		}else if("js-ht-images-container"==clazz){
		  	imagePage.init();
		}else if("js-ht-rings-container"==clazz){
		  	ringPage.init();
		}
	}
}

function initHTData(){
	$.ajax({
		type:"get",
		url:getHouseTypeViewUrl,
		async:true,
		dataType:"json",
		success:function(data){
			if(data){
				var ht = data.houseType;
				for(var i in ht){
					var ele = $("[name="+i+"]");
					if(ele[0]){
						ele.val(ht[i]);
					}
				}
				var housePlanUrl = ht["housePlanUrl"];
				if(housePlanUrl){
					$(".control-img").find("img").attr("src",housePlanUrl);
				}
			}
		},
		error:function(){
			alert("获取户型信息失败");
		}
	});
}
</script>