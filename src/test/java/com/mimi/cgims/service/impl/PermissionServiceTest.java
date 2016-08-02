package com.mimi.cgims.service.impl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.common.BaseJunit4Test;
import com.mimi.cgims.model.PermissionModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.util.LoginUtil;
import com.mimi.cgims.util.page.PageUtil;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PermissionServiceTest extends BaseJunit4Test {


    @Test
    public void listByUserId() throws Exception {
        List<UserModel> users = userDao.list();
//        for(UserModel user:users){
        for(int k = 0;k<100;k++){
            UserModel user = userDao.getByLoginName(Constants.USER_LOGIN_NAME_ADMIN);
            List<PermissionModel> list = permissionService.listByUserId(user.getId());
            assertFalse(list.isEmpty());
            for(int i=0;i<list.size();i++){
                for(int j=0;j<list.size();j++){
                    if(i==j){
                        continue;
                    }
                    assertNotEquals(list.get(i).getCode(),list.get(j).getCode());
                }
            }
        }
//        String userId = userDao.list().get(0).getId();
////        String roleId = roleDao.list().get(0).getId();
////        List<PermissionModel> list = permissionDao.list(roleId,null, PageUtil.BEGIN_PAGE,PageUtil.MAX_PAGE_SIZE);
//        List<PermissionModel> list = permissionService.listByUserId(userId);
//        for(PermissionModel permission:list){
//            System.out.println(permission.getName());
//        }
//        assertTrue(!list.isEmpty());
    }

}