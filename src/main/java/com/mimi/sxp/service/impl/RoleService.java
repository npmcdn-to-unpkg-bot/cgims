package com.mimi.sxp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mimi.sxp.Constants;
import com.mimi.sxp.dao.IBaseDao;
import com.mimi.sxp.dao.hibernateImpl.PermissionDao;
import com.mimi.sxp.dao.hibernateImpl.RoleDao;
import com.mimi.sxp.model.PermissionModel;
import com.mimi.sxp.model.RoleModel;
import com.mimi.sxp.model.query.RoleQueryModel;
import com.mimi.sxp.service.IRoleService;

//@Service("RoleService")
@Service
public class RoleService extends
		BaseService<RoleModel, String, RoleQueryModel> implements IRoleService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RoleService.class);

	private RoleDao roleDao;

	@Resource
	private PermissionDao permissionDao;

	@Resource
	@Override
	public void setBaseDao(IBaseDao<RoleModel, String, RoleQueryModel> roleDao) {
		this.baseDao = roleDao;
		this.roleDao = (RoleDao) roleDao;
	}

	@Override
	protected void initAction() {
		List<PermissionModel> permissions = permissionDao.listAll();
			RoleModel role = new RoleModel();
			role.setName(Constants.ROLE_DEFAULT_ADMIN_NAME);
				role.setPermissions(permissions);
			role.setCreateDate(new Date());
			role.setUpdateDate(new Date());
			roleDao.save(role);
			
//			initTest();
	}
	
	private void initTest(){
		String[] names = { "系统管理人员","采编", "注册用户", "日常运营人员" };
		List<PermissionModel> permissions = permissionDao.listAll();
		for(int k=0;k<6;k++){
			for (int i = 0; i < names.length; i++) {
				RoleModel role = new RoleModel();
				role.setName(names[i]+"_"+k);
				if(i==0){
					List<PermissionModel> tps = new ArrayList<PermissionModel>();
					for(int j=0;j<permissions.size();j++){
						if(permissions.get(j).getCode().contains(":user:")){
							tps.add(permissions.get(j));
						}
					}
					role.setPermissions(tps);
				}else{
					role.setPermissions(permissions);
				}
				role.setCreateDate(new Date());
				role.setUpdateDate(new Date());
				roleDao.save(role);
			}
		}
	}

}