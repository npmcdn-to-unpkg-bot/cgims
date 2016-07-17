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
import com.mimi.sxp.model.PermissionModel;
import com.mimi.sxp.model.RoleModel;
import com.mimi.sxp.model.query.PermissionQueryModel;
import com.mimi.sxp.model.query.RoleQueryModel;
import com.mimi.sxp.service.IPermissionService;
import com.mimi.sxp.service.IRoleService;
import com.mimi.sxp.service.IUserService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.ResultMapUtil;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.support.editor.DateEditor;

@Controller
public class RoleController {
	private static final Logger LOG = LoggerFactory
			.getLogger(RoleController.class);

	@Resource
	private IUserService userService;

	@Resource
	private IRoleService roleService;

	@Resource
	private IPermissionService permissionService;

	private String permissionObjectName = LoginUtil.PERMISSION_URL_ROLE_DISPLAY;

	// @RequestMapping(value = "/mi/role/search/-{searchKeyword}-", method = {
	// RequestMethod.GET })
	// public String toMiSearch(HttpServletRequest request,
	// @PathVariable String searchKeyword) {
	// RoleQueryModel rqm = new RoleQueryModel();
	// rqm.setSearchKeyword(searchKeyword);
	// rqm.setTargetPage(PageUtil.BEGIN_PAGE);
	// rqm.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
	// rqm.setExcludedAdmin(true);
	// CommonPageObject<RoleModel> roles = roleService.strictQuery(rqm);
	// request.setAttribute("rolePage", roles);
	// return "mi/role/search";
	// }

	@RequestMapping(value = "/mi/role/search", method = { RequestMethod.GET })
	public String toMiSearch() {
		return "mi/role/search";
	}

	@RequestMapping(value = { "/mi/role/search/page/json/{curPage}",
			"/mi/user/view/role/search/page/json/{curPage}",
			"/mi/user/add/role/search/page/json/{curPage}",
			"/mi/user/update/role/search/page/json/{curPage}",
			"/mi/self/update/role/search/page/json/{curPage}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiSearchPageJson(HttpServletRequest request,
			@PathVariable Integer curPage, Integer pageSize, String name,
			Boolean excludedAdmin) {
		RoleQueryModel rqm = new RoleQueryModel();
		rqm.setTargetPage(curPage);
		rqm.setPageSize(pageSize);
		rqm.setSearchKeyword(name);
		if(excludedAdmin!=null){
			rqm.setExcludedAdmin(excludedAdmin);
		}
		CommonPageObject<RoleModel> roles = roleService.strictQuery(rqm);
		return PageUtil.convert2ResultMap(roles);
	}

	@RequestMapping(value = "/mi/role/view/{id}", method = { RequestMethod.GET })
	public String toMiView(@PathVariable String id) {
		return "mi/role/view";
	}

	@RequestMapping(value = { "/mi/role/view/json/{id}",
			"/mi/role/add/view/json/{id}", "/mi/role/update/view/json/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiViewJson(HttpServletRequest request,
			@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			RoleModel role = roleService.get(id);
			if (role != null) {
				PermissionQueryModel pqm = new PermissionQueryModel();
				pqm.setTargetPage(PageUtil.BEGIN_PAGE);
				pqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
				pqm.setRoleId(id);
				List<PermissionModel> permissions = permissionService
						.strictList(pqm);
				resultMap.put("role", role);
				resultMap.put("relations", permissions);
			}
		} catch (Exception e) {

			resultMap.put("user", null);
			resultMap.put("relationroles", null);
			LOG.error(LoginUtil.SEARCH_DISPLAY + permissionObjectName
					+ "详细信息出错！", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/role/add", method = { RequestMethod.GET })
	public String toMiAdd() {
		return "mi/role/add";
	}

	@RequestMapping(value = "/mi/role/update/{id}", method = { RequestMethod.GET })
	public String toMiUpdate(@PathVariable String id) {
		return "mi/role/update";
	}

	@RequestMapping(value = { "/mi/role/update/json", "/mi/role/add/json" }, method = { RequestMethod.POST })
	@ResponseBody
	public Object miSaveOrUpdate(HttpServletRequest request, RoleModel role,
			String newPermissionIds) {
		return saveOrUpdateUser(request, role, newPermissionIds);
	}

	private Object saveOrUpdateUser(HttpServletRequest request, RoleModel role,
			String newPermissionIds) {
		Map<String, Object> resultMap = null;
		String actionName;
		if (StringUtils.isBlank(role.getId())) {
			actionName = LoginUtil.ADD_DISPLAY;
			role.setCreaterId(LoginUtil.getCurrentUserId(request));
		} else {
			actionName = LoginUtil.UPDATE_DISPLAY;
		}
		role.setEditorId(LoginUtil.getCurrentUserId(request));

		try {
			if (StringUtils.isNotBlank(newPermissionIds)) {
				List<PermissionModel> permissions = new ArrayList<PermissionModel>();
				String[] pids = newPermissionIds
						.split(Constants.MI_IDS_SPLIT_STRING);
				for (int i = 0; i < pids.length; i++) {
					PermissionModel permission = new PermissionModel();
					permission.setId(pids[i]);
					permissions.add(permission);
				}
				role.setPermissions(permissions);
			}
			roleService.saveOrUpdate(role);
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

	@RequestMapping(value = "/mi/role/delete/batch/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miBatchDelete(HttpServletRequest request, String roleIds) {
		String actionName = LoginUtil.DELETE_DISPLAY;
		if (StringUtils.isNotBlank(roleIds)) {
			String[] rids = roleIds.split(Constants.MI_IDS_SPLIT_STRING);
			if (rids.length > 0) {
				List<String> idList = Arrays.asList(rids);
				roleService.deleteBatch(idList);
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
