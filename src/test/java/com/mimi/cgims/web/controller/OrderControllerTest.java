package com.mimi.cgims.web.controller;

import com.mimi.cgims.Constants;
import com.mimi.cgims.common.BaseJunit4Test;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.model.WorkmanModel;
import com.mimi.cgims.util.AutoNumUtil;
import com.mimi.cgims.util.DateUtil;
import com.mimi.cgims.util.ListUtil;
import com.mimi.cgims.util.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.junit.Assert.*;

public class OrderControllerTest extends BaseJunit4Test {
    @Before
    public void before() {
//        initData.init();
//        if(!config.isInitTestData()){
//            initData.initTestData();
//        }
    }

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
        AutoNumUtil.test = true;
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
            String repairInfo = "维修信息";
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
            UserModel tu = users.get(random.nextInt(users.size()));
            UserModel user = new UserModel();

            user.setId(tu.getId());

            user.setLoginName(tu.getLoginName());
            user.setPassword("123123");
            if(user.getLoginName().equals(Constants.USER_LOGIN_NAME_ADMIN)){
                user.setPassword(Constants.USER_PASSWORD);
            }
            WorkmanModel workman = new WorkmanModel();
            workman.setId(workmanModels.get(random.nextInt(workmanModels.size())).getId());
            boolean orderPriceChanged = random.nextBoolean();
            boolean servicePriceChanged = random.nextBoolean();
            Date today = DateUtil.randomDate("2014-01-01 00:00:00", "2015-01-01 00:00:00");
            Date completeDate = DateUtil.randomDate("2015-01-01 00:00:00","2016-01-01 00:00:00");
            String workmanId = workman.getId();
            String serviceType = Constants.SERVICE_TYPE_LIST[random.nextInt(2)];
            String orderStatus = Constants.ORDER_STATUS_LIST[random.nextInt(Constants.ORDER_STATUS_LIST.length)];

            UserModel loginUser = new UserModel();
//            loginUser.setLoginName(Constants.USER_LOGIN_NAME_ADMIN);
//            loginUser.setPassword(Constants.USER_PASSWORD);
            userController.login(request, user);

            Calendar c = Calendar.getInstance();
            c.setTime(today);
            AutoNumUtil.setC(c);
            OrderModel order = new OrderModel();
//                order.setOrderNumber(AutoNumUtil.getOrderNum());
                order.setOrderStatus(orderStatus);
            order.setServiceType(serviceType);
//                if (random.nextBoolean()) {
//                    order.setServiceType(Constants.SERVICE_TYPE_PSAZ);
//                } else if(random.nextBoolean()){
//                        order.setServiceType(Constants.SERVICE_TYPE_WX);
//                }else{
//                    order.setServiceType(Constants.SERVICE_TYPE_WX + "," + Constants.SERVICE_TYPE_PSAZ);
//                }
                order.setCustomerName(customerName + i);
                order.setCustomerPhoneNum(customerPhoneNum + i);
                order.setCustomerTel(customerTel + i);
                order.setCustomerAddress(customerAddress + i);
                order.setProductInfo(productInfo + i);
                order.setProductImgs(imgUrl+i);
                order.setLogisticsInfo(logisticsInfo + i);
                order.setLogisticsImgs(imgUrl+i);
                order.setRepairInfo(repairInfo + i);
                order.setRepairImgs(imgUrl+i);
                order.setChecked(check);
                order.setCheckInfo(checkInfo+ i);
                order.setShopInfo(shopInfo + i);
                order.setOrderPrice(orderPrice);
                order.setServicePrice(servicePrice);
                order.setProfit(profit);
                order.setPriceChangeReason(priceChangeReason + i);
                order.setJudgment(judgment);
                order.setJudgeReason(judgeReason + i);
                order.setDescription(description+ i);
//                if(i%2==0){
//                    order.setUser(user);
//                }
                order.setCreateDate(today);
                order.setOrderPriceChanged(orderPriceChanged);
                order.setServicePriceChanged(servicePriceChanged);
                if (Constants.ORDER_STATUS_YSWC.equals(order.getOrderStatus())) {
                    order.setCompleteDate(completeDate);
                }
//                if(i%3==0){
//                    order.setWorkman(workman);
//                }
            Object result = orderController.add(request, order, workmanId);
//            System.out.println(ResultUtil.getResult((Map<String, Object>) result));
            assertResultSuccess(result);

            OrderModel newOrder = orderDao.get((String) ResultUtil.getResult((Map<String, Object>) result));
            assertTrue(null!=newOrder.getOrderNumber());
            assertEquals(orderStatus,newOrder.getOrderStatus());
            assertEquals(serviceType,newOrder.getServiceType());
            assertEquals(check,newOrder.getChecked());
            assertEquals(customerName + i,newOrder.getCustomerName());
            assertEquals(customerPhoneNum + i,newOrder.getCustomerPhoneNum());
            assertEquals(customerTel + i,newOrder.getCustomerTel());
            assertEquals(customerAddress + i,newOrder.getCustomerAddress());
            assertEquals(productInfo + i,newOrder.getProductInfo());
            assertEquals(imgUrl+i,newOrder.getProductImgs());
            assertEquals(logisticsInfo + i,newOrder.getLogisticsInfo());
            assertEquals(imgUrl+i,newOrder.getLogisticsImgs());
            assertEquals(repairInfo + i,newOrder.getRepairInfo());
            assertEquals(imgUrl+i,newOrder.getRepairImgs());
            assertEquals(checkInfo+ i,newOrder.getCheckInfo());
            assertEquals(shopInfo + i,newOrder.getShopInfo());
            assertEquals(Integer.valueOf(orderPrice),newOrder.getOrderPrice());
            assertEquals(Integer.valueOf(servicePrice),newOrder.getServicePrice());
            assertEquals(Integer.valueOf(profit),newOrder.getProfit());
            assertEquals(priceChangeReason + i,newOrder.getPriceChangeReason());
            assertEquals(Integer.valueOf(judgment),newOrder.getJudgment());
            assertEquals(judgeReason + i,newOrder.getJudgeReason());
            assertEquals(description+ i,newOrder.getDescription());
//            if(i%2==0){
                assertEquals(user.getId(),newOrder.getUser().getId());
//            }
            assertTrue(null!=newOrder.getCreateDate());
            assertEquals(false,newOrder.getOrderPriceChanged());
            assertEquals(false,newOrder.getServicePriceChanged());
            if (Constants.ORDER_STATUS_YSWC.equals((newOrder.getOrderStatus()))) {
                assertEquals(completeDate,newOrder.getCompleteDate());
            }
//            if(i%3==0){
                assertEquals(workman.getId(),newOrder.getWorkman().getId());
//            }
        }
        assertEquals(uCount,userDao.count());
        assertEquals(wCount,workmanDao.count());
        assertEquals(oCount+total,orderDao.count());
    }

    @Test
    public void update() throws Exception {
        AutoNumUtil.test = true;
        Random random = new Random();

        List<UserModel> users = userDao.list();
        List<WorkmanModel> workmanModels = workmanDao.list();
        List<OrderModel> orders = orderService.list();
        List<OrderModel> nOrders = randomList(orders);
        int uCount = users.size();
        int wCount = workmanModels.size();
        int oCount = orderDao.count();
        int total = nOrders.size();
        while(total>10){
            total = random.nextInt(total);
        }
        for (int i = 0; i < total; i++) {
            HttpServletRequest request = new MockHttpServletRequest();
            OrderModel oldOrder = nOrders.get(i);
            String oldId = oldOrder.getId();
            String oldOrderNumber = oldOrder.getOrderNumber();
            Date oldCreateDate = oldOrder.getCreateDate();
            Date oldCompleteDate = oldOrder.getCompleteDate();
            int oldServicePrice = oldOrder.getServicePrice();
            int oldOrderPrice = oldOrder.getOrderPrice();
            boolean oldOrderPriceChanged = oldOrder.getOrderPriceChanged();
            boolean oldServicePriceChanged = oldOrder.getServicePriceChanged();
            String oldUserId = null;
            if(oldOrder.getUser()!=null){
                oldUserId = oldOrder.getUser().getId();
            }

            String customerName = "客户名称";
            String customerPhoneNum = "客户电话";
            String customerTel = "备用电话";
            String customerAddress = "客户地址";
            String productInfo = "产品信息";
            String imgUrl = "img";
            String logisticsInfo = "物流信息";
            String repairInfo = "维修信息";
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
            UserModel tu = users.get(random.nextInt(users.size()));
            UserModel user = new UserModel();

            user.setId(tu.getId());
            user.setLoginName(tu.getLoginName());
            user.setPassword("123123");
            user.setLoginName(Constants.USER_LOGIN_NAME_ADMIN);
            if(user.getLoginName().equals(Constants.USER_LOGIN_NAME_ADMIN)){
                user.setPassword(Constants.USER_PASSWORD);
            }
            WorkmanModel workman = new WorkmanModel();
            workman.setId(workmanModels.get(random.nextInt(workmanModels.size())).getId());
            boolean orderPriceChanged = random.nextBoolean();
            boolean servicePriceChanged = random.nextBoolean();
            Date today = DateUtil.randomDate("2014-01-01 00:00:00", "2015-01-01 00:00:00");
            Date completeDate = DateUtil.randomDate("2015-01-01 00:00:00","2016-01-01 00:00:00");
            String workmanId = workman.getId();
            String serviceType = Constants.SERVICE_TYPE_LIST[random.nextInt(2)];
            String orderStatus = Constants.ORDER_STATUS_LIST[random.nextInt(Constants.ORDER_STATUS_LIST.length)];

            UserModel loginUser = new UserModel();
            assertResultSuccess(userController.login(request, user));
//            if(!LoginUtil.getUserPermissionCodes(request).contains(Constants.PERMISSION_CODE_ORDER_MANAGER)){
//                request = new MockHttpServletRequest();
//                assertResultSuccess(userController.login(request, user));
//            }

            Calendar c = Calendar.getInstance();
            c.setTime(today);
            AutoNumUtil.setC(c);
            OrderModel order = new OrderModel();
            order.setOrderStatus(orderStatus);
            order.setServiceType(serviceType);
            order.setCustomerName(customerName + i);
            order.setCustomerPhoneNum(customerPhoneNum + i);
            order.setCustomerTel(customerTel + i);
            order.setCustomerAddress(customerAddress + i);
            order.setProductInfo(productInfo + i);
            order.setProductImgs(imgUrl+i);
            order.setLogisticsInfo(logisticsInfo + i);
            order.setLogisticsImgs(imgUrl+i);
            order.setRepairInfo(repairInfo + i);
            order.setRepairImgs(imgUrl+i);
            order.setChecked(check);
            order.setCheckInfo(checkInfo+ i);
            order.setShopInfo(shopInfo + i);
            order.setOrderPrice(orderPrice);
            order.setServicePrice(servicePrice);
            order.setProfit(profit);
            order.setPriceChangeReason(priceChangeReason + i);
            order.setJudgment(judgment);
            order.setJudgeReason(judgeReason + i);
            order.setDescription(description+ i);
//                if(i%2==0){
//                    order.setUser(user);
//                }
            order.setCreateDate(today);
            order.setOrderPriceChanged(orderPriceChanged);
            order.setServicePriceChanged(servicePriceChanged);
            if (Constants.ORDER_STATUS_YSWC.equals(order.getOrderStatus())) {
                order.setCompleteDate(completeDate);
            }
//                if(i%3==0){
//                    order.setWorkman(workman);
//                }
            Object result = orderController.update(request, oldId,order, workmanId);
            System.out.println(ResultUtil.getResult((Map<String, Object>) result));
            System.out.println(ResultUtil.getMsg((Map<String, Object>) result));
                assertResultSuccess(result);

            OrderModel newOrder = orderDao.get(oldId);
            assertEquals(orderStatus,newOrder.getOrderStatus());
            assertEquals(serviceType,newOrder.getServiceType());
            assertEquals(check,newOrder.getChecked());
            assertEquals(customerName + i,newOrder.getCustomerName());
            assertEquals(customerPhoneNum + i,newOrder.getCustomerPhoneNum());
            assertEquals(customerTel + i,newOrder.getCustomerTel());
            assertEquals(customerAddress + i,newOrder.getCustomerAddress());
            assertEquals(productInfo + i,newOrder.getProductInfo());
            assertEquals(imgUrl+i,newOrder.getProductImgs());
            assertEquals(logisticsInfo + i,newOrder.getLogisticsInfo());
            assertEquals(imgUrl+i,newOrder.getLogisticsImgs());
            assertEquals(repairInfo + i,newOrder.getRepairInfo());
            assertEquals(imgUrl+i,newOrder.getRepairImgs());
            assertEquals(checkInfo+ i,newOrder.getCheckInfo());
            assertEquals(shopInfo + i,newOrder.getShopInfo());
            assertEquals(Integer.valueOf(orderPrice),newOrder.getOrderPrice());
            assertEquals(Integer.valueOf(servicePrice),newOrder.getServicePrice());
            assertEquals(Integer.valueOf(profit),newOrder.getProfit());
            assertEquals(priceChangeReason + i,newOrder.getPriceChangeReason());
            assertEquals(Integer.valueOf(judgment),newOrder.getJudgment());
            assertEquals(judgeReason + i,newOrder.getJudgeReason());
            assertEquals(description+ i,newOrder.getDescription());
//            if(i%2==0){
            if(newOrder.getUser()!=null){
                assertEquals(oldUserId,newOrder.getUser().getId());
                System.out.println(i);
            }
            assertEquals(oldId,newOrder.getId());
            assertEquals(oldOrderNumber,newOrder.getOrderNumber());
            assertEquals(oldCreateDate,newOrder.getCreateDate());
//            }
            assertEquals(oldOrderPrice!=orderPrice || oldOrderPriceChanged,newOrder.getOrderPriceChanged());
            System.out.println(oldServicePrice+":"+servicePrice+":"+oldServicePriceChanged+":"+newOrder.getServicePriceChanged());
            assertEquals(oldServicePrice!=servicePrice || oldServicePriceChanged,newOrder.getServicePriceChanged());
            if (Constants.ORDER_STATUS_YSWC.equals((newOrder.getOrderStatus()))) {
                assertNotEquals(null,newOrder.getCompleteDate());
            }
//            if(i%3==0){
            assertEquals(workman.getId(),newOrder.getWorkman().getId());
//            }
        }
        assertEquals(uCount,userDao.count());
        assertEquals(wCount,workmanDao.count());
        assertEquals(oCount,orderDao.count());
    }

//    @Test
//    public void delete() throws Exception {
//
//    }


//    public int batchUpdate(String name,Object value,String ... ids){
//
//        String updateHql = "update OrderModel set orderStatus = ? where id = ?";
////        String updateHql = "update "+this.entityClass.getSimpleName()+" set "+name+" = ? ";
////        String updateHql = "update tbl_order  set "+name+" = ? where id in ({ids})";
//        String idsStr = "";
//        for(int i=0;i<ids.length;i++){
//            if(i!=0){
//                idsStr+=",";
//            }
//            idsStr+="?";
//        }
//        updateHql = updateHql.replace("{ids}",idsStr);
////        return updateSql(updateHql,ListUtil.concat(value,ids));
//        return executeUpdate(updateHql,ListUtil.concat(value,ids));
////        return executeUpdate(updateHql,value);
//    }
//
//    public int executeUpdate(String hql,Object ... params){
//        Query updateQuery = getSession().createQuery(hql);
//        for(int i=0;i<params.length;i++){
//            updateQuery.setParameter(i,params[i]);
//        }
//        return updateQuery.executeUpdate();
//    }
    @Test
    public void batch() throws Exception {
        Random random = new Random();
        List<OrderModel> orders = orderService.list();
        List<OrderModel> nOrders = randomList(orders,1,10);
        List<OrderModel> nOrders2 = randomList(orders,1,5);
        String ids = getOrderIds(nOrders);
        String[] idList = ids.split(Constants.SPLIT_STRING_IDS);
        String status = Constants.ORDER_STATUS_LIST[random.nextInt(Constants.ORDER_STATUS_LIST.length)];

        assertResultSuccess(orderController.batch(ids,Constants.BATCH_ACTION_UPDATE,status));

        orderService.batchAction(ids, Constants.BATCH_ACTION_UPDATE,status);
        orderDao.batchUpdate("orderStatus",status,idList);
        for(String id:idList){
            System.out.println(orderDao.get(id).getOrderStatus());
//            assertEquals(status,orderDao.get(id).getOrderStatus());
        }
        int oCount = orders.size();
        int dCount = nOrders2.size();
        String ids2 = getOrderIds(nOrders2);
        Object result = orderController.batch(ids2,Constants.BATCH_ACTION_DELETE,null);
        System.out.println(ResultUtil.getMsg((Map<String, Object>) result));
        assertResultSuccess(result);
        System.out.println(dCount);
        assertEquals(oCount-dCount,orderDao.count());
    }
    private String getOrderIds(List<OrderModel> orders){
        String ids = "";
        for(OrderModel order:orders){
            if(StringUtils.isNotBlank(ids)){
                ids+=Constants.SPLIT_STRING_IDS;
            }
            ids+=order.getId();
        }
        return ids;
    }

    @Test
    public void upload() throws Exception {

    }

    @Test
    public void analysis() throws Exception {

    }

}