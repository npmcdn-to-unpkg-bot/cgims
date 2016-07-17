<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
						<div class="box-cnt js-column-detail-container" style="display:none">
							<div class="form">
								<fieldset>
									<input type="hidden" id="tableId" name ="tableId" value="${tableId}" />
									<input type="hidden" id="dataTypeId" name ="dataTypeId" value="${dataTypeId}" />
<c:if test="${inView or inUpdate}">
									<input type="hidden" id="columnId" name ="id" value="${id}" />
</c:if>
									<div class="control-group">
										<label class="control-label">名称</label>
										<div class="control">
											<input type="text" name="name" max="32" maxlength="32" error="列名长度最多32个字" 
											require="require" require_msg ="列名不能为空"  placeholder="输入列名称"  />
											<span class="help-inline"></span>
										</div>
									</div>
<c:if test="${inView or inUpdate}">
									<div class="control-group">
										<label class="control-label">列名</label>
										<div class="control">
											<input type="text" name="columnName"  readonly/>
											<span class="help-inline"></span>
										</div>
									</div>
</c:if>
									<div class="control-group">
										<label class="control-label">描述</label>
										<div class="control">
											<textarea name="description"  max="200"  error="描述最多200个字"></textarea>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">类型补充</label>
										<div class="control">
											<input type="text" name="dataTypeExtension" max="32" maxlength="32" error="长度最多32个字" value="" />
											<span class="help-inline"></span>
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label">数据类型</label>
										<div class="control">
											<input type="text" name="dataTypeName"  readonly value="${dataTypeName }"/>
											<span class="help-inline"></span>
										</div>
									</div>
<c:if test="${inAdd or inUpdate}">
									<div class="box box-inline">
										<div class="box-hd">
											<h2>选择数据类型</h2>
										</div>
										<div class="box-cnt">
											<div class="datatable" id="dataTypeInfo">
												<div class="datatabls-filter">
													<!--搜索：-->
													<input type="text" id="search-dataType-text"/>
													<input type="button" class="btn btn-primary" value="搜索" id="search-dataType-btn"/>
												</div>
												<table class="datatable-table">
													<thead>
														<tr>
															<th>名称</th>
															<th>编码</th>
															<th>描述</th>
															<th class="operation">操作</th>
														</tr>
													</thead>
													<tbody class="page-data-list">
														
													</tbody>
												</table>
												<div class="datatable-footer">
													<div class="datatable-info">
														<div>共0条</div>
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