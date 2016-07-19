package com.mimi.cgims.filter;

import com.mimi.cgims.Constants;
import com.mimi.cgims.service.IPermissionService;
import com.mimi.cgims.service.IUserService;
import com.mimi.cgims.util.ResultUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory
            .getLogger(LoginInterceptor.class);

    @Resource
    private IUserService userService;

    @Resource
    private IPermissionService permissionService;

    String DEFAULT_ACCESS_CHAR = "0-9a-zA-Z\u4E00-\u9FA5:/_-";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String sp = request.getServletPath();
        if (!isValidUrl(sp)) {
            response.sendError(404);
            return false;
        }
//        request.setAttribute("uri", request.getRequestURI());
        if (StringUtils.isBlank(sp) || "/".equals(sp.trim())) {
            if (isAuthenticated(request)) {
                return responseOut(request, response, "/html/index", true, "已登录", true);
            } else {
                return responseOut(request, response, "/html/login", true, "请登录", false);
            }
        } else if (isLoginPage(sp)) {
            if (isAuthenticated(request)) {
                return responseOut(request, response, "/html/index", true, "已登录", true);
            } else {
                return true;
            }
        } else if (isIndexPage(sp) || sp.startsWith("/user/self")) {
            if (isAuthenticated(request)) {
                return true;
            } else {
                return responseOut(request, response, "/html/login", true, "请登录", false);
            }
        } else if (isWorkmanLoginPage(sp)) {
            if (isWorkmanLogined(request)) {
                return responseOut(request, response, "/html/workman/self/" + request.getSession().getAttribute("workmanId"), true, "已登录", true);
            } else {
                return true;
            }
        } else if (isWorkmanSelfPage(sp) || sp.startsWith("/workman/self/")) {
            if (isWorkmanLogined(request)) {
                return true;
            } else {
                return responseOut(request, response, "/html/workman/login", true, "请登录登录", false);
            }
        } else if (sp.startsWith("/user/login") || sp.startsWith("/workman/phoneCaptcha") || sp.startsWith("/workman/login") || sp.startsWith("/error") || sp.startsWith("/user/logout")) {
            return true;
        }
        if (checkPermission(sp, request.getMethod(), (String) request.getSession().getAttribute(Constants.COOKIE_NAME_PERMISSION_CODES))) {
            return true;
        } else {
            return responseOut(request, response, "/html/index", true, "没有足够权限", false);
        }
//        if (isHtmlPage(sp)) {
//            if (isLoginPage(sp)) {
//                if (isAuthenticated(request)) {
//                    return responseOut(request, response, "/html/index", true, "已登录", true);
//                } else {
//                    return true;
//                }
//            } else if (isIndexPage(sp)) {
//                if (isAuthenticated(request)) {
//                    return true;
//                } else {
//                    return responseOut(request, response, "/html/login", false, "请登录", false);
//                }
//            } else if (isWorkmanLoginPage(sp)) {
//                if (isWorkmanLogined(request)) {
//                    return responseOut(request, response, "/html/workman/self/" + request.getSession().getAttribute("workmanId"), true, "已登录", true);
//                } else {
//                    return true;
//                }
//            } else if (isWorkmanSelfPage(sp)) {
//                if (isWorkmanLogined(request)) {
//                    return true;
//                } else {
//                    return responseOut(request, response, "/html/workman/login", true, "请登录登录", false);
//                }
//            }
//        } else {
//            if (sp.startsWith("/user/login") || sp.startsWith("/workman/phoneCaptcha") || sp.startsWith("/workman/login")) {
//                return true;
//            } else if (sp.startsWith("/user/self")) {
//                return isAuthenticated(request);
//            } else if (sp.startsWith("/workman/self/")) {
//                return isWorkmanLogined(request);
//            }
//            if(checkPermission(sp, request.getMethod(), (String) request.getSession().getAttribute(Constants.COOKIE_NAME_PERMISSION_CODES))){
//                return true;
//            }else{
//                return responseOut(request, response, "/html/index", true, "没有足够权限", false);
//            }
//        }
//        return true;
    }

    private boolean checkPermission(String sp, String method, String permissionCodesStr) {
        if(StringUtils.isBlank(sp) || StringUtils.isBlank(method) || StringUtils.isBlank(permissionCodesStr)){
            return false;
        }
        if (sp.startsWith("/permission")) {
            return permissionCodesStr.contains(Constants.PERMISSION_CODE_ROLE_MANAGER);
        } else if (sp.startsWith("/role")) {
            if (method.equalsIgnoreCase("GET")) {
                return permissionCodesStr.contains(Constants.PERMISSION_CODE_USER_MANAGER) || permissionCodesStr.contains(Constants.PERMISSION_CODE_ROLE_MANAGER);
            }
            return permissionCodesStr.contains(Constants.PERMISSION_CODE_ROLE_MANAGER);
        } else if (sp.startsWith("/user")) {
            if (sp.contains("/order")) {
                return permissionCodesStr.contains(Constants.PERMISSION_CODE_USER_ORDER_MANAGER);
            }
            return permissionCodesStr.contains(Constants.PERMISSION_CODE_USER_MANAGER);
        } else if (sp.startsWith("/order")) {
            if (method.equalsIgnoreCase("GET")) {
                return permissionCodesStr.contains(Constants.PERMISSION_CODE_ORDER_MANAGER) || permissionCodesStr.contains(Constants.PERMISSION_CODE_ORDER_VIEW);
            }
            return permissionCodesStr.contains(Constants.PERMISSION_CODE_ORDER_MANAGER);
        } else if (sp.startsWith("/workman")) {
            if (method.equalsIgnoreCase("GET")) {
                return permissionCodesStr.contains(Constants.PERMISSION_CODE_ORDER_MANAGER) || permissionCodesStr.contains(Constants.PERMISSION_CODE_ORDER_VIEW) || permissionCodesStr.contains(Constants.PERMISSION_CODE_USER_ORDER_MANAGER)
                        || permissionCodesStr.contains(Constants.PERMISSION_CODE_WORKMAN_MANAGER);
            }
            return permissionCodesStr.contains(Constants.PERMISSION_CODE_WORKMAN_MANAGER);
        } else if (sp.startsWith("/analysis")) {
            return permissionCodesStr.contains(Constants.PERMISSION_CODE_ANALYSIS_MANAGER);
        }
        return true;
    }

    private boolean isWorkmanSelfPage(String sp) {
        return StringUtils.isNotBlank(sp) && sp.startsWith("/html/workman/self");
    }

    private boolean isWorkmanLogined(HttpServletRequest request) {
        return StringUtils.isNotBlank((String) request.getSession().getAttribute(Constants.COOKIE_NAME_WORKMAN_LOGIN_ID));
    }

    private boolean isWorkmanLoginPage(String sp) {
        return StringUtils.isNotBlank(sp) && sp.startsWith("/html/workman/login");
    }

    private boolean isIndexPage(String sp) {
        return StringUtils.isNotBlank(sp) && sp.startsWith("/html/index");
    }

    private boolean isLoginPage(String sp) {
        return StringUtils.isNotBlank(sp) && sp.startsWith("/html/login");
    }

    private boolean isHtmlPage(String sp) {
        return StringUtils.isBlank(sp) || sp.startsWith("/html");
    }

    private boolean responseOut(HttpServletRequest request,
                                HttpServletResponse response, String responseUrl, boolean redirect, String responseMsg, boolean success) throws ServletException, IOException {
        if (isAjaxRequest(request)) {
//			Map<String,Object> resultMap = new HashMap<String,Object>();
//			resultMap.put("success", success);
//			resultMap.put("msg", responseMsg);
            responseOutWithJson(response, ResultUtil.getResultMap(success, responseMsg));
        } else {
            if (redirect) {
                response.sendRedirect(request.getContextPath() + responseUrl);
            } else {
                request.getRequestDispatcher(responseUrl)
                        .forward(request, response);
            }
        }
        return false;
    }

    /**
     * 以JSON格式输出
     *
     * @param response
     */
    protected void responseOutWithJson(HttpServletResponse response,
                                       Object responseObject) {
//		response.getWriter().write(JSONObject.fromObject(ResultUtil.getFailResultMap("操作失败，请稍后重试")).toString());
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


    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        return requestType != null && requestType.equals("XMLHttpRequest");
    }

    private boolean isValidUrl(String path) {
        return StringUtils.isBlank(path)
                || (path.matches("^[" + DEFAULT_ACCESS_CHAR + "]*$")
                && path.indexOf("//") == -1 && path.length() <= 800);
    }

    private boolean startWithStr(String str, String path) {
        return StringUtils.isBlank(str)
                || (StringUtils.isNotBlank(path) && path.matches("^/?" + str
                + "(/[" + DEFAULT_ACCESS_CHAR + "]*|)$"));
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        return StringUtils.isNotBlank((String) request.getSession()
                .getAttribute(Constants.COOKIE_NAME_USER_ID));
    }
}