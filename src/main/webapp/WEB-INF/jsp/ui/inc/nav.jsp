<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<div class="popShadow none" onclick="hideOrOpenNav()"></div>
		<div class="newNav none">
			<div class="nav-box mt10">
				<div class="nav-tit">
<%-- 					<a href="${ctx }/user"><span id="userinfo">个人中心</span></a>  --%>
					<strong>频道导航</strong>
				</div>
				<div class="nav-menu">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<td width="24%"><a href="${ctx }/index">首页</a></td>
								<td width="24%"><a href="${ctx }/houseType/search/-----">户型</a></td>
								<td width="24%"><a href="${ctx }/designPanorama/search/-----">装修方案</a></td>
								<td width="24%"><a href="${ctx }/product/search/--">商品</a></td>
							</tr>
							<tr>
								<td><a href="${ctx }/shop/search/--">供货商</a></td>
<%-- 								<td><a href="${ctx }/xzl">装修预算</a></td> --%>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<script>
			function hideOrOpenNav() {
				var navObj = $(".newNav");
				var shadow = $(".popShadow");
				if (navObj.hasClass("none")) {
					navObj.removeClass("none");
					shadow.removeClass("none");
				} else {
					navObj.addClass("none");
					shadow.addClass("none");
				}
			}
		</script>