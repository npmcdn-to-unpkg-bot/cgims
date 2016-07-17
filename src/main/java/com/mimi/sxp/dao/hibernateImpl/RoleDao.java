package com.mimi.sxp.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.mimi.sxp.dao.IRoleDao;
import com.mimi.sxp.model.RoleModel;
import com.mimi.sxp.model.query.RoleQueryModel;

@Repository("RoleDao")
public class RoleDao extends BaseDao<RoleModel, String,RoleQueryModel>
		implements IRoleDao {

}
