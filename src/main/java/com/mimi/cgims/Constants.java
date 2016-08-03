package com.mimi.cgims;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String PROJECT_NAME = "cgims";

    public static final String GEETEST_ID = "61658dd2f0dfc210aeb78192ea4fde05";
    public static final String GEETEST_KEY = "abceaa2fd512253fb743ba7e06780635";

    public static final String ACCESS_PHONE_NUM = "accessPhoneNum";
    public static final String ACCESS_PHONE_CAPTCHA = "accessPhoneCaptcha";

    public static final String SMOOTH_CAPTCHA_ERROR = "滑动验证码错误";


    public static final String MI_IDS_SPLIT_STRING = "/";


    public static final String SPLIT_STRING_IDS = ",";
    public static final String SPLIT_STRING_PARAMS = ",";
    public static final String SPLIT_STRING_KEYWORD = " ";

    /**
     * 权限枚举
     */
    public static final String PERMISSION_CODE_ROLE_MANAGER = "roleManager";
    public static final String PERMISSION_CODE_USER_MANAGER = "userManager";
    public static final String PERMISSION_CODE_ORDER_MANAGER = "orderManager";
    public static final String PERMISSION_CODE_ORDER_VIEW = "orderView";
    public static final String PERMISSION_CODE_USER_ORDER_MANAGER = "userOrderManager";
    public static final String PERMISSION_CODE_WORKMAN_MANAGER = "workmanManager";
    public static final String PERMISSION_CODE_ANALYSIS_MANAGER = "analysisManager";

    public static final String PERMISSION_NAME_ROLE_MANAGER = "角色管理";
    public static final String PERMISSION_NAME_USER_MANAGER = "用户管理";
    public static final String PERMISSION_NAME_ORDER_MANAGER = "订单管理";
    public static final String PERMISSION_NAME_ORDER_VIEW = "订单查看";
    public static final String PERMISSION_NAME_USER_ORDER_MANAGER = "个人订单管理";
    public static final String PERMISSION_NAME_WORKMAN_MANAGER = "师傅管理";
    public static final String PERMISSION_NAME_ANALYSIS_MANAGER = "统计分析";

    public static final String PERMISSION_DESCRIPTION_ROLE_MANAGER = "可以任意查询、添加、修改、删除角色";
    public static final String PERMISSION_DESCRIPTION_USER_MANAGER = "可以任意查询、添加、修改、删除用户";
    public static final String PERMISSION_DESCRIPTION_ORDER_MANAGER = "可以任意查询、添加、修改、删除全部订单";
    public static final String PERMISSION_DESCRIPTION_ORDER_VIEW = "可以查询全部订单";
    public static final String PERMISSION_DESCRIPTION_USER_ORDER_MANAGER = "可以添加订单，并对关联的用户所创建的订单进行查询、修改、删除";
    public static final String PERMISSION_DESCRIPTION_WORKMAN_MANAGER = "可以任意查询、添加、修改、删除师傅信息";
    public static final String PERMISSION_DESCRIPTION_ANALYSIS_MANAGER = "可以使用统计功能";

    private static final Map<String, String> permissionMap = new HashMap<String, String>() {
        {
            put(PERMISSION_CODE_ROLE_MANAGER, PERMISSION_NAME_ROLE_MANAGER);
            put(PERMISSION_CODE_USER_MANAGER, PERMISSION_NAME_USER_MANAGER);
            put(PERMISSION_CODE_ORDER_MANAGER, PERMISSION_NAME_ORDER_MANAGER);
            put(PERMISSION_CODE_ORDER_VIEW, PERMISSION_NAME_ORDER_VIEW);
            put(PERMISSION_CODE_USER_ORDER_MANAGER, PERMISSION_NAME_USER_ORDER_MANAGER);
            put(PERMISSION_CODE_WORKMAN_MANAGER, PERMISSION_NAME_WORKMAN_MANAGER);
            put(PERMISSION_CODE_ANALYSIS_MANAGER, PERMISSION_NAME_ANALYSIS_MANAGER);
        }
    };

    private static final Map<String, String> permissionDescMap = new HashMap<String, String>() {
        {
            put(PERMISSION_CODE_ROLE_MANAGER, PERMISSION_DESCRIPTION_ROLE_MANAGER);
            put(PERMISSION_CODE_USER_MANAGER, PERMISSION_DESCRIPTION_USER_MANAGER);
            put(PERMISSION_CODE_ORDER_MANAGER, PERMISSION_DESCRIPTION_ORDER_MANAGER);
            put(PERMISSION_CODE_ORDER_VIEW, PERMISSION_DESCRIPTION_ORDER_VIEW);
            put(PERMISSION_CODE_USER_ORDER_MANAGER, PERMISSION_DESCRIPTION_USER_ORDER_MANAGER);
            put(PERMISSION_CODE_WORKMAN_MANAGER, PERMISSION_DESCRIPTION_WORKMAN_MANAGER);
            put(PERMISSION_CODE_ANALYSIS_MANAGER, PERMISSION_DESCRIPTION_ANALYSIS_MANAGER);
        }
    };

    public static Map<String, String> getPermissionMap() {
        return Collections.unmodifiableMap(permissionMap);
    }

    public static Map<String, String> getPermissionDescMap() {
        return Collections.unmodifiableMap(permissionDescMap);
    }

    /**
     * 角色枚举
     */

    public static final String ROLE_NAME_ADMIN = "超级管理员";
    public static final String ROLE_NAME_CUSTOMER_SERVICE = "客服";
    public static final String ROLE_NAME_FINANCE = "财务";
    public static final String ROLE_NAME_MANAGER = "经理";

    public static final String ROLE_DESCRIPTION_ADMIN = "拥有最高权限";
    public static final String ROLE_DESCRIPTION_CUSTOMER_SERVICE = "管理个人订单及师傅信息，可查看所有订单";
    public static final String ROLE_DESCRIPTION_FINANCE = "管理所有订单及使用统计功能";
    public static final String ROLE_DESCRIPTION_MANAGER = "管理员工、所有订单、师傅信息、使用统计功能";

    public static final String[] ROLE_NAMES = {ROLE_NAME_ADMIN, ROLE_NAME_CUSTOMER_SERVICE, ROLE_NAME_FINANCE, ROLE_NAME_MANAGER};

    public static final String[] ROLE_DESCRIPTIONS = {ROLE_DESCRIPTION_ADMIN, ROLE_DESCRIPTION_CUSTOMER_SERVICE, ROLE_DESCRIPTION_FINANCE, ROLE_DESCRIPTION_MANAGER};

    public static final String[][] ROLE_PERMISSIONS = {{PERMISSION_CODE_ROLE_MANAGER, PERMISSION_CODE_USER_MANAGER, PERMISSION_CODE_ORDER_MANAGER, PERMISSION_CODE_WORKMAN_MANAGER, PERMISSION_CODE_ANALYSIS_MANAGER},
            {PERMISSION_CODE_ORDER_VIEW, PERMISSION_CODE_USER_ORDER_MANAGER, PERMISSION_CODE_WORKMAN_MANAGER},
            {PERMISSION_CODE_ORDER_MANAGER, PERMISSION_CODE_ANALYSIS_MANAGER},
            {PERMISSION_CODE_USER_MANAGER, PERMISSION_CODE_ORDER_MANAGER, PERMISSION_CODE_WORKMAN_MANAGER, PERMISSION_CODE_ANALYSIS_MANAGER}};

    public static final String USER_LOGIN_NAME_ADMIN = "admin";

    public static final String USER_NAME_ADMIN = "超级管理员";

    public static final String USER_PASSWORD = "123456";

    public static final String USER_DESCRIPTION_ADMIN = "拥有最高权限";

    public static final int RECEIVE_TYPE_ALIPAY = 0;
    public static final int RECEIVE_TYPE_BANK = 1;
    public static final int[] RECEIVE_TYPE_LIST = {RECEIVE_TYPE_ALIPAY,RECEIVE_TYPE_BANK};

    public static final String SERVICE_TYPE_WX = "维修";
    public static final String SERVICE_TYPE_PSAZ = "配送安装";
    public static final String[] SERVICE_TYPE_LIST = {SERVICE_TYPE_PSAZ,SERVICE_TYPE_WX};

    public static final String SERVICE_CATALOG_JJ = "家具类";
    public static final String SERVICE_CATALOG_DJ = "灯具类";
    public static final String SERVICE_CATALOG_WY = "卫浴类";
    public static final String SERVICE_CATALOG_JD = "家电类";
    public static final String SERVICE_CATALOG_MCWJ = "门窗五金类";

    public static final String[] SERVICE_CATALOG_JJ_ITEMS = {"民用家具", "办公家具", "定制家具（衣橱类）", "其他"};
    public static final String[] SERVICE_CATALOG_DJ_ITEMS = {"灯具安装", "灯具检测维修", "电路检测维修", "其他"};
    public static final String[] SERVICE_CATALOG_WY_ITEMS = {"马桶", "花洒", "淋浴屏", "浴室柜", "水龙头", "储物架", "水盆类", "其他"};
    public static final String[] SERVICE_CATALOG_JD_ITEMS = {"电视", "空调", "净水器", "热水器", "油烟机", "吊扇", "浴霸", "小家电", "其他"};
    public static final String[] SERVICE_CATALOG_MCWJ_ITEMS = {"晾衣架", "内门", "纱窗", "开锁换锁", "各种墙体挂板", "其他"};


    private static final Map<String, String[]> serviceItemsMap = new HashMap<String, String[]>() {
        {
            put(SERVICE_CATALOG_JJ, SERVICE_CATALOG_JJ_ITEMS);
            put(SERVICE_CATALOG_DJ, SERVICE_CATALOG_DJ_ITEMS);
            put(SERVICE_CATALOG_WY, SERVICE_CATALOG_WY_ITEMS);
            put(SERVICE_CATALOG_JD, SERVICE_CATALOG_JD_ITEMS);
            put(SERVICE_CATALOG_MCWJ, SERVICE_CATALOG_MCWJ_ITEMS);
        }
    };

    public static Map<String, String[]> getServiceItemsMap() {
        return Collections.unmodifiableMap(serviceItemsMap);
    }

    public static final String ORDER_STATUS_WSWF = "为收未付";
    public static final String ORDER_STATUS_WSXF = "未收需付";
    public static final String ORDER_STATUS_YSWF = "已收未付";
    public static final String ORDER_STATUS_YSXF = "已收需付";
    public static final String ORDER_STATUS_WSWW = "未收未完";
    public static final String ORDER_STATUS_WSWC = "未收完成";
    public static final String ORDER_STATUS_YSWW = "已收未完";
    public static final String ORDER_STATUS_YSWC = "已收完成";
    public static final String ORDER_STATUS_WSSB = "未收失败";
    public static final String ORDER_STATUS_YSSB = "已收失败";

    public static final String[] ORDER_STATUS_LIST = {ORDER_STATUS_WSWF, ORDER_STATUS_WSXF, ORDER_STATUS_YSWF, ORDER_STATUS_YSXF, ORDER_STATUS_WSWW, ORDER_STATUS_WSWC, ORDER_STATUS_YSWW, ORDER_STATUS_YSWC, ORDER_STATUS_WSSB, ORDER_STATUS_YSSB};
    public static final String[] PERSON_ORDER_STATUS_VIEW = {ORDER_STATUS_WSWF, ORDER_STATUS_YSWF};
    public static final String[] PERSON_ORDER_STATUS_OPERATION = {ORDER_STATUS_WSWF, ORDER_STATUS_WSXF, ORDER_STATUS_YSWF, ORDER_STATUS_YSXF, ORDER_STATUS_WSSB, ORDER_STATUS_YSSB};

    public static final String BATCH_ACTION_DELETE = "delete";
    public static final String BATCH_ACTION_UPDATE = "update";
    public static final String[] BATCH_ACTIONS = {BATCH_ACTION_DELETE,BATCH_ACTION_UPDATE};
}
