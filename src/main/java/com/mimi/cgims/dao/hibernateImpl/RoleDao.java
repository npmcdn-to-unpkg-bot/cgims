package com.mimi.cgims.dao.hibernateImpl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IRoleDao;
import com.mimi.cgims.model.RoleModel;
import com.mimi.cgims.util.DaoUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDao extends BaseDao<RoleModel, String>
        implements IRoleDao {

    @Override
    public List<RoleModel> list(String userId, String searchKeyword, int targetPage, int pageSize) {
        Criteria criteria = getCriteria();
        setParams(criteria, userId, searchKeyword);
        List<RoleModel> roles = list(criteria, targetPage, pageSize);
        DaoUtil.cleanLazyDataRoles(roles);
        return roles;
    }

    @Override
    public int count(String userId, String searchKeyword) {
        Criteria criteria = getCriteria();
        setParams(criteria, userId, searchKeyword);
        return count(criteria);
    }

    @Override
    public RoleModel getByName(String name) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("name",name));
        criteria.setMaxResults(1);
        List<RoleModel> list = criteria.list();
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    private void setParams(Criteria criteria, String userId, String searchKeyword) {
        if (StringUtils.isNotBlank(userId)) {
            criteria.createAlias("users", "u")
                    .add(Restrictions.eq("u.id", userId));
        }
//        if (StringUtils.isNotBlank(searchKeyword)) {
//            criteria.add(Restrictions.like("name", "%" + searchKeyword + "%"));
//        }
        if (StringUtils.isNotBlank(searchKeyword)) {
            String[] words = searchKeyword.split(Constants.SPLIT_STRING_KEYWORD);
            for(String word:words){
                if(StringUtils.isNotBlank(word)){
                    criteria.add(Restrictions.like("name", "%" + word.trim() + "%"));
                }
            }
//            criteria.add(Restrictions.or(Restrictions.like("loginName", "%" + searchKeyword + "%"),Restrictions.like("name", "%" + searchKeyword + "%")));
        }
    }


//    @Override
//    public List<RoleModel> list(String searchKeyword, int targetPage, int pageSize) {
//        String hql = HQL_LIST_ALL + appendSql(searchKeyword);
//        return list(hql, targetPage, pageSize, buildParams(searchKeyword).toArray());
//    }
//
//    @Override
//    public int count(String searchKeyword) {
//        String hql = HQL_COUNT_ALL + appendSql(searchKeyword);
//        return count(hql, buildParams(searchKeyword).toArray());
//    }
//
//    private String appendSql(String searchKeyword) {
//        String sql = "";
//        if (StringUtils.isNotBlank(searchKeyword)) {
//            sql = appendSql(sql, "name", "like", "?");
//        }
//        return sql;
//    }
//
//    private List<Object> buildParams(String searchKeyword) {
//        List<Object> list = new ArrayList<>();
//        if (StringUtils.isNotBlank(searchKeyword)) {
//            list.add("%"+searchKeyword+"%");
//        }
//        return list;
//    }
}
