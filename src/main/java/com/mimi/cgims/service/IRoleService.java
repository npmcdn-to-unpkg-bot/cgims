package com.mimi.cgims.service;

import com.mimi.cgims.model.RoleModel;

import java.util.Map;

public interface IRoleService extends IBaseService<RoleModel,String> {
    Map<String,Object> list4Page(String searchKeyword, int targetPage, int pageSize);

    RoleModel getWithPermissions(String id);

    String checkAdd(RoleModel role);

    String checkUpdate(RoleModel role);

    RoleModel getByName(String name);
}
