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
import com.mimi.sxp.model.DesignPanoramaModel;
import com.mimi.sxp.model.HouseTypeModel;
import com.mimi.sxp.model.ProductModel;
import com.mimi.sxp.model.query.DesignPanoramaQueryModel;
import com.mimi.sxp.model.query.ProductQueryModel;
import com.mimi.sxp.service.IAliyunOSSService;
import com.mimi.sxp.service.IDesignPanoramaService;
import com.mimi.sxp.service.IProductService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.ResultMapUtil;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.support.editor.DateEditor;

@Controller
public class DesignPanoramaController {
	private static final Logger LOG = LoggerFactory
			.getLogger(DesignPanoramaController.class);
	@Resource
	private IDesignPanoramaService designPanoramaService;

	@Resource
	private IProductService productService;

	@Resource
	private IAliyunOSSService aossService;

	private String permissionObjectName = LoginUtil.PERMISSION_URL_DESIGN_PANORAMA_DISPLAY;

	@RequestMapping(value = "/designPanorama/search/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-", method = { RequestMethod.GET })
	public String toSearch(HttpServletRequest request,
			@PathVariable String searchKeyword,
			@PathVariable String insideArea,
			@PathVariable String grossFloorArea,
			@PathVariable Integer roomNum) {
		DesignPanoramaQueryModel command = new DesignPanoramaQueryModel();
		command.setTargetPage(PageUtil.BEGIN_PAGE);
		command.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command.setSearchKeyword(searchKeyword);
		command.setInsideAreaLimit(insideArea);
		command.setGrossFloorAreaLimit(grossFloorArea);
		command.setRoomNum(roomNum);
		List<DesignPanoramaModel> list = designPanoramaService.strictList(command);
		for(DesignPanoramaModel designPanorama:list){
			designPanorama.setPreViewUrl(aossService.addImgParams(designPanorama.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		int total = designPanoramaService.strictCount(command);
		request.setAttribute("results", list);
		request.setAttribute("total", total);
		return "ui/designPanorama/search";
	}

	@RequestMapping(value = "/designPanorama/search/json/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-{targetPage}-{pageSize}-", method = { RequestMethod.GET })
	@ResponseBody
	public Object toSearchJson(HttpServletRequest request,
			@PathVariable String searchKeyword,
			@PathVariable String insideArea,
			@PathVariable String grossFloorArea,
			@PathVariable Integer roomNum,@PathVariable Integer targetPage,@PathVariable Integer pageSize) {
		DesignPanoramaQueryModel command = new DesignPanoramaQueryModel();
		command.setTargetPage(targetPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(searchKeyword);
		command.setInsideAreaLimit(insideArea);
		command.setGrossFloorAreaLimit(grossFloorArea);
		command.setRoomNum(roomNum);
		List<DesignPanoramaModel> list = designPanoramaService.strictList(command);
		for(DesignPanoramaModel designPanorama:list){
			designPanorama.setPreViewUrl(aossService.addImgParams(designPanorama.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		Map<String,Object> result = ResultMapUtil.getResultMap();
		result.put("results", list);
		return result;
	}

	@RequestMapping(value = "/mi/designPanorama/search", method = { RequestMethod.GET })
	public String toMiSearch() {
		return "mi/designPanorama/search";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/view/houseType/view/designPanorama/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/view/designPanorama/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designPanorama/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designPanorama/search/page/json/{curPage}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiSearchPageJson(HttpServletRequest request,
			@PathVariable Integer curPage, Integer pageSize, String name,
			String houseTypeId) {
		DesignPanoramaQueryModel command = new DesignPanoramaQueryModel();
		command.setTargetPage(curPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(name);
		command.setHouseTypeId(houseTypeId);
		CommonPageObject<DesignPanoramaModel> designPanoramas = designPanoramaService
				.strictQuery(command);
		for (DesignPanoramaModel designPanorama : designPanoramas.getItems()) {
			if (StringUtils.isNotBlank(designPanorama.getPreViewUrl())) {
				designPanorama.setPreViewUrl(aossService.addImgParams(
						designPanorama.getPreViewUrl(),
						Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
			}
		}
		return PageUtil.convert2ResultMap(designPanoramas);
	}

	@RequestMapping(value = {
			"/designPanorama/content/{id}" }, method = { RequestMethod.GET })
	public String toContent(HttpServletRequest request, @PathVariable String id) {
		request.setAttribute("designPanorama",designPanoramaService.get(id));
		return "ui/designPanorama/content";
	}

	@RequestMapping(value = 
			"/designPanorama/view/{id}" , method = { RequestMethod.GET })
	public String toView(HttpServletRequest request,@PathVariable String id) {
		DesignPanoramaModel designPanorama = designPanoramaService.get(id);
		ProductQueryModel pqm = new ProductQueryModel();
		pqm.setTargetPage(PageUtil.BEGIN_PAGE);
		pqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
		pqm.setDesignPanoramaId(id);
		designPanorama.setProducts(productService.strictList(pqm));
		request.setAttribute("designPanorama", designPanorama);
		return "ui/designPanorama/view";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/view/houseType/view/designPanorama/view/{realEstateProjectId}/{houseTypeId}/{id}",
			"/mi/realEstateProject/update/houseType/view/designPanorama/view/{realEstateProjectId}/{houseTypeId}/{id}",
			"/mi/realEstateProject/update/houseType/update/designPanorama/view/{realEstateProjectId}/{houseTypeId}/{id}",
			"/mi/realEstateProject/update/houseType/update/designPanorama/view/{realEstateProjectId}/{houseTypeId}/{id}" }, method = { RequestMethod.GET })
	public String toMiView(HttpServletRequest request,
			@PathVariable String realEstateProjectId,
			@PathVariable String houseTypeId, @PathVariable String id) {
		return "mi/designPanorama/view";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/view/houseType/view/designPanorama/view/json/{id}",
			"/mi/realEstateProject/update/houseType/view/designPanorama/view/json/{id}",
			"/mi/realEstateProject/update/houseType/update/designPanorama/view/json/{id}",
			"/mi/realEstateProject/update/houseType/update/designPanorama/update/view/json/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiViewJson(HttpServletRequest request,
			@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			DesignPanoramaModel designPanorama = designPanoramaService.get(id);
			if (designPanorama != null) {
				designPanorama.setPreViewUrl(aossService.addImgParams(
						designPanorama.getPreViewUrl(),
						Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG));
				resultMap.put("designPanorama", designPanorama);
				
				ProductQueryModel command = new ProductQueryModel();
				command.setTargetPage(PageUtil.BEGIN_PAGE);
				command.setPageSize(PageUtil.MAX_PAGE_SIZE);
				command.setDesignPanoramaId(id);
				resultMap.put("relationProducts", productService.strictList(command));
			}
		} catch (Exception e) {
			resultMap.put("designPanorama", null);
			LOG.error(LoginUtil.SEARCH_DISPLAY + permissionObjectName
					+ "详细信息出错！", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/update/designPanorama/add/{realEstateProjectId}/{houseTypeId}", method = { RequestMethod.GET })
	public String toMiAdd(@PathVariable String realEstateProjectId,
			@PathVariable String houseTypeId) {
		return "mi/designPanorama/add";
	}

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/update/designPanorama/update/{realEstateProjectId}/{houseTypeId}/{id}", method = { RequestMethod.GET })
	public String toMiUpdate(@PathVariable String realEstateProjectId,
			@PathVariable String houseTypeId, @PathVariable String id) {
		return "mi/designPanorama/update";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/update/houseType/update/designPanorama/update/json",
			"/mi/realEstateProject/update/houseType/update/designPanorama/add/json" }, method = { RequestMethod.POST })
	@ResponseBody
	public Object miSaveOrUpdate(HttpServletRequest request,
			DesignPanoramaModel designPanorama, String houseTypeId,String newProductIds) {
		return saveOrUpdateUser(request, designPanorama, houseTypeId,newProductIds);
	}

	private Object saveOrUpdateUser(HttpServletRequest request,
			DesignPanoramaModel designPanorama, String houseTypeId,String newProductIds) {
		Map<String, Object> resultMap = null;
		String actionName;
		if (StringUtils.isBlank(designPanorama.getId())) {
			actionName = LoginUtil.ADD_DISPLAY;
			designPanorama.setCreaterId(LoginUtil.getCurrentUserId(request));
		} else {
			actionName = LoginUtil.UPDATE_DISPLAY;
		}
		HouseTypeModel houseType = new HouseTypeModel();
		houseType.setId(houseTypeId);
		designPanorama.setHouseType(houseType);
		designPanorama.setEditorId(LoginUtil.getCurrentUserId(request));
		designPanorama.setPreViewUrl(aossService.clearImgParams(designPanorama
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
				designPanorama.setProducts(products);
			}
			designPanoramaService.saveOrUpdate(designPanorama);
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

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/update/designPanorama/delete/batch/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miBatchDelete(HttpServletRequest request,
			String designPanoramaIds) {
		String actionName = LoginUtil.DELETE_DISPLAY;
		if (StringUtils.isNotBlank(designPanoramaIds)) {
			String[] rids = designPanoramaIds
					.split(Constants.MI_IDS_SPLIT_STRING);
			if (rids.length > 0) {
				List<String> idList = Arrays.asList(rids);
				designPanoramaService.deleteBatch(idList);
				return ResultMapUtil.getResultMap(actionName
						+ permissionObjectName + "成功！");
			}
		}
		return ResultMapUtil.getResultMap(actionName + "对象不能为空！", false);
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/update/houseType/update/designPanorama/update/uploadPreView",
			"/mi/realEstateProject/update/houseType/update/designPanorama/add/uploadPreView" }, method = { RequestMethod.POST })
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
			"/mi/realEstateProject/update/houseType/update/designPanorama/update/uploadContent",
			"/mi/realEstateProject/update/houseType/update/designPanorama/add/uploadContent" }, method = { RequestMethod.POST })
	public @ResponseBody
	Object miUploadContent(HttpServletRequest request,
			@RequestParam("theFile") MultipartFile theFile) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String path = aossService.upzipAndSaveFileToServer(theFile,Constants.ALIYUN_OSS_UPLOAD_PANORAMA_URL);
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
