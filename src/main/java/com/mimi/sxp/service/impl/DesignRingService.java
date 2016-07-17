package com.mimi.sxp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mimi.sxp.dao.IBaseDao;
import com.mimi.sxp.dao.IProductDao;
import com.mimi.sxp.dao.hibernateImpl.DesignRingDao;
import com.mimi.sxp.dao.hibernateImpl.HouseTypeDao;
import com.mimi.sxp.model.DesignRingModel;
import com.mimi.sxp.model.HouseTypeModel;
import com.mimi.sxp.model.ProductModel;
import com.mimi.sxp.model.query.DesignRingQueryModel;
import com.mimi.sxp.service.IDesignRingService;

@Service
public class DesignRingService extends
		BaseService<DesignRingModel, String, DesignRingQueryModel> implements IDesignRingService {

	private static final Logger LOG = LoggerFactory
			.getLogger(DesignRingService.class);

	private DesignRingDao designRingDao;

	@Resource
	private HouseTypeDao houseTypeDao;
	
	@Resource
	private IProductDao productDao;

	@Resource
	@Override
	public void setBaseDao(IBaseDao<DesignRingModel, String, DesignRingQueryModel> designRingDao) {
		this.baseDao = designRingDao;
		this.designRingDao = (DesignRingDao) designRingDao;
	}

	@Override
	protected void initAction() {
		
		initTest();
	}
	
	private void initTest(){
		List<HouseTypeModel> hts = houseTypeDao.listAll();
		List<ProductModel> products = productDao.listAll();
		for(HouseTypeModel ht:hts){
			for(int i=0;i<10;i++){
				DesignRingModel dr = new DesignRingModel();
				dr.setName("效果三维"+i);
				dr.setPriority(RandomUtils.nextInt(100));
				dr.setPreViewUrl("http://img.uniqueid.cn/applicationData/test/upload/images/2015/10/29/17/4b7c1a11-61fb-44cd-a4ac-ca635dac1934.jpg");
				if(i%2==0){
					dr.setOutLinkUrl("http://img.uniqueid.cn/images/birdeye/msh3/mshBig3/");
				}
				dr.setContentUrl("http://img.uniqueid.cn/images/birdeye/mjc_1/mjc_1/");
				dr.setHouseType(ht);
				
				List<ProductModel> tempProducts = new ArrayList<ProductModel>();
				for(ProductModel product:products){
					if(RandomUtils.nextBoolean()){
						tempProducts.add(product);
					}
					if(tempProducts.size()>RandomUtils.nextInt(100)){
						break;
					}
				}
				dr.setProducts(tempProducts);
				designRingDao.save(dr);
			}
		}
	}
}