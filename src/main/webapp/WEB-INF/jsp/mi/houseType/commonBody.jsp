<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
						<div class="box-cnt js-ht-detail-container" style="display:none">
							<div class="form">
								<fieldset>
									<input type="hidden" id="repId" name ="realEstateProjectId" value="${realEstateProjectId}" />
<c:if test="${inView or inUpdate}">
									<input type="hidden" id="htId" name ="id" value="${id}" />
</c:if>
									<div class="control-group">
										<label class="control-label">名称</label>
										<div class="control">
											<input type="text" name="name" max="32" maxlength="32" error="户型名长度最多32个字" 
											require="require" require_msg ="户型名不能为空"  placeholder="输入户型名称"  />
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">建筑面积</label>
										<div class="control">
											<input type="text" name="grossFloorArea"  max="9"  error="建筑面积范围0.00-999999.99" 
											patterns = "^\d{1,6}(\.\d{1,2})?$"  placeholder="输入建筑面积0.00-999999.99" value="0.0"  />&nbsp;平方米
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">套内面积</label>
										<div class="control">
											<input type="text" name="insideArea"  max="9"  error="套内面积范围0.00-999999.99" 
											patterns = "^\d{1,6}(\.\d{1,2})?$"  placeholder="输入套内面积0.00-999999.99" value="0.0"  />&nbsp;平方米
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">居室数量</label>
										<div class="control">
											<input type="text"  name="roomNum" id="roomNum" max="2"  error="居室数量范围0-99" 
											patterns = "^\d{1,2}$"  placeholder="输入居室数量0-99" value="0" />
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">厅数量</label>
										<div class="control">
											<input type="text"  name="hallNum" id="hallNum" max="2"  error="厅数量范围0-99" 
											patterns = "^\d{1,2}$"  placeholder="输入厅数量0-99" value="0" />
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">厨房数量</label>
										<div class="control">
											<input type="text"  name="kitchenNum" id="kitchenNum" max="2"  error="厨房数量范围0-99" 
											patterns =  "^\d{1,2}$"  placeholder="输入厨房数量 0-99" value="0" />
											<span class="help-inline"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">卫生间数量</label>
										<div class="control">
											<input type="text"  name="toiletNum" id="toiletNum" max="2"  error="卫生间数量范围0-99" 
											patterns =  "^\d{1,2}$"  placeholder="输入卫生间数量0-99" value="0" />
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
											<label class="control-label">上传平面图纸</label>
											<div class="control">
												<div class="uploader">
													<input type="hidden" name="housePlanUrl" />
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
										<label class="control-label">平面图纸</label>
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