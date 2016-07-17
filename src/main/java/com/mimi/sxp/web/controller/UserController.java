package com.mimi.sxp.web.controller;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mimi.sxp.Constants;
import com.mimi.sxp.model.RoleModel;
import com.mimi.sxp.model.UserModel;
import com.mimi.sxp.model.query.RoleQueryModel;
import com.mimi.sxp.model.query.UserQueryModel;
import com.mimi.sxp.service.IAliyunOSSService;
import com.mimi.sxp.service.IRoleService;
import com.mimi.sxp.service.IUserService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.RSAUtil;
import com.mimi.sxp.util.ResultMapUtil;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.support.editor.DateEditor;

@Controller
public class UserController {
	private static final Logger LOG = LoggerFactory
			.getLogger(UserController.class);

	@Resource
	private IUserService userService;

	@Resource
	private IRoleService roleService;

	@Resource
	private IAliyunOSSService aossService;

	private String permissionObjectName = LoginUtil.PERMISSION_URL_USER_DISPLAY;

//	@RequestMapping(value = "/mi/user/search/-{searchKeyword}-", method = { RequestMethod.GET })
//	public String toMiSearch(HttpServletRequest request,
//			@PathVariable String searchKeyword) {
//		UserQueryModel uqm = new UserQueryModel();
//		uqm.setSearchKeyword(searchKeyword);
//		uqm.setTargetPage(PageUtil.BEGIN_PAGE);
//		uqm.setPageSize(PageUtil.DEFAULT_PAGE_SIZE);
//		uqm.setExcludedId(LoginUtil.getCurrentUserId(request));
//		uqm.setExcludedAdmin(true);
//		CommonPageObject<UserModel> users = userService.strictQuery(uqm);
//		request.setAttribute("userPage", users);
//		return "mi/user/search";
//	}

	@RequestMapping(value = "/mi/user/search", method = { RequestMethod.GET })
	public String toMiSearch() {
		return "mi/user/search";
	}

	@RequestMapping(value = "/mi/user/search/page/json/{curPage}", method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiSearchPageJson(HttpServletRequest request,
			@PathVariable Integer curPage, Integer pageSize, String name) {
		UserQueryModel uqm = new UserQueryModel();
		uqm.setTargetPage(curPage);
		uqm.setPageSize(pageSize);
		uqm.setSearchKeyword(name);
		uqm.setExcludedId(LoginUtil.getCurrentUserId(request));
		uqm.setExcludedAdmin(true);
		CommonPageObject<UserModel> users = userService.strictQuery(uqm);
		for (UserModel user : users.getItems()) {
			user.setRoles(null);
		}
		return PageUtil.convert2ResultMap(users);
	}

	@RequestMapping(value = "/mi/user/view/{id}", method = { RequestMethod.GET })
	public String toMiView(@PathVariable String id) {
		return "mi/user/view";
	}

	@RequestMapping(value = { "/mi/user/view/json/{id}",
			"/mi/self/update/view/json/{id}", "/mi/user/update/view/json/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiViewJson(HttpServletRequest request,
			@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			UserModel user = (UserModel) userService.get(id);
			if (user != null) {
				user.setHeadImgUrl(aossService.addImgParams(user.getHeadImgUrl(), Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG));
				resultMap.put("user", user);
				RoleQueryModel rqm = new RoleQueryModel();
				rqm.setTargetPage(PageUtil.BEGIN_PAGE);
				rqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
				rqm.setUserId(id);
				List<RoleModel> relationroles = roleService.strictList(rqm);
				resultMap.put("relationroles", relationroles);
			}
		} catch (Exception e) {

			resultMap.put("user", null);
			resultMap.put("relationroles", null);
			LOG.error(LoginUtil.SEARCH_DISPLAY + permissionObjectName
					+ "详细信息出错！", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/user/add", method = { RequestMethod.GET })
	public String toMiAdd(Model model) {
		setRSAParams(model);
		return "mi/user/add";
	}

	@RequestMapping(value = "/mi/user/add/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miAdd(HttpServletRequest request, UserModel user,
			String newRoleIds, String publicExponent, String modulus) {
		return saveOrUpdateUser(request, user, newRoleIds, publicExponent,
				modulus, true);
	}

	@RequestMapping(value = "/mi/user/update/{id}", method = { RequestMethod.GET })
	public String toMiUpdate(Model model, @PathVariable String id) {
		setRSAParams(model);
		return "mi/user/update";
	}

	@RequestMapping(value = { "/mi/user/update/json", "/mi/self/update/json" }, method = { RequestMethod.POST })
	@ResponseBody
	public Object miUpdate(HttpServletRequest request, UserModel user,
			String newRoleIds, String publicExponent, String modulus,
			Boolean resetPwd) {
		return saveOrUpdateUser(request, user, newRoleIds, publicExponent,
				modulus, resetPwd);
	}

	@RequestMapping(value = "/mi/user/update/batch/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miBatchUpdate(HttpServletRequest request, UserModel user,
			String userIds) {
		String actionName = LoginUtil.UPDATE_DISPLAY;
		if (user == null || user.getLocked() == null) {
			return ResultMapUtil.getResultMap(actionName + "内容不能为空！", false);
		}
		if (StringUtils.isNotBlank(userIds)) {
			String[] uids = userIds.split(Constants.MI_IDS_SPLIT_STRING);
			if (uids.length > 0) {
				userService.updateBatch(uids, user);
				return ResultMapUtil.getResultMap(actionName
						+ permissionObjectName + "成功！");
			}
		}
		return ResultMapUtil.getResultMap(actionName + "对象不能为空！", false);
	}

	private Map<String, Object> saveOrUpdateUser(HttpServletRequest request,
			UserModel user, String newRoleIds, String publicExponent,
			String modulus, Boolean resetPwd) {
		Map<String, Object> resultMap = null;
		String actionName;
		if (StringUtils.isBlank(user.getId())) {
			actionName = LoginUtil.ADD_DISPLAY;
			user.setCreaterId(LoginUtil.getCurrentUserId(request));
			user.setSalt(LoginUtil.getRandomSalt());
		} else {
			actionName = LoginUtil.UPDATE_DISPLAY;
		}

		if (resetPwd) {
			try {
				String password = LoginUtil.getFinalPassword(publicExponent,
						modulus, user.getPassword(), user.getSalt());
				user.setPassword(password);
			} catch (Exception e) {
				LOG.error(actionName + permissionObjectName + "密码解析出错！", e);
				return ResultMapUtil.getResultMap("密码解析出错，请稍后重试!", false);
			}
		}
		user.setHeadImgUrl(aossService.clearImgParams(user.getHeadImgUrl()));
		user.setEditorId(LoginUtil.getCurrentUserId(request));

		try {
			if (StringUtils.isNotBlank(newRoleIds)) {
				List<RoleModel> roles = new ArrayList<RoleModel>();
				String[] rids = newRoleIds.split(Constants.MI_IDS_SPLIT_STRING);
				for (int i = 0; i < rids.length; i++) {
					RoleModel role = new RoleModel();
					role.setId(rids[i]);
					roles.add(role);
				}
				user.setRoles(roles);
			}
			userService.saveOrUpdate(user);
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

	@RequestMapping(value = { "/mi/user/add/uploadHeadImg",
			"/mi/user/update/uploadHeadImg", "/mi/self/update/uploadHeadImg" }, method = { RequestMethod.POST })
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
			LOG.error("保存用户头像出错！", e);
			resultMap.put("success", false);
			resultMap.put("msg", "保存图片失败");
		}
		return resultMap;
	}

	@RequestMapping(value = "/mi/user/delete/json/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object miDelete(@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		userService.delete(id);
		resultMap.put("success", true);
		resultMap.put("msg", LoginUtil.DELETE_DISPLAY + permissionObjectName
				+ "成功！");
		return resultMap;
	}

	@RequestMapping(value = "/mi/user/delete/batch/json", method = { RequestMethod.POST })
	@ResponseBody
	public Object miBatchDelete(HttpServletRequest request, String userIds) {
		String actionName = LoginUtil.DELETE_DISPLAY;
		if (StringUtils.isNotBlank(userIds)) {
			String[] uids = userIds.split(Constants.MI_IDS_SPLIT_STRING);
			if (uids.length > 0) {
				List<String> idList = Arrays.asList(uids);
				userService.deleteBatch(idList);
				return ResultMapUtil.getResultMap(actionName
						+ permissionObjectName + "成功！");
			}
		}
		return ResultMapUtil.getResultMap(actionName + "对象不能为空！", false);
	}

	@RequestMapping(value = "/mi/self/update/{id}", method = { RequestMethod.GET })
	public String toMISelfUpdate(HttpServletRequest request, Model model,
			@PathVariable String id) {
		setRSAParams(model);
		setGeetestId(model);
		return "mi/user/self";
	}

	// 未登录跳转
	@RequestMapping(value = "/mi/login", method = { RequestMethod.GET })
	public String toMILogin(HttpServletRequest request, Model model) {
		setRSAParams(model);
		setGeetestId(model);
		return "mi/user/login";
	}

	// 登录失败跳转
	@RequestMapping(value = "/mi/login", method = { RequestMethod.POST })
	public String login(HttpServletRequest request, Model model,
			String loginName) {
		model.addAttribute("loginName", loginName);
		loginAction(request, model);
		setRSAParams(model);
		setGeetestId(model);
		return "mi/user/login";
	}

	private void loginAction(HttpServletRequest request, Model model) {
		String error = (String) request
				.getAttribute(LoginUtil.LOGIN_FAILURE_MESSAGE);
		model.addAttribute("error", error);
	}

	private void setRSAParams(Model model) {
		RSAPublicKey rpu = RSAUtil.getCurrentPublicKey();
		model.addAttribute("publicExponent",
				rpu.getPublicExponent().toString(16));
		model.addAttribute("modulus", rpu.getModulus().toString(16));
	}

	private void setGeetestId(Model model) {
		model.addAttribute("geetestId", Constants.GEETEST_ID);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

}
