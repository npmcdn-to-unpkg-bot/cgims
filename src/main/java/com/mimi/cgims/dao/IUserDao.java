package com.mimi.cgims.dao;

import com.mimi.cgims.model.UserModel;

import java.util.List;

public interface IUserDao extends
        IBaseDao<UserModel, String> {
    List<UserModel> list(String searchKeyword, int targetPage, int pageSize);

    int count(String searchKeyword);

    UserModel getByLoginName(String loginName);
}
