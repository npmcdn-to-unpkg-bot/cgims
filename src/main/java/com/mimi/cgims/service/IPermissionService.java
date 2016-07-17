package com.mimi.cgims.service;

import com.mimi.cgims.model.PermissionModel;

import java.util.List;
import java.util.Map;

public interface IPermissionService extends IBaseService<PermissionModel, String> {

    List<PermissionModel> list(String searchKeyword, int targetPage, int pageSize);

    int count(String searchKeyword);

    Map<String,Object> list4Page(String searchKeyword, int targetPage, int pageSize);
}
