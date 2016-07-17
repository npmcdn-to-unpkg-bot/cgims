package com.mimi.sxp.model.query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


public class PermissionQueryModel extends BaseQueryModel {

	private String userId;

	private String roleId;

	private String nameLike;
	
	private String codeLike;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getCodeLike() {
		return codeLike;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public Criteria setParameters(Criteria criteria) {
		super.setParameters(criteria);
		if(StringUtils.isNotBlank(getNameLike())){
			criteria.add( Restrictions.like("name", "%"+getNameLike()+"%"));
		}
		if(StringUtils.isNotBlank(getCodeLike())){
			criteria.add( Restrictions.like("code", "%"+getCodeLike()+"%"));
		}
//		if(StringUtils.isNotBlank(getUserId())){
//			criteria.createAlias("users", "u")  
//		    .add(Restrictions.eq("u.id", getUserId()));  
//		}
		if(StringUtils.isNotBlank(getRoleId())){
			criteria.createAlias("roles", "r")  
		    .add(Restrictions.eq("r.id", getRoleId()));  
		}else if(StringUtils.isNotBlank(getUserId())){
			criteria.createAlias("roles", "r")
			.createAlias("r.users", "u")  
		    .add(Restrictions.eq("u.id",getUserId())); 
			
		}
		return criteria;
	}
    
}
