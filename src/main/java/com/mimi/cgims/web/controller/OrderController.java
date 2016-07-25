package com.mimi.cgims.web.controller;

import com.mimi.cgims.Constants;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.model.WorkmanModel;
import com.mimi.cgims.service.IAliyunOSSService;
import com.mimi.cgims.service.IOrderService;
import com.mimi.cgims.service.IWorkmanService;
import com.mimi.cgims.service.impl.AliyunOSSService;
import com.mimi.cgims.util.ListUtil;
import com.mimi.cgims.util.LoginUtil;
import com.mimi.cgims.util.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Resource
    private IOrderService orderService;
    @Resource
    private IAliyunOSSService aliyunOSSService;
//    @Resource
//    private IWorkmanService workmanService;

    @RequestMapping(value = {"/user/{id}/order/batch","/order/batch"}, method = RequestMethod.POST)
    @ResponseBody
    public Object batch(String ids,String action,String orderStatus) {
        List<String> idList = ListUtil.getListByStr(ids);
        if(ListUtil.isEmpty(idList)){
            return ResultUtil.getFailResultMap("操作内容为空");
        }
        if(!ListUtil.contains(Constants.BATCH_ACTIONS,action)){
            return ResultUtil.getFailResultMap("未知操作方式");
        }
        if(!ListUtil.contains(Constants.ORDER_STATUS_LIST,orderStatus)){
            return ResultUtil.getFailResultMap("未知订单状态");
        }
        orderService.batchAction(ids,action,orderStatus);

        return ResultUtil.getSuccessResultMap();
    }

    @RequestMapping(value = {"/user/{id}/order","/order"}, method = RequestMethod.GET)
    @ResponseBody
    public Object search(String searchKeyword,String orderStatus,String serviceType,String userId,String beginTime,String endTime, int targetPage, int pageSize) {
        return orderService.list4Page(searchKeyword,orderStatus,serviceType,userId,beginTime,endTime,searchKeyword, targetPage, pageSize);
    }

    @RequestMapping(value = {" /user/{id}/order/{orderId}","/order/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable String id) {
        return ResultUtil.getSuccessResultMap(orderService.get(id));
    }

    @RequestMapping(value = {"/user/{id}/order","/order"}, method = RequestMethod.POST)
    @ResponseBody
    public Object add(HttpServletRequest request,OrderModel order,String workmanId) {
        if(StringUtils.isNotBlank(workmanId)){
            WorkmanModel workman = new WorkmanModel();
            workman.setId(workmanId);
            order.setWorkman(workman);
        }
        UserModel user = new UserModel();
        user.setId(LoginUtil.getCurUserId(request));
        order.setUser(user);
        String error = orderService.checkAdd(order);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        return ResultUtil.getSuccessResultMap(orderService.addAndRefresh(order));
    }

    @RequestMapping(value = {" /user/{id}/order/{orderId}","/order/{id}"}, method = RequestMethod.PATCH)
    @ResponseBody
    public Object update(@PathVariable String id, OrderModel order,String workmanId) {
        if(StringUtils.isNotBlank(workmanId)){
            WorkmanModel workman = new WorkmanModel();
            workman.setId(workmanId);
            order.setWorkman(workman);
        }
        order.setId(id);
        String error = orderService.checkUpdate(order);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        orderService.updateAndRefresh(order);
        return ResultUtil.getSuccessResultMap();
    }

    @RequestMapping(value = {" /user/{id}/order/{orderId}","/order/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable String id) {
        orderService.deleteAndRefresh(id);
        return ResultUtil.getSuccessResultMap(id);
    }


    @RequestMapping(value = {
            "/order/upload/product",
            "/order/upload/logistics",
            "/order/upload/repair","/user/{id}/order/upload/product",
            "/user/{id}/order/upload/logistics",
            "/user/{id}/order/upload/repair"}, method = { RequestMethod.POST })
    public @ResponseBody
    Object upload(HttpServletRequest request,
                           @RequestParam("theFile") MultipartFile theFile) {
        return uploadAction(theFile);
    }
    private Map<String,Object> uploadAction(MultipartFile theFile){
        try {
            String path = aliyunOSSService.saveFileToServer(theFile);
            path = aliyunOSSService.addImgParams(path,
                    AliyunOSSService.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG);
            return ResultUtil.getSuccessResultMap(path);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getFailResultMap("保存图片失败");
        }
    }

}
