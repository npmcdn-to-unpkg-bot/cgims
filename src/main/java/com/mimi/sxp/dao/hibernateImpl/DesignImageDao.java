package com.mimi.sxp.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.mimi.sxp.dao.IDesignImageDao;
import com.mimi.sxp.model.DesignImageModel;
import com.mimi.sxp.model.query.DesignImageQueryModel;

@Repository
public class DesignImageDao extends
		BaseDao<DesignImageModel, String, DesignImageQueryModel>
		implements IDesignImageDao {

}
