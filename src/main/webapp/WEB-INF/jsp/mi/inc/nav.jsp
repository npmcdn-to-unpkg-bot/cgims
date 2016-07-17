<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("ctx", request.getContextPath());%>
<div class="navbar skin">
	<a class="navbar-brand skin">后台管理</a>
	<div class="navbar-inner">
		<ul class="nav">
<%-- 			<li><a href="${ctx}/mi/user/${miCurrentUserId}/person"class="btn btn-primary"><i class="icon-wrench"></i></a></li> --%>
			<li ><a href="${ctx}/mi/logout" onclick="logout()" class="btn btn-primary nav-logout"><i class="icon-off "></i></a></li>
			<li>
				<a href="${ctx}/mi/self/update/${loginedUserID}">
					<div class="nav-avatar">
						<img src="${loginedUserHeadImgUrl}" id="mi-cur-headImageUrl" alt="Avatar">
					</div>
					<div class="nav-user">
						<p>Welcome!</p>
						<p> </p>
					</div>
				</a> 
			</li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	function logout(){
		deleteCookie("miPassword");
	}
</script>