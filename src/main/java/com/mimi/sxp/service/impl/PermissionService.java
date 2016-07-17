package com.mimi.sxp.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mimi.sxp.dao.IBaseDao;
import com.mimi.sxp.dao.hibernateImpl.PermissionDao;
import com.mimi.sxp.model.PermissionModel;
import com.mimi.sxp.model.query.PermissionQueryModel;
import com.mimi.sxp.service.IPermissionService;
import com.mimi.sxp.util.LoginUtil;

@Service
public class PermissionService extends
		BaseService<PermissionModel, String, PermissionQueryModel> implements IPermissionService {

	private static final Logger LOG = LoggerFactory
			.getLogger(PermissionService.class);

	private PermissionDao permissionDao;

	@Resource
	@Override
	public void setBaseDao(IBaseDao<PermissionModel, String, PermissionQueryModel> permissionDao) {
		this.baseDao = permissionDao;
		this.permissionDao = (PermissionDao) permissionDao;
	}

	@Override
	protected void initAction() {
		String[] codes = {LoginUtil.MIHEAD};
		String[] codes2 = LoginUtil.PERMISSION_URLS;
		String[] codes3 = LoginUtil.PERMISSION_ACTIONS;
		String[] names = {LoginUtil.MIHEAD_DISPLAY};
		String[] names2 = LoginUtil.PERMISSION_URLS_DISPLAY;
		String[] names3 = LoginUtil.PERMISSION_ACTIONS_DISPLAY;
		for(int i=0;i<codes.length;i++){
			for(int j=0;j<codes2.length;j++){
				for(int k=0;k<codes3.length;k++){
					String code = codes[i]+LoginUtil.PERMISSION_SPLIT+codes2[j]+LoginUtil.PERMISSION_SPLIT+codes3[k];
					String name = names[i]+names2[j]+names3[k];
					PermissionModel permission = new PermissionModel();
					permission.setName(name);
					permission.setCode(code);
					permission.setCreaterId(LoginUtil.NULL_ID_DEFAULT);
					permission.setEditorId(LoginUtil.NULL_ID_DEFAULT);
					permissionDao.save(permission);
				}
			}
		}
	}

}