package com.mimi.cgims.web.controller;

import com.mimi.cgims.service.IPermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class PermissionController {

    @Resource
    private IPermissionService permissionService;

    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    @ResponseBody
    public Object search(String searchKeyword, int targetPage, int pageSize) {
        return permissionService.list4Page(searchKeyword, targetPage, pageSize);
    }
}
