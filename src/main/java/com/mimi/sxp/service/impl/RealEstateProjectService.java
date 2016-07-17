package com.mimi.sxp.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mimi.sxp.dao.IBaseDao;
import com.mimi.sxp.dao.hibernateImpl.RealEstateProjectDao;
import com.mimi.sxp.model.RealEstateProjectModel;
import com.mimi.sxp.model.query.RealEstateProjectQueryModel;
import com.mimi.sxp.service.IRealEstateProjectService;

@Service
public class RealEstateProjectService extends
		BaseService<RealEstateProjectModel, String, RealEstateProjectQueryModel> implements IRealEstateProjectService {

	private static final Logger LOG = LoggerFactory
			.getLogger(RealEstateProjectService.class);

	private RealEstateProjectDao realEstateProjectDao;

	@Resource
	@Override
	public void setBaseDao(IBaseDao<RealEstateProjectModel, String, RealEstateProjectQueryModel> realEstateProjectDao) {
		this.baseDao = realEstateProjectDao;
		this.realEstateProjectDao = (RealEstateProjectDao) realEstateProjectDao;
	}

	@Override
	protected void initAction() {
		initTest();
	}
	
	private void initTest(){
		for(int i=0;i<10;i++){
			RealEstateProjectModel rep = new RealEstateProjectModel();
			rep.setName("楼盘"+i);
			rep.setPriority(RandomUtils.nextInt(100));
			realEstateProjectDao.save(rep);
		}
	}
}