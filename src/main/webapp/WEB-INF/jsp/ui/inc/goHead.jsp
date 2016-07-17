<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<a id="showgohead" class="backTopBtn none" href="javascript:void(0)" onclick="gotohead()">&nbsp;</a>
<script>
	$(function() {
		window.addEventListener("scroll", function scrollHandler() {
			var backTopBtn = $("#showgohead");
			if (backTopBtn[0]) {
				window.pageYOffset > window.innerHeight * 2 - 60 ? backTopBtn
						.removeClass("none") : backTopBtn.addClass("none");
			}
		});
	});
	function gotohead() {
		window.scrollTo(0, 1);
	}
</script>