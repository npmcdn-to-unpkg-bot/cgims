package com.mimi.sxp.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mimi.sxp.model.PermissionModel;
import com.mimi.sxp.model.query.PermissionQueryModel;
import com.mimi.sxp.service.IPermissionService;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.support.editor.DateEditor;


@Controller
public class PermissionController {
	private static final Logger LOG = LoggerFactory
			.getLogger(PermissionController.class);

	@Resource
	private IPermissionService permissionService;

	@RequestMapping(value = { "/mi/role/update/permission/search/page/json/{curPage}",
			"/mi/role/add/permission/search/page/json/{curPage}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Object getMiSearchPageJson(HttpServletRequest request,
			@PathVariable Integer curPage, Integer pageSize, String name,String code) {
		PermissionQueryModel pqm = new PermissionQueryModel();
		pqm.setTargetPage(curPage);
		pqm.setPageSize(pageSize);
		pqm.setNameLike(name);
		pqm.setCodeLike(code);
		CommonPageObject<PermissionModel> permissions = permissionService.strictQuery(pqm);
		return PageUtil.convert2ResultMap(permissions);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

}
