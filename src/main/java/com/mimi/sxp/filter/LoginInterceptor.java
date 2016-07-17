package com.mimi.sxp.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mimi.sxp.model.PermissionModel;
import com.mimi.sxp.model.UserModel;
import com.mimi.sxp.model.query.PermissionQueryModel;
import com.mimi.sxp.model.query.UserQueryModel;
import com.mimi.sxp.service.IPermissionService;
import com.mimi.sxp.service.IUserService;
import com.mimi.sxp.util.LoginUtil;
import com.mimi.sxp.util.MD5Util;
import com.mimi.sxp.util.RSAUtil;
import com.mimi.sxp.util.pageUtil.PageUtil;
import com.mimi.sxp.web.captcha.GeetestLib;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = LoggerFactory
			.getLogger(LoginInterceptor.class);

    private GeetestLib geetest;
    
	@Resource
	private IUserService userService;

	@Resource
	private IPermissionService permissionService;

	String DEFAULT_ACCESS_CHAR = "0-9a-zA-Z\u4E00-\u9FA5:/_-";

	private String miHead = LoginUtil.MIHEAD;

	// private String[] needPermissionUrlHead = { "self", "mi" };

	private String loginUrlHead = LoginUtil.LOGIN_HEAD;

	private String logoutUrlHead = LoginUtil.LOGOUT_HEAD;

	public GeetestLib getGeetest() {
		return geetest;
	}

	public void setGeetest(GeetestLib geetest) {
		this.geetest = geetest;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IPermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(IPermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public String getMiHead() {
		return miHead;
	}

	public void setMiHead(String miHead) {
		this.miHead = miHead;
	}

	public String getLoginUrlHead() {
		return loginUrlHead;
	}

	public void setLoginUrlHead(String loginUrlHead) {
		this.loginUrlHead = loginUrlHead;
	}

	public String getLogoutUrlHead() {
		return logoutUrlHead;
	}

	public void setLogoutUrlHead(String logoutUrlHead) {
		this.logoutUrlHead = logoutUrlHead;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String sp = request.getServletPath();
		if (!isValidUrl(sp)) {
			response.sendError(404);
			return false;
		}
		request.setAttribute("uri", request.getRequestURI());
		if (isLoginUrl(sp)) {
			if (isAuthenticated(request)) {
//				request.getRequestDispatcher("/mi/index").forward(request,
//						response);
//				return false;
				return responseOut(request, response, "/mi/index", false, "已登录",true);
			} else {
				if (request.getMethod().equalsIgnoreCase("post")) {
					return loginAction(request, response);
				}
				return true;
			}
		}
		if (isLogoutUrl(sp)) {
			LoginUtil.logoutAction(request);
//			request.getRequestDispatcher("/mi/login")
//					.forward(request, response);
//			return false;
			return responseOut(request, response, "/mi/login", false, "已安全退出",true);
		}
		if(needAuthenticated(sp)){
			if (!isAuthenticated(request)) {
				if (isMIUrl(sp)) {
//					request.getRequestDispatcher("/error/unauthenticated")
//							.forward(request, response);
//					response.sendRedirect(request.getContextPath() + "/mi/login");
//					return false;
					return responseOut(request, response, "/mi/login", true, "请登录",false);
				} else {
//					request.getRequestDispatcher("/index").forward(request,
//							response);
//					return false;
					return responseOut(request, response, "/index", false, "请登录",false);
				}
			}else if (needAuthorized(sp) && !isAuthorized(request, sp)) {
//				request.getRequestDispatcher("/error/unauthorized")
//				.forward(request, response);
//				return false;
				return responseOut(request, response, "/error/unauthorized", false, "没有足够权限访问",false);
			}
		}
		return true;
	}
	
	private boolean responseOut(HttpServletRequest request,
			HttpServletResponse response,String responseUrl,boolean redirect,String responseMsg,boolean success) throws ServletException, IOException{
		if(isAjaxRequest(request)){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("success", success);
			resultMap.put("msg", responseMsg);
			responseOutWithJson(response, resultMap);
		}else{
			if(redirect){
				response.sendRedirect(request.getContextPath() + responseUrl);
			}else{
				request.getRequestDispatcher(responseUrl)
				.forward(request, response);
			}
		}
		return false;
	}
	/**
	 * 以JSON格式输出
	 * @param response
	 */
	protected void responseOutWithJson(HttpServletResponse response,
			Object responseObject) {
		//将实体对象转换为JSON Object转换
		JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(responseJSONObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	private boolean loginAction(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		if(!doCaptchaValidate(request)){
			request.setAttribute(LoginUtil.LOGIN_FAILURE_MESSAGE, "验证码错误");
			return true;
		}
		String loginName;
		String password;
		String loginFailure = null;
		try {
			loginName = request.getParameter("loginName");
			password = RSAUtil.getResult(
					request.getParameter("publicExponent"),
					request.getParameter("modulus"),
					request.getParameter("password"));
		} catch (Exception e) {
			loginName = "";
			password = "";
			loginFailure = "密码解析出错";
			LOG.error("密码解析出错", e);
		}
		if (StringUtils.isBlank(loginName)
				|| StringUtils.isBlank(password)) {
			loginFailure = "账号密码不能为空";
			request.setAttribute(LoginUtil.LOGIN_FAILURE_MESSAGE, loginFailure);
			return true;
		}
		if (StringUtils.isNotBlank(loginFailure)) {
			request.setAttribute(LoginUtil.LOGIN_FAILURE_MESSAGE, loginFailure);
			return true;
		}
		UserQueryModel uqm = new UserQueryModel();
		uqm.setLoginName(loginName);
		List<UserModel> users = userService.strictList(uqm);
		if (users == null || users.isEmpty()) {
			request.setAttribute(LoginUtil.LOGIN_FAILURE_MESSAGE, "登录名不存在！");
			return true;
		} else if(users.get(0).getLocked()==true){
			request.setAttribute(LoginUtil.LOGIN_FAILURE_MESSAGE, "用户已被锁定！");
			return true;
		}else if (!MD5Util.isPasswordValid(users.get(0).getSalt(),
				users.get(0).getPassword(), password)) {
			request.setAttribute(LoginUtil.LOGIN_FAILURE_MESSAGE, "密码错误！");
			return true;
		} else {
			PermissionQueryModel pqm = new PermissionQueryModel();
			pqm.setTargetPage(PageUtil.BEGIN_PAGE);
			pqm.setPageSize(PageUtil.MAX_PAGE_SIZE);
			pqm.setUserId(users.get(0).getId());
			List<PermissionModel> permissions = permissionService
					.strictList(pqm);
			List<String> codes = new ArrayList<String>();
			for (PermissionModel p : permissions) {
				codes.add(p.getCode());
			}
			LoginUtil.loginAction(request, users.get(0).getId(), loginName,users.get(0).getHeadImgUrl(),
					codes);
			
//			response.sendRedirect(request.getContextPath() + "/mi/index");
//			return false;
			return responseOut(request, response, "/mi/index", true, "登录成功",true);
		}
	}

    // 验证码校验
    protected boolean doCaptchaValidate(HttpServletRequest request) {
//    	return true;
    	return geetest.validateRequest(request);
    }
    
	private boolean isLogoutUrl(String sp) {
		return startWithStr(miHead + LoginUtil.URL_SPLIT + logoutUrlHead, sp);
	}

	private boolean isLoginUrl(String sp) {
		return startWithStr(miHead + LoginUtil.URL_SPLIT + loginUrlHead, sp);
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = (String) request.getHeader("X-Requested-With");
		return requestType != null && requestType.equals("XMLHttpRequest");
	}

	private boolean isAuthorized(HttpServletRequest request, String path) {
		if (StringUtils.isBlank(path)) {
			return true;
		}
		List<String> codes = (List<String>) request.getSession().getAttribute(
				LoginUtil.LOGIN_USER_PERMISSIONS);
		if (codes != null) {
			for (String code : codes) {
				code = code.replace(LoginUtil.PERMISSION_SPLIT, LoginUtil.URL_SPLIT);
				if (startWithStr(code, path)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isValidUrl(String path) {
//		return StringUtils.isBlank(path)
//				|| ( path.indexOf("//") == -1 && path.length() <= 800);
		return StringUtils.isBlank(path)
				|| (path.matches("^[" + DEFAULT_ACCESS_CHAR + "]*$")
						&& path.indexOf("//") == -1 && path.length() <= 800);
	}

	private boolean isMIUrl(String path) {
		if (StringUtils.isBlank(miHead)) {
			return true;
		}
		if (StringUtils.isBlank(path)) {
			return false;
		}
		return startWithStr(miHead, path);
	}

	private boolean needAuthenticated(String path) {
		if (StringUtils.isBlank(path) || path.contains(loginUrlHead)) {
			return false;
		}
		return isMIUrl(path);
	}

	private boolean needAuthorized(String path) {
		if (StringUtils.isBlank(path) || path.contains(loginUrlHead)) {
			return false;
		}
		// for (int i = 0; i < needPermissionUrlHead.length; i++) {
		// for(int j=0;j<LoginUtil.PERMISSION_URLS.length;j++){
		// if
		// (startWithStr(needPermissionUrlHead[i]+LoginUtil.URL_SPLIT+LoginUtil.PERMISSION_URLS[j],
		// path)) {
		// return true;
		// }
		// }
		// }
		for (int j = 0; j < LoginUtil.PERMISSION_URLS.length; j++) {
			if (startWithStr(miHead + LoginUtil.URL_SPLIT + LoginUtil.PERMISSION_URLS[j], path)) {
				return true;
			}
		}
		return false;
	}

	private boolean startWithStr(String str, String path) {
		return StringUtils.isBlank(str)
				|| (StringUtils.isNotBlank(path) && path.matches("^/?" + str
						+ "(/[" + DEFAULT_ACCESS_CHAR + "]*|)$"));
	}

	private boolean isAuthenticated(HttpServletRequest request) {
		return StringUtils.isNotBlank((String) request.getSession()
				.getAttribute(LoginUtil.LOGIN_USER_ID));
	}
}
