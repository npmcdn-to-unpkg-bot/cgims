package com.mimi.cgims.web.controller;

import com.mimi.cgims.Constants;
import com.mimi.cgims.model.PermissionModel;
import com.mimi.cgims.model.RoleModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.service.IPermissionService;
import com.mimi.cgims.service.IUserService;
import com.mimi.cgims.util.LoginUtil;
import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.util.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IPermissionService permissionService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Object search(String searchKeyword, @RequestParam(defaultValue = "1") Integer curPage,@RequestParam(defaultValue = "10") Integer pageSize) {
        return userService.list4Page(searchKeyword, curPage, pageSize);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable String id) {
        return ResultUtil.getSuccessResultMap(userService.getWithDatas(id));
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public Object add(UserModel user, String roleIds) {
        String error = userService.checkAdd(user);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        user.setPassword(LoginUtil.buildPassword(user.getPassword()));
        user.setRoles(buildRoles(roleIds));
        userService.add(user);
        return ResultUtil.getSuccessResultMap();
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public Object get(@PathVariable String id, UserModel user, String roleIds) {
        String error = userService.checkUpdate(user);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        UserModel newModel = userService.get(id);
        if (StringUtils.isBlank(user.getPassword())) {
            BeanUtils.copyProperties(user, newModel, "id", "roles");
        } else {
            BeanUtils.copyProperties(user, newModel, "id", "roles", "password");
            newModel.setPassword(LoginUtil.buildPassword(newModel.getPassword()));
        }
        newModel.setRoles(buildRoles(roleIds));
        userService.update(newModel);
        return ResultUtil.getSuccessResultMap();
    }

    private List<RoleModel> buildRoles(String roleIds) {
        List<RoleModel> roleModels = new ArrayList<>();
        if (StringUtils.isNotBlank(roleIds)) {
            for (String roleId : roleIds.split(Constants.SPLIT_STRING_IDS)) {
                if (StringUtils.isNotBlank(roleId)) {
                    RoleModel roleModel = new RoleModel();
                    roleModel.setId(roleId);
                    roleModels.add(roleModel);
                }
            }
        }
        return roleModels;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable String id) {
        userService.delete(id);
        return ResultUtil.getResultMap(ResultUtil.RESULT_SUCCESS, null, id);
    }

    @RequestMapping(value = "/user/batch", method = RequestMethod.POST)
    @ResponseBody
    public Object batch(String ids) {

        return ResultUtil.getResultMap(ResultUtil.RESULT_SUCCESS, null, ids);
    }



    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request,UserModel user) {
        String error = userService.login(user);
        if(StringUtils.isNotBlank(error)){
            return ResultUtil.getFailResultMap(error);
        }
        List<PermissionModel> permissionModels = permissionService.listByUserId(user.getId());
        String permissionCodes = "";
        for(PermissionModel permission:permissionModels){
            if(StringUtils.isNotBlank(permissionCodes)){
                permissionCodes+=Constants.SPLIT_STRING_PARAMS;
            }
            permissionCodes+=permission.getCode();
        }
        LoginUtil.userLogin(request,user,permissionCodes);
        return ResultUtil.getSuccessResultMap();
    }


    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    @ResponseBody
    public Object logout(HttpServletRequest request) {
        LoginUtil.userLogout(request);
        return ResultUtil.getSuccessResultMap();
    }


}
