<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
						<div class="box-cnt">
							<div class="form">
								<fieldset>
									<input type="hidden" name="publicExponent" id="publicExponent" value="${publicExponent }" />
									<input type="hidden" name="modulus" id="modulus" value="${modulus }"  />
 <c:if test="${inView or inSelf or inUpdate}">
									<input type="hidden" id="userid" name ="id" value="${id}" />
</c:if>
<c:choose> 
  <c:when test="${inAdd or inUpdate or inSelf}">   
<!--  									<div class="control-group"> -->
<!-- 										<label class="control-label">图像</label> -->
<!-- 										<div class="control control-img-box"> -->
<!-- 											<img class="control-user-img" /> -->
<!-- 										</div> -->
<!-- 									</div> -->
									<form enctype="multipart/form-data" method="post" id="uploadForm">
									<div class="control-group">
										<label class="control-label">上传图像</label>
										<div class="control">
											<div class="uploader">
												<input type="hidden" name="headImgUrl" />
												<input type="file" name="theFile" accept="image/*"/>
												<span class="filename" style="-webkit-user-select: none;">没有选择文件...</span>
												<span class="action" style="-webkit-user-select: none;">选择</span>
											</div>
										</div>
										<div class="control uploader-loading" style="display: none;">
											<div class="loading">
												<img src="${ctx}/assets/img/loading.gif"/>
											</div>
										</div>
										<div class="control-img">
											<img class="control-user-img" />
										</div>
									</div>
									</form>
  </c:when> 
  <c:otherwise>   
									<div class="control-group">
										<label class="control-label">图像</label>
										<div class="control control-img-box">
											<img class="control-user-img" />
										</div>
									</div>
  </c:otherwise> 
</c:choose> 
									<div class="control-group">
										<div class="control-group">
										<label class="control-label">用户名</label>
										<div class="control">
											<input type="text" name="name" require="require" require_msg ="用户名称不能为空" max="32" min="5" maxlength="32" error="用户名长度5~32只能包含小写字母、数字、下划线并以小写字母开头" 
					patterns = "^[a-z]([a-zA-Z0-9_]){3,31}$"  placeholder="输入用户名"  />
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">密码</label>
										<div class="control">
<c:choose> 
  <c:when test="${inAdd}">   
											<input type="password"  name="pwd" id="pwd" max="32" min="6"  maxlength="32"  error="密码长度6~32只能包含大小写字母、数字、部分特殊符号 !@#$%^&*()" 
					require="require" require_msg ="密码不能为空" patterns = "^[A-Za-z0-9!@#$%\^&\*\(\)]*$" placeholder="输入密码" value="" />
  </c:when>
  <c:otherwise>   
											<input type="password"  name="pwd" id="pwd" max="32" min="6"  maxlength="32"  error="密码长度6~32只能包含大小写字母、数字、部分特殊符号 !@#$%^&*()" 
					require="require" require_msg ="密码不能为空" patterns = "^[A-Za-z0-9!@#$%\^&\*\(\)]*$" placeholder="输入密码" value="******" />
  </c:otherwise>
 </c:choose>
											<input type="hidden"  name="password" id="password"/>
											<input type="hidden"  name="salt" id="salt"/>
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">邮箱</label>
										<div class="control">
											<input type="text" name="email" placeholder="输入邮箱" max="32" maxlength="32" patterns="^([a-zA-Z0-9_\.\-])+@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9])+$" />
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">手机号码</label>
										<div class="control">
											<input type="text" name="phoneNum" placeholder="输入手机号码" max="11" maxlength="11"  patterns="^1[0-9]{10}$"  error="手机号码格式不正确"/>
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">状态</label>
										<div class="control">
<c:choose> 
  <c:when test="${inSelf or inView}">   
											<select name="locked" disabled="disabled">
  </c:when> 
  <c:otherwise>   
											<select name="locked">
  </c:otherwise> 
</c:choose> 
												<option value="false" checked>正常</option>
												<option value="true">锁定</option>
											</select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">描述</label>
										<div class="control">
											<textarea name="description"  maxlength="200" max="200" error="描述最大长度为200"></textarea>
											<span class="help-inline"></span>
										</div>
									</div>
 <c:if test="${inAdd or inUpdate}">
									<div class="box box-inline">
										<div class="box-hd">
											<h2>添加角色</h2>
										</div>
										<div class="box-cnt">
											<div class="datatable" id="roleinfo">
												<div class="datatabls-filter">
													<!--搜索：-->
													<input type="text" id="searchbyname"/>
													<input type="button" class="btn btn-primary" value="搜索" id="search-role" />
												</div>
												<table class="datatable-table">
													<thead>
														<tr>
															<th>角色</th>
															<th>描述</th>
															<th class="operation">操作</th>
														</tr>
													</thead>
													<tbody class="page-data-list">
														
													</tbody>
												</table>
												<div class="datatable-footer">
													<div class="datatable-info">
														<div>共32条 当前展示第1条到第10条</div>
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
											<h2>已担当角色</h2>
										</div>
										<div class="box-cnt">
											<ul class="relation">
												
											</ul>
											<div class="clearfix"></div>
										</div>
									</div>
									<div class="form-actions">
 <c:if test="${inAdd or inUpdate or inSelf}">
									  <button type="button" class="btn btn-primary" id="submit">保存</button>
</c:if>
									  <button type="reset" class="btn cancle">返回</button>
									</div>
								</fieldset>
							</div>
						</div>