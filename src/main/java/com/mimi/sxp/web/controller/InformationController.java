package com.mimi.sxp.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.mimi.sxp.Constants;
import com.mimi.sxp.model.InformationModel;
import com.mimi.sxp.model.query.InformationQueryModel;
import com.mimi.sxp.service.IAliyunOSSService;
import com.mimi.sxp.service.IInformationService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.ResultMapUtil;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.support.editor.DateEditor;

@Controller
public class InformationController {
	private static final Logger LOG = LoggerFactory
			.getLogger(InformationController.class);
	@Resource
	private IInformationService informationService;

	@Resource
	private IAliyunOSSService aossService;

	private String permissionObjectName = LoginUtil.PERMISSION_URL_INFORMATION_DISPLAY;

	@RequestMapping(value = "/information/search", method = { RequestMethod.GET })
	public String toSearch(HttpServletRequest request) {
		InformationQueryModel command = new InformationQueryModel();
		command.setTargetPage(PageUtil.BEGIN_PAGE);
		command.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		List<InformationModel> informationList = informationService.list(command);
		if(informationList!=null && !informationList.isEmpty()){
			for(InformationModel information:informationList){
				information.setPreViewUrl(aossService.addImgParams(information.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
			}
		}
		int total = informationService.count(command);
		request.setAttribute("results",informationList);
		request.setAttribute("total", total);
		return "ui/information/search";
	}

	@RequestMapping(value = "/information/search/-{searchKeyword}-", method = { RequestMethod.GET })
	public String toSearch(HttpServletRequest request,@PathVariable String searchKeyword) {
		InformationQueryModel command = new InformationQueryModel();
		command.setTargetPage(PageUtil.BEGIN_PAGE);
		command.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command.setSearchKeyword(searchKeyword);
		List<InformationModel> informationList = informationService.list(command);
		if(informationList!=null && !informationList.isEmpty()){
			for(InformationModel information:informationList){
				information.setPreViewUrl(aossService.addImgParams(information.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
			}
		}
		int total = informationService.count(command);
		request.setAttribute("results",informationList);
		request.setAttribute("total", total);
		return "ui/information/search";
	}

	@RequestMapping(value = "/information/search/json/{searchKeyword}-{targetPage}-{pageSize}", method = { RequestMethod.GET })
	@ResponseBody
	public Object getSearchJson(@PathVariable Integer targetPage, @PathVariable Integer pageSize,@PathVariable String searchKeyword) {
		InformationQueryModel repqm = new InformationQueryModel();
		repqm.setTargetPage(targetPage);
		repqm.setPageSize(pageSize);
		repqm.setSearchKeyword(searchKeyword);
		List<InformationModel> informationList = informationService.list(repqm);
		if(informationList!=null && !informationList.isEmpty()){
			for(InformationModel information:informationList){
				information.setPreViewUrl(aossService.addImgParams(information.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
			}
		}
		Map<String, Object> resultMap = ResultMapUtil.getResultMap();
		resultMap.put("results", informationList);
		return resultMap;
	}

	@RequestMapping(value = "/mi/information/search", method = { RequestMethod.GET })
	public String toMiSearch() {
		return "mi/information/search";
	}

	@RequestMapping(value = { "/mi/information/search/page/json/{curPage}"}, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiSearchPageJson(HttpServletRequest request,
			@PathVariable Integer curPage, Integer pageSize, String searchKeyword) {
		InformationQueryModel repqm = new InformationQueryModel();
		repqm.setTargetPage(curPage);
		repqm.setPageSize(pageSize);
		repqm.setSearchKeyword(searchKeyword);
//		CommonPageObject<InformationModel> informations = informationService.strictQuery(repqm);
		CommonPageObject<InformationModel> informations = informationService.query(repqm);
		return PageUtil.convert2ResultMap(informations);
	}

	@RequestMapping(value = "/information/view/{id}", method = { RequestMethod.GET })
	public String toView(HttpServletRequest request,@PathVariable String id) {
		request.setAttribute("information",informationService.get(id));
		return "ui/information/view";
	}

	@RequestMapping(value = "/mi/information/view/{id}", method = { RequestMethod.GET })
	public String toMiView(@PathVariable String id) {
		return "mi/information/view";
	}

	@RequestMapping(value = { "/mi/information/view/json/{id}",
			"/mi/information/add/view/json/{id}", "/mi/information/update/view/json/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiViewJson(HttpServletRequest request,
			@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			InformationModel information = informationService.get(id);
			if (information != null) {
//				PermissionQueryModel pqm = new PermissionQueryModel();
//				pqm.setTargetPage(PageUtil.BEGIN_PAGE);
//				pqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
//				pqm.setInformationId(id);
//				List<PermissionModel> permissions = permissionService
//						.strictList(pqm);
				information.setPreViewUrl(aossService.addImgParams(information.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG));
				resultMap.put("information", information);
//				resultMap.put("relations", permissions);
			}
		} catch (Exception e) {

			resultMap.put("information", null);
			LOG.error(LoginUtil.SEARCH_DISPLAY + permissionObjectName
					+ "详细信息出错！", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/information/add", method = { RequestMethod.GET })
	public String toMiAdd() {
		return "mi/information/add";
	}

	@RequestMapping(value = "/mi/information/update/{id}", method = { RequestMethod.GET })
	public String toMiUpdate(@PathVariable String id) {
		return "mi/information/update";
	}

	@RequestMapping(value = { "/mi/information/update/json", "/mi/information/add/json" }, method = { RequestMethod.POST })
	@ResponseBody
	public Object miSaveOrUpdate(HttpServletRequest request, InformationModel information,
			String newPermissionIds) {
		return saveOrUpdateUser(request, information, newPermissionIds);
	}

	private Object saveOrUpdateUser(HttpServletRequest request, InformationModel information,
			String newPermissionIds) {
		Map<String, Object> resultMap = null;
		String actionName;
		if (StringUtils.isBlank(information.getId())) {
			actionName = LoginUtil.ADD_DISPLAY;
			information.setCreaterId(LoginUtil.getCurrentUserId(request));
		} else {
			actionName = LoginUtil.UPDATE_DISPLAY;
		}
		information.setEditorId(LoginUtil.getCurrentUserId(request));

		try {
//			if (StringUtils.isNotBlank(newPermissionIds)) {
//				List<PermissionModel> permissions = new ArrayList<PermissionModel>();
//				String[] pids = newPermissionIds
//						.split(Constants.MI_IDS_SPLIT_STRING);
//				for (int i = 0; i < pids.length; i++) {
//					PermissionModel permission = new PermissionModel();
//					permission.setId(pids[i]);
//					permissions.add(permission);
//				}
//				information.setPermissions(permissions);
//			}
			informationService.saveOrUpdate(information);
			resultMap = ResultMapUtil.getResultMap(actionName
					+ permissionObjectName + "成功!");
		} catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> constraintViolations = e
					.getConstraintViolations();
			Iterator<ConstraintViolation<?>> iter = constraintViolations
					.iterator();
			while (iter.hasNext()) {
				ConstraintViolation<?> cv = iter.next();
				resultMap = ResultMapUtil.getResultMap(cv.getMessage(), cv
						.getPropertyPath().toString());
				break;
			}
			if (resultMap == null) {
				resultMap = ResultMapUtil.getResultMap(actionName
						+ permissionObjectName + "失败!", false);
			}
			LOG.error(actionName + permissionObjectName + "失败！", e);
		} catch (DataIntegrityViolationException e) {
			LOG.error(actionName + permissionObjectName + "失败！", e);
			resultMap = ResultMapUtil.getResultMap("name", permissionObjectName
					+ "名重复!");
		} catch (Exception e) {
			LOG.error(actionName + permissionObjectName + "失败！", e);
			resultMap = ResultMapUtil.getResultMap(actionName
					+ permissionObjectName + "失败!", false);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/information/delete/batch/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miBatchDelete(HttpServletRequest request, String informationIds) {
		String actionName = LoginUtil.DELETE_DISPLAY;
		if (StringUtils.isNotBlank(informationIds)) {
			String[] rids = informationIds.split(Constants.MI_IDS_SPLIT_STRING);
			if (rids.length > 0) {
				List<String> idList = Arrays.asList(rids);
				informationService.deleteBatch(idList);
				return ResultMapUtil.getResultMap(actionName
						+ permissionObjectName + "成功！");
			}
		}
		return ResultMapUtil.getResultMap(actionName + "对象不能为空！", false);
	}
	
	@RequestMapping(value = {
			"/mi/information/update/uploadPreView",
			"/mi/information/add/uploadPreView" }, method = { RequestMethod.POST })
	public @ResponseBody
	Object miUpload(HttpServletRequest request,
			@RequestParam("theFile") MultipartFile theFile) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String path = aossService.saveFileToServer(theFile);
			path = aossService.addImgParams(path,
					Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG);
			resultMap.put("imgPath", path);
			resultMap.put("success", true);
		} catch (IOException e) {
			LOG.error("保存预览图出错！", e);
			resultMap.put("success", false);
			resultMap.put("msg", "保存预览图失败");
		}
		return resultMap;
	}


	@RequestMapping(value = "/ueditor/controller", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void ueditorController(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletRequestBindingException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html");
		String rootPath = request.getSession().getServletContext()
				.getRealPath("/");
		response.getWriter().write(new ActionEnter(request, rootPath).exec());
	}

	@RequestMapping(value = "/ueditor/uploadImage", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void ueditorUploadImage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("upfile") MultipartFile upfile) throws IOException {

		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html");
		State state;
		try {
			List<String> allowSuffix = new ArrayList<String>();
			allowSuffix.add(".jpg");
			allowSuffix.add(".jpeg");
			allowSuffix.add(".png");
			allowSuffix.add(".gif");
			String fileName = upfile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			if (!allowSuffix.contains(suffix)) {
				state = new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			} else if (upfile.getSize() > 25 * 1024 * 1024) {
				state = new BaseState(false, AppInfo.MAX_SIZE);
			} else {
				String path = aossService.saveFileToServer(upfile);
				path = aossService.addImgParams(path,
						Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_INFO_IMG);
				state = new BaseState(true);
				state.putInfo("url", path);
				state.putInfo("type", suffix);
				state.putInfo("original", fileName);
				state.putInfo("size", upfile.getSize());
				state.putInfo("title", fileName);
			}
		} catch (Exception e) {
			state = new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}
		response.getWriter().write(state.toJSONString());
	}
	@RequestMapping(value = "/ueditor/catchImage", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void ueditorCatchImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html");
		String[] list = request.getParameterValues( "source[]");
		MultiState state = new MultiState( true );
		for ( String source : list ) {
			state.addState( aossService.saveImageToServer( source ) );
		}
		response.getWriter().write(state.toJSONString());
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

}
