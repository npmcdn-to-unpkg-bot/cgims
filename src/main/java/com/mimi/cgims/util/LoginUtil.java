package com.mimi.cgims.util;

import com.mimi.cgims.Constants;
import com.mimi.cgims.model.UserModel;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class LoginUtil {

    public static final String COOKIE_NAME_USER_ID = "userId";
    public static final String COOKIE_NAME_LOGIN_NAME = "loginName";
    public static final String COOKIE_NAME_USER_NAME = "userName";
    public static final String COOKIE_NAME_PERMISSION_CODES = "permissionCodes";
    public static final String COOKIE_NAME_SLAVE_NAMES = "slaveNames";
    public static final String COOKIE_NAME_SLAVE_IDS = "slaveIds";

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
        request.getSession().removeAttribute(COOKIE_NAME_USER_ID);
        request.getSession().removeAttribute(COOKIE_NAME_USER_NAME);
        request.getSession().removeAttribute(COOKIE_NAME_LOGIN_NAME);
        request.getSession().removeAttribute(COOKIE_NAME_PERMISSION_CODES);
        request.getSession().removeAttribute(COOKIE_NAME_SLAVE_IDS);
        request.getSession().removeAttribute(COOKIE_NAME_SLAVE_NAMES);
    }

    public static void workmanLogin(HttpServletRequest request, String workmanId) {
        request.getSession().setAttribute(COOKIE_NAME_WORKMAN_LOGIN_ID, workmanId);
    }

    public static void workmanLogout(HttpServletRequest request, String workmanId) {
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
}
