package com.mimi.cgims.dao.hibernateImpl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IPermissionDao;
import com.mimi.cgims.model.PermissionModel;
import com.mimi.cgims.util.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PermissionDao extends BaseDao<PermissionModel, String>
        implements IPermissionDao {
    @Override
    public int count(String roleId,String searchKeyword){
        Criteria criteria = getSession().createCriteria(PermissionModel.class);
        setParams(criteria,roleId,searchKeyword);
        criteria.setProjection(Projections.rowCount());
        Integer count = ((Long)criteria.uniqueResult()).intValue();
        return count;
    }

    @Override
    public List<PermissionModel> list(String roleId,String searchKeyword,int targetPage,int pageSize){
        Criteria criteria = getSession().createCriteria(PermissionModel.class);
        setParams(criteria,roleId,searchKeyword);
        int start = PageUtil.getPageStart(targetPage,pageSize);
        if (start != 0) {
            criteria.setFirstResult(start);
        }
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    @Override
    public List<PermissionModel> list(String userId){
        Criteria criteria = getSession().createCriteria(PermissionModel.class);
        if(StringUtils.isNotBlank(userId)){
            criteria.createAlias("roles", "r").createAlias("r.users","u")
                    .add(Restrictions.eq("u.id", userId));
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<PermissionModel> list = criteria.list();
        return list;
//        return criteria.list();
    }




    private void setParams(Criteria criteria,String roleId,String searchKeyword){
        if(StringUtils.isNotBlank(roleId)){
            criteria.createAlias("roles", "r")
                    .add(Restrictions.eq("r.id", roleId));
        }
//        if(StringUtils.isNotBlank(searchKeyword)){
//            criteria.add(Restrictions.like("name","%"+searchKeyword+"%"));
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
//    public List<PermissionModel> list(String searchKeyword, int targetPage, int pageSize) {
//        String hql = HQL_LIST_ALL + appendSql(searchKeyword);
//        return list(hql, targetPage, pageSize, buildParams(searchKeyword).toArray());
//    }
//    @Override
//    public int count(String searchKeyword) {
//        String hql = HQL_COUNT_ALL + appendSql(searchKeyword);
//        return count(hql, buildParams(searchKeyword).toArray());
//    }
//
//    @Override
//    public List<PermissionModel> listByRoleId(String roleId, int targetPage, int pageSize) {
//        Criteria criteria = getSession().createCriteria(PermissionModel.class);
//        if(StringUtils.isNotBlank(roleId)){
//            criteria.createAlias("roles", "r")
//                    .add(Restrictions.eq("r.id", roleId));
//        }
//        int start = PageUtil.getPageStart(targetPage,pageSize);
//        if (start != 0) {
//            criteria.setFirstResult(start);
//        }
//        criteria.setMaxResults(pageSize);
//        return criteria.list();
//    }
//    @Override
//    public List<PermissionModel> list(String searchKeyword, int targetPage, int pageSize) {
//        String hql = HQL_LIST_ALL + appendSql(searchKeyword);
//        return list(hql, targetPage, pageSize, buildParams(searchKeyword).toArray());
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
