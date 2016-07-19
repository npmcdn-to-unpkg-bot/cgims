package com.mimi.cgims.web.controller;

import com.mimi.cgims.Constants;
import com.mimi.cgims.model.PermissionModel;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.service.IOrderService;
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
public class OrderController {

    @Resource
    private IOrderService orderService;

//    @RequestMapping(value = "/order", method = RequestMethod.GET)
//    @ResponseBody
//    public Object search(String searchKeyword, int targetPage, int pageSize) {
//        return orderService.list4Page(orderStatus,serviceType,userId,beginTime,endTime,searchKeyword, targetPage, pageSize);
//    }
//
//    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public Object get(@PathVariable String id) {
//        return ResultUtil.getResultMap(true, null, orderService.getWithPermissions(id));
//    }
//
//    @RequestMapping(value = "/order", method = RequestMethod.POST)
//    @ResponseBody
//    public Object add(OrderModel order, String permissionIds) {
//        String error = orderService.checkAdd(order);
//        if (StringUtils.isNotBlank(error)) {
//            return ResultUtil.getFailResultMap(error);
//        }
//        order.setPermissions(buildPermissions(permissionIds));
//        return ResultUtil.getResultMap(true, null, orderService.add(order));
//    }
//
//    @RequestMapping(value = "/order/{id}", method = RequestMethod.PATCH)
//    @ResponseBody
//    public Object update(@PathVariable String id, OrderModel order, String permissionIds) {
//        String error = orderService.checkUpdate(order);
//        if (StringUtils.isNotBlank(error)) {
//            return ResultUtil.getFailResultMap(error);
//        }
//        OrderModel newModel = orderService.get(id);
//        BeanUtils.copyProperties(order, newModel, "id", "users", "permissions");
//        newModel.setPermissions(buildPermissions(permissionIds));
//        orderService.update(newModel);
//        return ResultUtil.getSuccessResultMap();
//    }
//
//    private List<PermissionModel> buildPermissions(String permissionIds) {
//        List<PermissionModel> permissionModels = new ArrayList<>();
//        if (StringUtils.isNotBlank(permissionIds)) {
//            for (String permissionId : permissionIds.split(Constants.SPLIT_STRING_IDS)) {
//                if (StringUtils.isNotBlank(permissionId)) {
//                    PermissionModel permissionModel = new PermissionModel();
//                    permissionModel.setId(permissionId);
//                    permissionModels.add(permissionModel);
//                }
//            }
//        }
//        return permissionModels;
//    }
//
//    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public Object delete(@PathVariable String id) {
//        orderService.delete(id);
//        return ResultUtil.getResultMap(true, null, id);
//    }
}
