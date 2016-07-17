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
import com.mimi.sxp.model.ProductModel;
import com.mimi.sxp.model.ShopModel;
import com.mimi.sxp.model.query.DesignImageQueryModel;
import com.mimi.sxp.model.query.DesignPanoramaQueryModel;
import com.mimi.sxp.model.query.DesignRingQueryModel;
import com.mimi.sxp.model.query.ProductQueryModel;
import com.mimi.sxp.service.IAliyunOSSService;
import com.mimi.sxp.service.IDesignImageService;
import com.mimi.sxp.service.IDesignPanoramaService;
import com.mimi.sxp.service.IDesignRingService;
import com.mimi.sxp.service.IProductService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.ResultMapUtil;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.support.editor.DateEditor;

@Controller
public class ProductController {
	private static final Logger LOG = LoggerFactory
			.getLogger(ProductController.class);
	@Resource
	private IProductService productService;
	
	@Resource
	private IDesignPanoramaService designPanoramaService;

	@Resource
	private IDesignImageService designImageService;

	@Resource
	private IDesignRingService designRingService;

	@Resource
	private IAliyunOSSService aossService;

	private String permissionObjectName = LoginUtil.PERMISSION_URL_PRODUCT_DISPLAY;

	@RequestMapping(value = "/product/search/-{searchKeyword}-", method = { RequestMethod.GET })
	public String toSearch(HttpServletRequest request,
			@PathVariable String searchKeyword) {
		ProductQueryModel command = new ProductQueryModel();
		command.setTargetPage(PageUtil.BEGIN_PAGE);
		command.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command.setSearchKeyword(searchKeyword);
		List<ProductModel> list = productService.strictList(command);
		for(ProductModel product:list){
			product.setPreViewUrl(aossService.addImgParams(product.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		int total = productService.strictCount(command);
		request.setAttribute("results", list);
		request.setAttribute("total", total);
		return "ui/product/search";
	}

	@RequestMapping(value = "/product/search/json/{searchKeyword}-{targetPage}-{pageSize}", method = { RequestMethod.GET })
	@ResponseBody
	public Object toSearchJson(HttpServletRequest request,
			@PathVariable String searchKeyword,@PathVariable Integer targetPage,@PathVariable Integer pageSize) {
		ProductQueryModel command = new ProductQueryModel();
		command.setTargetPage(targetPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(searchKeyword);
		List<ProductModel> list = productService.strictList(command);
		for(ProductModel product:list){
			product.setPreViewUrl(aossService.addImgParams(product.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
		}
		Map<String,Object> result = ResultMapUtil.getResultMap();
		result.put("results", list);
		return result;
	}

	@RequestMapping(value = "/mi/product/search", method = { RequestMethod.GET })
	public String toMiSearch() {
		return "mi/product/search";
	}

	@RequestMapping(value = { "/mi/shop/view/product/search/page/json/{curPage}",
			"/mi/shop/update/product/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designPanorama/add/product/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designPanorama/update/product/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designRing/add/product/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designRing/update/product/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designImage/add/product/search/page/json/{curPage}",
			"/mi/realEstateProject/update/houseType/update/designImage/update/product/search/page/json/{curPage}"}, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiSearchPageJson(HttpServletRequest request,
			@PathVariable Integer curPage, Integer pageSize, String name,
			String shopId) {
		ProductQueryModel command = new ProductQueryModel();
		command.setTargetPage(curPage);
		command.setPageSize(pageSize);
		command.setSearchKeyword(name);
		command.setShopId(shopId);
		CommonPageObject<ProductModel> products = productService
				.strictQuery(command);
		return PageUtil.convert2ResultMap(products);
	}

	@RequestMapping(value = 
			"/product/view/preView/{id}" , method = { RequestMethod.GET })
	public void toViewPreView(HttpServletRequest request,HttpServletResponse response, @PathVariable String id) throws IOException {
		ProductModel product = productService.get(id);
		product.setPreViewUrl(aossService.addImgParams(product.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_WATER_MARK));
		response.sendRedirect(product.getPreViewUrl());
	}

	@RequestMapping(value = "/product/view/{id}", method = { RequestMethod.GET })
	public String toView(HttpServletRequest request,@PathVariable String id) {
		
		DesignImageQueryModel command1 = new DesignImageQueryModel();
		command1.setTargetPage(PageUtil.BEGIN_PAGE);
		command1.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command1.setProductId(id);
		List<DesignImageModel> designImages = designImageService.strictList(command1);
		for(DesignImageModel designImage:designImages){
			designImage.setContentUrl(aossService.addImgParams(designImage.getContentUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER));
		}
//		request.setAttribute("designImages", designImages);

		DesignRingQueryModel command2 = new DesignRingQueryModel();
		command2.setTargetPage(PageUtil.BEGIN_PAGE);
		command2.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command2.setProductId(id);
		List<DesignRingModel> designRings = designRingService.strictList(command2);
		for(DesignRingModel designRing:designRings){
			designRing.setPreViewUrl(aossService.addImgParams(designRing.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER));
		}
//		request.setAttribute("designRings", designRings);
		
		DesignPanoramaQueryModel command3 = new DesignPanoramaQueryModel();
		command3.setTargetPage(PageUtil.BEGIN_PAGE);
		command3.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
		command3.setProductId(id);
		List<DesignPanoramaModel> designPanoramas = designPanoramaService.strictList(command3);
		for(DesignPanoramaModel designPanorama:designPanoramas){
			designPanorama.setPreViewUrl(aossService.addImgParams(designPanorama.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER));
		}
		
		ProductModel product = productService.get(id);
		product.setPreViewUrl(aossService.addImgParams(product.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER));
		product.setDesignImages(designImages);
		product.setDesignPanoramas(designPanoramas);
		product.setDesignRings(designRings);
		request.setAttribute("product", product);
		return "ui/product/view";
	}

	@RequestMapping(value = {"/mi/shop/view/product/view/{shopId}/{id}",
			"/mi/shop/update/product/view/{shopId}/{id}"}, method = { RequestMethod.GET })
	public String toMiView(HttpServletRequest request,@PathVariable String shopId,@PathVariable String id) {
		return "mi/product/view";
	}

	@RequestMapping(value = { 
			"/mi/shop/view/product/view/json/{id}",
			"/mi/shop/update/product/view/json/{id}",
			"/mi/shop/update/product/update/view/json/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiViewJson(HttpServletRequest request,
			@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			ProductModel product = productService.get(id);
			if (product != null) {
				product.setPreViewUrl(aossService.addImgParams(product.getPreViewUrl(),
						Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG));
				resultMap.put("product", product);
			}
		} catch (Exception e) {

			resultMap.put("product", null);
			LOG.error(LoginUtil.SEARCH_DISPLAY + permissionObjectName
					+ "详细信息出错！", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/shop/update/product/add/{shopId}", method = { RequestMethod.GET })
	public String toMiAdd(@PathVariable String shopId) {
		return "mi/product/add";
	}

	@RequestMapping(value = "/mi/shop/update/product/update/{shopId}/{id}", method = { RequestMethod.GET })
	public String toMiUpdate(@PathVariable String shopId,@PathVariable String id) {
		return "mi/product/update";
	}

	@RequestMapping(value = { "/mi/shop/update/product/update/json",
			"/mi/shop/update/product/add/json" }, method = { RequestMethod.POST })
	@ResponseBody
	public Object miSaveOrUpdate(HttpServletRequest request,
			ProductModel product,String shopId) {
		return saveOrUpdateUser(request, product,shopId);
	}

	private Object saveOrUpdateUser(HttpServletRequest request,
			ProductModel product,String shopId) {
		Map<String, Object> resultMap = null;
		String actionName;
		if (StringUtils.isBlank(product.getId())) {
			actionName = LoginUtil.ADD_DISPLAY;
			product.setCreaterId(LoginUtil.getCurrentUserId(request));
		} else {
			actionName = LoginUtil.UPDATE_DISPLAY;
		}
		ShopModel shop = new ShopModel();
		shop.setId(shopId);
		product.setShop(shop);
		product.setEditorId(LoginUtil.getCurrentUserId(request));
		product.setPreViewUrl(aossService.clearImgParams(product.getPreViewUrl()));

		try {
			productService.saveOrUpdate(product);
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

	@RequestMapping(value = "/mi/shop/update/product/delete/batch/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miBatchDelete(HttpServletRequest request, String productIds) {
		String actionName = LoginUtil.DELETE_DISPLAY;
		if (StringUtils.isNotBlank(productIds)) {
			String[] rids = productIds.split(Constants.MI_IDS_SPLIT_STRING);
			if (rids.length > 0) {
				List<String> idList = Arrays.asList(rids);
				productService.deleteBatch(idList);
				return ResultMapUtil.getResultMap(actionName
						+ permissionObjectName + "成功！");
			}
		}
		return ResultMapUtil.getResultMap(actionName + "对象不能为空！", false);
	}

	@RequestMapping(value = { "/mi/shop/update/product/update/uploadPreView",
			"/mi/shop/update/product/add/uploadPreView" }, method = { RequestMethod.POST })
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
