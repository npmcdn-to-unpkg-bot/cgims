package com.mimi.cgims.web.controller;

import com.mimi.cgims.Config;
import com.mimi.cgims.util.LoginUtil;
import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.util.TestUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class IndexController {
    @Resource
    private Config config;

    @RequestMapping(value = {"/error","/error_all"}, method = {RequestMethod.GET})
    public String error(HttpServletRequest request) {
        return "error_all";
    }


    @RequestMapping(value = "/html/index", method = {RequestMethod.GET})
    public String index(HttpServletRequest request) {
        Map<String,String> map = LoginUtil.getUserLoginMsg(request);
        request.setAttribute("loginUserMap", JSONObject.fromObject(map));
        return "index";
    }

    @RequestMapping(value = "/html/indexOld", method = {RequestMethod.GET})
    public String indexOld(HttpServletRequest request) {
        return "indexOld";
    }

    @RequestMapping(value = "/html/login", method = {RequestMethod.GET})
    public String login(HttpServletRequest request) {
        return "login";
    }


    @RequestMapping(value = "/html/index2", method = {RequestMethod.GET})
    public String index2(HttpServletRequest request) {
        TestUtil.makeError();
        return "index";
    }

    @RequestMapping(value = "/index/test", method = RequestMethod.GET)
    @ResponseBody
    public Object testGet(HttpServletRequest request,
                          String searchKeyword) {
        TestUtil.makeError();
        return ResultUtil.getSuccessResultMap("GET" + searchKeyword);
    }

    @RequestMapping(value = "/index/test", method = {RequestMethod.POST})
    @ResponseBody
    public Object testPost(HttpServletRequest request,
                           String searchKeyword) {
        return ResultUtil.getSuccessResultMap("POST" + searchKeyword);
    }

    @RequestMapping(value = "/index/test", method = {RequestMethod.PUT})
    @ResponseBody
    public Object testPut(HttpServletRequest request,
                          String searchKeyword) {
        return ResultUtil.getSuccessResultMap("PUT" + searchKeyword);
    }

    @RequestMapping(value = "/index/test", method = {RequestMethod.PATCH})
    @ResponseBody
    public Object testPatch(HttpServletRequest request,
                            String searchKeyword) {
        return ResultUtil.getSuccessResultMap("PATCH" + searchKeyword);
    }

    @RequestMapping(value = "/index/test", method = {RequestMethod.DELETE})
    @ResponseBody
    public Object testDelete(HttpServletRequest request,
                             String searchKeyword) {
        return ResultUtil.getSuccessResultMap("DELETE" + searchKeyword);
    }
}
