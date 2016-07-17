package com.mimi.sxp.model.query;

import org.apache.commons.lang.StringUtils;


public class InformationQueryModel extends BasePriorityQueryModel {

	public org.apache.lucene.search.Query setParameters(
			org.hibernate.search.query.dsl.QueryBuilder qb) {
		org.apache.lucene.search.Query luceneQuery;
		if (StringUtils.isNotBlank(getSearchKeyword())) {
			luceneQuery = qb.keyword().onFields("name", "contentText")
					.matching(getSearchKeyword()).createQuery();
		} else {
			luceneQuery = qb.all().createQuery();
		}
		return luceneQuery;
	}
}
