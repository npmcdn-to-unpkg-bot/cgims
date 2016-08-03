package com.mimi.cgims.util;

import com.geetest.sdk.java.GeetestLib;
import com.mimi.cgims.Config;

import javax.servlet.http.HttpServletRequest;

public class GeetestUtil {
    private static String geetestId;
    private static String geetestKey;

    public static void init(Config config) {
        geetestId = config.getGeetestId();
        geetestKey = config.getGeetestKey();
    }

    public static boolean valid(HttpServletRequest request) {

        GeetestLib gtSdk = new GeetestLib(geetestId, geetestKey);

        String challenge = request.getParameter(com.geetest.sdk.java.GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(com.geetest.sdk.java.GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(com.geetest.sdk.java.GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //从session中获取userid
        String userid = (String) request.getSession().getAttribute("userid");

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
        }
        return gtResult == 1;
    }

    public static String getInitData(HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(geetestId, geetestKey);

        String resStr = "{}";

        //自定义userid
        String userid = "test";

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(userid);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);

        return gtSdk.getResponseStr();
    }
}
