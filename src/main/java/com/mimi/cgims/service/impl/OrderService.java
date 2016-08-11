package com.mimi.cgims.service.impl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IBaseDao;
import com.mimi.cgims.dao.IOrderDao;
import com.mimi.cgims.dao.IUserDao;
import com.mimi.cgims.dao.IWorkmanDao;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.model.WorkmanModel;
import com.mimi.cgims.service.IOrderService;
import com.mimi.cgims.util.*;
import com.mimi.cgims.util.page.PageUtil;
import org.apache.commons.lang.StringUtils;
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
//                    order.setOrderStatus(OrderStatus.random().getName());
                    order.setOrderStatus(Constants.ORDER_STATUS_LIST[random.nextInt(Constants.ORDER_STATUS_LIST.length)]);
                    if (Math.random() > 0.5) {
                        order.setServiceType(Constants.SERVICE_TYPE_PSAZ);
                    } else {
                        order.setServiceType(Constants.SERVICE_TYPE_WX);
//                        if (Math.random() > 0.5) {
//                            order.setServiceType(Constants.SERVICE_TYPE_WX);
//                        } else {
//                            order.setServiceType(Constants.SERVICE_TYPE_WX + "," + Constants.SERVICE_TYPE_PSAZ);
//                        }
                    }
                    order.setCustomerName("客户名称" + i);
                    order.setCustomerPhoneNum("客户电话" + i);
                    order.setCustomerTel("备用电话" + i);
                    order.setCustomerAddress("客户地址 爱上了咖啡就上课了发动机安乐死的" + i);
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
                    order.setDescription("备注发神经的饭卡上对方来看阿斯兰的发生的附件案发生的离开房间" + i);
                    if(random.nextBoolean()){
                        order.setUser(user);
                    }
                    order.setCreateDate(today);
                    order.setOrderPriceChanged(random.nextBoolean());
                    order.setServicePriceChanged(random.nextBoolean());
//                    if (OrderStatus.YSWC.getName().equals(order.getOrderStatus())) {
                    if (Constants.ORDER_STATUS_YSWC.equals(order.getOrderStatus())) {
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
    public OrderModel get(String id){
        OrderModel order = orderDao.get(id);
        DaoUtil.cleanLazyData(order);
        return order;
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
        List<String> errors = commonCheck(order, true);
        if (errors.isEmpty()) {
            return null;
        }
        return errors.get(0);
    }

    @Override
    public String checkUpdate(OrderModel order) {
        List<String> errors = commonCheck(order, false);
        if (errors.isEmpty()) {
            return null;
        }
        return errors.get(0);
    }
    private List<String> commonCheck(OrderModel order, boolean isAdd) {
        List<String> errors = new ArrayList<>();
        String error;
        if(order == null){
            errors.add("内容为空");
            return errors;
        }
        error = FormatUtil.checkFormat(order.getOrderNumber(), FormatUtil.REGEX_ORDER_NUMBER, true, 0, FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "订单号");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        if(!ListUtil.contains(Constants.ORDER_STATUS_LIST,order.getOrderStatus())){
            errors.add("未知订单状态");
        }
        if(!ListUtil.contains(Constants.SERVICE_TYPE_LIST,order.getServiceType())){
            errors.add("未知订单类型");
        }
        error = FormatUtil.checkLengthOnly(order.getCustomerName(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "客户名称");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getCustomerPhoneNum(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "客户电话");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getCustomerTel(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "备用电话");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getCustomerAddress(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "客户地址");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getProductInfo(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L3, "产品信息");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getProductImgs(), FormatUtil.MAX_LENGTH_COMMON_LONG_L6, "产品图片");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getLogisticsInfo(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L3, "物流信息");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getLogisticsImgs(), FormatUtil.MAX_LENGTH_COMMON_LONG_L6, "物流图片");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getRepairInfo(), FormatUtil.MAX_LENGTH_COMMON_LONG_L1, "维修信息");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getRepairImgs(), FormatUtil.MAX_LENGTH_COMMON_LONG_L6, "维修图片");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        if(order.getChecked()==null){
            errors.add("核销选项缺失");
        }
        error = FormatUtil.checkLengthOnly(order.getCheckInfo(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "核销信息");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getShopInfo(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "商家信息");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getQq(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "QQ");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getPriceChangeReason(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L2, "价格变动原因");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getJudgeReason(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L2, "评价备注");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(order.getDescription(), FormatUtil.MAX_LENGTH_COMMON_LONG_L1, "备注");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        if(order.getCreateDate()==null){
            errors.add("创建日期缺失");
        }

        return errors;
    }

    @Override
    public String addAndRefresh(OrderModel order) {
        String id = orderDao.add(order);
        refreshWorkman(order.getWorkman());
        return id;
    }

    @Override
    public void updateAndRefresh(OrderModel order,String oldWorkmanId) {
        orderDao.update(order);
        if(order.getWorkman()!=null && !order.getWorkman().getId().equals(oldWorkmanId)){
            refreshWorkman(order.getWorkman());
        }
        refreshWorkman(oldWorkmanId);
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
            refreshWorkman(workman.getId());
        }
    }
    private void refreshWorkman(String workmanId){
        if(StringUtils.isNotBlank(workmanId)){
            List<OrderModel> orders = orderDao.list(null, null, null, null,workmanId, null,null, PageUtil.BEGIN_PAGE, PageUtil.MAX_PAGE_SIZE);
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
            WorkmanModel nw = workmanDao.get(workmanId);
            nw.setCooperateTimes(count);
            nw.setScore(judgment);
            workmanDao.update(nw);
        }
    }

    @Override
    public void batchAction(String ids, String action, String orderStatus) {
        if(StringUtils.isBlank(ids)){
            return;
        }
        if(Constants.BATCH_ACTION_UPDATE.equals(action) || ListUtil.contains(Constants.ORDER_STATUS_LIST,orderStatus)){
            orderDao.batchUpdate("orderStatus",orderStatus,ids.split(Constants.SPLIT_STRING_IDS));
        }
        if(Constants.BATCH_ACTION_DELETE.equals(action)){
            List<String> workmanIds = new ArrayList<>();
            for(String id:ids.split(Constants.SPLIT_STRING_IDS)){
                OrderModel order = orderDao.get(id);
                if(order.getWorkman()!=null && StringUtils.isNotBlank(order.getWorkman().getId()) && !workmanIds.contains(order.getWorkman().getId())){
                    workmanIds.add(order.getWorkman().getId());
                }
            }
            orderDao.batchDelete(ids.split(Constants.SPLIT_STRING_IDS));
            workmanIds.forEach(this::refreshWorkman);
        }
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
        if(income==0){
            return 0;
        }
        return (float)(income-expenditure)/income;
    }

    @Override
    public int analysisIncomeP(String creatorId, String serviceType, String beginTime, String endTime) {
        int income = orderDao.analysisIncome(creatorId,serviceType,beginTime,endTime);
        int count = orderDao.analysisOrderCount(creatorId,serviceType,beginTime,endTime);
        if(count==0){
            return 0;
        }
        return income/count;
    }

    @Override
    public int analysisProfitP(String creatorId, String serviceType, String beginTime, String endTime) {
        int income = orderDao.analysisIncome(creatorId,serviceType,beginTime,endTime);
        int expenditure = orderDao.analysisExpenditure(creatorId,serviceType,beginTime,endTime);
        int count = orderDao.analysisOrderCount(creatorId,serviceType,beginTime,endTime);
        if(count==0){
            return 0;
        }
        return (income-expenditure)/count;
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
