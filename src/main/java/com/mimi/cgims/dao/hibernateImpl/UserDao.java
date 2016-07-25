package com.mimi.cgims.dao.hibernateImpl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IUserDao;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.util.DaoUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends BaseDao<UserModel, String>
        implements IUserDao {

    @Override
    public List<UserModel> list(String searchKeyword, int targetPage, int pageSize) {
        Criteria criteria = getCriteria();
        setParams(criteria, searchKeyword);
        List<UserModel> users = list(criteria, targetPage, pageSize);
        DaoUtil.cleanLazyDataUsers(users);
        return users;
    }

    @Override
    public int count(String searchKeyword) {
        Criteria criteria = getCriteria();
        setParams(criteria, searchKeyword);
        return count(criteria);
    }

    @Override
    public UserModel getByLoginName(String loginName) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("loginName",loginName));
        criteria.setMaxResults(1);
        List<UserModel> list = criteria.list();
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    private void setParams(Criteria criteria, String searchKeyword) {
        if (StringUtils.isNotBlank(searchKeyword)) {
            String[] words = searchKeyword.split(Constants.SPLIT_STRING_KEYWORD);
            for(String word:words){
                if(StringUtils.isNotBlank(word)){
                    criteria.add(Restrictions.or(Restrictions.like("loginName", "%" + word.trim() + "%"),Restrictions.like("name", "%" + word.trim() + "%")));
                }
            }
//            criteria.add(Restrictions.or(Restrictions.like("loginName", "%" + searchKeyword + "%"),Restrictions.like("name", "%" + searchKeyword + "%")));
        }
    }

//    @Override
//    public List<UserModel> list(String searchKeyword, int targetPage, int pageSize) {
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
//            list.add("%" + searchKeyword + "%");
//        }
//        return list;
//    }
}
