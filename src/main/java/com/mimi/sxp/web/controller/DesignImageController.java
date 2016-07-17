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
import com.mimi.sxp.model.HouseTypeModel;
import com.mimi.sxp.model.ProductModel;
import com.mimi.sxp.model.query.DesignImageQueryModel;
import com.mimi.sxp.model.query.ProductQueryModel;
import com.mimi.sxp.service.IAliyunOSSService;
import com.mimi.sxp.service.IDesignImageService;
import com.mimi.sxp.service.IProductService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.ResultMapUtil;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.support.editor.DateEditor;

@Controller
public class DesignImageController {
	private static final Logger LOG = LoggerFactory
			.getLogger(DesignImageController.class);
	@Resource
	private IDesignImageService designImageService;

	@Resource
	private IProductService productService;

	@Resource
	private IAliyunOSSService aossService;

	private String permissionObjectName = LoginUtil.PERMISSION_URL_DESIGN_IMAGE_DISPLAY;

	@RequestMapping(value = "/designImage/search/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-", method = { RequestMethod.GET })
	public String toSearch(HttpServletRequest request,
			@PathVariable String searchKeyword,
			@PathVariable String insideArea,
			@PathVariable String grossFloorArea,
			@PathVariable Integer roomNum) {
		DesignImageQueryModel command = new DesignImageQueryModel();
		command.setTargetPage(PageUtil.BEGIN_PAGE);
		command.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command.setSearchKeyword(searchKeyword);
		command.setInsideAreaLimit(insideArea);
		command.setGrossFloorAreaLimit(grossFloorArea);
		command.setRoomNum(roomNum);
		List<DesignImageModel> list = designImageService.strictList(command);
		for(DesignImageModel designImage:list){
			designImage.setContentUrl(aossService.addImgParams(designImage.getContentUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		int total = designImageService.strictCount(command);
		request.setAttribute("results", list);
		request.setAttribute("total", total);
		return "ui/designImage/search";
	}

	@RequestMapping(value = "/designImage/search/json/-{searchKeyword}-{insideArea}-{grossFloorArea}-{roomNum}-{targetPage}-{pageSize}-", method = { RequestMethod.GET })
	@ResponseBody
	public Object toSearchJson(HttpServletRequest request,
			@PathVariable String searchKeyword,
			@PathVariable String insideArea,
			@PathVariable String grossFloorArea,
			@PathVariable Integer roomNum,@PathVariable Integer targetPage,@PathVariable Integer pageSize) {
		DesignImageQueryModel command = new DesignImageQueryModel();
		command.setTargetPage(targetPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(searchKeyword);
		command.setInsideAreaLimit(insideArea);
		command.setGrossFloorAreaLimit(grossFloorArea);
		command.setRoomNum(roomNum);
		List<DesignImageModel> list = designImageService.strictList(command);
		for(DesignImageModel designImage:list){
			designImage.setContentUrl(aossService.addImgParams(designImage.getContentUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		Map<String,Object> result = ResultMapUtil.getResultMap();
		result.put("results", list);
		return result;
	}

	@RequestMapping(value = "/mi/designImage/search", method = { RequestMethod.GET })
	public String toMiSearch() {
		return "mi/designImage/search";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/view/houseType/view/designImage/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/view/designImage/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designImage/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designImage/search/page/json/{curPage}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiSearchPageJson(HttpServletRequest request,
			@PathVariable Integer curPage, Integer pageSize, String name,
			String houseTypeId) {
		DesignImageQueryModel command = new DesignImageQueryModel();
		command.setTargetPage(curPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(name);
		command.setHouseTypeId(houseTypeId);
		CommonPageObject<DesignImageModel> designImages = designImageService
				.strictQuery(command);
		for (DesignImageModel designImage : designImages.getItems()) {
			if (StringUtils.isNotBlank(designImage.getContentUrl())) {
				designImage.setContentUrl(aossService.addImgParams(
						designImage.getContentUrl(),
						Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
			}
		}
		return PageUtil.convert2ResultMap(designImages);
	}

	@RequestMapping(value = 
			"/designImage/content/{id}" , method = { RequestMethod.GET })
	public void toContent(HttpServletRequest request,HttpServletResponse response, @PathVariable String id) throws IOException {
		DesignImageModel designImage = designImageService.get(id);
		designImage.setContentUrl(aossService.addImgParams(designImage.getContentUrl(), Constants.ALIYUN_OSS_IMAGE_WATER_MARK));
		response.sendRedirect(designImage.getContentUrl());
	}

	@RequestMapping(value = 
			"/designImage/view/{id}" , method = { RequestMethod.GET })
	public String toView(HttpServletRequest request, @PathVariable String id) {
		DesignImageModel designImage = designImageService.get(id);
		ProductQueryModel pqm = new ProductQueryModel();
		pqm.setTargetPage(PageUtil.BEGIN_PAGE);
		pqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
		pqm.setDesignImageId(id);
		designImage.setProducts(productService.strictList(pqm));
		designImage.setContentUrl(aossService.addImgParams(designImage.getContentUrl(), Constants.ALIYUN_OSS_IMAGE_WATER_MARK));
		request.setAttribute("designImage", designImage);
		return "ui/designImage/view";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/view/houseType/view/designImage/view/{realEstateProjectId}/{houseTypeId}/{id}",
			"/mi/realEstateProject/update/houseType/view/designImage/view/{realEstateProjectId}/{houseTypeId}/{id}",
			"/mi/realEstateProject/update/houseType/update/designImage/view/{realEstateProjectId}/{houseTypeId}/{id}",
			"/mi/realEstateProject/update/houseType/update/designImage/view/{realEstateProjectId}/{houseTypeId}/{id}" }, method = { RequestMethod.GET })
	public String toMiView(HttpServletRequest request,
			@PathVariable String realEstateProjectId,
			@PathVariable String houseTypeId, @PathVariable String id) {
		return "mi/designImage/view";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/view/houseType/view/designImage/view/json/{id}",
			"/mi/realEstateProject/update/houseType/view/designImage/view/json/{id}",
			"/mi/realEstateProject/update/houseType/update/designImage/view/json/{id}",
			"/mi/realEstateProject/update/houseType/update/designImage/update/view/json/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiViewJson(HttpServletRequest request,
			@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			DesignImageModel designImage = designImageService.get(id);
			if (designImage != null) {
				designImage.setContentUrl(aossService.addImgParams(
						designImage.getContentUrl(),
						Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG));
				resultMap.put("designImage", designImage);
				
				ProductQueryModel command = new ProductQueryModel();
				command.setTargetPage(PageUtil.BEGIN_PAGE);
				command.setPageSize(PageUtil.MAX_PAGE_SIZE);
				command.setDesignImageId(id);
				resultMap.put("relationProducts", productService.strictList(command));
			}
		} catch (Exception e) {
			resultMap.put("designImage", null);
			LOG.error(LoginUtil.SEARCH_DISPLAY + permissionObjectName
					+ "详细信息出错！", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/update/designImage/add/{realEstateProjectId}/{houseTypeId}", method = { RequestMethod.GET })
	public String toMiAdd(@PathVariable String realEstateProjectId,
			@PathVariable String houseTypeId) {
		return "mi/designImage/add";
	}

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/update/designImage/update/{realEstateProjectId}/{houseTypeId}/{id}", method = { RequestMethod.GET })
	public String toMiUpdate(@PathVariable String realEstateProjectId,
			@PathVariable String houseTypeId, @PathVariable String id) {
		return "mi/designImage/update";
	}

	@RequestMapping(value = {
			"/mi/realEstateProject/update/houseType/update/designImage/update/json",
			"/mi/realEstateProject/update/houseType/update/designImage/add/json" }, method = { RequestMethod.POST })
	@ResponseBody
	public Object miSaveOrUpdate(HttpServletRequest request,
			DesignImageModel designImage, String houseTypeId,String newProductIds) {
		return saveOrUpdateUser(request, designImage, houseTypeId, newProductIds);
	}

	private Object saveOrUpdateUser(HttpServletRequest request,
			DesignImageModel designImage, String houseTypeId,String newProductIds) {
		Map<String, Object> resultMap = null;
		String actionName;
		if (StringUtils.isBlank(designImage.getId())) {
			actionName = LoginUtil.ADD_DISPLAY;
			designImage.setCreaterId(LoginUtil.getCurrentUserId(request));
		} else {
			actionName = LoginUtil.UPDATE_DISPLAY;
		}
		HouseTypeModel houseType = new HouseTypeModel();
		houseType.setId(houseTypeId);
		designImage.setHouseType(houseType);
		designImage.setEditorId(LoginUtil.getCurrentUserId(request));
		designImage.setContentUrl(aossService.clearImgParams(designImage
				.getContentUrl()));

		try {
			if (StringUtils.isNotBlank(newProductIds)) {
				List<ProductModel> products = new ArrayList<ProductModel>();
				String[] pids = newProductIds.split(Constants.MI_IDS_SPLIT_STRING);
				for (int i = 0; i < pids.length; i++) {
					ProductModel product = new ProductModel();
					product.setId(pids[i]);
					products.add(product);
				}
				designImage.setProducts(products);
			}
			designImageService.saveOrUpdate(designImage);
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

	@RequestMapping(value = "/mi/realEstateProject/update/houseType/update/designImage/delete/batch/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miBatchDelete(HttpServletRequest request,
			String designImageIds) {
		String actionName = LoginUtil.DELETE_DISPLAY;
		if (StringUtils.isNotBlank(designImageIds)) {
			String[] rids = designImageIds
					.split(Constants.MI_IDS_SPLIT_STRING);
			if (rids.length > 0) {
				List<String> idList = Arrays.asList(rids);
				designImageService.deleteBatch(idList);
				return ResultMapUtil.getResultMap(actionName
						+ permissionObjectName + "成功！");
			}
		}
		return ResultMapUtil.getResultMap(actionName + "对象不能为空！", false);
	}


	@RequestMapping(value = {
			"/mi/realEstateProject/update/houseType/update/designImage/update/uploadContent",
			"/mi/realEstateProject/update/houseType/update/designImage/add/uploadContent" }, method = { RequestMethod.POST })
	public @ResponseBody
	Object miUploadContent(HttpServletRequest request,
			@RequestParam("theFile") MultipartFile theFile) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String path = aossService.saveFileToServer(theFile);
			path = aossService.addImgParams(path,
					Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG);
			resultMap.put("imgPath", path);
			resultMap.put("success", true);
		} catch (IOException e) {
			LOG.error("保存图片出错！", e);
			resultMap.put("success", false);
			resultMap.put("msg", "保存图片失败");
		}
		return resultMap;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

}
