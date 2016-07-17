<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			<div class="main skin">
				<div class="content">
					<div class="box">
						<div class="box-hd" onclick="openCloseREPDetail('js-rep-detail-container')">
<c:choose> 
  <c:when test="${inUpdate}">   
							<h2>更新楼盘</h2>
  </c:when> 
  <c:when test="${inAdd}">   
							<h2>添加楼盘</h2>
  </c:when> 
  <c:when test="${inview}">   
							<h2>楼盘详情</h2>
  </c:when> 
  <c:otherwise>   
   </c:otherwise> 
</c:choose> 
						</div>
						<div class="box-cnt js-rep-detail-container" style="display:none">
							<div class="form">
								<fieldset>
<c:if test="${inView or inUpdate}">
									<input type="hidden" id="repId" name ="id" value="${id}" />
</c:if>
									<div class="control-group">
										<label class="control-label">名称</label>
										<div class="control">
											<input type="text" name="name" max="32" maxlength="32" error="楼盘名长度最多32个字" 
											require="require" require_msg ="楼盘名不能为空"  placeholder="输入楼盘名称"  />
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">优先级</label>
										<div class="control">
											<input type="text"  name="priority" id="priority" max="4"  error="优先级范围0-9999" 
											patterns = "^\d{1,4}$"  placeholder="输入优先级 0-9999" value="0" />
											<span class="help-inline"></span>
										</div>
									</div>
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
						<div class="box-hd" onclick="openCloseREPDetail('js-rep-ht-container')">
							<h2>楼盘户型</h2>
						</div>
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
										<a class="btn btn-primary" href="${toHouseTypeAddUrl }">新增</a>
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
</c:if>
					<div class="form-actions">
					  <button type="reset" class="btn cancle">返回</button>
					</div>
				</div>
			</div>
						