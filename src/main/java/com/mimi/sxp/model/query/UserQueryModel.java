package com.mimi.sxp.model.query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.mimi.sxp.Constants;

public class UserQueryModel extends BaseQueryModel {
	private String loginName;
	private String excludedId;
	private String[] includedIds;
	private boolean excludedAdmin = false;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getExcludedId() {
		return excludedId;
	}

	public void setExcludedId(String excludedId) {
		this.excludedId = excludedId;
	}

	public boolean isExcludedAdmin() {
		return excludedAdmin;
	}

	public void setExcludedAdmin(boolean excludedAdmin) {
		this.excludedAdmin = excludedAdmin;
	}

	public String[] getIncludedIds() {
		return includedIds;
	}

	public void setIncludedIds(String[] includedIds) {
		this.includedIds = includedIds;
	}

	public Criteria setParameters(Criteria criteria) {
		super.setParameters(criteria);
		if (StringUtils.isNotBlank(getLoginName())) {
			criteria.add(Restrictions.eq("name", getLoginName()));
		}
		if (StringUtils.isNotBlank(getExcludedId())) {
			criteria.add(Restrictions.ne("id", getExcludedId()));
		}
		if(isExcludedAdmin()){
			criteria.add(Restrictions.ne("name", Constants.USER_DEFAULT_ADMIN_NAME));
		}
		if(getIncludedIds()!=null && getIncludedIds().length>0){
			criteria.add(Restrictions.in("id", getIncludedIds()));
		}
		return criteria;
	}
}
