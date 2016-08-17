package com.mimi.cgims.dao.hibernateImpl;

import com.mimi.cgims.dao.IBaseDao;
import com.mimi.cgims.util.ListUtil;
import com.mimi.cgims.util.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

public abstract class BaseDao<M extends Serializable, PK extends Serializable>
        implements IBaseDao<M, PK> {

    protected static final Logger LOG = LoggerFactory
            .getLogger(BaseDao.class);
    private final Class<M> entityClass;
    public final String HQL_LIST_ALL;
    public final String HQL_COUNT_ALL;
    private String pkName = null;

    @SuppressWarnings("unchecked")
    public BaseDao() {
        this.entityClass = (Class<M>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        Field[] fields = this.entityClass.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Id.class)) {
                this.pkName = f.getName();
            }
        }

        HQL_LIST_ALL = "from " + this.entityClass.getSimpleName();
        HQL_COUNT_ALL = " select count(*) from "
                + this.entityClass.getSimpleName();
    }

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public Session getSession() {
        // 事务必须是开启的(Required)，否则获取不到
//        return sessionFactory.getCurrentSession();
        Session session =  sessionFactory.getCurrentSession();
//        session.setFlushMode(FlushMode.ALWAYS);
        return session;
    }

    public int count() {
        return count(getCriteria());
    }

    public List<M> list() {
        return list(getCriteria(), PageUtil.BEGIN_PAGE, PageUtil.MAX_PAGE_SIZE);
    }

    public M get(PK id) {
        return getSession().get(this.entityClass, id);
    }

    public PK add(M model) {
        return (PK) getSession().save(model);
    }

    public void update(M model) {
        getSession().update(model);
    }

    public void delete(PK id) {
        batchDelete(id);
    }

    public int batchDelete(PK ... ids){
        String deleteHql = "delete "+this.entityClass.getSimpleName()+" model where model.id in ({ids})";
        String idsStr = "";
        for(int i=0;i<ids.length;i++){
            if(i!=0){
                idsStr+=",";
            }
            idsStr+="?";
        }
        deleteHql = deleteHql.replace("{ids}",idsStr);
        return executeUpdate(deleteHql,ids);
    }

    public int batchUpdate(String name,Object value,PK ... ids){
        String updateHql = "update "+this.entityClass.getSimpleName()+" set "+name+" = ? where id in ({ids})";
        String idsStr = "";
        for(int i=0;i<ids.length;i++){
            if(i!=0){
                idsStr+=",";
            }
            idsStr+="?";
        }
        updateHql = updateHql.replace("{ids}",idsStr);
        return executeUpdate(updateHql,ListUtil.concat(value,ids));
    }

    public int executeUpdate(String hql,Object ... params){
        Query updateQuery = getSession().createQuery(hql);
        for(int i=0;i<params.length;i++){
            updateQuery.setParameter(i,params[i]);
        }
        return updateQuery.executeUpdate();
    }



//    public int count() {
//        return count(HQL_COUNT_ALL);
//    }
//
//    public List<M> list() {
//        return list(HQL_LIST_ALL, PageUtil.BEGIN_PAGE, PageUtil.MAX_PAGE_SIZE);
//    }
//    protected int count(final String hql, final Object... paramlist) {
//        Query query = getSession().createQuery(hql);
//        setParameters(query, paramlist);
//        return ((Long) query.uniqueResult()).intValue();
//    }

//    @SuppressWarnings("unchecked")
//    protected List<M> list(final String hql, final int targetPage,
//                           final int pageSize, final Object... paramlist) {
//        Query query = getSession().createQuery(hql);
//        setParameters(query, paramlist);
//        if (targetPage > -1 && pageSize > -1) {
//            query.setMaxResults(pageSize);
//            int start = PageUtil.getPageStart(targetPage, pageSize);
//            query.setFirstResult(start);
//        }
//        if (targetPage < 0) {
//            query.setFirstResult(0);
//        }
//        return query.list();
//    }
//
//    protected void setParameters(Query query, Object[] paramlist) {
//        if (paramlist != null) {
//            for (int i = 0; i < paramlist.length; i++) {
//                if (paramlist[i] instanceof Date) {
//                    // TODO 难道这是bug 使用setParameter不行？？
//                    query.setTimestamp(i, (Date) paramlist[i]);
//                } else {
//                    query.setParameter(i, paramlist[i]);
//                }
//            }
//        }
//    }

    public int updateSql(String sql,Object ... args){
        Query query = getSession().createSQLQuery(sql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i, args[i]);
        }
        return query.executeUpdate();
    }

    public List querySql(String sql, Object... args) {
        Query query = getSession().createSQLQuery(sql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i, args[i]);
        }
        return query.list();
    }


    protected String appendSql(String sql, String key, String condition, String value) {
        if (StringUtils.isBlank(sql)) {
            sql += " where ";
        } else {
            sql += " and ";
        }
        return sql + " " + key + " " + condition + " " + value;
    }

    protected Criteria getCriteria() {
        return getSession().createCriteria(this.entityClass);
    }

    protected List<M> list(Criteria criteria,int targetPage,int pageSize){
        int start = PageUtil.getPageStart(targetPage,pageSize);
        if (start != 0) {
            criteria.setFirstResult(start);
        }
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    protected int count(Criteria criteria){
        criteria.setProjection(Projections.rowCount());
        Integer count = ((Long)criteria.uniqueResult()).intValue();
        return count;
    }

    protected int sum(Criteria criteria,String name){
        criteria.setProjection(Projections.sum(name));
        Object result = criteria.uniqueResult();
        if(result==null){
            return 0;
        }
        Integer count = ((Long)result).intValue();
        return count;
    }
}
