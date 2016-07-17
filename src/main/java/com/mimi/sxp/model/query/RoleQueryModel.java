package com.mimi.sxp.model.query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.mimi.sxp.Constants;


public class RoleQueryModel extends BaseQueryModel {

	private String userId;
	
	private boolean excludedAdmin = false;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isExcludedAdmin() {
		return excludedAdmin;
	}

	public void setExcludedAdmin(boolean excludedAdmin) {
		this.excludedAdmin = excludedAdmin;
	}

	public Criteria setParameters(Criteria criteria) {
		super.setParameters(criteria);
		if(StringUtils.isNotBlank(getUserId())){
			criteria.createAlias("users", "u")  
		    .add(Restrictions.eq("u.id", getUserId()));  
		}
		if(isExcludedAdmin()){
			criteria.add(Restrictions.ne("name", Constants.ROLE_DEFAULT_ADMIN_NAME));
		}
		return criteria;
	}
    
}
