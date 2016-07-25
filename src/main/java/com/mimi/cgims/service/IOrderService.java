package com.mimi.cgims.service;

import com.mimi.cgims.model.OrderModel;

public interface IOrderService extends IBaseService<OrderModel, String> {
    Object list4Page(String searchKeyword, String orderStatus, String serviceType, String userId, String beginTime, String endTime, String searchKeyword1, int targetPage, int pageSize);

    String checkAdd(OrderModel order);

    String checkUpdate(OrderModel order);

    Object addAndRefresh(OrderModel order);

    void updateAndRefresh(OrderModel order);

    void deleteAndRefresh(String id);

    void batchAction(String ids, String action, String orderStatus);
}
