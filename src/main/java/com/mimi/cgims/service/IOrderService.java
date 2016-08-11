package com.mimi.cgims.service;

import com.mimi.cgims.model.OrderModel;

public interface IOrderService extends IAutoNumService<OrderModel, String> {
    Object list4Page(String searchKeyword, String orderStatus, String serviceType, String userId,String workmanId, String beginTime, String endTime,  int targetPage, int pageSize);

    String checkAdd(OrderModel order);

    String checkUpdate(OrderModel order);

    String addAndRefresh(OrderModel order);

    void updateAndRefresh(OrderModel order,String oldWorkmanId);

    void deleteAndRefresh(String id);

    void batchAction(String ids, String action, String orderStatus);

    int analysisOrderCount(String creatorId, String serviceType, String beginTime, String endTime);

    int analysisIncome(String creatorId, String serviceType, String beginTime, String endTime);

    int analysisExpenditure(String creatorId, String serviceType, String beginTime, String endTime);

    int analysisProfit(String creatorId, String serviceType, String beginTime, String endTime);

    float analysisProfitMargin(String creatorId, String serviceType, String beginTime, String endTime);

    int analysisIncomeP(String creatorId, String serviceType, String beginTime, String endTime);

    int analysisProfitP(String creatorId, String serviceType, String beginTime, String endTime);
}
