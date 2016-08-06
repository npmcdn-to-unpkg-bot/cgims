package com.mimi.cgims.web.controller;

import com.mimi.cgims.Constants;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.model.WorkmanModel;
import com.mimi.cgims.service.IAliyunOSSService;
import com.mimi.cgims.service.IOrderService;
import com.mimi.cgims.service.IUserService;
import com.mimi.cgims.service.IWorkmanService;
import com.mimi.cgims.service.impl.AliyunOSSService;
import com.mimi.cgims.util.AutoNumUtil;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Resource
    private IOrderService orderService;
    @Resource
    private IAliyunOSSService aliyunOSSService;
    @Resource
    private IWorkmanService workmanService;
    @Resource
    private IUserService userService;

    private String[] ignores = {"id", "orderNumber", "user", "createDate", "completeDate","orderPriceChanged","servicePriceChanged"};

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
        if(Constants.BATCH_ACTION_UPDATE.equals(action) && !ListUtil.contains(Constants.ORDER_STATUS_LIST,orderStatus)){
            return ResultUtil.getFailResultMap("未知订单状态");
        }
        orderService.batchAction(ids,action,orderStatus);

        return ResultUtil.getSuccessResultMap();
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @ResponseBody
    public Object search(String searchKeyword,String orderStatus,String serviceType,String userId,String workmanId,String beginTime,String endTime,@RequestParam(defaultValue = "1") Integer curPage,@RequestParam(defaultValue = "10") Integer pageSize) {
        beginTime = buildBeginTime(beginTime);
        endTime = buildEndTime(endTime);
        return orderService.list4Page(searchKeyword,orderStatus,serviceType,userId,workmanId,beginTime,endTime,curPage, pageSize);
    }

    @RequestMapping(value = "/user/{id}/order", method = RequestMethod.GET)
    @ResponseBody
    public Object userSearch(@PathVariable String id,String searchKeyword,String orderStatus,String serviceType,String userId,String beginTime,String endTime,@RequestParam(defaultValue = "1") Integer curPage,@RequestParam(defaultValue = "10") Integer pageSize) {
        beginTime = buildBeginTime(beginTime);
        endTime = buildEndTime(endTime);
        return orderService.list4Page(searchKeyword,orderStatus,serviceType,id,null,beginTime,endTime,curPage, pageSize);
    }

    @RequestMapping(value = "/workman/{id}/order", method = RequestMethod.GET)
    @ResponseBody
    public Object workmanSearch(@PathVariable String id, String searchKeyword,String orderStatus,String serviceType,String userId,String beginTime,String endTime,@RequestParam(defaultValue = "1") Integer curPage,@RequestParam(defaultValue = "10") Integer pageSize) {
        beginTime = buildBeginTime(beginTime);
        endTime = buildEndTime(endTime);
        return orderService.list4Page(searchKeyword,orderStatus,serviceType,userId,id,beginTime,endTime,curPage, pageSize);
    }

    @RequestMapping(value = {"/workman/{id}/order/{orderId}"," /user/{id}/order/{orderId}","/order/{orderId}"}, method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable String orderId) {
        return ResultUtil.getSuccessResultMap(orderService.get(orderId));
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ResponseBody
    public Object add(HttpServletRequest request,OrderModel order,String workmanId) {
        return addAction(request,order,workmanId,null);
    }


    @RequestMapping(value = "/user/{id}/order", method = RequestMethod.POST)
    @ResponseBody
    public Object personAdd(@PathVariable String id, HttpServletRequest request,OrderModel order,String workmanId) {
        return addAction(request,order,workmanId,id);
    }

    private Object addAction(HttpServletRequest request,OrderModel order,String workmanId,String userId){
        if(StringUtils.isBlank(userId)){
            userId = LoginUtil.getCurUserId(request);
        }
        if(StringUtils.isBlank(userId)){
            return ResultUtil.getFailResultMap("请先登录");
        }
        if(StringUtils.isNotBlank(workmanId)){
            WorkmanModel workman = new WorkmanModel();
            workman.setId(workmanId);
            order.setWorkman(workman);
//            order.setWorkman(workmanService.get(workmanId));
        }
        UserModel user = new UserModel();
        user.setId(userId);
        order.setUser(user);
//        order.setUser(userService.get(LoginUtil.getCurUserId(request)));
        order.setOrderNumber(AutoNumUtil.getOrderNum());
        order.setCreateDate(new Date());
        order.setOrderPriceChanged(false);
        order.setServicePriceChanged(false);
        String error = orderService.checkAdd(order);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        return ResultUtil.getSuccessResultMap(orderService.addAndRefresh(order));
    }

    @RequestMapping(value = " /user/{userId}/order/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object userUpdate(HttpServletRequest request,@PathVariable String id, @PathVariable String userId, OrderModel order,String workmanId) {
        return updateAction(request,id,order,workmanId,userId);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(HttpServletRequest request,@PathVariable String id, OrderModel order,String workmanId) {
        return updateAction(request,id,order,workmanId,null);
    }

    private Object updateAction(HttpServletRequest request,String id,OrderModel order,String workmanId,String userId){
        OrderModel newOrder = orderService.get(id);
        String error = checkPermission(request,order,newOrder);
        if(StringUtils.isNotBlank(error)){
            return ResultUtil.getResultMap(ResultUtil.RESULT_FAIL_AUTHORIZATION,error);
//            return ResultUtil.getFailResultMap(error);
        }
        if(Constants.ORDER_STATUS_YSWC.equals(order.getOrderStatus()) && !Constants.ORDER_STATUS_YSWC.equals(newOrder.getOrderStatus())){
            newOrder.setCompleteDate(new Date());
        }
//        newOrder.setOrderPriceChanged(order.getOrderPriceChanged());
//        newOrder.setServicePriceChanged(order.getServicePriceChanged());
        if(order.getOrderPrice()!=newOrder.getOrderPrice()){
            newOrder.setOrderPriceChanged(true);
        }
        if(order.getServicePrice()!=newOrder.getServicePrice()){
            newOrder.setServicePriceChanged(true);
        }
        BeanUtils.copyProperties(order, newOrder, ignores);

        if(StringUtils.isNotBlank(workmanId)){
            WorkmanModel workman = new WorkmanModel();
            workman.setId(workmanId);
            newOrder.setWorkman(workman);
        }
        error = orderService.checkUpdate(newOrder);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        orderService.updateAndRefresh(newOrder);
        return ResultUtil.getSuccessResultMap();
    }

    private String checkPermission(HttpServletRequest request,OrderModel order,OrderModel newOrder){
        if(!LoginUtil.getUserPermissionCodes(request).contains(Constants.PERMISSION_CODE_ORDER_MANAGER)){
            if(!ListUtil.contains(Constants.PERSON_ORDER_STATUS_OPERATION,order.getOrderStatus())){
                return "没有足够权限修改订单状态";
            }
            if(!ListUtil.contains(Constants.PERSON_ORDER_STATUS_VIEW,newOrder.getOrderStatus())){
                return "没有足够权限修改订单状态";
            }
        }
        return null;
    }
//    @RequestMapping(value = {" /user/{id}/order/{orderId}","/order/{id}"}, method = RequestMethod.DELETE)
//    @ResponseBody
//    public Object delete(@PathVariable String id) {
//        orderService.deleteAndRefresh(id);
//        return ResultUtil.getSuccessResultMap(id);
//    }


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


    @RequestMapping(value = "/analysis/{type}", method = { RequestMethod.GET })
    public @ResponseBody
    Object analysis(@PathVariable String type,String creatorId,String serviceType,String beginTime,String endTime) {
        beginTime = buildBeginTime(beginTime);
        endTime = buildEndTime(endTime);
        Object result;
        switch (type){
            case "orderCount":
                result = orderService.analysisOrderCount(creatorId,serviceType,beginTime,endTime);
                break;
            case "income":
                result = orderService.analysisIncome(creatorId,serviceType,beginTime,endTime);
                break;
            case "expenditure":
                result = orderService.analysisExpenditure(creatorId,serviceType,beginTime,endTime);
                break;
            case "profit":
                result = orderService.analysisProfit(creatorId,serviceType,beginTime,endTime);
                break;
            case "profitMargin":
                result = orderService.analysisProfitMargin(creatorId,serviceType,beginTime,endTime);
                break;
            case "incomeP":
                result  = orderService.analysisIncomeP(creatorId,serviceType,beginTime,endTime);
                break;
            case "profitP":
                result  = orderService.analysisProfitP(creatorId,serviceType,beginTime,endTime);
                break;
            default:
                return ResultUtil.getFailResultMap("未知分析类型");
        }
        return ResultUtil.getSuccessResultMap(result);
    }

    private String buildBeginTime(String beginTime){
        if(StringUtils.isNotBlank(beginTime)){
            beginTime = beginTime+" 00:00:00";
        }
        return beginTime;
    }
    private String buildEndTime(String endTime){
        if(StringUtils.isNotBlank(endTime)){
            endTime = endTime+" 23:59:59";
        }
        return endTime;
    }
}
