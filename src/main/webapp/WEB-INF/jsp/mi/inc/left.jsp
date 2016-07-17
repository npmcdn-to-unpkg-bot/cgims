<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="slider-nav skin">
	<c:if test="${fn:contains(loginedUserPermissions,'mi:user:search') || fn:contains(loginedUserPermissions,'mi:role:search') }">
	<li class="submenu">
		<a href="javascript:void(0)">
			<i class="icon-cogs"></i>
			<span class="hidden-tablet"> 系统管理</span>
			<span class="label">0</span>
		</a>
		<ul class="subNav" >
		    <c:if test="${fn:contains(loginedUserPermissions,'mi:user:search') }">
				<li>
					<a  href="${ctx}/mi/user/search">
						<i class="icon-"></i>
						<span > 用户管理</span>
					</a>
				</li>
		    </c:if>  
		    <c:if test="${fn:contains(loginedUserPermissions,'mi:role:search')}">
				<li>
					<a  href="${ctx}/mi/role/search">
						<i class="icon-"></i>
						<span > 角色管理</span>
					</a>
				</li>
		    </c:if>  
		</ul>
	</li>
	</c:if>
	<li class="submenu">
		<a href="javascript:void(0)">
			<i class="icon-cog"></i>
			<span class="hidden-tablet"> 结构管理</span>
			<span class="label">0</span>
		</a>
		<ul class="subNav" >
		    <c:if test="${fn:contains(loginedUserPermissions,'mi:table:search')}">
				<li>
					<a  href="${ctx}/mi/table/search">
						<i class="icon-"></i>
						<span > 库表管理</span>
					</a>
				</li>
		    </c:if>  
		    <c:if test="${fn:contains(loginedUserPermissions,'mi:relationship:search')}">
				<li>
					<a  href="${ctx}/mi/relationship/search">
						<i class="icon-"></i>
						<span > 关系管理</span>
					</a>
				</li>
		    </c:if>  
		    <c:if test="${fn:contains(loginedUserPermissions,'mi:dataType:search')}">
				<li>
					<a  href="${ctx}/mi/dataType/search">
						<i class="icon-"></i>
						<span > 数据类型管理</span>
					</a>
				</li>
		    </c:if>  
		    <c:if test="${fn:contains(loginedUserPermissions,'mi:interface:search')}">
				<li>
					<a  href="${ctx}/mi/interface/search">
						<i class="icon-"></i>
						<span > 接口管理</span>
					</a>
				</li>
		    </c:if>  
		</ul>
	</li>
</ul>

<script>
localData = {
    hname:location.hostname?location.hostname:'localStatus',
    isLocalStorage:window.localStorage?true:false,
    dataDom:null,
    cookie:navigator.cookieEnabled,
	
    initDom:function(){ //初始化userData
        if(!this.dataDom){
            try{
                this.dataDom = document.createElement('input');//这里使用hidden的input元素
                this.dataDom.type = 'hidden';
                this.dataDom.style.display = "none";
                this.dataDom.addBehavior('#default#userData');//这是userData的语法
                document.body.appendChild(this.dataDom);
                var exDate = new Date();
                exDate = exDate.getDate()+30;
                this.dataDom.expires = exDate.toUTCString();//设定过期时间
            }catch(ex){
                return false;
            }
        }
        return true;
    },
    set:function(key,value){
        if(this.isLocalStorage){
            window.localStorage.setItem(key,value);
        }else if(this.cookie){
			addCookie(key,value);
        }else{
            if(this.initDom()){
                this.dataDom.load(this.hname);
                this.dataDom.setAttribute(key,value);
                this.dataDom.save(this.hname)
            }
        }
    },
    get:function(key){
        if(this.isLocalStorage){
            return window.localStorage.getItem(key);
        }else if(this.cookie){
			return getCookie(key)
        }else{
            if(this.initDom()){
                this.dataDom.load(this.hname);
                return this.dataDom.getAttribute(key);
            }
        }
    },
    remove:function(key){
        if(this.isLocalStorage){
            localStorage.removeItem(key);
        }else if(this.cookie){
			deleteCookie(key)
        }else{
            if(this.initDom()){
                this.dataDom.load(this.hname);
                this.dataDom.removeAttribute(key);
                this.dataDom.save(this.hname)
            }
        }
    }
}
//	$(".submenu").each(function(){
//		var ele = $(this);
//		var len = ele.children(".subNav").children().length;
//		if(len>0){
//			ele.find(".label").html(len);
//		}else{
//			ele.hide();
//		}
//		
//		var curUrl = window.location.href;
//		ele.find("a").each(function(){
//			if(curUrl.indexOf($(this).attr("href")+"/")>-1){
//// 				ele.addClass("open");
//				ele.addClass("active");
//				ele.click();
//			}
//			if(curUrl.substring(curUrl.indexOf($(this).attr("href")))==$(this).attr("href")){
//				ele.click();
//				ele.addClass("active");
//			}
//		});
	$(function(){
		var key = "__mi__silder__nav__temp__";
		var curUrl = window.location.href;
		var check = !1;
		
		$(".submenu").each(function(){
			var ele = $(this);
			var len = ele.children(".subNav").children().length;
			if(len>0){
				ele.find(".label").html(len);
			}else{
				ele.hide();
			}
		})

		$(".slider-nav .subNav").on("click",function(e){
			e.stopPropagation();
		})
		
		var host = window.location.pathname.replace("/zfw/mi","");
		if(host.length==0 || host === "/"){
			check = true;
			return ;
		}
		
		function checkNav(url){
			$(".slider-nav").find("a").each(function(i,e){
				var $e = $(e);
				var href = $e.attr("href");
				var id =0;
				if(!!href && (id=url.indexOf(href))>-1){
					var end = url.substr(id);
					if(end !== href ){
						return;
					}
					var tempString = href.substr(0,id);
					$e.parent().addClass("active");
					var $emp = $e.parentsUntil(".submenu");
					$emp.css("display","block");
					$emp.parent().addClass("open");
					localData.set(key,url);
					check = !0;
				}
			})
		}
		checkNav(curUrl);
		
		//使用路径截取，没有上级目录，如下场景：直接打开页面http://localhost:8080/zfw/mi/d256ca42-a8f1-4471-a4ab-5877aea3b0ef/xfpano/2d61dd24-0825-40f6-9d39-21cb0dcae517/edit
		//如未登陆，会调到登陆页面再登陆，此时无路径判断，直接截取部分路径进行判断
//		if(!check){
//			var m = host.match(/\/[a-zA-Z]*\//g);
//			for(var i =0,l=m.length;i<l;i++){
//				var k = m[i].substr(0,m[i].length-1);
//				$(".slider-nav").find("a").each(function(i,e){
//					var $e = $(e);
//					var href = $e.attr("href");
//					if(!!href){
//						href = href.substr(href.lastIndexOf("/"));
//						if(k.indexOf(href)>-1){
//							$e.parent().addClass("active");
//							var $emp = $e.parentsUntil(".submenu");
//							$emp.css("display","block");
//							$emp.parent().addClass("open");
//							localData.set(key,href);
//							check = !0;
//						};
//					}
//				
//				})
//			}
//		}
		//		//使用存储的上级目录
		if(!check){
			var lastUrl = localData.get(key);
			checkNav(lastUrl);
		}
	})
</script>
