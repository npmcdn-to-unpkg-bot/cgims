package com.mimi.sxp.service;

import com.mimi.sxp.model.UserModel;
import com.mimi.sxp.model.query.UserQueryModel;

public interface IUserService extends
		IBaseService<UserModel, String, UserQueryModel> {

	public void updateBatch(String[] ids, UserModel user);
}
