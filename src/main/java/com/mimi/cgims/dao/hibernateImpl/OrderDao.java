package com.mimi.cgims.dao.hibernateImpl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IOrderDao;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.util.DaoUtil;
import com.mimi.cgims.util.DateUtil;
import com.mimi.cgims.util.ListUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class OrderDao extends BaseDao<OrderModel, String>
        implements IOrderDao {

    @Override
    public int count(String searchKeyword, String orderStatus, String serviceType, String userId, String workmanId, String beginTime, String endTime) {
        Criteria criteria = getCriteria();
        setParams(criteria, searchKeyword, orderStatus, serviceType, userId, workmanId, beginTime, endTime);
        return count(criteria);
    }

    @Override
    public List<OrderModel> list(String searchKeyword, String orderStatus, String serviceType, String userId, String workmanId, String beginTime, String endTime, int targetPage, int pageSize) {
        Criteria criteria = getCriteria();
        setParams(criteria, searchKeyword, orderStatus, serviceType, userId, workmanId, beginTime, endTime);
        criteria.addOrder(Order.desc("orderNumber"));
        List<OrderModel> orders = list(criteria, targetPage, pageSize);
        DaoUtil.cleanLazyDataOrders(orders);
        return orders;
    }

    @Override
    public int analysisOrderCount(String creatorId, String serviceType, String beginTime, String endTime) {
        Criteria criteria = getCriteria();
        setAnalysisParams(criteria, creatorId, serviceType, beginTime, endTime);
        return count(criteria);
    }

    @Override
    public int analysisIncome(String creatorId, String serviceType, String beginTime, String endTime) {
        Criteria criteria = getCriteria();
        setAnalysisParams(criteria, creatorId, serviceType, beginTime, endTime);
        return sum(criteria, "orderPrice");
    }

    @Override
    public int analysisExpenditure(String creatorId, String serviceType, String beginTime, String endTime) {
        Criteria criteria = getCriteria();
        setAnalysisParams(criteria, creatorId, serviceType, beginTime, endTime);
        return sum(criteria, "servicePrice");
    }

    @Override
    public void cleanUserId(String userId) {
        String sql = "update tbl_order set user_id = ? where user_id = ?";
        updateSql(sql, null, userId);
    }

    @Override
    public void cleanWorkmanId(String workmanId) {
        String sql = "update tbl_order set workman_id = ? where workman_id = ?";
        updateSql(sql, null, workmanId);
    }

    @Override
    public OrderModel getNewest(int year, int month, int day) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.like("orderNumber", DateUtil.convert2String(year, month, day) + "%"));
        criteria.addOrder(Order.desc("orderNumber"));
        criteria.setMaxResults(1);
        List<OrderModel> list = criteria.list();
        if (ListUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    private void setAnalysisParams(Criteria criteria, String creatorId, String serviceType, String beginTime, String endTime) {
        if (StringUtils.isNotBlank(serviceType)) {
            criteria.add(Restrictions.like("serviceType", "%" + serviceType + "%"));
        }
        if (StringUtils.isNotBlank(creatorId)) {
            criteria.createAlias("user", "u")
                    .add(Restrictions.eq("u.id", creatorId));
        }
        if (StringUtils.isNotBlank(beginTime)) {
            criteria.add(Restrictions.ge("completeDate", DateUtil.toDate(beginTime)));
//            criteria.add(Restrictions.ge("completeDate", new java.util.Date()));
        }
        if (StringUtils.isNotBlank(endTime)) {
            criteria.add(Restrictions.le("completeDate", DateUtil.toDate(endTime)));
//            criteria.add(Restrictions.lt("completeDate", endTime));
        }
        criteria.add(Restrictions.eq("orderStatus", Constants.ORDER_STATUS_YSWC));
    }

    private void setParams(Criteria criteria, String searchKeyword, String orderStatus, String serviceType, String userId, String workmanId, String beginTime, String endTime) {
        if (StringUtils.isNotBlank(searchKeyword)) {
            String keyword = "%" + searchKeyword.trim() + "%";
            criteria.add(Restrictions.or(Restrictions.like("orderNumber", keyword), Restrictions.like("customerName", keyword), Restrictions.like("customerPhoneNum", keyword), Restrictions.like("customerTel", keyword), Restrictions.like("customerAddress", keyword),
                    Restrictions.like("productInfo", keyword), Restrictions.like("logisticsInfo", keyword), Restrictions.like("repairInfo", keyword), Restrictions.like("checkInfo", keyword), Restrictions.like("shopInfo", keyword)));
        }

        if (StringUtils.isNotBlank(orderStatus)) {
            criteria.add(Restrictions.in("orderStatus", orderStatus.split(Constants.SPLIT_STRING_PARAMS)));
        }
        if (StringUtils.isNotBlank(serviceType)) {
            criteria.add(Restrictions.like("serviceType", "%" + serviceType + "%"));
        }
        if (StringUtils.isNotBlank(userId)) {
            criteria.createAlias("user", "u")
                    .add(Restrictions.eq("u.id", userId));
        }
        if (StringUtils.isNotBlank(workmanId)) {
            criteria.createAlias("workman", "w")
                    .add(Restrictions.eq("w.id", workmanId));
        }
        if (StringUtils.isNotBlank(beginTime)) {
            criteria.add(Restrictions.ge("createDate", DateUtil.toDate(beginTime)));
        }
        if (StringUtils.isNotBlank(endTime)) {
            criteria.add(Restrictions.le("createDate", DateUtil.toDate(endTime)));
        }
    }
}
