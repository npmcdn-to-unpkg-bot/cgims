package com.mimi.cgims.dao.hibernateImpl;

import com.mimi.cgims.dao.IOrderDao;
import com.mimi.cgims.model.OrderModel;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao extends BaseDao<OrderModel, String>
        implements IOrderDao {

}
