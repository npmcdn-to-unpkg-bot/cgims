package com.mimi.sxp.util;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class LoginUtil {
	
	public static final String NULL_ID_DEFAULT = "-1";

	public static final String LOGIN_USER_ID = "loginedUserID";
	public static final String LOGIN_USER_NAME = "loginedUserName";
	public static final String LOGIN_USER_HEAD_IMG_URL = "loginedUserHeadImgUrl";
	public static final String LOGIN_USER_PERMISSIONS = "loginedUserPermissions";
	public static final String LOGIN_FAILURE_MESSAGE = "loginFailureMessage";

	public static final String PERMISSION_SPLIT = ":";
	public static final String URL_SPLIT = "/";
	
	public static final String MIHEAD = "mi";
	public static final String MIHEAD_DISPLAY = "后台";
	public static final String LOGIN_HEAD = "login";
	public static final String LOGOUT_HEAD = "logout";
	
	public static final String ADD = "add";
	public static final String UPDATE = "update";
	public static final String VIEW = "view";
	public static final String DELETE = "delete";
	public static final String SEARCH = "search";
	public static final String[] PERMISSION_ACTIONS = {ADD,UPDATE,VIEW,DELETE,SEARCH};
	public static final String ADD_DISPLAY = "添加";
	public static final String UPDATE_DISPLAY = "更新";
	public static final String VIEW_DISPLAY = "浏览";
	public static final String DELETE_DISPLAY = "删除";
	public static final String SEARCH_DISPLAY = "查询";
	public static final String[] PERMISSION_ACTIONS_DISPLAY = {ADD_DISPLAY,UPDATE_DISPLAY,VIEW_DISPLAY,DELETE_DISPLAY,SEARCH_DISPLAY};
	
	public static final String PERMISSION_URL_USER = "user";
	public static final String PERMISSION_URL_ROLE = "role";
	public static final String PERMISSION_URL_INFORMATION = "information";
	public static final String PERMISSION_URL_SHOP = "shop";
	public static final String PERMISSION_URL_REAL_ESTATE_PROJECT = "realEstateProject";
	
	public static final String PERMISSION_URL_USER_DISPLAY = "用户";
	public static final String PERMISSION_URL_ROLE_DISPLAY = "角色";
	public static final String PERMISSION_URL_INFORMATION_DISPLAY = "资讯";
	public static final String PERMISSION_URL_SHOP_DISPLAY = "商铺";
	public static final String PERMISSION_URL_REAL_ESTATE_PROJECT_DISPLAY = "楼盘";
	
	public static final String PERMISSION_URL_PRODUCT_DISPLAY = "产品";
	public static final String PERMISSION_URL_HOUSE_TYPE_DISPLAY = "户型";
	public static final String PERMISSION_URL_DESIGN_PANORAMA_DISPLAY = "效果全景";
	public static final String PERMISSION_URL_DESIGN_IMAGE_DISPLAY = "效果图片";
	public static final String PERMISSION_URL_DESIGN_RING_DISPLAY = "效果三维";
	
	public static final String[] PERMISSION_URLS = {PERMISSION_URL_USER,PERMISSION_URL_ROLE,PERMISSION_URL_REAL_ESTATE_PROJECT,PERMISSION_URL_INFORMATION,PERMISSION_URL_SHOP};
	public static final String[] PERMISSION_URLS_DISPLAY = {PERMISSION_URL_USER_DISPLAY,PERMISSION_URL_ROLE_DISPLAY,PERMISSION_URL_REAL_ESTATE_PROJECT_DISPLAY,PERMISSION_URL_INFORMATION_DISPLAY,PERMISSION_URL_SHOP_DISPLAY};
	
	public static void main(String args[]){
		for(int i=0;i<10;i++){
			System.out.println(getRandomSalt());
		}
	}
//	public static void loginAction(HttpServletRequest req){
//		String loginName;
//		String password;
//	    try {
//	    	loginName = req.getParameter("loginName");
//			password = RSAUtil.getResult(
//			    req.getParameter("publicExponent"),
//			    req.getParameter("modulus"),
//			    req.getParameter("password"));
//			user.setPassword(MD5Util.encode(salt,MD5Util.MD5(Constants.USER_DEFAULT_ADMIN_PASSWORD)));
//		} catch (Exception e) {
//			password = "";
//			LOG.error("密码解析出错",e);
//		}
//	}
	private static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }   

	public static String getRandomSalt(){
		return getRandomString(16);
	}

	public static String getCurrentUserId(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(LOGIN_USER_ID);
	}
	
	public static String getFinalPassword( String publicExponent, String modulus,String RSAPass,String salt) throws Exception{
		String password = RSAUtil.getResult(publicExponent, modulus,RSAPass);
		return MD5Util.encode(salt,password);
	}
	
	public static String getFinalPassword(String rawPass,String salt) {
		return MD5Util.encode(salt,rawPass);
	}

	public static void loginAction(HttpServletRequest request, String userId,
			String loginName,String headImgUrl, List<String> permissionCodes) {
		request.getSession().setAttribute(LOGIN_USER_ID, userId);
		request.getSession().setAttribute(LOGIN_USER_NAME, loginName);
		request.getSession().setAttribute(LOGIN_USER_HEAD_IMG_URL, headImgUrl);
		request.getSession().setAttribute(LOGIN_USER_PERMISSIONS,
				permissionCodes);
	}

	public static void logoutAction(HttpServletRequest request) {
		request.getSession().removeAttribute(LOGIN_USER_ID);
		request.getSession().removeAttribute(LOGIN_USER_NAME);
		request.getSession().removeAttribute(LOGIN_USER_HEAD_IMG_URL);
		request.getSession().removeAttribute(LOGIN_USER_PERMISSIONS);
	}
}
