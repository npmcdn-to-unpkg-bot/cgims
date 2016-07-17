package com.mimi.sxp.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mimi.sxp.Constants;
import com.mimi.sxp.model.DesignImageModel;
import com.mimi.sxp.model.DesignPanoramaModel;
import com.mimi.sxp.model.DesignRingModel;
import com.mimi.sxp.model.HouseTypeModel;
import com.mimi.sxp.model.InformationModel;
import com.mimi.sxp.model.query.DesignImageQueryModel;
import com.mimi.sxp.model.query.DesignPanoramaQueryModel;
import com.mimi.sxp.model.query.DesignRingQueryModel;
import com.mimi.sxp.model.query.HouseTypeQueryModel;
import com.mimi.sxp.model.query.InformationQueryModel;
import com.mimi.sxp.model.query.ProductQueryModel;
import com.mimi.sxp.model.query.ShopQueryModel;
import com.mimi.sxp.service.IAliyunOSSService;
import com.mimi.sxp.service.IDesignImageService;
import com.mimi.sxp.service.IDesignPanoramaService;
import com.mimi.sxp.service.IDesignRingService;
import com.mimi.sxp.service.IHouseTypeService;
import com.mimi.sxp.service.IInformationService;
import com.mimi.sxp.service.IProductService;
import com.mimi.sxp.service.IShopService;
import com.mimi.sxp.service.IUserService;
import com.mimi.sxp.util.pageUtil.PageUtil;

@Controller
public class IndexController {
	private static final Logger LOG = LoggerFactory
			.getLogger(IndexController.class);

	@Resource
	private IUserService userService;

	@Resource
	private IInformationService informationService;

	@Resource
	private IHouseTypeService houseTypeService;

	@Resource
	private IDesignPanoramaService designPanoramaService;

	@Resource
	private IDesignRingService designRingService;

	@Resource
	private IDesignImageService designImageService;

	@Resource
	private IShopService shopService;

	@Resource
	private IProductService productService;

	@Resource
	private IAliyunOSSService aossService;

	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(HttpServletRequest request)
			throws ServletRequestBindingException {
		InformationQueryModel command = new InformationQueryModel();
		command.setPageSize(15);
		command.setTargetPage(PageUtil.BEGIN_PAGE);
		command.setOrderByPriority(true);
		List<InformationModel> informationList = informationService.strictList(command);
		if(informationList!=null && !informationList.isEmpty()){
			for(InformationModel information:informationList){
				information.setPreViewUrl(aossService.addImgParams(information.getPreViewUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
			}
		}
		request.setAttribute("informationList", informationList);
		
		List<Map<String,String>> topBannerList = new ArrayList<Map<String,String>>();
		
		HouseTypeQueryModel command3 = new HouseTypeQueryModel();
		command3.setPageSize(2);
		command3.setTargetPage(PageUtil.BEGIN_PAGE);
		command3.setOrderByPriority(true);
		List<HouseTypeModel> houseTypeList = houseTypeService.strictList(command3);
		if(houseTypeList!=null && !houseTypeList.isEmpty()){
			for(HouseTypeModel houseType:houseTypeList){
				Map<String,String> map = new HashMap<String,String>();
				map.put("preViewUrl", houseType.getHousePlanUrl());
				map.put("contentUrl", request.getContextPath()+"/houseType/view/"+houseType.getId());
				topBannerList.add(map);
			}
		}
		
		DesignPanoramaQueryModel command2 = new DesignPanoramaQueryModel();
		command2.setPageSize(2);
		command2.setTargetPage(PageUtil.BEGIN_PAGE);
		command2.setOrderByPriority(true);
		List<DesignPanoramaModel> designPanoramaList = designPanoramaService.strictList(command2);
		if(designPanoramaList!=null && !designPanoramaList.isEmpty()){
			for(DesignPanoramaModel designPanorama:designPanoramaList){
				Map<String,String> map = new HashMap<String,String>();
				map.put("preViewUrl", designPanorama.getPreViewUrl());
				map.put("contentUrl", request.getContextPath()+"/designPanorama/view/"+designPanorama.getId());
				topBannerList.add(map);
			}
		}
		
		DesignRingQueryModel command4 = new DesignRingQueryModel();
		command4.setPageSize(2);
		command4.setTargetPage(PageUtil.BEGIN_PAGE);
		command4.setOrderByPriority(true);
		List<DesignRingModel> designRingList = designRingService.strictList(command4);
		if(designRingList!=null && !designRingList.isEmpty()){
			for(DesignRingModel designRing:designRingList){
				Map<String,String> map = new HashMap<String,String>();
				map.put("preViewUrl", designRing.getPreViewUrl());
				map.put("contentUrl", request.getContextPath()+"/designRing/view/"+designRing.getId());
				topBannerList.add(map);
			}
		}
		
		DesignImageQueryModel command5 = new DesignImageQueryModel();
		command5.setPageSize(2);
		command5.setTargetPage(PageUtil.BEGIN_PAGE);
		command5.setOrderByPriority(true);
		List<DesignImageModel> designImageList = designImageService.strictList(command5);
		if(designImageList!=null && !designImageList.isEmpty()){
			for(DesignImageModel designImage:designImageList){
				Map<String,String> map = new HashMap<String,String>();
				map.put("preViewUrl", designImage.getContentUrl());
				map.put("contentUrl", request.getContextPath()+"/designImage/view/"+designImage.getId());
				topBannerList.add(map);
			}
		}
		
		for(Map<String,String> map:topBannerList){
			map.put("preViewUrl",aossService.addImgParams(map.get("preViewUrl"), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER));
		}
		
		request.setAttribute("topBannerList", topBannerList);
		
		return "ui/index";
	}

	@RequestMapping(value = "/search/-{searchKeyword}-", method = { RequestMethod.GET })
	public String index(HttpServletRequest request,@PathVariable String searchKeyword){
		
		HouseTypeQueryModel htqm = new HouseTypeQueryModel();
		htqm.setSearchKeyword(searchKeyword);
		int houseTypeNum = houseTypeService.strictCount(htqm);
		
		InformationQueryModel iqm = new InformationQueryModel();
		iqm.setSearchKeyword(searchKeyword);
		int informationNum = informationService.count(iqm);
		
		DesignPanoramaQueryModel dpqm = new DesignPanoramaQueryModel();
		dpqm.setSearchKeyword(searchKeyword);
		int designPanoramaNum = designPanoramaService.strictCount(dpqm);
		
		DesignRingQueryModel drqm = new DesignRingQueryModel();
		drqm.setSearchKeyword(searchKeyword);
		int designRingNum = designRingService.strictCount(drqm);
		
		DesignImageQueryModel diqm = new DesignImageQueryModel();
		diqm.setSearchKeyword(searchKeyword);
		int designImageNum = designImageService.strictCount(diqm);
		
		ShopQueryModel sqm = new ShopQueryModel();
		sqm.setSearchKeyword(searchKeyword);
		int shopNum = shopService.strictCount(sqm);
		
		ProductQueryModel pqm = new ProductQueryModel();
		pqm.setSearchKeyword(searchKeyword);
		int productNum = productService.strictCount(pqm);
		
    	int totalNum = houseTypeNum+informationNum+designPanoramaNum+designRingNum+designImageNum+shopNum+productNum;
    	request.setAttribute("houseTypeNum", houseTypeNum);
    	request.setAttribute("informationNum", informationNum);
    	request.setAttribute("designPanoramaNum", designPanoramaNum);
    	request.setAttribute("designRingNum", designRingNum);
    	request.setAttribute("designImageNum", designImageNum);
    	request.setAttribute("shopNum", shopNum);
    	request.setAttribute("productNum", productNum);
    	request.setAttribute("totalNum", totalNum);
    	return "ui/searchResult";
	}

	@RequestMapping(value = "/error_all", method = { RequestMethod.GET })
	public String errorAll(HttpServletRequest request)
			throws ServletRequestBindingException {
		return "error_all";
	}

	@RequestMapping(value = "/error/unauthorized", method = { RequestMethod.GET })
	public String unauthorizedError(HttpServletRequest request)
			throws ServletRequestBindingException {
		return "error/unauthorized";
	}

	@RequestMapping(value = "/error/unauthenticated", method = { RequestMethod.GET })
	public String unauthenticatedError(HttpServletRequest request)
			throws ServletRequestBindingException {
		return "error/unauthenticated";
	}
	@RequestMapping(value = {"/mi","/mi/index"}, method = { RequestMethod.GET })
	public String toMIIndex(HttpServletRequest request, Model model) {
		return "mi/index";
	}
}
