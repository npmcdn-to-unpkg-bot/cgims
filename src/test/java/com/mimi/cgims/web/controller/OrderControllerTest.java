package com.mimi.cgims.web.controller;

import com.mimi.cgims.Constants;
import com.mimi.cgims.common.BaseJunit4Test;
import com.mimi.cgims.enums.OrderStatus;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.model.WorkmanModel;
import com.mimi.cgims.util.AutoNumUtil;
import com.mimi.cgims.util.DateUtil;
import com.mimi.cgims.util.ListUtil;
import com.mimi.cgims.util.ResultUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.junit.Assert.*;

public class OrderControllerTest extends BaseJunit4Test {

    @Test
    public void search() throws Exception {
        List<OrderModel> orders = orderDao.list();
        List<UserModel> users = userDao.list();
        List<WorkmanModel> workmans = workmanDao.list();
        String rBegin = DateUtil.toString(DateUtil.randomDate("2014-01-01 00:00:00", "2015-01-01 00:00:00"));
        String rEnd = DateUtil.toString(DateUtil.randomDate("2014-01-01 00:00:00", "2015-01-01 00:00:00"));
//        String rEnd = DateUtil.toString(DateUtil.randomDate("2015-01-01 00:00:00", "2016-01-01 00:00:00"));
        int total = orderDao.count();
        String rUserId = users.get(10).getId();
        String rWorkmanId = workmans.get(10).getId();
//        searchKeyword,orderStatus,serviceType,userId,workmanId,beginTime,endTime,curPage, pageSize
        int curPage = 1;
        int pageSize = 10;
        for (int i = 0; i < 10; i++) {
            String searchKeyword = "";
            String orderStatus = "";
            String serviceType = "";
            String userId = "";
            String workmanId = "";
            String beginTime = "";
            String endTime = "";
            switch (i) {
                case 0:
                    searchKeyword = "客户名称1";
                    break;
                case 1:
                    orderStatus = Constants.ORDER_STATUS_YSWC;
                    break;
                case 2:
                    serviceType = Constants.SERVICE_TYPE_WX;
                    break;
                case 3:
                    userId = rUserId;
                    break;
                case 4:
                    workmanId = rWorkmanId;
                    break;
                case 5:
                    beginTime = rBegin;
                    break;
                case 6:
                    endTime = rEnd;
                    break;
                case 7:
                    workmanId = rWorkmanId;
                    endTime = rEnd;
                    break;
                case 8:
                    serviceType = Constants.SERVICE_TYPE_WX;
                    beginTime = rBegin;
                    break;
                case 9:
                    workmanId = rWorkmanId;
                    searchKeyword = "1";
                    break;
            }
            Object result = orderController.search(searchKeyword, orderStatus, serviceType, userId, workmanId, beginTime, endTime, curPage, pageSize);
            assertResultSuccess(result);
            int nowCount = ResultUtil.getPageTotal((Map<String, Object>) result);
            System.out.println(nowCount + "_" + total);
            assertNotEquals(total, nowCount);
            List<OrderModel> tOrders = (List<OrderModel>) ResultUtil.getDatas((Map<String, Object>) result);
            for (OrderModel tOrder : tOrders) {
                if (tOrder.getUser() != null) {
                    assertTrue(ListUtil.isEmpty(tOrder.getUser().getRoles()));
                    assertTrue(ListUtil.isEmpty(tOrder.getUser().getOrders()));
                }
                if (tOrder.getWorkman() != null) {
                    System.out.println("workman");
                    assertTrue(ListUtil.isEmpty(tOrder.getWorkman().getOrders()));
                }
            }
        }
    }

    @Test
    public void userSearch() throws Exception {
    }

    @Test
    public void workmanSearch() throws Exception {

    }

    @Test
    public void get() throws Exception {
        List<OrderModel> orders = orderDao.list();
        String oldId = orders.get(5).getId();
        Object result = orderController.get(oldId);
        assertResultSuccess(result);
        OrderModel order = (OrderModel) ResultUtil.getResult((Map<String, Object>) result);
        assertEquals(oldId, order.getId());
    }

    @Test
    public void add() throws Exception {

        Random random = new Random();

        List<UserModel> users = userDao.list();
        List<WorkmanModel> workmanModels = workmanDao.list();
        int uCount = users.size();
        int wCount = workmanModels.size();
        int oCount = orderDao.count();
        int total = random.nextInt(50);
        for (int i = 0; i < total; i++) {
            HttpServletRequest request = new MockHttpServletRequest();
            String customerName = "客户名称";
            String customerPhoneNum = "客户电话";
            String customerTel = "备用电话";
            String customerAddress = "客户地址";
            String productInfo = "产品信息";
            String imgUrl = "img";
            String logisticsInfo = "物流信息";
            String repairImg = "维修信息";
            String checkInfo = "核销信息";
            String shopInfo = "商家信息";
            boolean check = random.nextBoolean();
            int orderPrice = random.nextInt(300) + 100;
            int servicePrice = orderPrice - random.nextInt(100);
            int profit = orderPrice - servicePrice;
            String priceChangeReason = "价格变动原因";
            int judgment = random.nextInt(5) + 1;
            String judgeReason = "评价理由";
            String description = "备注";
            UserModel user = users.get(random.nextInt(users.size()));
            WorkmanModel workman = workmanModels.get(random.nextInt(workmanModels.size()));
            boolean orderPriceChanged = random.nextBoolean();
            boolean servicePriceChanged = random.nextBoolean();
            Date today = DateUtil.randomDate("2014-01-01 00:00:00", "2015-01-01 00:00:00");
            String workmanId = workman.getId();

            UserModel loginUser = new UserModel();
            loginUser.setLoginName(Constants.USER_LOGIN_NAME_ADMIN);
            loginUser.setPassword(Constants.USER_PASSWORD);
            userController.login(request, loginUser);

            Calendar c = Calendar.getInstance();
            c.setTime(today);
            AutoNumUtil.setC(c);
            OrderModel order = new OrderModel();
//                order.setOrderNumber(AutoNumUtil.getOrderNum());
//                order.setOrderStatus(OrderStatus.random().getName());
//                if (random.nextBoolean()) {
//                    order.setServiceType(Constants.SERVICE_TYPE_PSAZ);
//                } else if(random.nextBoolean()){
//                        order.setServiceType(Constants.SERVICE_TYPE_WX);
//                }else{
//                    order.setServiceType(Constants.SERVICE_TYPE_WX + "," + Constants.SERVICE_TYPE_PSAZ);
//                }
//                order.setCustomerName(customerName + i);
//                order.setCustomerPhoneNum(customerPhoneNum + i);
//                order.setCustomerTel(customerTel + i);
//                order.setCustomerAddress(customerAddress + i);
//                order.setProductInfo(productInfo + i);
//                order.setProductImgs(imgUrl+i);
//                order.setLogisticsInfo(logisticsInfo + i);
//                order.setLogisticsImgs(imgUrl+i);
//                order.setRepairInfo(repairImg + i);
//                order.setRepairImgs(imgUrl+i);
//                order.setChecked(check);
//                order.setCheckInfo(checkInfo+ i);
//                order.setShopInfo(shopInfo + i);
//                order.setOrderPrice(orderPrice);
//                order.setServicePrice(servicePrice);
//                order.setProfit(profit);
//                order.setPriceChangeReason(priceChangeReason + i);
//                order.setJudgment(judgment);
//                order.setJudgeReason(judgeReason + i);
//                order.setDescription(description+ i);
//                if(i%2==0){
//                    order.setUser(user);
//                }
//                order.setCreateDate(today);
//                order.setOrderPriceChanged(orderPriceChanged);
//                order.setServicePriceChanged(servicePriceChanged);
//                if (OrderStatus.YSWC.getName().equals(order.getOrderStatus())) {
//                    order.setCompleteDate(DateUtil.randomDate("2015-01-01 00:00:00","2016-01-01 00:00:00"));
//                }
//                if(i%3==0){
//                    order.setWorkman(workman);
//                }

            assertResultSuccess(orderController.add(request, order, workmanId));
        }
        assertEquals(uCount,userDao.count());
        assertEquals(wCount,workmanDao.count());
        assertEquals(oCount+total,orderDao.count());
    }

    @Test
    public void update() throws Exception {

    }

//    @Test
//    public void delete() throws Exception {
//
//    }

    @Test
    public void batch() throws Exception {

    }

    @Test
    public void upload() throws Exception {

    }

    @Test
    public void analysis() throws Exception {

    }

}