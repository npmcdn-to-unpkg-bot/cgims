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
import com.mimi.cgims.service.IOrderService;
import com.mimi.cgims.util.AutoNumUtil;
import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.util.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
            List<UserModel> users = userDao.list();
            List<WorkmanModel> workmanModels = workmanDao.list();
            for (UserModel user : users) {
                for (int i = 0; i < 30; i++) {
                    if (Math.random() > 0.9) {
                        break;
                    }
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
                    Random random = new Random();
                    order.setOrderPrice(random.nextInt(300) + 100);
                    order.setServicePrice(order.getOrderPrice() - random.nextInt(100));
                    order.setProfit(order.getOrderPrice() - order.getServicePrice());
                    order.setPriceChangeReason("价格变动原因" + i);
                    order.setJudgment(random.nextInt(5) + 1);
                    order.setJudgeReason("评价理由" + i);
                    order.setDescription("备注" + i);
                    order.setUser(user);
                    order.setCreateDate(new Date());
                    order.setOrderPriceChanged(random.nextBoolean());
                    order.setServicePriceChanged(random.nextBoolean());
                    if (OrderStatus.YSWC.getName().equals(order.getOrderStatus())) {
                        order.setCompleteDate(new Date());
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
    public Object list4Page(String searchKeyword, String orderStatus, String serviceType, String userId, String beginTime, String endTime, String searchKeyword1, int targetPage, int pageSize) {
        int total = orderDao.count(searchKeyword, orderStatus, serviceType, userId, beginTime,endTime);
        targetPage = PageUtil.fitPage(total, targetPage, pageSize);
        List<OrderModel> list = orderDao.list(searchKeyword, orderStatus, serviceType, userId, beginTime,endTime, targetPage, pageSize);
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
    public Object addAndRefresh(OrderModel order) {
        return null;
    }

    @Override
    public void updateAndRefresh(OrderModel order) {

    }

    @Override
    public void deleteAndRefresh(String id) {

    }

    @Override
    public void batchAction(String ids, String action, String orderStatus) {
    }
}
