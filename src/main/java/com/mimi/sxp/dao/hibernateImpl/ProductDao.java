package com.mimi.sxp.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.mimi.sxp.dao.IProductDao;
import com.mimi.sxp.model.ProductModel;
import com.mimi.sxp.model.query.ProductQueryModel;

@Repository
public class ProductDao extends
		BaseDao<ProductModel, String, ProductQueryModel>
		implements IProductDao {

}
