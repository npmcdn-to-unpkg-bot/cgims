package com.mimi.cgims.listener;

import com.mimi.cgims.service.*;
import com.mimi.cgims.util.CityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class InitData implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    IPermissionService permissionService;
    @Resource
    IRoleService roleService;
    @Resource
    IUserService userService;
    @Resource
    IOrderService orderService;
    @Resource
    IWorkmanService workmanService;

    @Value("${test.init_data:false}")
    private boolean initTestData;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getDisplayName()
                .equals("Root WebApplicationContext")) {
            init();
        }
    }

    private void init(){
        CityUtil.init();
        permissionService.initData();
        roleService.initData();
        userService.initData();

        if(initTestData){
            initTestData();
        }
    }

    private void initTestData(){
        roleService.initTestData();
        userService.initTestData();
        workmanService.initTestData();
        orderService.initTestData();
    }
}
