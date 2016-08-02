package com.mimi.cgims.dao;

import com.mimi.cgims.model.PermissionModel;

import java.util.List;

public interface IPermissionDao extends
        IBaseDao<PermissionModel, String> {
    List<PermissionModel> list(String roleId,String searchKeyword, int targetPage, int pageSize);
    int count(String roleId,String searchKeyword);

    List<PermissionModel> list(String userId);

//    List<PermissionModel> listByRoleId(String roleId, int targetPage, int pageSize);
}
