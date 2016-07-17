package com.mimi.sxp.dao.hibernateImpl;

import org.springframework.stereotype.Repository;

import com.mimi.sxp.dao.IUserDao;
import com.mimi.sxp.model.UserModel;
import com.mimi.sxp.model.query.UserQueryModel;

@Repository("UserDao")
public class UserDao extends BaseDao<UserModel, String,UserQueryModel>
		implements IUserDao {
//	public List<UserModel> aa(){
//		FullTextSession fullTextSession = Search.getFullTextSession(getSession());  
//		org.hibernate.search.query.dsl.QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(UserModel.class).get();
//		org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("name","email","description","roles.name").matching("采编").createQuery();
//		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery);
//		fullTextQuery.getResultSize();
//		fullTextQuery.setFirstResult(0);
//		fullTextQuery.setMaxResults(10);
//		List<UserModel> result = fullTextQuery.list(); 
//		return result;
//	}

}
