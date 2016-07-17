package com.mimi.sxp.model.query;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;

import com.mimi.sxp.util.pageUtil.PageUtil;

public class BaseQueryModel {
	private String searchKeyword;
	private Integer targetPage = PageUtil.BEGIN_PAGE;// 从1开始
	private Integer pageSize = PageUtil.MAX_PAGE_SIZE;

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public Integer getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(Integer targetPage) {
		if (targetPage == null) {
			return;
		}
		this.targetPage = targetPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize == null) {
			return;
		}
		this.pageSize = pageSize;
	}
	
	public FullTextQuery setSort(FullTextQuery fullTextQuery){
        //按发布时间排倒序
        Sort sort =
        		new Sort(new SortField(
				"updateDate", SortField.Type.LONG, true));
        fullTextQuery.setSort(sort);
		return fullTextQuery;
	}

	public org.apache.lucene.search.Query setParameters(
			org.hibernate.search.query.dsl.QueryBuilder qb) {
		org.apache.lucene.search.Query luceneQuery;
		if (StringUtils.isNotBlank(getSearchKeyword())) {
//			luceneQuery = qb.keyword().onFields("name", "description")
//					.matching(getSearchKeyword()).createQuery();
			luceneQuery = qb.keyword().onField("name")
					.matching(getSearchKeyword()).createQuery();
		} else {
			luceneQuery = qb.all().createQuery();
		}
		return luceneQuery;
	}

	public Criteria setParameters(Criteria criteria) {
		criteria.addOrder(Order.desc("updateDate"));
		if (StringUtils.isNotBlank(getSearchKeyword())) {
			criteria.add(Restrictions.like("name", "%" + getSearchKeyword()
					+ "%"));
		}
		return criteria;
	}

}
