package com.mimi.cgims.dao;

import com.mimi.cgims.model.WorkmanModel;

import java.util.List;

public interface IWorkmanDao extends
        IBaseDao<WorkmanModel, String> {
    int count(String searchKeyword, String province, String city, String area, String serviceType);

    List<WorkmanModel> list(String searchKeyword, String province, String city, String area, String serviceType, int targetPage, int pageSize);

    WorkmanModel getNewest(int year, int month, int day);

    WorkmanModel getByPhoneNum(String phoneNum);
}
