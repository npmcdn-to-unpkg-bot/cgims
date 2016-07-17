package com.mimi.sxp.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.mimi.sxp.dao.IHouseTypeDao;
import com.mimi.sxp.model.HouseTypeModel;
import com.mimi.sxp.model.query.HouseTypeQueryModel;

@Repository
public class HouseTypeDao extends
		BaseDao<HouseTypeModel, String, HouseTypeQueryModel>
		implements IHouseTypeDao {

}
