package com.mimi.sxp.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.mimi.sxp.dao.IPermissionDao;
import com.mimi.sxp.model.PermissionModel;
import com.mimi.sxp.model.query.PermissionQueryModel;

@Repository
public class PermissionDao extends BaseDao<PermissionModel, String,PermissionQueryModel>
		implements IPermissionDao {

}
