package com.mimi.sxp.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mimi.sxp.dao.IBaseDao;
import com.mimi.sxp.dao.hibernateImpl.ShopDao;
import com.mimi.sxp.model.ShopModel;
import com.mimi.sxp.model.query.ShopQueryModel;
import com.mimi.sxp.service.IShopService;

@Service
public class ShopService extends
		BaseService<ShopModel, String, ShopQueryModel> implements IShopService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ShopService.class);

	private ShopDao shopDao;

	@Resource
	@Override
	public void setBaseDao(IBaseDao<ShopModel, String, ShopQueryModel> shopDao) {
		this.baseDao = shopDao;
		this.shopDao = (ShopDao) shopDao;
	}

	@Override
	protected void initAction() {
		initTest();
	}
	
	private void initTest(){
		for(int i=0;i<25;i++){
			ShopModel shop = new ShopModel();
			shop.setName("商铺"+i);
			shop.setPriority(RandomUtils.nextInt(100));
			shop.setPreViewUrl("http://img.uniqueid.cn/applicationData/test/upload/images/2015/11/4/16/9da2031e-8331-46d9-a39e-52f7d19f38fc.jpg");
			shop.setIntroduction("介绍"+i);
			shopDao.save(shop);
		}
	}
}