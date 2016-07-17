package com.mimi.cgims.web.controller;

import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.util.TestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @RequestMapping(value = "/html/index", method = {RequestMethod.GET})
    public String index(HttpServletRequest request) {
        return "index";
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
