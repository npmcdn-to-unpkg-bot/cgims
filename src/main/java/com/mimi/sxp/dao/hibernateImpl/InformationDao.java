package com.mimi.sxp.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.mimi.sxp.dao.IInformationDao;
import com.mimi.sxp.model.InformationModel;
import com.mimi.sxp.model.query.InformationQueryModel;

@Repository
public class InformationDao extends
		BaseDao<InformationModel, String, InformationQueryModel>
		implements IInformationDao {

}
