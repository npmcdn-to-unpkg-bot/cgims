<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			<div class="main skin">
				<div class="content">
					<div class="box">
						<div class="box-hd">
<c:choose> 
  <c:when test="${inUpdate}">   
							<h2>更新效果三维</h2>
  </c:when> 
  <c:when test="${inAdd}">   
							<h2>新增效果三维</h2>
  </c:when> 
  <c:when test="${inview}">   
							<h2>楼盘效果三维</h2>
  </c:when> 
  <c:otherwise>   
   </c:otherwise> 
</c:choose> 
						</div>
						<div class="box-cnt">
							<div class="form">
								<fieldset>
									<input type="hidden" id="repId" name ="realEstateProjectId" value="${realEstateProjectId}" />
									<input type="hidden" id="htId" name ="houseTypeId" value="${houseTypeId}" />
									<input type="hidden" id="contentUrl" name ="contentUrl"/>
<c:if test="${inView or inUpdate}">
									<input type="hidden" id="ringId" name ="id" value="${id}" />
</c:if>
									<div class="control-group">
										<label class="control-label">名称</label>
										<div class="control">
											<input type="text" name="name" max="32" maxlength="32" error="名称长度最多32个字" 
											require="require" require_msg ="名称不能为空"  placeholder="输入名称"  />
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">外链内容路径</label>
										<div class="control">
											<textarea name="outLinkUrl"  max="200"  error="外链内容路径最多200个字"></textarea>
<!-- 											<input type="text" name="outLinkUrl" max="200" maxlength="200" error="外链内容路径长度最多200个字" placeholder="输入外链内容路径"  /> -->
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
									<form enctype="multipart/form-data" method="post" id="uploadImageForm">
										<div class="control-group">
											<label class="control-label">上传预览图</label>
											<div class="control">
												<div class="uploader">
													<input type="hidden" name="preViewUrl" />
													<input type="file" id="imageFile" name="theFile" accept="image/*"/>
													<span class="filename" style="-webkit-user-select: none;">没有选择文件...</span>
													<span class="action" style="-webkit-user-select: none;">选择</span>
												</div>
												<div class="control uploader-image-loading" style="display: none;">
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
									<form enctype="multipart/form-data" method="post" id="uploadContentForm">
										<div class="control-group">
											<label class="control-label">上传内容</label>
											<div class="control">
												<div class="uploader">
													<input type="hidden" name="contentZip" />
													<input type="file" id="contentFile" name="theFile" accept="application/zip"/>
													<span class="filename" style="-webkit-user-select: none;">没有选择文件...</span>
													<span class="action" style="-webkit-user-select: none;">选择</span>
												</div>
												<div class="control uploader-content-loading" style="display: none;">
													<div class="loading">
														<img src="${ctx}/assets/img/loading.gif"  />
													</div>
												</div>
												<div class="control">
													<span class="help-inline uploade-img-error"></span>
												</div>
												<input type="text" name="contentUrl" readonly="readonly"/>
												<span class="help-inline"></span>
											</div>
<!-- 											<div class="control"> -->
<!-- 												<textarea name="contentUrl"  max="200"  error="内容路径最多200个字"></textarea> -->
<!-- 											</div> -->
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
									<div class="control-group">
										<label class="control-label">内容路径</label>
										<div class="control">
											<textarea name="contentUrl"  max="200"  error="内容路径最多200个字"></textarea>
<!-- 											<input type="text" name="contentUrl" readonly="readonly"/> -->
											<span class="help-inline"></span>
										</div>
									</div>
  </c:when> 
  <c:otherwise>   
   </c:otherwise> 
</c:choose> 
									<div class="control-group">
										<label class="control-label">描述</label>
										<div class="control">
											<textarea name="description"  max="200"  error="描述最多200个字"></textarea>
										</div>
									</div>
									
									<%@include file="../inc/relate2product/commonBody.jsp" %>
									
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
						