package com.mimi.cgims.listener;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.mimi.cgims.Config;
import com.mimi.cgims.service.*;
import com.mimi.cgims.util.CityUtil;
import com.mimi.cgims.web.captcha.GeetestLib;
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

    @Resource
    private Config config;

//    @Value("${test.init_data:false}")
//    private boolean initTestData;

    @Resource
    private CCPRestSmsSDK ytxAPI;

    @Resource
    private GeetestLib geetestLib;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getDisplayName()
                .equals("Root WebApplicationContext")) {
            init();
        }
    }

    private void init() {
        CityUtil.init();
        initGeetest();
        initYTX();
        permissionService.initData();
        roleService.initData();
        userService.initData();

        if (config.isInitTestData()) {
            initTestData();
        }
    }

    private void initTestData() {
        roleService.initTestData();
        userService.initTestData();
//        userService.initTestData2();
        workmanService.initTestData();
        orderService.initTestData();
    }

    private void initGeetest(){
        geetestLib.init(config.getGeetestKey());
    }

    private void initYTX() {
//		ytxAPI.init("sandboxapp.cloopen.com", "8883");
//		ytxAPI.setAccount("8a48b5514d32a2a8014d95626ee147c3",
//				"ce3126c41d6a4181b13fb9399db922b2");
//		ytxAPI.setAppId("8a48b5514d32a2a8014d9562b2ca47c6");

        ytxAPI.init(config.getYtxServerIp(), config.getYtxServerPort());
        ytxAPI.setAccount(config.getYtxAccountSid(), config.getYtxAuthToken());
        ytxAPI.setAppId(config.getYtxAppId());
//        ytxAPI.init("app.cloopen.com", "8883");
//        ytxAPI.setAccount("aaf98f894e999d73014eaab7b09911a1",
//                "8cdd05817a5f4863875cd9d59cd27dab");
//        ytxAPI.setAppId("aaf98f894e999d73014eaacb530f11bc");
    }
}
