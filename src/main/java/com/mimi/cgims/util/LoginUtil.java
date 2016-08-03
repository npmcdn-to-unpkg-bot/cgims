package com.mimi.cgims.util;

import com.mimi.cgims.Constants;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.model.WorkmanModel;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class LoginUtil {

    public static final String COOKIE_NAME_USER_ID = "userId";
    public static final String COOKIE_NAME_LOGIN_NAME = "loginName";
    public static final String COOKIE_NAME_USER_NAME = "userName";
    public static final String COOKIE_NAME_PERMISSION_CODES = "permissionCodes";
    public static final String COOKIE_NAME_SLAVE_NAMES = "slaveNames";
    public static final String COOKIE_NAME_SLAVE_IDS = "slaveIds";
    public static final String[] COOKIE_USER_LIST = {COOKIE_NAME_USER_ID,COOKIE_NAME_LOGIN_NAME,COOKIE_NAME_USER_NAME,COOKIE_NAME_PERMISSION_CODES,COOKIE_NAME_SLAVE_IDS,COOKIE_NAME_SLAVE_NAMES};

    public static final String COOKIE_NAME_WORKMAN_LOGIN_ID = "workmanLoginId";


    public static void userLogin(HttpServletRequest request, UserModel user, String permissionCodes, String slaveIds, String slaveNames) {
        request.getSession().setAttribute(COOKIE_NAME_USER_ID, user.getId());
        request.getSession().setAttribute(COOKIE_NAME_USER_NAME, user.getName());
        request.getSession().setAttribute(COOKIE_NAME_LOGIN_NAME, user.getLoginName());
        request.getSession().setAttribute(COOKIE_NAME_PERMISSION_CODES, permissionCodes);
        request.getSession().setAttribute(COOKIE_NAME_SLAVE_IDS, slaveIds);
        request.getSession().setAttribute(COOKIE_NAME_SLAVE_NAMES, slaveNames);
    }

    public static void userLogout(HttpServletRequest request) {
        for(String name:COOKIE_USER_LIST){
            request.getSession().removeAttribute(name);
        }
//        request.getSession().removeAttribute(COOKIE_NAME_USER_ID);
//        request.getSession().removeAttribute(COOKIE_NAME_USER_NAME);
//        request.getSession().removeAttribute(COOKIE_NAME_LOGIN_NAME);
//        request.getSession().removeAttribute(COOKIE_NAME_PERMISSION_CODES);
//        request.getSession().removeAttribute(COOKIE_NAME_SLAVE_IDS);
//        request.getSession().removeAttribute(COOKIE_NAME_SLAVE_NAMES);
    }

    public static Map<String,String> getUserLoginMsg(HttpServletRequest request) {
        Map<String,String> map = new HashMap<>();
        for(String name:COOKIE_USER_LIST){
            String value = (String) request.getSession().getAttribute(name);
            if(StringUtils.isNotBlank(value)){
                map.put(name,value);
            }
        }
        if(map.get(COOKIE_NAME_USER_ID)==null){
            map = null;
        }
        return map;
    }

    public static void workmanLogin(HttpServletRequest request, WorkmanModel workman) {
        request.getSession().setAttribute(COOKIE_NAME_WORKMAN_LOGIN_ID, workman.getId());
    }

    public static void workmanLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(COOKIE_NAME_WORKMAN_LOGIN_ID);
    }

    public static boolean isWorkmanLogined(HttpServletRequest request) {
        return StringUtils.isNotBlank(getCurWorkmanId(request));
    }

    public static boolean isUserLogined(HttpServletRequest request) {
        return StringUtils.isNotBlank(getCurUserId(request));
    }

    public static String getCurWorkmanId(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(COOKIE_NAME_WORKMAN_LOGIN_ID);
    }

    public static String getCurUserId(HttpServletRequest request) {
        return (String) request.getSession()
                .getAttribute(COOKIE_NAME_USER_ID);
    }

    public static String getUserPermissionCodes(HttpServletRequest request) {
        return (String) request.getSession()
                .getAttribute(COOKIE_NAME_PERMISSION_CODES);
    }

    public static String getUserSlaveIds(HttpServletRequest request) {
        return (String) request.getSession()
                .getAttribute(COOKIE_NAME_SLAVE_IDS);
    }

    public static String buildPassword(String password) {
        return com.mimi.cgims.util.MD5Util.MD5(MD5Util.MD5(password + Constants.PROJECT_NAME));
    }

    public static boolean isAdmin(String loginName) {
        return Constants.USER_LOGIN_NAME_ADMIN.equals(loginName);
    }

    public static void initPhoneCaptcha(HttpServletRequest request, String phoneNum, String captchaText) {
        request.getSession().setAttribute(Constants.ACCESS_PHONE_NUM, phoneNum);
        request.getSession().setAttribute(Constants.ACCESS_PHONE_CAPTCHA,captchaText);
    }

    public static boolean checkPhoneCaptcha(HttpServletRequest request, String phoneNum, String captchaText) {
        return StringUtils.equals((String) request.getSession().getAttribute(Constants.ACCESS_PHONE_NUM), phoneNum)
                && StringUtils.equals((String) request.getSession().getAttribute(Constants.ACCESS_PHONE_CAPTCHA), captchaText);
    }
}
