<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<div class="main">
		<%@include file="../homeNav.jsp" %>
		<form class="search0620_new flexbox" name="wapSearchForm" action=""
			onsubmit="return false;" method="get" autocomplete="off">
			<div class="searbox_new">
				<div class="ipt" id="wapdsy_D01_09">
					<input type="search" name="searchKeyword" id="searchKeyword" value="${searchKeyword }"
						placeholder="" autocomplete="off">
						<a href="javascript:;" class="off" style="display: none;"></a>
				</div>
				<a href="javascript:;" id="searchBtn" onclick="searchAction()" class="btn" rel="nofollow"><i></i></a>
			</div>
		</form>

		<section class="whitebg bdt bdb">
			<div class="searchCriteria">
				<ul id="criteriaList" class="criteriaList">
					<li>
						<p>
							<span class="js-selected" data-name="resultType" data-condition="${enName }">效果${cnName } </span> <span
								class="js-sift-condition none" data-condition="designPanorama">效果全景 </span> <span
								class="js-sift-condition" data-condition="designRing">效果三维</span> <span
								class="js-sift-condition" data-condition="designImage">效果图片</span>

<!-- 							<span -->
<%-- 								class="js-sift-condition <c:if test="${enName == 'designPanorama'}"> --%>
<!--   none -->
<%--  </c:if>" data-condition="designPanorama ">效果全景 </span> <span --%>
<%-- 								class="js-sift-condition <c:if test="${enName == 'designRing'}"> --%>
<!--   none -->
<%--  </c:if>" data-condition="designRing">效果三维</span> <span --%>
<%-- 								class="js-sift-condition <c:if test="${enName == 'designImage'}"> --%>
<!--   none -->
<%--  </c:if>" data-condition="designImage">效果图片</span> --%>
						</p> <strong>效果</strong> <em class="arrowDown"></em>
					</li>
					<li >
						<p>
							<span class="js-selected"  data-name="insideArea" data-condition=""> 不限 </span>
							<span class="js-sift-condition none" data-condition=""> 不限</span>
							<span class="js-sift-condition" data-condition="1:49"> 49或以下</span>
							<span class="js-sift-condition" data-condition="50:69"> 50-69 </span>
							<span class="js-sift-condition" data-condition="70:89"> 70-89 </span>
							<span class="js-sift-condition" data-condition="90:109"> 90-109 </span>
							<span class="js-sift-condition" data-condition="110:129"> 110-129</span>
							<span class="js-sift-condition" data-condition="130:149"> 130-149</span>
							<span class="js-sift-condition" data-condition="150:199"> 150-199</span>
							<span class="js-sift-condition" data-condition="200:10000"> 200或以上</span>
						</p> <strong>套内</strong> <em class="arrowDown"></em>
					</li>
					<li class="notImportant none">
						<p>
							<span class="js-selected"  data-name="grossFloorArea" data-condition=""> 不限 </span>
							<span class="js-sift-condition none" data-condition=""> 不限</span>
							<span class="js-sift-condition" data-condition="1:49"> 49或以下</span>
							<span class="js-sift-condition" data-condition="50:69"> 50-69 </span>
							<span class="js-sift-condition" data-condition="70:89"> 70-89 </span>
							<span class="js-sift-condition" data-condition="90:109"> 90-109 </span>
							<span class="js-sift-condition" data-condition="110:129"> 110-129</span>
							<span class="js-sift-condition" data-condition="130:149"> 130-149</span>
							<span class="js-sift-condition" data-condition="150:199"> 150-199</span>
							<span class="js-sift-condition" data-condition="200:10000"> 200或以上</span>
						</p> <strong>建筑</strong> <em class="arrowDown"></em>
					</li>
					<li class="notImportant none">
						<p>
							<span class="js-selected"  data-name="roomNum" data-condition=""> 不限 </span>
							<span class="js-sift-condition none" data-condition=""> 不限</span>
							<span class="js-sift-condition" data-condition="1"> 一居 </span>
							<span class="js-sift-condition" data-condition="2"> 二居 </span>
							<span class="js-sift-condition" data-condition="3"> 三居 </span>
							<span class="js-sift-condition" data-condition="4"> 四居 </span>
							<span class="js-sift-condition" data-condition="5"> 五居 </span>
							<span class="js-sift-condition" data-condition="6"> 五居以上 </span>
						</p> <strong>户型</strong> <em class="arrowDown"></em>
					</li>
				</ul>

				<div id="listMore" class="down" onclick="spreadList()">
					<span class="icon"></span> <span class="txt">展开更多查询条件</span>
				</div>
			</div>
		</section>
		<div class="resultWarp whitebg mt10 bdt">
			<h5 id="search_result_num">
				<h5>共搜索到${total }个效果${cnName } </h5>
			</h5>
		</div>
		<section class="homeList whitebg bdb">
			<ul id="itemList">
       			<c:forEach items="${results}" var="t" varStatus="status">
				<li><a id="${t.id}" href="${ctx}/${enName }/view/${t.id }">
						<div class="img">
							<c:choose> 
  <c:when test="${enName == 'designImage'}">   
							<img
								src="${t.contentUrl }">
  </c:when> 
  <c:otherwise>   
							<img
								src="${t.preViewUrl }">
   </c:otherwise> 
</c:choose> 
						</div>
						<div class="txt">
							<p class="x-intro">
							<h2>${t.name }</h2>
							</p>
							<p><c:if test="${t.houseType.roomNum != null and t.houseType.roomNum != '0' }">
									${t.houseType.roomNum}室
								</c:if><c:if test="${t.houseType.hallNum != null and t.houseType.hallNum != '0' }">
									${t.houseType.hallNum}厅
								</c:if><c:if test="${t.houseType.toiletNum != null and t.houseType.toiletNum != '0' }">
									${t.houseType.toiletNum}卫
								</c:if><c:if test="${t.houseType.kitchenNum != null and t.houseType.kitchenNum != '0' }">
									${t.houseType.kitchenNum}厨
								</c:if></p>
							<p><c:if test="${t.houseType.grossFloorArea != null and t.houseType.grossFloorArea != '0' }">建筑约<fmt:formatNumber value="${t.houseType.grossFloorArea}" maxFractionDigits="2"/>㎡</c:if> <c:if test="${t.houseType.grossFloorArea != null and t.houseType.grossFloorArea != '0' }">套内约<fmt:formatNumber value="${t.houseType.insideArea}" maxFractionDigits="2"/>㎡
								</c:if></p>
						</div>
				</a></li>
       			</c:forEach>
			</ul>
		</section>
		<c:choose>
			<c:when test="${total!=0}">
				<div id="drag" class="cenBtn">
					<a class="draginner" href="javascript:void(0);">查看更多效果${cnName } </a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="searchNo">
			    	<p class="f14">暂未搜索到符合条件的效果${cnName } 。</p>
				</div>
			</c:otherwise>
		</c:choose>
		<%@include file="../footer.jsp" %>
		<%@include file="../goHead.jsp" %>
	</div>
	<input type="hidden" id="resultType" value="效果${cnName } ">
	<input type="hidden" id="total" value="${total }">