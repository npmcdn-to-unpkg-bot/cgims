package com.mimi.cgims.service;

import com.mimi.cgims.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IUserService extends IBaseService<UserModel, String> {
    UserModel getWithRoles(String id);

    Map<String,Object> list4Page(String searchKeyword, int targetPage, int pageSize);

    String computePwd(String password);

    String checkAdd(UserModel user);

    String checkUpdate(UserModel user);

    String login(HttpServletRequest request, String loginName, String password);

    void logout(HttpServletRequest request);
}
