package com.mimi.cgims.service.impl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IBaseDao;
import com.mimi.cgims.dao.IOrderDao;
import com.mimi.cgims.dao.IUserDao;
import com.mimi.cgims.dao.IWorkmanDao;
import com.mimi.cgims.enums.OrderStatus;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.model.WorkmanModel;
import com.mimi.cgims.service.IAutoNumService;
import com.mimi.cgims.service.IOrderService;
import com.mimi.cgims.util.AutoNumUtil;
import com.mimi.cgims.util.DateUtil;
import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.util.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OrderService extends BaseService<OrderModel, String> implements IOrderService {

    private IOrderDao orderDao;

    @Resource
    private IUserDao userDao;

    @Resource
    private IWorkmanDao workmanDao;

    @Resource
    @Override
    public void setBaseDao(IBaseDao<OrderModel, String> baseDao) {
        this.baseDao = baseDao;
        this.orderDao = (IOrderDao) baseDao;
    }

    @Override
    protected void initAction() {
    }

    @Override
    public void initTestData() {
        int count = orderDao.count();
        if (count == 0) {
            Random random = new Random();

            List<UserModel> users = userDao.list();
            List<WorkmanModel> workmanModels = workmanDao.list();
            for (UserModel user : users) {
                int total = random.nextInt(50);
                for (int i = 0; i < total; i++) {
                    Date today = DateUtil.randomDate("2014-01-01 00:00:00","2015-01-01 00:00:00");
                    Calendar c = Calendar.getInstance();
                    c.setTime(today);
                    AutoNumUtil.setC(c);
                    OrderModel order = new OrderModel();
                    order.setOrderNumber(AutoNumUtil.getOrderNum());
                    order.setOrderStatus(OrderStatus.random().getName());
                    if (Math.random() > 0.5) {
                        order.setServiceType(Constants.SERVICE_TYPE_PSAZ);
                    } else {
                        if (Math.random() > 0.5) {
                            order.setServiceType(Constants.SERVICE_TYPE_WX);
                        } else {
                            order.setServiceType(Constants.SERVICE_TYPE_WX + "," + Constants.SERVICE_TYPE_PSAZ);
                        }
                    }
                    order.setCustomerName("客户名称" + i);
                    order.setCustomerPhoneNum("客户电话" + i);
                    order.setCustomerTel("备用电话" + i);
                    order.setCustomerAddress("客户地址" + i);
                    order.setProductInfo("产品信息" + i);
                    order.setProductImgs(randomTestImgs());
                    order.setLogisticsInfo("物流信息" + i);
                    order.setLogisticsImgs(randomTestImgs());
                    order.setRepairInfo("维修信息" + i);
                    order.setRepairImgs(randomTestImgs());
                    order.setChecked(Math.random() > 0.5);
                    order.setCheckInfo("核销信息" + i);
                    order.setShopInfo("商家信息" + i);
                    order.setOrderPrice(random.nextInt(300) + 100);
                    order.setServicePrice(order.getOrderPrice() - random.nextInt(100));
                    order.setProfit(order.getOrderPrice() - order.getServicePrice());
                    order.setPriceChangeReason("价格变动原因" + i);
                    order.setJudgment(random.nextInt(5) + 1);
                    order.setJudgeReason("评价理由" + i);
                    order.setDescription("备注" + i);
                    if(random.nextBoolean()){
                        order.setUser(user);
                    }
                    order.setCreateDate(today);
                    order.setOrderPriceChanged(random.nextBoolean());
                    order.setServicePriceChanged(random.nextBoolean());
                    if (OrderStatus.YSWC.getName().equals(order.getOrderStatus())) {
                        order.setCompleteDate(DateUtil.randomDate("2015-01-01 00:00:00","2016-01-01 00:00:00"));
                    }
                    if(random.nextBoolean()){
                        order.setWorkman(workmanModels.get(random.nextInt(workmanModels.size())));
                    }
                    orderDao.add(order);
                }
            }
        }
    }

    private String randomTestImgs(){
        String imgUrl = "http://pic25.nipic.com/20121112/5955207_224247025000_2.jpg";
        String imgUrl2 = "http://pic14.nipic.com/20110427/2944718_000916112196_2.jpg";
        String imgUrl3 = "http://pic1a.nipic.com/2008-11-13/200811139126243_2.jpg";
        String imgStr = "";
        for (int j = 0; j < 15; j++) {
            double random = Math.random();
            if (random > 0.5) {
                break;
            }
            if (StringUtils.isNotBlank(imgStr)) {
                imgStr += ",";
            }
            if(random > 0.9){
                imgStr += imgUrl;
            }else if(random>0.8){
                imgStr += imgUrl2;
            }else{
                imgStr += imgUrl3;
            }
        }
        return imgStr;
    }

    @Override
    public Object list4Page(String searchKeyword, String orderStatus, String serviceType, String userId,String workmanId,  String beginTime, String endTime, int targetPage, int pageSize) {
        int total = orderDao.count(searchKeyword, orderStatus, serviceType, userId,workmanId, beginTime,endTime);
        targetPage = PageUtil.fitPage(total, targetPage, pageSize);
        List<OrderModel> list = orderDao.list(searchKeyword, orderStatus, serviceType, userId,workmanId, beginTime,endTime, targetPage, pageSize);
        int totalPage = PageUtil.getTotalPage(total, pageSize);
        return ResultUtil.getResultMap(total, totalPage, targetPage, pageSize, list);
    }

    @Override
    public String checkAdd(OrderModel order) {
        // TODO: 2016/7/23  
        return null;
    }

    @Override
    public String checkUpdate(OrderModel order) {
        // TODO: 2016/7/23  
        return null;
    }

    @Override
    public String addAndRefresh(OrderModel order) {
        String id = orderDao.add(order);
        refreshWorkman(order.getWorkman());
        return id;
    }

    @Override
    public void updateAndRefresh(OrderModel order) {
        orderDao.update(order);
        refreshWorkman(order.getWorkman());
    }

    @Override
    public void deleteAndRefresh(String id) {
        OrderModel order = orderDao.get(id);
        WorkmanModel workman = order.getWorkman();
        orderDao.delete(id);
        refreshWorkman(workman);
    }

    private void refreshWorkman(WorkmanModel workman){
        if(workman!=null){
            List<OrderModel> orders = orderDao.list(null, null, null, null,workman.getId(), null,null, PageUtil.BEGIN_PAGE, PageUtil.MAX_PAGE_SIZE);
            int count = 0;
            int sum = 0;
            for(OrderModel tOrder:orders){
                if(tOrder.getJudgment()!=null){
                    count++;
                    sum+=tOrder.getJudgment();
                }
            }
            float judgment = 0;
            if(count>0){
                judgment = (float)sum/count;
            }
            WorkmanModel nw = workmanDao.get(workman.getId());
            nw.setCooperateTimes(count);
            nw.setScore(judgment);
            workmanDao.update(nw);
        }
    }

    @Override
    public void batchAction(String ids, String action, String orderStatus) {
        // TODO: 2016/7/26
    }

    @Override
    public int analysisOrderCount(String creatorId, String serviceType, String beginTime, String endTime) {
        return orderDao.analysisOrderCount(creatorId,serviceType,beginTime,endTime);
    }

    @Override
    public int analysisIncome(String creatorId, String serviceType, String beginTime, String endTime) {
        return orderDao.analysisIncome(creatorId,serviceType,beginTime,endTime);
    }

    @Override
    public int analysisExpenditure(String creatorId, String serviceType, String beginTime, String endTime) {
        return orderDao.analysisExpenditure(creatorId,serviceType,beginTime,endTime);
    }

    @Override
    public int analysisProfit(String creatorId, String serviceType, String beginTime, String endTime) {
        return orderDao.analysisIncome(creatorId,serviceType,beginTime,endTime) - orderDao.analysisExpenditure(creatorId,serviceType,beginTime,endTime);
    }

    @Override
    public float analysisProfitMargin(String creatorId, String serviceType, String beginTime, String endTime) {
        int income = orderDao.analysisIncome(creatorId,serviceType,beginTime,endTime);
        int expenditure = orderDao.analysisExpenditure(creatorId,serviceType,beginTime,endTime);

        return (float)(income-expenditure)/income;
    }

    @Override
    public int getNewestCount(int year, int month, int day) {
        OrderModel order = orderDao.getNewest(year,month,day);
        if(order!=null){
            return Integer.parseInt(order.getOrderNumber().substring(8));
        }
        return 0;
    }
}
