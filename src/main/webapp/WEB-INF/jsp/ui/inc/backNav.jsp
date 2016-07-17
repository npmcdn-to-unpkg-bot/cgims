<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<header>
			<div class="left">
				<a href="javascript:void(0);" onclick="history.back(-1);"
					class="back"><i></i></a>
			</div>
			<div class="cent">${headTitle }</div>
			<div class="show_redrict head-icon">
				<a class="icon-nav" id="show_redrict" href="javascript:void(0);"
					onclick="hideOrOpenNav()"> <span><i></i>
						<p>导航</p></span>
				</a>
			</div>
		</header>
		<%@include file="nav.jsp" %>