<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="box-cnt js-rep-ht-container" style="display:none">
	<div class="datatable" id="htList">
		<div class="datatabls-filter">
			<!--搜索：--> 
			<input type="text" class="js-search-text"  placeholder="名称"/>
			<input type="button" class="btn js-search-btn" value="搜索"/>
		</div>
		<table class="datatable-table">
			<thead>
<c:if test="${inUpdate}">
				<th class="checkarea"><input type="checkbox" id="selectAll"/></th>
</c:if>
				<th class="name">名称</th>
				<th>优先级</th>
				<th class="time">最后修改时间</th>
				<th class="operation">操作</th>
			</thead>
			<tbody class="page-data-list">
			</tbody>
		</table>
<c:if test="${inUpdate}">
		<div class="datatable-toolbar">
			<div class="toolbar">
				<select id="batch_option">
					<option value="del" selected="selected">删除</option>
				</select> <a class="btn btn-primary" href="javascript:;" onclick="htBatchOperation(this);">批量操作</a>
				<a class="btn btn-primary" href="${ctx}/mi/houseType/add/${id}">新增</a>
			</div>
		</div>
</c:if>
		<div class="datatable-footer">
			<div class="datatable-info">共0条</div>
			<div class="center">
				<div class="datatable-pagination">
					<ul class="pagination">
	
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
