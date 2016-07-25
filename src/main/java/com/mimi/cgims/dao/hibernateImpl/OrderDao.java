package com.mimi.cgims.dao.hibernateImpl;

import com.mimi.cgims.dao.IOrderDao;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.util.DaoUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao extends BaseDao<OrderModel, String>
        implements IOrderDao {

    @Override
    public int count(String searchKeyword, String orderStatus, String serviceType, String userId, String beginTime, String endTime) {
        Criteria criteria = getCriteria();
        setParams(criteria, searchKeyword, orderStatus, serviceType, userId, beginTime,endTime);
        return count(criteria);
    }

    @Override
    public List<OrderModel> list(String searchKeyword, String orderStatus, String serviceType, String userId, String beginTime, String endTime, int targetPage, int pageSize) {
        Criteria criteria = getCriteria();
        setParams(criteria, searchKeyword, orderStatus, serviceType, userId, beginTime,endTime);
        List<OrderModel> orders = list(criteria, targetPage, pageSize);
        DaoUtil.cleanLazyDataOrders(orders);
        return orders;
    }

    private void setParams(Criteria criteria, String searchKeyword, String orderStatus, String serviceType, String userId, String beginTime, String endTime) {
        if (StringUtils.isNotBlank(searchKeyword)) {
            String keyword = "%" + searchKeyword.trim() + "%";
            criteria.add(Restrictions.or(Restrictions.like("name", keyword), Restrictions.like("phoneNum", keyword)));
        }
        if (StringUtils.isNotBlank(orderStatus)) {
            criteria.add(Restrictions.eq("orderStatus",orderStatus));
        }
        if (StringUtils.isNotBlank(serviceType)) {
            criteria.add(Restrictions.like("serviceType","%"+serviceType+"%"));
        }
        if (StringUtils.isNotBlank(userId)) {
            criteria.createAlias("user", "u")
                    .add(Restrictions.eq("u.id", userId));
        }
        if(StringUtils.isNotBlank(beginTime)){
            criteria.add(Restrictions.ge("createTime",beginTime));
        }
        if(StringUtils.isNotBlank(endTime)){
            criteria.add(Restrictions.lt("createTime",endTime));
        }
    }
}
