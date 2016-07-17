package com.mimi.cgims;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String PROJECT_NAME = "cgims";

    public static final String ROLE_DEFAULT_ADMIN_NAME = "超级管理员";
    public static final String USER_DEFAULT_ADMIN_NAME = "admin";
    public static final String USER_DEFAULT_ADMIN_PASSWORD = "123456";
    public static final String MI_HEAD_IMG_DEFAULT_URL = "/assets/img/ui/logo.jpg";

    public static String COMMAND = "command";

    public static final String GEETEST_ID = "61658dd2f0dfc210aeb78192ea4fde05";
    public static final String GEETEST_KEY = "abceaa2fd512253fb743ba7e06780635";


    public static final String ALIYUN_OSS_IMAGE_BUCKET = "bluemimi4common";
    public static final String ALIYUN_OSS_ACCESS_KEY_ID = "8yCPOEyqRL87gGa3";
    public static final String ALIYUN_OSS_ACCESS_KEY_SECRET = "oTYhHWx8jTQVV03MiCucmLAI396PUS";
    public static final String ALIYUN_OSS_ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
    public static final String ALIYUN_OSS_CDN_URL = "http://img.uniqueid.cn/";

    //	public static final String ALIYUN_OSS_UPLOAD_HEAD_URL = "applicationData/test/upload";
    public static final String ALIYUN_OSS_UPLOAD_HEAD_URL = "applicationData/sxp/upload";
    public static final String ALIYUN_OSS_UPLOAD_IMAGE_URL = "/images";
    public static final String ALIYUN_OSS_UPLOAD_PANORAMA_URL = "/panoramaData";
    public static final String ALIYUN_OSS_UPLOAD_RING_URL = "/ringData";

//	public static final String ALIYUN_OSS_IMAGE_BUCKET = "h0758";
//	public static final String ALIYUN_OSS_ACCESS_KEY_ID = "2LRpdxG01eHaZbdN";
//	public static final String ALIYUN_OSS_ACCESS_KEY_SECRET = "jySfraVNJBpDFXwf4YwnCusdWOao9A";
//	public static final String ALIYUN_OSS_ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
//	public static final String ALIYUN_OSS_CDN_URL = "http://mimage.h0758.net/";


    public static final String[] ALIYUN_OSS_TEST_IMG_URLS = {
            "http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_00.jpg",
            "http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_02.jpg",
            "http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_03.jpg",
            "http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_04.jpg",
            "http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_05.jpg",
            "http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_06.jpg",
            "http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_07.jpg",
            "http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_08.jpg",
            "http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_09.jpg",
            "http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_13.jpg"};

    public static final String[] ALIYUN_OSS_IMAGE_ALLOWED_SUFFIX = {"jpg",
            "jpeg", "png", "webp", "bmp"};
    public static final String ALIYUN_OSS_IMAGE_PARAMS_SPLIT = "@";
    public static final String ALIYUN_OSS_IMAGE_PARAMS_AND = "|";
    public static final String ALIYUN_OSS_IMAGE_WATER_MARK = "watermark=2&text=6ZqP5b-D6YWN&type=ZmFuZ3poZW5na2FpdGk&size=40&color=I2ZmZmZmZg&p=9&y=10&x=10&t=40";
    public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_RING_SMALL_IMG = "1e_1280w_720h_1c_0i_1o_90Q_1x.{suffix}" + ALIYUN_OSS_IMAGE_PARAMS_AND + ALIYUN_OSS_IMAGE_WATER_MARK;
    public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_RING_IMG = "1e_1920w_1080h_1c_0i_1o_90Q_1x.{suffix}" + ALIYUN_OSS_IMAGE_PARAMS_AND + ALIYUN_OSS_IMAGE_WATER_MARK;
    public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_PHOTO_SMALL_IMG = "0e_100w_100h_0c_0i_1o_90Q_1x.{suffix}";
    public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_PHOTO_IMG = "0e_800w_800h_1c_0i_0o_90Q_1x.{suffix}" + ALIYUN_OSS_IMAGE_PARAMS_AND + ALIYUN_OSS_IMAGE_WATER_MARK;
    public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER = "1e_400w_300h_1c_0i_1o_90Q_1x.{suffix}" + ALIYUN_OSS_IMAGE_PARAMS_AND + ALIYUN_OSS_IMAGE_WATER_MARK;
    public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_AD = "1e_400w_100h_1c_0i_1o_90Q_1x.{suffix}";
    public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_AD_SMALL = "1e_100w_100h_1c_0i_1o_90Q_1x.{suffix}";
    public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG = "1e_100w_100h_1c_0i_1o_90Q_1x.{suffix}";
    public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG = "1e_120w_90h_1c_0i_1o_90Q_1x.{suffix}";
    public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_INFO_IMG = "0e_400w_400h_1c_0i_0o_90Q_1x.{suffix}" + ALIYUN_OSS_IMAGE_PARAMS_AND + ALIYUN_OSS_IMAGE_WATER_MARK;
    public static final String ALIYUN_OSS_IMAGE_STYLE_SPLIT = "@!";
    public static final String ALIYUN_OSS_IMAGE_STYLE_TYPE_HEAD_IMG = "head-img";

    public static final String IMAGE_URLS_SPLIT_STRING = ",";

    public static final String MI_IDS_SPLIT_STRING = "/";


    public static final String COOKIE_NAME_USER_ID = "userId";
    public static final String COOKIE_NAME_LOGIN_NAME = "loginName";
    public static final String COOKIE_NAME_USER_NAME = "userName";
    public static final String COOKIE_NAME_PERMISSION_CODES = "permissionCodes";

    public static final String SPLIT_STRING_IDS = ",";
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
}
