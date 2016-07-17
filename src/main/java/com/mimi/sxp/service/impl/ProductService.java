package com.mimi.sxp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mimi.sxp.dao.IBaseDao;
import com.mimi.sxp.dao.hibernateImpl.ProductDao;
import com.mimi.sxp.dao.hibernateImpl.ShopDao;
import com.mimi.sxp.model.ProductModel;
import com.mimi.sxp.model.ShopModel;
import com.mimi.sxp.model.query.ProductQueryModel;
import com.mimi.sxp.service.IProductService;

@Service
public class ProductService extends
		BaseService<ProductModel, String, ProductQueryModel> implements IProductService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ProductService.class);

	private ProductDao productDao;

	@Resource
	private ShopDao shopDao;

	@Resource
	@Override
	public void setBaseDao(IBaseDao<ProductModel, String, ProductQueryModel> productDao) {
		this.baseDao = productDao;
		this.productDao = (ProductDao) productDao;
	}

	@Override
	protected void initAction() {
		
		initTest();
	}
	
	private void initTest(){
		List<ShopModel> shops = shopDao.listAll();
		for(ShopModel shop:shops){
			int count = RandomUtils.nextInt(20);
			for(int i=0;i<count;i++){
				ProductModel product = new ProductModel();
				product.setName("产品"+i);
				product.setPriority(RandomUtils.nextInt(100));
				product.setShop(shop);
				product.setPreViewUrl("http://img.uniqueid.cn/applicationData/test/upload/images/2015/11/4/14/4c2dd0c0-4452-413f-bc13-4b6f01ad2090.jpg");
				product.setIntroduction("产品介绍"+i);
				productDao.save(product);
			}
		}
	}
}