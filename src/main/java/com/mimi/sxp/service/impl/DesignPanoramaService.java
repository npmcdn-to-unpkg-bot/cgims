package com.mimi.sxp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mimi.sxp.dao.IBaseDao;
import com.mimi.sxp.dao.IDesignPanoramaDao;
import com.mimi.sxp.dao.IHouseTypeDao;
import com.mimi.sxp.dao.IProductDao;
import com.mimi.sxp.model.DesignPanoramaModel;
import com.mimi.sxp.model.HouseTypeModel;
import com.mimi.sxp.model.ProductModel;
import com.mimi.sxp.model.query.DesignPanoramaQueryModel;
import com.mimi.sxp.service.IDesignPanoramaService;

@Service
public class DesignPanoramaService extends
		BaseService<DesignPanoramaModel, String, DesignPanoramaQueryModel> implements IDesignPanoramaService {

	private static final Logger LOG = LoggerFactory
			.getLogger(DesignPanoramaService.class);

	private IDesignPanoramaDao designPanoramaDao;
	
	@Resource
	private IHouseTypeDao houseTypeDao;
	
	@Resource
	private IProductDao productDao;

	@Resource
	@Override
	public void setBaseDao(IBaseDao<DesignPanoramaModel, String, DesignPanoramaQueryModel> designPanoramaDao) {
		this.baseDao = designPanoramaDao;
		this.designPanoramaDao = (IDesignPanoramaDao) designPanoramaDao;
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
				DesignPanoramaModel dp = new DesignPanoramaModel();
				dp.setName("效果全景"+i);
				dp.setPriority(RandomUtils.nextInt(100));
				dp.setPreViewUrl("http://img.uniqueid.cn/applicationData/test/upload/images/2015/10/16/16/98d09c90-f224-4ef8-ba79-956c33307e91.jpg");
				if(i%2==0){
					dp.setOutLinkUrl("http://data.uniqueid.cn/pano/1/content");
				}
				dp.setContentUrl("http://data.uniqueid.cn/diy/13/content");
				dp.setHouseType(ht);
				
				List<ProductModel> tempProducts = new ArrayList<ProductModel>();
				for(ProductModel product:products){
					if(RandomUtils.nextBoolean()){
						tempProducts.add(product);
					}
					if(tempProducts.size()>RandomUtils.nextInt(100)){
						break;
					}
				}
				dp.setProducts(tempProducts);
				designPanoramaDao.save(dp);
			}
		}
	}
}