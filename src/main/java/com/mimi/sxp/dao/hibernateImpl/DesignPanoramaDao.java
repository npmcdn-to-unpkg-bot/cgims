package com.mimi.sxp.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.mimi.sxp.dao.IDesignPanoramaDao;
import com.mimi.sxp.model.DesignPanoramaModel;
import com.mimi.sxp.model.query.DesignPanoramaQueryModel;

@Repository
public class DesignPanoramaDao extends
		BaseDao<DesignPanoramaModel, String, DesignPanoramaQueryModel>
		implements IDesignPanoramaDao {

}
