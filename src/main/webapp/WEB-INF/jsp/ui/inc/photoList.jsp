<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="headTitle" value="图册"/>
<%@include file="../inc/header.jsp" %>
<link rel="stylesheet" href="${ctx }/assets/tools/photoswipe/photoswipe.css">
<link rel="stylesheet" href="${ctx }/assets/tools/photoswipe/default-skin/default-skin.css">
<script src="${ctx }/assets/tools/photoswipe/photoswipe.min.js"></script>
<script src="${ctx }/assets/tools/photoswipe/photoswipe-ui-default.min.js"></script>
<script src="${ctx }/assets/tools/lazyload/jquery.lazyload.min.js"></script>
</head>
<body>
	<div class="main">
		<header>
			<div class="left">
				<a href="javascript:void(0);" onclick="history.back(-1);"
					class="back"><i></i></a>
			</div>
			<div class="cent">图册</div>
		</header>
		<div id="container" class="allPics">
			<div class="picbox">
				<c:if test="${photos != null and fn:length(photos) != 0 }">
	       			<c:forEach items="${photos}" var="p" varStatus="statusP">
						<c:if test="${p.list != null and fn:length(p.list) != 0 }">
		       				<h3>${p.name }</h3>
							<div class="gallery" >
				       			<c:forEach items="${p.list}" var="t" varStatus="status">
								<figure >
									<a href="${t.contentUrl }" data-type="${t.dataType }"> <img
										class="lazy" data-original="${t.preImageUrl}" src="${ctx }/assets/img/loading.gif"
										itemprop="thumbnail" alt="Image description" />
									</a>
									<figcaption itemprop="caption description">${t.name }</figcaption>
								</figure>
				       			</c:forEach>
							</div>
						</c:if>
	       			</c:forEach>
				</c:if>
			</div>
		</div>


		<!-- Root element of PhotoSwipe. Must have class pswp. -->
		<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">

			<!-- Background of PhotoSwipe. 
         It's a separate element, as animating opacity is faster than rgba(). -->
			<div class="pswp__bg"></div>

			<!-- Slides wrapper with overflow:hidden. -->
			<div class="pswp__scroll-wrap">

				<!-- Container that holds slides. PhotoSwipe keeps only 3 slides in DOM to save memory. -->
				<!-- don't modify these 3 pswp__item elements, data is added later on. -->
				<div class="pswp__container">
					<div class="pswp__item"></div>
					<div class="pswp__item"></div>
					<div class="pswp__item"></div>
				</div>
        		<a href="http://www.baidu.com" class="playBtn none"></a>
        		<a href="http://www.baidu.com" class="play360Btn none"></a>

				<!-- Default (PhotoSwipeUI_Default) interface on top of sliding area. Can be changed. -->
				<div class="pswp__ui pswp__ui--hidden">

					<div class="pswp__top-bar">

						<!--  Controls are self-explanatory. Order can be changed. -->

						<div class="pswp__counter"></div>

						<button class="pswp__button pswp__button--close"
							title="Close (Esc)"></button>

						<button class="pswp__button pswp__button--zoom"
							title="Zoom in/out"></button>

						<!-- Preloader demo http://codepen.io/dimsemenov/pen/yyBWoR -->
						<!-- element will get class pswp__preloader--active when preloader is running -->
						<div class="pswp__preloader">
							<div class="pswp__preloader__icn">
								<div class="pswp__preloader__cut">
									<div class="pswp__preloader__donut"></div>
								</div>
							</div>
						</div>
					</div>

					<div
						class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
						<div class="pswp__share-tooltip"></div>
					</div>

					<button class="pswp__button pswp__button--arrow--left"
						title="Previous (arrow left)"></button>

					<button class="pswp__button pswp__button--arrow--right"
						title="Next (arrow right)"></button>

					<div class="pswp__caption">
						<div class="pswp__caption__center"></div>
					</div>

				</div>

			</div>

		</div>
	</div>
</body>
<script>
	var initPhotoSwipeFromDOM = function(gallerySelector) {

		// parse slide data (url, title, size ...) from DOM elements 
		// (children of gallerySelector)
		var parseThumbnailElements = function(el) {
			var thumbElements = el.childNodes, numNodes = thumbElements.length, items = [], figureEl, linkEl, size, item,type,contentUrl;

			for (var i = 0; i < numNodes; i++) {

				figureEl = thumbElements[i]; // <figure> element

				// include only element nodes 
				if (figureEl.nodeType !== 1) {
					continue;
				}

				linkEl = figureEl.children[0]; // <a> element

				type = linkEl.getAttribute('data-type');
				contentUrl = linkEl.getAttribute('content-url');

				// create slide object
				item = {
						src : linkEl.getAttribute('href'),
						type:type,
						contentUrl:contentUrl,
						w:0,
						h:0
				};

				if (figureEl.children.length > 1) {
					// <figcaption> content
					item.title = figureEl.children[1].innerHTML;
				}

				if (linkEl.children.length > 0) {
					// <img> thumbnail element, retrieving thumbnail url
					item.msrc = linkEl.children[0].getAttribute('src');
				}

				item.el = figureEl; // save link to element for getThumbBoundsFn
				items.push(item);
			}

			return items;
		};

		// find nearest parent element
		var closest = function closest(el, fn) {
			return el && (fn(el) ? el : closest(el.parentNode, fn));
		};

		// triggers when user clicks on thumbnail
		var onThumbnailsClick = function(e) {
			e = e || window.event;
			e.preventDefault ? e.preventDefault() : e.returnValue = false;

			var eTarget = e.target || e.srcElement;

			// find root element of slide
			var clickedListItem = closest(eTarget, function(el) {
				return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
			});

			if (!clickedListItem) {
				return;
			}

			// find index of clicked item by looping through all child nodes
			// alternatively, you may define index via data- attribute
			var clickedGallery = clickedListItem.parentNode, childNodes = clickedListItem.parentNode.childNodes, numChildNodes = childNodes.length, nodeIndex = 0, index;

			for (var i = 0; i < numChildNodes; i++) {
				if (childNodes[i].nodeType !== 1) {
					continue;
				}

				if (childNodes[i] === clickedListItem) {
					index = nodeIndex;
					break;
				}
				nodeIndex++;
			}

			if (index >= 0) {
				// open PhotoSwipe if valid index found
				openPhotoSwipe(index, clickedGallery);
			}
			return false;
		};

		// parse picture index and gallery index from URL (#&pid=1&gid=2)
		var photoswipeParseHash = function() {
			var hash = window.location.hash.substring(1), params = {};

			if (hash.length < 5) {
				return params;
			}

			var vars = hash.split('&');
			for (var i = 0; i < vars.length; i++) {
				if (!vars[i]) {
					continue;
				}
				var pair = vars[i].split('=');
				if (pair.length < 2) {
					continue;
				}
				params[pair[0]] = pair[1];
			}

			if (params.gid) {
				params.gid = parseInt(params.gid, 10);
			}

			return params;
		};

		var openPhotoSwipe = function(index, galleryElement, disableAnimation,
				fromURL) {
			var pswpElement = document.querySelectorAll('.pswp')[0], gallery, options, items;

			items = parseThumbnailElements(galleryElement);

			// define options (if needed)
			options = {

				// define gallery index (for URL)
				galleryUID : galleryElement.getAttribute('data-pswp-uid'),

				getThumbBoundsFn : function(index) {
					// See Options -> getThumbBoundsFn section of documentation for more info
					var thumbnail = items[index].el.getElementsByTagName('img')[0], // find thumbnail
					pageYScroll = window.pageYOffset
							|| document.documentElement.scrollTop, rect = thumbnail
							.getBoundingClientRect();

					return {
						x : rect.left,
						y : rect.top + pageYScroll,
						w : rect.width
					};
				}

			};

			// PhotoSwipe opened from URL
			if (fromURL) {
				if (options.galleryPIDs) {
					// parse real index when custom PIDs are used 
					// http://photoswipe.com/documentation/faq.html#custom-pid-in-url
					for (var j = 0; j < items.length; j++) {
						if (items[j].pid == index) {
							options.index = j;
							break;
						}
					}
				} else {
					// in URL indexes start from 1
					options.index = parseInt(index, 10) - 1;
				}
			} else {
				options.index = parseInt(index, 10);
			}

			// exit if index not found
			if (isNaN(options.index)) {
				return;
			}

			if (disableAnimation) {
				options.showAnimationDuration = 0;
			}

			// Pass data to PhotoSwipe and initialize it
			gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, items,
					options);
			gallery.listen('gettingData', function(index, item) {
				if (item.w < 1 || item.h < 1) { // unknown size
					var img = new Image();
					img.onload = function() { // will get size after load
						item.w = this.width; // set image width
						item.h = this.height; // set image height
						gallery.invalidateCurrItems(); // reinit Items
						gallery.updateSize(true); // reinit Items
					}
					img.src = item.src; // let's download image
// 					if (img.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数
// 						item.w = this.width; // set image width
// 						item.h = this.height; // set image height
// 						gallery.invalidateCurrItems(); // reinit Items
// 						gallery.updateSize(true); // reinit Items
// 					}
				}
			});
			gallery.listen('afterChange', function() {
				var item = gallery.currItem;
				$(".playBtn").addClass("none");
				$(".play360Btn").addClass("none");
				if (item.type=="pano" || item.type=="ring") {
					$(".play360Btn").removeClass("none");
					$(".play360Btn").attr("href", item.contentUrl);
				} else if(item.type=="video"){
					$(".playBtn").removeClass("none");
					$(".playBtn").attr("href", item.contentUrl);
				}
			});

			gallery.listen('close', function() {
				$(".playBtn").addClass("none");
				$(".play360Btn").addClass("none");
			});
			gallery.init();
		};

		// loop through all gallery elements and bind events
		var galleryElements = document.querySelectorAll(gallerySelector);

		for (var i = 0, l = galleryElements.length; i < l; i++) {
			galleryElements[i].setAttribute('data-pswp-uid', i + 1);
			galleryElements[i].onclick = onThumbnailsClick;
		}

		// Parse URL and open gallery if it contains #&pid=3&gid=1
		var hashData = photoswipeParseHash();
		if (hashData.pid && hashData.gid) {
			openPhotoSwipe(hashData.pid, galleryElements[hashData.gid - 1],
					true, true);
		}
	};

	// execute above function
	initPhotoSwipeFromDOM('.gallery');
	
	$(function(){
		setTimeout(function(){
			$("img.lazy").lazyload({
				container: $("#container")
			});
		}, 500);
	})
</script>
</html>