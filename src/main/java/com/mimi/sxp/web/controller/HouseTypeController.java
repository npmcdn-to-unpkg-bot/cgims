package com.mimi.sxp.web.controller;

import java.io.IOException;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mimi.sxp.Constants;
import com.mimi.sxp.model.DesignImageModel;
import com.mimi.sxp.model.DesignPanoramaModel;
import com.mimi.sxp.model.DesignRingModel;
import com.mimi.sxp.model.HouseTypeModel;
import com.mimi.sxp.model.RealEstateProjectModel;
import com.mimi.sxp.model.query.DesignImageQueryModel;
import com.mimi.sxp.model.query.DesignPanoramaQueryModel;
import com.mimi.sxp.model.query.DesignRingQueryModel;
import com.mimi.sxp.model.query.HouseTypeQueryModel;
import com.mimi.sxp.model.query.RealEstateProjectQueryModel;
import com.mimi.sxp.service.IAliyunOSSService;
import com.mimi.sxp.service.IDesignImageService;
import com.mimi.sxp.service.IDesignPanoramaService;
import com.mimi.sxp.service.IDesignRingService;
import com.mimi.sxp.service.IHouseTypeService;
import com.mimi.sxp.service.IRealEstateProjectService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.ResultMapUtil;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.support.editor.DateEditor;

@Controller
public class HouseTypeController {
	private static final Logger LOG = LoggerFactory
			.getLogger(HouseTypeController.class);
	@Resource
	private IHouseTypeService houseTypeService;
	
	@Resource
	private IRealEstateProjectService realEstateProjectService;
	
	@Resource
	private IDesignImageService designImageService;
	
	@Resource
	private IDesignPanoramaService designPanoramaService;
	
	@Resource
	private IDesignRingService designRingService;

	@Resource
	private IAliyunOSSService aossService;

	private String permissionObjectName = LoginUtil.PERMISSION_URL_HOUSE_TYPE_DISPLAY;

	@RequestMapping(value = "/houseType/search", method = { RequestMethod.GET })
	public String toSearch(HttpServletRequest request) {
		HouseTypeQueryModel command = new HouseTypeQueryModel();
		command.setTargetPage(PageUtil.BEGIN_PAGE);
		command.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		List<HouseTypeModel> list = houseTypeService.strictList(command);
		for(HouseTypeModel houseType:list){
			houseType.setHousePlanUrl(aossService.addImgParams(houseType.getHousePlanUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		int total = houseTypeService.strictCount(command);
		request.setAttribute("results", list);
		request.setAttribute("total", total);

		RealEstateProjectQueryModel repqm = new RealEstateProjectQueryModel();
		repqm.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		repqm.setTargetPage(PageUtil.BEGIN_PAGE);
		repqm.setOrderByPriority(true);
		List<RealEstateProjectModel> realEstateProjectList = realEstateProjectService.strictList(repqm);
		request.setAttribute("hotSearchKeywords", realEstateProjectList);
		return "ui/houseType/search";
	}

	@RequestMapping(value = "/houseType/search/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-", method = { RequestMethod.GET })
	public String toSearch(HttpServletRequest request,
			@PathVariable String searchKeyword,
			@PathVariable String insideArea,
			@PathVariable String grossFloorArea,
			@PathVariable Integer roomNum) {
		HouseTypeQueryModel command = new HouseTypeQueryModel();
		command.setTargetPage(PageUtil.BEGIN_PAGE);
		command.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command.setSearchKeyword(searchKeyword);
		command.setInsideAreaLimit(insideArea);
		command.setGrossFloorAreaLimit(grossFloorArea);
		command.setRoomNum(roomNum);
		List<HouseTypeModel> list = houseTypeService.strictList(command);
		for(HouseTypeModel houseType:list){
			houseType.setHousePlanUrl(aossService.addImgParams(houseType.getHousePlanUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		int total = houseTypeService.strictCount(command);
		request.setAttribute("results", list);
		request.setAttribute("total", total);
		
		RealEstateProjectQueryModel repqm = new RealEstateProjectQueryModel();
		repqm.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		repqm.setTargetPage(PageUtil.BEGIN_PAGE);
		repqm.setOrderByPriority(true);
		List<RealEstateProjectModel> realEstateProjectList = realEstateProjectService.strictList(repqm);
		request.setAttribute("hotSearchKeywords", realEstateProjectList);
		
		
		return "ui/houseType/search";
	}

	@RequestMapping(value = "/houseType/search/json/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-{targetPage}-{pageSize}-", method = { RequestMethod.GET })
	@ResponseBody
	public Object toSearchJson(HttpServletRequest request,
			@PathVariable String searchKeyword,
			@PathVariable String insideArea,
			@PathVariable String grossFloorArea,
			@PathVariable Integer roomNum,@PathVariable Integer targetPage,@PathVariable Integer pageSize) {
		HouseTypeQueryModel command = new HouseTypeQueryModel();
		command.setTargetPage(targetPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(searchKeyword);
		command.setInsideAreaLimit(insideArea);
		command.setGrossFloorAreaLimit(grossFloorArea);
		command.setRoomNum(roomNum);
		List<HouseTypeModel> list = houseTypeService.strictList(command);
		for(HouseTypeModel houseType:list){
			houseType.setHousePlanUrl(aossService.addImgParams(houseType.getHousePlanUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		Map<String,Object> result = ResultMapUtil.getResultMap();
		result.put("results", list);
		return result;
	}

	@RequestMapping(value = "/mi/houseType/search", method = { RequestMethod.GET })
	public String toMiSearch() {
		return "mi/houseType/search";
	}

	@RequestMapping(value = { "/mi/realEstateProject/view/houseType/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/search/page/json/{curPage}"}, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiSearchPageJson(HttpServletRequest request,
			@PathVariable Integer curPage, Integer pageSize, String name,
			String realEstateProjectId) {
		HouseTypeQueryModel command = new HouseTypeQueryModel();
		command.setTargetPage(curPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(name);
		command.setRealEstateProjectId(realEstateProjectId);
		CommonPageObject<HouseTypeModel> houseTypes = houseTypeService
				.strictQuery(command);
		return PageUtil.convert2ResultMap(houseTypes);
	}

	@RequestMapping(value = 
			"/houseType/view/housePlan/{id}" , method = { RequestMethod.GET })
	public void toViewHousePlan(HttpServletRequest request,HttpServletResponse response, @PathVariable String id) throws IOException {
		HouseTypeModel houseType = houseTypeService.get(id);
		houseType.setHousePlanUrl(aossService.addImgParams(houseType.getHousePlanUrl(), Constants.ALIYUN_OSS_IMAGE_WATER_MARK));
		response.sendRedirect(houseType.getHousePlanUrl());
	}

	@RequestMapping(value = "/houseType/view/{id}", method = { RequestMethod.GET })
	public String toView(HttpServletRequest request,@PathVariable String id) {
		
		DesignImageQueryModel command1 = new DesignImageQueryModel();
		command1.setTargetPage(PageUtil.BEGIN_PAGE);
		command1.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command1.setHouseTypeId(id);
		List<DesignImageModel> designImages = designImageService.strictList(command1);
		for(DesignImageModel designImage:designImages){
			designImage.setContentUrl(aossService.addImgParams(designImage.getContentUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER));
		}
//		request.setAttribute("designImages", designImages);

		DesignRingQueryModel command2 = new DesignRingQueryModel();
		command2.setTargetPage(PageUtil.BEGIN_PAGE);
		command2.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command2.setHouseTypeId(id);
		List<DesignRingModel> designRings = designRingService.strictList(command2);
		for(DesignRingModel designRing:designRings){
			designRing.setPreViewUrl(aossService.addImgParams(designRing.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER));
		}
//		request.setAttribute("designRings", designRings);
		
		DesignPanoramaQueryModel command3 = new DesignPanoramaQueryModel();
		command3.setTargetPage(PageUtil.BEGIN_PAGE);
		command3.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command3.setHouseTypeId(id);
		List<DesignPanoramaModel> designPanoramas = designPanoramaService.strictList(command3);
		for(DesignPanoramaModel designPanorama:designPanoramas){
			designPanorama.setPreViewUrl(aossService.addImgParams(designPanorama.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER));
		}
//		request.setAttribute("designPanoramas", designPanoramas);

		HouseTypeModel houseType = houseTypeService.get(id);
		houseType.setHousePlanUrl(aossService.addImgParams(houseType.getHousePlanUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER));
		houseType.setDesignImages(designImages);
		houseType.setDesignPanoramas(designPanoramas);
		houseType.setDesignRings(designRings);
		request.setAttribute("houseType", houseType);
		return "ui/houseType/view";
	}

	@RequestMapping(value = {"/mi/realEstateProject/view/houseType/view/{realEstateProjectId}/{id}",
			"/mi/realEstateProject/update/houseType/view/{realEstateProjectId}/{id}"}, method = { RequestMethod.GET })
	public String toMiView(HttpServletRequest request,@PathVariable String realEstateProjectId,@PathVariable String id) {
		return "mi/houseType/view";
	}

	@RequestMapping(value = { 
			"/mi/realEstateProject/view/houseType/view/json/{id}",
			"/mi/realEstateProject/update/houseType/view/json/{id}",
			"/mi/realEstateProject/update/houseType/update/view/json/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiViewJson(HttpServletRequest request,
			@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			HouseTypeModel houseType = houseTypeService.get(id);
			if (houseType != null) {
				// PermissionQueryModel pqm = new PermissionQueryModel();
				// pqm.setTargetPage(PageUtil.BEGIN_PAGE);
				// pqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
				// pqm.setHouseTypeId(id);
				// List<PermissionModel> permissions = permissionService
				// .strictList(pqm);
				houseType.setHousePlanUrl(aossService.addImgParams(houseType.getHousePlanUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG));
				resultMap.put("houseType", houseType);
				// resultMap.put("relations", permissions);
			}
		} catch (Exception e) {

			resultMap.put("houseType", null);
			LOG.error(LoginUtil.SEARCH_DISPLAY + permissionObjectName
					+ "详细信息出错！", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/add/{realEstateProjectId}", method = { RequestMethod.GET })
	public String toMiAdd(@PathVariable String realEstateProjectId) {
		return "mi/houseType/add";
	}

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/update/{realEstateProjectId}/{id}", method = { RequestMethod.GET })
	public String toMiUpdate(@PathVariable String realEstateProjectId,@PathVariable String id) {
		return "mi/houseType/update";
	}

	@RequestMapping(value = { "/mi/realEstateProject/update/houseType/update/json",
			"/mi/realEstateProject/update/houseType/add/json" }, method = { RequestMethod.POST })
	@ResponseBody
	public Object miSaveOrUpdate(HttpServletRequest request,
			HouseTypeModel houseType,String realEstateProjectId) {
		return saveOrUpdateUser(request, houseType,realEstateProjectId);
	}

	private Object saveOrUpdateUser(HttpServletRequest request,
			HouseTypeModel houseType,String realEstateProjectId) {
		Map<String, Object> resultMap = null;
		String actionName;
		if (StringUtils.isBlank(houseType.getId())) {
			actionName = LoginUtil.ADD_DISPLAY;
			houseType.setCreaterId(LoginUtil.getCurrentUserId(request));
		} else {
			actionName = LoginUtil.UPDATE_DISPLAY;
		}
		RealEstateProjectModel realEstateProject = new RealEstateProjectModel();
		realEstateProject.setId(realEstateProjectId);
		houseType.setRealEstateProject(realEstateProject);
		houseType.setEditorId(LoginUtil.getCurrentUserId(request));
		houseType.setHousePlanUrl(aossService.clearImgParams(houseType.getHousePlanUrl()));

		try {
			houseTypeService.saveOrUpdate(houseType);
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

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/delete/batch/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miBatchDelete(HttpServletRequest request, String houseTypeIds) {
		String actionName = LoginUtil.DELETE_DISPLAY;
		if (StringUtils.isNotBlank(houseTypeIds)) {
			String[] rids = houseTypeIds.split(Constants.MI_IDS_SPLIT_STRING);
			if (rids.length > 0) {
				List<String> idList = Arrays.asList(rids);
				houseTypeService.deleteBatch(idList);
				return ResultMapUtil.getResultMap(actionName
						+ permissionObjectName + "成功！");
			}
		}
		return ResultMapUtil.getResultMap(actionName + "对象不能为空！", false);
	}

	@RequestMapping(value = { "/mi/realEstateProject/update/houseType/update/uploadHousePlan",
			"/mi/realEstateProject/update/houseType/add/uploadHousePlan" }, method = { RequestMethod.POST })
	public @ResponseBody
	Object miUpload(HttpServletRequest request,
			@RequestParam("theFile") MultipartFile theFile) {
		System.out.println(request.getServletPath());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String path = aossService.saveFileToServer(theFile);
			path = aossService.addImgParams(path,
					Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG);
			resultMap.put("imgPath", path);
			resultMap.put("success", true);
		} catch (IOException e) {
			LOG.error("保存平面图纸出错！", e);
			resultMap.put("success", false);
			resultMap.put("msg", "保存平面图纸失败");
		}
		return resultMap;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

}
