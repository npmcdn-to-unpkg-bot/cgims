package com.mimi.sxp.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.mimi.sxp.dao.IShopDao;
import com.mimi.sxp.model.ShopModel;
import com.mimi.sxp.model.query.ShopQueryModel;

@Repository
public class ShopDao extends
		BaseDao<ShopModel, String, ShopQueryModel>
		implements IShopDao {

}
