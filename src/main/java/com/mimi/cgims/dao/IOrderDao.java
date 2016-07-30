package com.mimi.cgims.dao;

import com.mimi.cgims.model.OrderModel;

import java.util.List;

public interface IOrderDao extends
        IBaseDao<OrderModel, String> {
    int count(String searchKeyword, String orderStatus, String serviceType, String userId,String workmanId,  String beginTime, String endTime);

    List<OrderModel> list(String searchKeyword, String orderStatus, String serviceType, String userId,String workmanId,  String beginTime, String endTime, int targetPage, int pageSize);

    int analysisOrderCount(String creatorId, String serviceType, String beginTime, String endTime);

    int analysisIncome(String creatorId, String serviceType, String beginTime, String endTime);

    int analysisExpenditure(String creatorId, String serviceType, String beginTime, String endTime);

    void cleanUserId(String userId);

    OrderModel getNewest(int year, int month, int day);
}
