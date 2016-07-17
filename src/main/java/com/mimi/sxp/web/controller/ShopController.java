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
import com.mimi.sxp.model.ProductModel;
import com.mimi.sxp.model.ShopModel;
import com.mimi.sxp.model.query.ProductQueryModel;
import com.mimi.sxp.model.query.ShopQueryModel;
import com.mimi.sxp.service.IAliyunOSSService;
import com.mimi.sxp.service.IProductService;
import com.mimi.sxp.service.IShopService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.ResultMapUtil;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.support.editor.DateEditor;

@Controller
public class ShopController {
	private static final Logger LOG = LoggerFactory
			.getLogger(ShopController.class);
	@Resource
	private IShopService shopService;

	@Resource
	private IProductService productService;

	@Resource
	private IAliyunOSSService aossService;

	private String permissionObjectName = LoginUtil.PERMISSION_URL_SHOP_DISPLAY;

	@RequestMapping(value = "/shop/search/-{searchKeyword}-", method = { RequestMethod.GET })
	public String toSearch(HttpServletRequest request,
			@PathVariable String searchKeyword) {
		ShopQueryModel command = new ShopQueryModel();
		command.setTargetPage(PageUtil.BEGIN_PAGE);
		command.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command.setSearchKeyword(searchKeyword);
		List<ShopModel> list = shopService.strictList(command);
		for(ShopModel shop:list){
			shop.setPreViewUrl(aossService.addImgParams(shop.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		int total = shopService.strictCount(command);
		request.setAttribute("results", list);
		request.setAttribute("total", total);
		return "ui/shop/search";
	}

	@RequestMapping(value = "/shop/search/json/{searchKeyword}-{targetPage}-{pageSize}", method = { RequestMethod.GET })
	@ResponseBody
	public Object toSearchJson(HttpServletRequest request,
			@PathVariable String searchKeyword,@PathVariable Integer targetPage,@PathVariable Integer pageSize) {
		ShopQueryModel command = new ShopQueryModel();
		command.setTargetPage(targetPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(searchKeyword);
		List<ShopModel> list = shopService.strictList(command);
		for(ShopModel shop:list){
			shop.setPreViewUrl(aossService.addImgParams(shop.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		Map<String,Object> result = ResultMapUtil.getResultMap();
		result.put("results", list);
		return result;
	}

	@RequestMapping(value = "/mi/shop/search", method = { RequestMethod.GET })
	public String toMiSearch() {
		return "mi/shop/search";
	}

	@RequestMapping(value = { "/mi/shop/search/page/json/{curPage}"}, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiSearchPageJson(HttpServletRequest request,
			@PathVariable Integer curPage, Integer pageSize, String name) {
		ShopQueryModel command = new ShopQueryModel();
		command.setTargetPage(curPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(name);
		CommonPageObject<ShopModel> shops = shopService.strictQuery(command);
		return PageUtil.convert2ResultMap(shops);
	}

	@RequestMapping(value = 
			"/shop/view/preView/{id}" , method = { RequestMethod.GET })
	public void toViewPreView(HttpServletRequest request,HttpServletResponse response, @PathVariable String id) throws IOException {
		ShopModel shop = shopService.get(id);
		shop.setPreViewUrl(aossService.addImgParams(shop.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_WATER_MARK));
		response.sendRedirect(shop.getPreViewUrl());
	}

	@RequestMapping(value = "/shop/view/{id}", method = { RequestMethod.GET })
	public String toView(HttpServletRequest request,@PathVariable String id) {

		ShopModel shop = shopService.get(id);
		shop.setPreViewUrl(aossService.addImgParams(shop.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER));
		ProductQueryModel pqm = new ProductQueryModel();
		pqm.setTargetPage(PageUtil.BEGIN_PAGE);
		pqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
		pqm.setShopId(id);
		List<ProductModel> list = productService.strictList(pqm);
		for(ProductModel product:list){
			product.setPreViewUrl(aossService.addImgParams(product.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		shop.setProducts(list);
		request.setAttribute("shop", shop);
		return "ui/shop/view";
	}

	@RequestMapping(value = "/mi/shop/view/{id}", method = { RequestMethod.GET })
	public String toMiView(@PathVariable String id) {
		return "mi/shop/view";
	}

	@RequestMapping(value = { "/mi/shop/view/json/{id}",
			"/mi/shop/add/view/json/{id}", "/mi/shop/update/view/json/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiViewJson(HttpServletRequest request,
			@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			ShopModel shop = shopService.get(id);
			if (shop != null) {
//				PermissionQueryModel pqm = new PermissionQueryModel();
//				pqm.setTargetPage(PageUtil.BEGIN_PAGE);
//				pqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
//				pqm.setShopId(id);
//				List<PermissionModel> permissions = permissionService
//						.strictList(pqm);
				shop.setPreViewUrl(aossService.addImgParams(shop.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG));
				resultMap.put("shop", shop);
//				resultMap.put("relations", permissions);
			}
		} catch (Exception e) {

			resultMap.put("shop", null);
			LOG.error(LoginUtil.SEARCH_DISPLAY + permissionObjectName
					+ "详细信息出错！", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/shop/add", method = { RequestMethod.GET })
	public String toMiAdd() {
		return "mi/shop/add";
	}

	@RequestMapping(value = "/mi/shop/update/{id}", method = { RequestMethod.GET })
	public String toMiUpdate(@PathVariable String id) {
		return "mi/shop/update";
	}

	@RequestMapping(value = { "/mi/shop/update/json", "/mi/shop/add/json" }, method = { RequestMethod.POST })
	@ResponseBody
	public Object miSaveOrUpdate(HttpServletRequest request, ShopModel shop,
			String newPermissionIds) {
		return saveOrUpdateUser(request, shop, newPermissionIds);
	}

	private Object saveOrUpdateUser(HttpServletRequest request, ShopModel shop,
			String newPermissionIds) {
		Map<String, Object> resultMap = null;
		String actionName;
		if (StringUtils.isBlank(shop.getId())) {
			actionName = LoginUtil.ADD_DISPLAY;
			shop.setCreaterId(LoginUtil.getCurrentUserId(request));
		} else {
			actionName = LoginUtil.UPDATE_DISPLAY;
		}
		shop.setEditorId(LoginUtil.getCurrentUserId(request));
		shop.setPreViewUrl(aossService.clearImgParams(shop.getPreViewUrl()));

		try {
			shopService.saveOrUpdate(shop);
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

	@RequestMapping(value = "/mi/shop/delete/batch/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miBatchDelete(HttpServletRequest request, String shopIds) {
		String actionName = LoginUtil.DELETE_DISPLAY;
		if (StringUtils.isNotBlank(shopIds)) {
			String[] rids = shopIds.split(Constants.MI_IDS_SPLIT_STRING);
			if (rids.length > 0) {
				List<String> idList = Arrays.asList(rids);
				shopService.deleteBatch(idList);
				return ResultMapUtil.getResultMap(actionName
						+ permissionObjectName + "成功！");
			}
		}
		return ResultMapUtil.getResultMap(actionName + "对象不能为空！", false);
	}

	@RequestMapping(value = { "/mi/shop/update/uploadPreView",
			"/mi/shop/add/uploadPreView" }, method = { RequestMethod.POST })
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
			LOG.error("保存预览图出错！", e);
			resultMap.put("success", false);
			resultMap.put("msg", "保存预览图失败");
		}
		return resultMap;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

}
