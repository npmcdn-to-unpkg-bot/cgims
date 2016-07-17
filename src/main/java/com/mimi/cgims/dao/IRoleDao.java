package com.mimi.cgims.dao;

import com.mimi.cgims.model.RoleModel;

import java.util.List;

public interface IRoleDao extends
        IBaseDao<RoleModel, String> {
    List<RoleModel> list(String userId,String searchKeyword, int targetPage, int pageSize);

    int count(String userId,String searchKeyword);

    RoleModel getByName(String name);
}