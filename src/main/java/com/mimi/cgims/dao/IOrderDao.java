package com.mimi.cgims.dao;

import com.mimi.cgims.model.OrderModel;

import java.util.List;

public interface IOrderDao extends
        IBaseDao<OrderModel, String> {
    int count(String searchKeyword, String orderStatus, String serviceType, String userId, String beginTime, String endTime);

    List<OrderModel> list(String searchKeyword, String orderStatus, String serviceType, String userId, String beginTime, String endTime, int targetPage, int pageSize);
}
