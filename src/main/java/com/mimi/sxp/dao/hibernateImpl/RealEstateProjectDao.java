package com.mimi.sxp.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.mimi.sxp.dao.IRealEstateProjectDao;
import com.mimi.sxp.model.RealEstateProjectModel;
import com.mimi.sxp.model.query.RealEstateProjectQueryModel;

@Repository
public class RealEstateProjectDao extends
		BaseDao<RealEstateProjectModel, String, RealEstateProjectQueryModel>
		implements IRealEstateProjectDao {

}
