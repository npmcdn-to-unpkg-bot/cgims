package com.mimi.cgims.dao.hibernateImpl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IWorkmanDao;
import com.mimi.cgims.model.WorkmanModel;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkmanDao extends BaseDao<WorkmanModel, String>
        implements IWorkmanDao {

    @Override
    public int count(String searchKeyword, String province, String city, String area, String serviceType) {
        Criteria criteria = getCriteria();
        setParams(criteria, searchKeyword, province, city, area, serviceType);
        return count(criteria);
    }

    @Override
    public List<WorkmanModel> list(String searchKeyword, String province, String city, String area, String serviceType, int targetPage, int pageSize) {
        Criteria criteria = getCriteria();
        setParams(criteria, searchKeyword, province, city, area, serviceType);
        List<WorkmanModel> workmans = list(criteria, targetPage, pageSize);
        return workmans;
    }

    private void setParams(Criteria criteria, String searchKeyword, String province, String city, String area, String serviceType) {
        if (StringUtils.isNotBlank(searchKeyword)) {
            String keyword = "%" + searchKeyword.trim() + "%";
            criteria.add(Restrictions.or(Restrictions.like("name", keyword), Restrictions.like("phoneNum", keyword)));
        }
        if (StringUtils.isNotBlank(province)) {
            criteria.add(Restrictions.eq("province",province));
        }
        if (StringUtils.isNotBlank(city)) {
            criteria.add(Restrictions.eq("city",city));
        }
        if (StringUtils.isNotBlank(area)) {
            criteria.add(Restrictions.eq("area",area));
        }
        if (StringUtils.isNotBlank(serviceType)) {
            criteria.add(Restrictions.like("serviceType","%"+serviceType+"%"));
        }
//        if(serviceType!=-1){
//            criteria.add(Restrictions.eq("serviceType",serviceType));
//        }
    }
}
