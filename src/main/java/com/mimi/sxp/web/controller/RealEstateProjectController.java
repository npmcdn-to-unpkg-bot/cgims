package com.mimi.sxp.web.controller;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mimi.sxp.Constants;
import com.mimi.sxp.model.HouseTypeModel;
import com.mimi.sxp.model.RealEstateProjectModel;
import com.mimi.sxp.model.query.HouseTypeQueryModel;
import com.mimi.sxp.model.query.RealEstateProjectQueryModel;
import com.mimi.sxp.service.IAliyunOSSService;
import com.mimi.sxp.service.IHouseTypeService;
import com.mimi.sxp.service.IRealEstateProjectService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.ResultMapUtil;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.support.editor.DateEditor;

@Controller
public class RealEstateProjectController {
	private static final Logger LOG = LoggerFactory
			.getLogger(RealEstateProjectController.class);
	
	@Resource
	private IRealEstateProjectService realEstateProjectService;
	
	@Resource
	private IHouseTypeService houseTypeService;

	@Resource
	private IAliyunOSSService aossService;

	private String permissionObjectName = LoginUtil.PERMISSION_URL_REAL_ESTATE_PROJECT_DISPLAY;

	@RequestMapping(value = "/mi/realEstateProject/search", method = { RequestMethod.GET })
	public String toMiSearch() {
		return "mi/realEstateProject/search";
	}

	@RequestMapping(value = { "/mi/realEstateProject/search/page/json/{curPage}"}, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiSearchPageJson(HttpServletRequest request,
			@PathVariable Integer curPage, Integer pageSize, String name) {
		RealEstateProjectQueryModel repqm = new RealEstateProjectQueryModel();
		repqm.setTargetPage(curPage);
		repqm.setPageSize(pageSize);
		repqm.setSearchKeyword(name);
		CommonPageObject<RealEstateProjectModel> realEstateProjects = realEstateProjectService.strictQuery(repqm);
		return PageUtil.convert2ResultMap(realEstateProjects);
	}

	@RequestMapping(value = "/realEstateProject/view/{id}", method = { RequestMethod.GET })
	public String toView(HttpServletRequest request,@PathVariable String id) {
		RealEstateProjectModel realEstateProject = realEstateProjectService.get(id);
		HouseTypeQueryModel htqm = new HouseTypeQueryModel();
		htqm.setTargetPage(PageUtil.BEGIN_PAGE);
		htqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
		htqm.setRealEstateProjectId(id);
		List<HouseTypeModel> houseTypes = houseTypeService.strictList(htqm);
		realEstateProject.setHouseTypes(houseTypes);
		
		List<HouseTypeModel> oneRoomHouseTypes = new ArrayList<HouseTypeModel>();
		List<HouseTypeModel> twoRoomHouseTypes = new ArrayList<HouseTypeModel>();
		List<HouseTypeModel> threeRoomHouseTypes = new ArrayList<HouseTypeModel>();
		List<HouseTypeModel> fourRoomHouseTypes = new ArrayList<HouseTypeModel>();
		List<HouseTypeModel> fiveRoomHouseTypes = new ArrayList<HouseTypeModel>();
		List<HouseTypeModel> overFiveRoomHouseTypes = new ArrayList<HouseTypeModel>();
		for(HouseTypeModel houseType:houseTypes){
			houseType.setHousePlanUrl(aossService.addImgParams(houseType.getHousePlanUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG));
			switch (houseType.getRoomNum()) {
			case 1:
				oneRoomHouseTypes.add(houseType);
				break;
			case 2:
				twoRoomHouseTypes.add(houseType);
				break;
			case 3:
				threeRoomHouseTypes.add(houseType);
				break;
			case 4:
				fourRoomHouseTypes.add(houseType);
				break;
			case 5:
				fiveRoomHouseTypes.add(houseType);
				break;
			default:
				overFiveRoomHouseTypes.add(houseType);
				break;
			}
		}
		
		request.setAttribute("realEstateProject", realEstateProject);
		request.setAttribute("oneRoomHouseTypes", oneRoomHouseTypes);
		request.setAttribute("twoRoomHouseTypes", twoRoomHouseTypes);
		request.setAttribute("threeRoomHouseTypes", threeRoomHouseTypes);
		request.setAttribute("fourRoomHouseTypes", fourRoomHouseTypes);
		request.setAttribute("fiveRoomHouseTypes", fiveRoomHouseTypes);
		request.setAttribute("overFiveRoomHouseTypes", overFiveRoomHouseTypes);
		
		return "ui/realEstateProject/view";
	}

	@RequestMapping(value = "/mi/realEstateProject/view/{id}", method = { RequestMethod.GET })
	public String toMiView(@PathVariable String id) {
		return "mi/realEstateProject/view";
	}

	@RequestMapping(value = { "/mi/realEstateProject/view/json/{id}",
			"/mi/realEstateProject/add/view/json/{id}", "/mi/realEstateProject/update/view/json/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiViewJson(HttpServletRequest request,
			@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			RealEstateProjectModel realEstateProject = realEstateProjectService.get(id);
			if (realEstateProject != null) {
//				PermissionQueryModel pqm = new PermissionQueryModel();
//				pqm.setTargetPage(PageUtil.BEGIN_PAGE);
//				pqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
//				pqm.setRealEstateProjectId(id);
//				List<PermissionModel> permissions = permissionService
//						.strictList(pqm);
				resultMap.put("realEstateProject", realEstateProject);
//				resultMap.put("relations", permissions);
			}
		} catch (Exception e) {

			resultMap.put("realEstateProject", null);
			LOG.error(LoginUtil.SEARCH_DISPLAY + permissionObjectName
					+ "详细信息出错！", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/realEstateProject/add", method = { RequestMethod.GET })
	public String toMiAdd() {
		return "mi/realEstateProject/add";
	}

	@RequestMapping(value = "/mi/realEstateProject/update/{id}", method = { RequestMethod.GET })
	public String toMiUpdate(@PathVariable String id) {
		return "mi/realEstateProject/update";
	}

	@RequestMapping(value = { "/mi/realEstateProject/update/json", "/mi/realEstateProject/add/json" }, method = { RequestMethod.POST })
	@ResponseBody
	public Object miSaveOrUpdate(HttpServletRequest request, RealEstateProjectModel realEstateProject,
			String newPermissionIds) {
		return saveOrUpdateUser(request, realEstateProject, newPermissionIds);
	}

	private Object saveOrUpdateUser(HttpServletRequest request, RealEstateProjectModel realEstateProject,
			String newPermissionIds) {
		Map<String, Object> resultMap = null;
		String actionName;
		if (StringUtils.isBlank(realEstateProject.getId())) {
			actionName = LoginUtil.ADD_DISPLAY;
			realEstateProject.setCreaterId(LoginUtil.getCurrentUserId(request));
		} else {
			actionName = LoginUtil.UPDATE_DISPLAY;
		}
		realEstateProject.setEditorId(LoginUtil.getCurrentUserId(request));

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
//				realEstateProject.setPermissions(permissions);
//			}
			realEstateProjectService.saveOrUpdate(realEstateProject);
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

	@RequestMapping(value = "/mi/realEstateProject/delete/batch/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miBatchDelete(HttpServletRequest request, String realEstateProjectIds) {
		String actionName = LoginUtil.DELETE_DISPLAY;
		if (StringUtils.isNotBlank(realEstateProjectIds)) {
			String[] rids = realEstateProjectIds.split(Constants.MI_IDS_SPLIT_STRING);
			if (rids.length > 0) {
				List<String> idList = Arrays.asList(rids);
				realEstateProjectService.deleteBatch(idList);
				return ResultMapUtil.getResultMap(actionName
						+ permissionObjectName + "成功！");
			}
		}
		return ResultMapUtil.getResultMap(actionName + "对象不能为空！", false);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

}
