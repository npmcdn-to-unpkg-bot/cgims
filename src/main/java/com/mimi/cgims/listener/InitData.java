package com.mimi.cgims.listener;

import com.mimi.cgims.service.IPermissionService;
import com.mimi.cgims.service.IRoleService;
import com.mimi.cgims.service.IUserService;
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
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getDisplayName()
                .equals("Root WebApplicationContext")) {
            init();
        }
    }

    private void init(){
        permissionService.initData();
        roleService.initData();
        userService.initData();
    }
}
