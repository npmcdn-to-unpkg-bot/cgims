package com.mimi.cgims.service;

import com.mimi.cgims.model.WorkmanModel;

import java.util.Map;

public interface IWorkmanService extends IBaseService<WorkmanModel, String> {
    Map<String,Object> list4Page(String searchKeyword, String province, String city, String area, String serviceType, int targetPage, int pageSize);

    String checkAdd(WorkmanModel workman);

    String checkUpdate(WorkmanModel workman);
}
