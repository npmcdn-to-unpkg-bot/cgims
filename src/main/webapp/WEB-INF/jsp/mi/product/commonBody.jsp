<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
						<div class="box-cnt js-product-detail-container" style="display:none">
							<div class="form">
								<fieldset>
									<input type="hidden" id="shopId" name ="shopId" value="${shopId}" />
<c:if test="${inView or inUpdate}">
									<input type="hidden" id="productId" name ="id" value="${id}" />
</c:if>
									<div class="control-group">
										<label class="control-label">名称</label>
										<div class="control">
											<input type="text" name="name" max="32" maxlength="32" error="产品名长度最多32个字" 
											require="require" require_msg ="产品名不能为空"  placeholder="输入产品名称"  />
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
<c:choose> 
  <c:when test="${inUpdate or inAdd}">   
									<form enctype="multipart/form-data" method="post" id="uploadForm">
										<div class="control-group">
											<label class="control-label">上传预览图</label>
											<div class="control">
												<div class="uploader">
													<input type="hidden" name="preViewUrl" />
													<input type="file" name="theFile" accept="image/*"/>
													<span class="filename" style="-webkit-user-select: none;">没有选择文件...</span>
													<span class="action" style="-webkit-user-select: none;">选择</span>
												</div>
												<div class="control uploader-loading" style="display: none;">
													<div class="loading">
														<img src="${ctx}/assets/img/loading.gif"  />
													</div>
												</div>
												<div class="control">
													<span class="help-inline uploade-img-error"></span>
												</div>
												<div class="control-img">
													<img src=""/>
												</div>
											</div>
										</div>
									</form>
  </c:when> 
  <c:when test="${inView}">   
									<div class="control-group">
										<label class="control-label">预览图</label>
										<div class="control">
											<div class="control-img">
												<img src=""/>
											</div>
										</div>
									</div>
  </c:when> 
  <c:otherwise>   
   </c:otherwise> 
</c:choose> 
									<div class="control-group">
										<label class="control-label">介绍</label>
										<div class="control">
											<textarea name="introduction"  max="200"  error="介绍最多200个字"></textarea>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">描述</label>
										<div class="control">
											<textarea name="description"  max="200"  error="描述最多200个字"></textarea>
										</div>
									</div>
									<div class="form-actions">
<c:if test="${inAdd or inUpdate}">
									  <button type="button" class="btn btn-primary" id="submit">保存</button>
</c:if>
<c:if test="${inAdd}">
									  <button type="reset" class="btn cancle" style="display: none;">返回</button>
</c:if>
									</div>
								</fieldset>
							</div>
						</div>