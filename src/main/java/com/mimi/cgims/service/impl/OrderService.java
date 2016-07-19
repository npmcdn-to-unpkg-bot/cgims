package com.mimi.cgims.service.impl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IBaseDao;
import com.mimi.cgims.dao.IOrderDao;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.service.IOrderService;
import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.util.page.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OrderService extends BaseService<OrderModel,String> implements IOrderService{

    private IOrderDao orderDao;

    @Resource
    @Override
    public void setBaseDao(IBaseDao<OrderModel, String> baseDao) {
        this.baseDao = baseDao;
        this.orderDao = (IOrderDao) baseDao;
    }

    @Override
    protected void initAction() {
    }
}
