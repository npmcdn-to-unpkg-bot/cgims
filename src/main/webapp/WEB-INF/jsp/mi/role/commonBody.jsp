<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
						<div class="box-cnt">
							<form class="form">
								<fieldset>
									<div class="control-group">
										<label class="control-label">角色名称</label>
 <c:if test="${inView or inUpdate}">
										<input type="hidden" id="id" name ="id" value="${id}" />
</c:if>
										<div class="control">
											<input type="text" name="name" require="require" require_msg ="角色名称不能为空" maxlength="32" max="32" error="角色名称最大长度为32"  placeholder="输入角色名名称"  />
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">描述</label>
										<div class="control">
											<textarea name="description" maxlength="200" max="200" error="描述最大长度为200"></textarea>
											<span class="help-inline"></span>
										</div>
									</div>
									
<c:if test="${inAdd or inUpdate}">
									<div class="box box-inline">
										<div class="box-hd">
											<h2>添加权限</h2>
										</div>
										<div class="box-cnt">
											<div class="datatable" id="permission">
												<div class="datatabls-filter">
													<!--搜索：-->
													权限名称：<input type="text" id="searchbyname"/>
													权限编码：<input type="text" id="searchbycode"/>
													<input type="button" class="btn btn-primary" value="搜索" id="search-btn"/>
												</div>
												<table class="datatable-table">
													<thead>
														<tr>
															<th>权限名称</th>
															<th>权限编码</th>
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
											<h2>已拥有权限</h2>
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
							</form>
						</div>