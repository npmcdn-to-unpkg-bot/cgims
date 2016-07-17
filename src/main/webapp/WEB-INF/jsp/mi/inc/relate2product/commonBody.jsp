<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
									
 <c:if test="${inAdd or inUpdate}">
									<div class="box box-inline js-relation-select-box">
										<div class="box-hd">
											<h2>关联产品</h2>
										</div>
										<div class="box-cnt">
											<div class="datatable" id="productInfo">
												<div class="datatabls-filter">
													<!--搜索：-->
													<input type="text" id="search-product-text"/>
													<input type="button" class="btn btn-primary" value="搜索" id="search-product-btn"/>
												</div>
												<table class="datatable-table">
													<thead>
														<tr>
															<th>名称</th>
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
										
									<div class="box box-inline">
										<div class="box-hd">
											<h2>已选关联产品</h2>
										</div>
										<div class="box-cnt">
											<ul class="relation" id="product-relation">
												
											</ul>
											<div class="clearfix"></div>
										</div>
									</div>