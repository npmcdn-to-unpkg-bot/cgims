<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			<div class="main skin">
				<div class="content">
					<div class="box">
						<div class="box-hd" onclick="openCloseTableDetail('js-table-detail-container')">
<c:choose> 
  <c:when test="${inUpdate}">   
							<h2>更新库表</h2>
  </c:when> 
  <c:when test="${inAdd}">   
							<h2>添加库表</h2>
  </c:when> 
  <c:when test="${inview}">   
							<h2>库表详情</h2>
  </c:when> 
  <c:otherwise>   
   </c:otherwise> 
</c:choose> 
						</div>
						<div class="box-cnt js-table-detail-container" style="display:none">
							<div class="form">
								<fieldset>
<c:if test="${inView or inUpdate}">
									<input type="hidden" id="tableId" name ="id" value="${id}" />
</c:if>
									<div class="control-group">
										<label class="control-label">名称</label>
										<div class="control">
											<input type="text" name="name" max="32" maxlength="32" error="库表名长度最多32个字" 
											require="require" require_msg ="库表名不能为空"  placeholder="输入库表名称"  />
											<span class="help-inline"></span>
										</div>
									</div>
<c:if test="${inView or inUpdate}">
									<div class="control-group">
										<label class="control-label">表名</label>
										<div class="control">
											<input type="text" name="tableName"  readonly/>
											<span class="help-inline"></span>
										</div>
									</div>
</c:if>
									<div class="control-group">
										<label class="control-label">描述</label>
										<div class="control">
											<textarea name="description" max="200" error="描述最多200个字" maxlength="200"></textarea>
										</div>
									</div>
									
									<div class="form-actions">
<c:if test="${inAdd or inUpdate}">
									  <button type="button" class="btn btn-primary" id="submit">保存</button>
</c:if>
<c:if test="${inAdd}">
									  <button type="reset" class="btn cancle">返回</button>
</c:if>
									</div>
								</fieldset>
							</div>
						</div>
					</div>
<c:if test="${inView or inUpdate}">
					<div class="box">
						<div class="box-hd" onclick="openCloseTableDetail('js-table-column-container')">
							<h2>库表列</h2>
						</div>
						<div class="box-cnt js-table-column-container" style="display:none">
							<div class="datatable" id="columnList">
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
										<th>列名</th>
										<th>描述</th>
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
										</select> <a class="btn btn-primary" href="javascript:;" onclick="columnBatchOperation(this);">批量操作</a>
										<a class="btn btn-primary" href="${toColumnAddUrl }">新增</a>
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
					</div>
					
					<div class="box">
						<div class="box-hd" onclick="openCloseTableDetail('js-table-relationship-container')">
							<h2>库表关系</h2>
						</div>
						<div class="box-cnt js-table-relationship-container" style="display:none">
							<div class="datatable" id="relationshipList">
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
										<th>表名</th>
										<th>描述</th>
										<th class="time">最后修改时间</th>
										<th class="operation">操作</th>
									</thead>
									<tbody class="page-data-list">
									</tbody>
								</table>
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
					</div>
</c:if>
<c:if test="${inView or inUpdate}">
					<div class="form-actions">
					  <button type="reset" class="btn cancle">返回</button>
					</div>
</c:if>
				</div>
			</div>
						