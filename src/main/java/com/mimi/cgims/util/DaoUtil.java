package com.mimi.cgims.util;

import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.model.RoleModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.model.WorkmanModel;

import java.util.List;

public class DaoUtil {

    public static void cleanLazyDataUsers(List<UserModel> users) {
        if(users!=null && !users.isEmpty()){
            for (UserModel user : users) {
                cleanLazyData(user);
            }
        }
    }

    public static void cleanLazyDataOrders(List<OrderModel> orders) {
        if(orders!=null && !orders.isEmpty()){
            for(OrderModel order:orders){
                DaoUtil.cleanLazyData(order);
            }
        }
    }

    public static void cleanLazyDataRoles(List<RoleModel> roles) {
        if(roles!=null && !roles.isEmpty()){
            for (RoleModel role : roles) {
                DaoUtil.cleanLazyData(role);
            }
        }
    }

    public static void cleanLazyData(UserModel user) {
        if(user!=null){
            user.setRoles(null);
            user.setSlaves(null);
            user.setMasters(null);
            user.setOrders(null);
        }
    }

    public static void cleanLazyData(OrderModel order) {
        if(order!=null){
            cleanLazyData(order.getUser());
            cleanLazyData(order.getWorkman());
        }
    }

    public static void cleanLazyData(WorkmanModel workman){
        if(workman!=null){
        }
    }

    public static void cleanLazyData(RoleModel role){
        if(role!=null){
            role.setPermissions(null);
            role.setUsers(null);
        }
    }
}
