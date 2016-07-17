package com.mimi.sxp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mimi.sxp.Constants;
import com.mimi.sxp.dao.IBaseDao;
import com.mimi.sxp.dao.hibernateImpl.RoleDao;
import com.mimi.sxp.dao.hibernateImpl.UserDao;
import com.mimi.sxp.model.RoleModel;
import com.mimi.sxp.model.UserModel;
import com.mimi.sxp.model.query.RoleQueryModel;
import com.mimi.sxp.model.query.UserQueryModel;
import com.mimi.sxp.service.IUserService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.MD5Util;

@Service
public class UserService extends
		BaseService<UserModel, String, UserQueryModel> implements IUserService {

	private static final Logger LOG = LoggerFactory
			.getLogger(UserService.class);

	private UserDao userDao;

	@Resource
	private RoleDao roleDao;

	@Resource
	@Override
	public void setBaseDao(IBaseDao<UserModel, String, UserQueryModel> userDao) {
		this.baseDao = userDao;
		this.userDao = (UserDao) userDao;
	}

	@Override
	protected void initAction() {
		RoleQueryModel rqm = new RoleQueryModel();
		rqm.setSearchKeyword(Constants.ROLE_DEFAULT_ADMIN_NAME);
		List<RoleModel> roles = roleDao.strictQuery(rqm);
		UserModel user = new UserModel();
		String salt = LoginUtil.getRandomSalt();
		user.setName(Constants.USER_DEFAULT_ADMIN_NAME);
//		user.setPassword(MD5Util.encode(salt,MD5Util.MD5(Constants.USER_DEFAULT_ADMIN_PASSWORD)));
		user.setPassword(LoginUtil.getFinalPassword(MD5Util.MD5(Constants.USER_DEFAULT_ADMIN_PASSWORD),salt));
		user.setSalt(salt);
		user.setHeadImgUrl(Constants.MI_HEAD_IMG_DEFAULT_URL);
		user.setRoles(roles);
		user.setLocked(false);
		user.setCreaterId(LoginUtil.NULL_ID_DEFAULT);
		user.setEditorId(LoginUtil.NULL_ID_DEFAULT);
		userDao.save(user);
		
//		initTest();
	}
	
	private void initTest(){
		String[] names = { "test1", "test2", "test3", "test4" };
		String[] desc = { "hello,this is a cow", "儿童公园", "", "儿童饼干" };
		List<RoleModel> roles = roleDao.listAll(1, 10);
		for(int j=0;j<6;j++){
			for (int i = 0; i < names.length; i++) {
				String name = names[i]+"_"+j;
				UserModel user = new UserModel();
				user.setEmail(name + "@qq.com");
				user.setPassword("123123");
				user.setName(name);
				user.setDescription(desc[i]);
				user.setHeadImgUrl("http://img5.imgtn.bdimg.com/it/u=3580210867,3098509580&fm=21&gp=0.jpg");
				user.setLocked(false);
				user.setCreateDate(new Date());
				user.setUpdateDate(new Date());
				if (i == 0 || i == 2) {
					user.setRoles(roles);
				}
				userDao.save(user);
			}
		}
	}

	@Override
	public void updateBatch(String[] ids, UserModel user) {
		if(ids==null || ids.length==0){
			return;
		}
		UserQueryModel uqm = new UserQueryModel();
		uqm.setIncludedIds(ids);
		List<UserModel> users = baseDao.strictQuery(uqm);
		for(UserModel u:users){
			u.setLocked(user.getLocked());
			baseDao.update(u);
		}
	}
}