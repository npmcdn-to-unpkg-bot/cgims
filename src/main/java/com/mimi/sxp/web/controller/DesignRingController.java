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
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.mimi.sxp.model.DesignRingModel;
import com.mimi.sxp.model.HouseTypeModel;
import com.mimi.sxp.model.ProductModel;
import com.mimi.sxp.model.query.DesignRingQueryModel;
import com.mimi.sxp.model.query.ProductQueryModel;
import com.mimi.sxp.service.IAliyunOSSService;
import com.mimi.sxp.service.IDesignRingService;
import com.mimi.sxp.service.IProductService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.ResultMapUtil;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.support.editor.DateEditor;

@Controller
public class DesignRingController {
	private static final Logger LOG = LoggerFactory
			.getLogger(DesignRingController.class);
	@Resource
	private IDesignRingService designRingService;

	@Resource
	private IProductService productService;

	@Resource
	private IAliyunOSSService aossService;

	private String permissionObjectName = LoginUtil.PERMISSION_URL_DESIGN_RING_DISPLAY;

	@RequestMapping(value = "/designRing/search/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-", method = { RequestMethod.GET })
	public String toSearch(HttpServletRequest request,
			@PathVariable String searchKeyword,
			@PathVariable String insideArea,
			@PathVariable String grossFloorArea,
			@PathVariable Integer roomNum) {
		DesignRingQueryModel command = new DesignRingQueryModel();
		command.setTargetPage(PageUtil.BEGIN_PAGE);
		command.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command.setSearchKeyword(searchKeyword);
		command.setInsideAreaLimit(insideArea);
		command.setGrossFloorAreaLimit(grossFloorArea);
		command.setRoomNum(roomNum);
		List<DesignRingModel> list = designRingService.strictList(command);
		for(DesignRingModel designRing:list){
			designRing.setPreViewUrl(aossService.addImgParams(designRing.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		int total = designRingService.strictCount(command);
		request.setAttribute("results", list);
		request.setAttribute("total", total);
		return "ui/designRing/search";
	}

	@RequestMapping(value = "/designRing/search/json/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-{targetPage}-{pageSize}-", method = { RequestMethod.GET })
	@ResponseBody
	public Object toSearchJson(HttpServletRequest request,
			@PathVariable String searchKeyword,
			@PathVariable String insideArea,
			@PathVariable String grossFloorArea,
			@PathVariable Integer roomNum,@PathVariable Integer targetPage,@PathVariable Integer pageSize) {
		DesignRingQueryModel command = new DesignRingQueryModel();
		command.setTargetPage(targetPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(searchKeyword);
		command.setInsideAreaLimit(insideArea);
		command.setGrossFloorAreaLimit(grossFloorArea);
		command.setRoomNum(roomNum);
		List<DesignRingModel> list = designRingService.strictList(command);
		for(DesignRingModel designRing:list){
			designRing.setPreViewUrl(aossService.addImgParams(designRing.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		Map<String,Object> result = ResultMapUtil.getResultMap();
		result.put("results", list);
		return result;
	}

	@RequestMapping(value = "/mi/designRing/search", method = { RequestMethod.GET })
	public String toMiSearch() {
		return "mi/designRing/search";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/view/houseType/view/designRing/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/view/designRing/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designRing/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designRing/search/page/json/{curPage}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiSearchPageJson(HttpServletRequest request,
			@PathVariable Integer curPage, Integer pageSize, String name,
			String houseTypeId) {
		DesignRingQueryModel command = new DesignRingQueryModel();
		command.setTargetPage(curPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(name);
		command.setHouseTypeId(houseTypeId);
		CommonPageObject<DesignRingModel> designRings = designRingService
				.strictQuery(command);
		for (DesignRingModel designRing : designRings.getItems()) {
			if (StringUtils.isNotBlank(designRing.getPreViewUrl())) {
				designRing.setPreViewUrl(aossService.addImgParams(
						designRing.getPreViewUrl(),
						Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
			}
		}
		return PageUtil.convert2ResultMap(designRings);
	}

	@RequestMapping(value = {
			"/designRing/content/{id}" }, method = { RequestMethod.GET })
	public String toContent(HttpServletRequest request, @PathVariable String id) {
		DesignRingModel designRing = designRingService.get(id);
		String basePath;
		if(StringUtils.isNotBlank(designRing.getOutLinkUrl())){
			basePath = designRing.getOutLinkUrl();
		}else{
			basePath = designRing.getContentUrl();
		}
		List<String> pathList = aossService.getChildPathList(basePath);
		List<String> imgUrls = new ArrayList<String>();
		List<String> bigImgUrls = new ArrayList<String>();
		for(String path:pathList){
			imgUrls.add(aossService.addImgParams(path, Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_RING_SMALL_IMG));
			bigImgUrls.add(aossService.addImgParams(path, Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_RING_IMG));
		}
		request.setAttribute("designRing",designRing);
		request.setAttribute("imgUrls",JSONArray.fromObject(imgUrls));
		request.setAttribute("bigImgUrls",JSONArray.fromObject(bigImgUrls));
		return "ui/designRing/content";
	}

	@RequestMapping(value = {
			"/designRing/view/{id}" }, method = { RequestMethod.GET })
	public String toView(HttpServletRequest request, @PathVariable String id) {
		DesignRingModel designRing = designRingService.get(id);
		String basePath;
		if(StringUtils.isNotBlank(designRing.getOutLinkUrl())){
			basePath = designRing.getOutLinkUrl();
		}else{
			basePath = designRing.getContentUrl();
		}
		List<String> pathList = aossService.getChildPathList(basePath);
		List<String> imgUrls = new ArrayList<String>();
		List<String> bigImgUrls = new ArrayList<String>();
		for(String path:pathList){
			imgUrls.add(aossService.addImgParams(path, Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_RING_SMALL_IMG));
			bigImgUrls.add(aossService.addImgParams(path, Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_RING_IMG));
		}
		ProductQueryModel pqm = new ProductQueryModel();
		pqm.setTargetPage(PageUtil.BEGIN_PAGE);
		pqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
		pqm.setDesignRingId(id);
		designRing.setProducts(productService.strictList(pqm));
		request.setAttribute("designRing",designRing);
		request.setAttribute("imgUrls",JSONArray.fromObject(imgUrls));
		request.setAttribute("bigImgUrls",JSONArray.fromObject(bigImgUrls));
		return "ui/designRing/view";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/view/houseType/view/designRing/view/{realEstateProjectId}/{houseTypeId}/{id}",
			"/mi/realEstateProject/update/houseType/view/designRing/view/{realEstateProjectId}/{houseTypeId}/{id}",
			"/mi/realEstateProject/update/houseType/update/designRing/view/{realEstateProjectId}/{houseTypeId}/{id}",
			"/mi/realEstateProject/update/houseType/update/designRing/view/{realEstateProjectId}/{houseTypeId}/{id}" }, method = { RequestMethod.GET })
	public String toMiView(HttpServletRequest request,
			@PathVariable String realEstateProjectId,
			@PathVariable String houseTypeId, @PathVariable String id) {
		return "mi/designRing/view";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/view/houseType/view/designRing/view/json/{id}",
			"/mi/realEstateProject/update/houseType/view/designRing/view/json/{id}",
			"/mi/realEstateProject/update/houseType/update/designRing/view/json/{id}",
			"/mi/realEstateProject/update/houseType/update/designRing/update/view/json/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiViewJson(HttpServletRequest request,
			@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			DesignRingModel designRing = designRingService.get(id);
			if (designRing != null) {
				designRing.setPreViewUrl(aossService.addImgParams(
						designRing.getPreViewUrl(),
						Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG));
				resultMap.put("designRing", designRing);
				
				ProductQueryModel command = new ProductQueryModel();
				command.setTargetPage(PageUtil.BEGIN_PAGE);
				command.setPageSize(PageUtil.MAX_PAGE_SIZE);
				command.setDesignRingId(id);
				resultMap.put("relationProducts", productService.strictList(command));
			}
		} catch (Exception e) {
			resultMap.put("designRing", null);
			LOG.error(LoginUtil.SEARCH_DISPLAY + permissionObjectName
					+ "详细信息出错！", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/update/designRing/add/{realEstateProjectId}/{houseTypeId}", method = { RequestMethod.GET })
	public String toMiAdd(@PathVariable String realEstateProjectId,
			@PathVariable String houseTypeId) {
		return "mi/designRing/add";
	}

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/update/designRing/update/{realEstateProjectId}/{houseTypeId}/{id}", method = { RequestMethod.GET })
	public String toMiUpdate(@PathVariable String realEstateProjectId,
			@PathVariable String houseTypeId, @PathVariable String id) {
		return "mi/designRing/update";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/update/houseType/update/designRing/update/json",
			"/mi/realEstateProject/update/houseType/update/designRing/add/json" }, method = { RequestMethod.POST })
	@ResponseBody
	public Object miSaveOrUpdate(HttpServletRequest request,
			DesignRingModel designRing, String houseTypeId,String newProductIds) {
		return saveOrUpdateUser(request, designRing, houseTypeId,newProductIds);
	}

	private Object saveOrUpdateUser(HttpServletRequest request,
			DesignRingModel designRing, String houseTypeId,String newProductIds) {
		Map<String, Object> resultMap = null;
		String actionName;
		if (StringUtils.isBlank(designRing.getId())) {
			actionName = LoginUtil.ADD_DISPLAY;
			designRing.setCreaterId(LoginUtil.getCurrentUserId(request));
		} else {
			actionName = LoginUtil.UPDATE_DISPLAY;
		}
		HouseTypeModel houseType = new HouseTypeModel();
		houseType.setId(houseTypeId);
		designRing.setHouseType(houseType);
		designRing.setEditorId(LoginUtil.getCurrentUserId(request));
		designRing.setPreViewUrl(aossService.clearImgParams(designRing
				.getPreViewUrl()));

		try {

			if (StringUtils.isNotBlank(newProductIds)) {
				List<ProductModel> products = new ArrayList<ProductModel>();
				String[] pids = newProductIds.split(Constants.MI_IDS_SPLIT_STRING);
				for (int i = 0; i < pids.length; i++) {
					ProductModel product = new ProductModel();
					product.setId(pids[i]);
					products.add(product);
				}
				designRing.setProducts(products);
			}
			designRingService.saveOrUpdate(designRing);
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
		} catch (Exception e) {
			LOG.error(actionName + permissionObjectName + "失败！", e);
			resultMap = ResultMapUtil.getResultMap(actionName
					+ permissionObjectName + "失败!", false);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/update/designRing/delete/batch/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miBatchDelete(HttpServletRequest request,
			String designRingIds) {
		String actionName = LoginUtil.DELETE_DISPLAY;
		if (StringUtils.isNotBlank(designRingIds)) {
			String[] rids = designRingIds
					.split(Constants.MI_IDS_SPLIT_STRING);
			if (rids.length > 0) {
				List<String> idList = Arrays.asList(rids);
				designRingService.deleteBatch(idList);
				return ResultMapUtil.getResultMap(actionName
						+ permissionObjectName + "成功！");
			}
		}
		return ResultMapUtil.getResultMap(actionName + "对象不能为空！", false);
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/update/houseType/update/designRing/update/uploadPreView",
			"/mi/realEstateProject/update/houseType/update/designRing/add/uploadPreView" }, method = { RequestMethod.POST })
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

	@RequestMapping(value = {
			"/mi/realEstateProject/update/houseType/update/designRing/update/uploadContent",
			"/mi/realEstateProject/update/houseType/update/designRing/add/uploadContent" }, method = { RequestMethod.POST })
	public @ResponseBody
	Object miUploadContent(HttpServletRequest request,
			@RequestParam("theFile") MultipartFile theFile) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String path = aossService.upzipAndSaveFileToServer(theFile,Constants.ALIYUN_OSS_UPLOAD_RING_URL);
			resultMap.put("contentUrl", path);
			resultMap.put("success", true);
		} catch (IOException e) {
			LOG.error("保存内容出错！", e);
			resultMap.put("success", false);
			resultMap.put("msg", "保存内容失败");
		}
		return resultMap;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

}
