package com.mimi.sxp.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.mimi.sxp.dao.IDesignRingDao;
import com.mimi.sxp.model.DesignRingModel;
import com.mimi.sxp.model.query.DesignRingQueryModel;

@Repository
public class DesignRingDao extends
		BaseDao<DesignRingModel, String, DesignRingQueryModel>
		implements IDesignRingDao {

}
