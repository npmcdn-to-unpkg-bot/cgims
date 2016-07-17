package com.mimi.sxp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mimi.sxp.dao.IBaseDao;
import com.mimi.sxp.dao.hibernateImpl.HouseTypeDao;
import com.mimi.sxp.dao.hibernateImpl.RealEstateProjectDao;
import com.mimi.sxp.model.HouseTypeModel;
import com.mimi.sxp.model.RealEstateProjectModel;
import com.mimi.sxp.model.query.HouseTypeQueryModel;
import com.mimi.sxp.service.IHouseTypeService;

@Service
public class HouseTypeService extends
		BaseService<HouseTypeModel, String, HouseTypeQueryModel> implements IHouseTypeService {

	private static final Logger LOG = LoggerFactory
			.getLogger(HouseTypeService.class);

	private HouseTypeDao houseTypeDao;

	@Resource
	private RealEstateProjectDao realEstateProjectDao;

	@Resource
	@Override
	public void setBaseDao(IBaseDao<HouseTypeModel, String, HouseTypeQueryModel> houseTypeDao) {
		this.baseDao = houseTypeDao;
		this.houseTypeDao = (HouseTypeDao) houseTypeDao;
	}

	@Override
	protected void initAction() {
		
		initTest();
	}
	
	private void initTest(){
		List<RealEstateProjectModel> reps = realEstateProjectDao.listAll();
		for(RealEstateProjectModel rep:reps){
			int htNum = RandomUtils.nextInt(20);
			for(int i=0;i<htNum;i++){
				HouseTypeModel ht = new HouseTypeModel();
				ht.setName("户型"+i);
				ht.setPriority(RandomUtils.nextInt(100));
				ht.setInsideArea(RandomUtils.nextInt(200)+RandomUtils.nextFloat());
				ht.setGrossFloorArea(ht.getInsideArea()+RandomUtils.nextInt(50)+RandomUtils.nextFloat());
				ht.setRoomNum(RandomUtils.nextInt(5));
				ht.setHallNum(RandomUtils.nextInt(5));
				ht.setToiletNum(RandomUtils.nextInt(5));
				ht.setKitchenNum(RandomUtils.nextInt(5));
//				ht.setHousePlanUrl("http://img.uniqueid.cn/applicationData/test/upload/images/2015/10/16/16/98d09c90-f224-4ef8-ba79-956c33307e91.jpg");
				ht.setHousePlanUrl("http://img.uniqueid.cn/applicationData/test/upload/images/2015/10/23/16/1b9ea88e-5c1c-4d74-87b7-91ac9af3dab0.jpg");
				ht.setRealEstateProject(rep);
				houseTypeDao.save(ht);
			}
		}
	}
}