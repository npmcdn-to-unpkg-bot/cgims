package com.mimi.cgims.web.controller;

import com.mimi.cgims.Constants;
import com.mimi.cgims.model.PermissionModel;
import com.mimi.cgims.model.RoleModel;
import com.mimi.cgims.service.IRoleService;
import com.mimi.cgims.util.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RoleController {

    @Resource
    private IRoleService roleService;

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    @ResponseBody
    public Object search(String searchKeyword, int targetPage, int pageSize) {
        return roleService.list4Page(searchKeyword, targetPage, pageSize);
    }

    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable String id) {
        return ResultUtil.getSuccessResultMap(roleService.getWithPermissions(id));
    }

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    @ResponseBody
    public Object add(RoleModel role, String permissionIds) {
        String error = roleService.checkAdd(role);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        role.setPermissions(buildPermissions(permissionIds));
        return ResultUtil.getSuccessResultMap(roleService.add(role));
    }

    @RequestMapping(value = "/role/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public Object update(@PathVariable String id, RoleModel role, String permissionIds) {
        String error = roleService.checkUpdate(role);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        RoleModel newModel = roleService.get(id);
        BeanUtils.copyProperties(role, newModel, "id", "users", "permissions");
        newModel.setPermissions(buildPermissions(permissionIds));
        roleService.update(newModel);
        return ResultUtil.getSuccessResultMap();
    }

    private List<PermissionModel> buildPermissions(String permissionIds) {
        List<PermissionModel> permissionModels = new ArrayList<>();
        if (StringUtils.isNotBlank(permissionIds)) {
            for (String permissionId : permissionIds.split(Constants.SPLIT_STRING_IDS)) {
                if (StringUtils.isNotBlank(permissionId)) {
                    PermissionModel permissionModel = new PermissionModel();
                    permissionModel.setId(permissionId);
                    permissionModels.add(permissionModel);
                }
            }
        }
        return permissionModels;
    }

    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable String id) {
        roleService.delete(id);
        return ResultUtil.getSuccessResultMap(id);
    }
}
