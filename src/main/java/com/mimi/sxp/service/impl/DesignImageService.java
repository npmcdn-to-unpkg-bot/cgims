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
import com.mimi.sxp.dao.hibernateImpl.DesignImageDao;
import com.mimi.sxp.dao.hibernateImpl.HouseTypeDao;
import com.mimi.sxp.model.DesignImageModel;
import com.mimi.sxp.model.HouseTypeModel;
import com.mimi.sxp.model.ProductModel;
import com.mimi.sxp.model.query.DesignImageQueryModel;
import com.mimi.sxp.service.IDesignImageService;

@Service
public class DesignImageService extends
		BaseService<DesignImageModel, String, DesignImageQueryModel> implements IDesignImageService {

	private static final Logger LOG = LoggerFactory
			.getLogger(DesignImageService.class);

	private DesignImageDao designImageDao;

	@Resource
	private HouseTypeDao houseTypeDao;
	
	@Resource
	private IProductDao productDao;

	@Resource
	@Override
	public void setBaseDao(IBaseDao<DesignImageModel, String, DesignImageQueryModel> designImageDao) {
		this.baseDao = designImageDao;
		this.designImageDao = (DesignImageDao) designImageDao;
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
				DesignImageModel di = new DesignImageModel();
				di.setName("效果图片"+i);
				di.setPriority(RandomUtils.nextInt(100));
				di.setContentUrl("http://img.uniqueid.cn/applicationData/test/upload/images/2015/10/29/17/16451b79-02c5-4c12-98ce-765377e7c442.jpg");
				di.setOutLinkUrl("");
				di.setHouseType(ht);
				
				List<ProductModel> tempProducts = new ArrayList<ProductModel>();
				for(ProductModel product:products){
					if(RandomUtils.nextBoolean()){
						tempProducts.add(product);
					}
					if(tempProducts.size()>RandomUtils.nextInt(100)){
						break;
					}
				}
				di.setProducts(tempProducts);
				designImageDao.save(di);
			}
		}
	}
}