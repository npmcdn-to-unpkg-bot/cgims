<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			<div class="main skin">
				<div class="content">
					<div class="box">
						<div class="box-hd" onclick="openCloseRelationshipDetail('js-relationship-detail-container')">
<c:choose> 
  <c:when test="${inUpdate}">   
							<h2>更新关系</h2>
  </c:when> 
  <c:when test="${inAdd}">   
							<h2>添加关系</h2>
  </c:when> 
  <c:when test="${inview}">   
							<h2>关系详情</h2>
  </c:when> 
  <c:otherwise>   
   </c:otherwise> 
</c:choose> 
						</div>
						<div class="box-cnt js-relationship-detail-container" style="display:none">
							<div class="form">
								<fieldset>
<c:if test="${inView or inUpdate}">
									<input type="hidden" id="relationshipId" name ="id" value="${id}" />
</c:if>
									<div class="control-group">
										<label class="control-label">名称</label>
										<div class="control">
											<input type="text" name="name" max="32" maxlength="32" error="关系名长度最多32个字" 
											require="require" require_msg ="关系名不能为空"  placeholder="输入关系名称"  />
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
<c:if test="${inAdd}">
									<div class="box box-inline">
										<div class="box-hd">
											<h2>选择库表</h2>
										</div>
										<div class="box-cnt">
											<div class="datatable" id="relationTable">
												<div class="datatabls-filter">
													<!--搜索：-->
													库表名称：<input type="text" id="searchbyname"/>
													表名：<input type="text" id="searchbycode"/>
													<input type="button" class="btn btn-primary" value="搜索" id="search-btn"/>
												</div>
												<table class="datatable-table">
													<thead>
														<tr>
															<th>库表名称</th>
															<th>表名</th>
															<th>描述</th>
															<th class="operation">操作</th>
														</tr>
													</thead>
													<tbody class="page-data-list">
														
													</tbody>
												</table>
												<div class="datatable-footer">
													<div class="datatable-info">
														<div></div>
													</div>
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
									
									<div class="box box-inline">
										<div class="box-hd">
											<h2>关联库表</h2>
										</div>
										<div class="box-cnt">
											<ul class="relation">
												
											</ul>
											<div class="clearfix"></div>
										</div>
									</div>
									
									<div class="form-actions">
<c:if test="${inAdd or inUpdate}">
									  <button type="button" class="btn btn-primary" id="submit">保存</button>
</c:if>
									  <button type="reset" class="btn cancle">返回</button>
									</div>
								</fieldset>
							</div>
						</div>
					</div>
				</div>
			</div>
						