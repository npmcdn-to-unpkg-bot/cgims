package com.mimi.sxp.model.query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


public class ProductQueryModel extends BasePriorityQueryModel {
	private String shopId;
	
	private String designPanoramaId;
	
	private String designRingId;
	
	private String designImageId;

	public String getDesignPanoramaId() {
		return designPanoramaId;
	}

	public void setDesignPanoramaId(String designPanoramaId) {
		this.designPanoramaId = designPanoramaId;
	}

	public String getDesignRingId() {
		return designRingId;
	}

	public void setDesignRingId(String designRingId) {
		this.designRingId = designRingId;
	}

	public String getDesignImageId() {
		return designImageId;
	}

	public void setDesignImageId(String designImageId) {
		this.designImageId = designImageId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}


	public Criteria setParameters(Criteria criteria) {
		super.setParameters(criteria);
		if(StringUtils.isNotBlank(getShopId())){
			criteria.createAlias("shop", "s")  
		    .add(Restrictions.eq("s.id", getShopId()));  
		}
		if(StringUtils.isNotBlank(getDesignPanoramaId())){
			criteria.createAlias("designPanoramas", "dp")  
		    .add(Restrictions.eq("dp.id", getDesignPanoramaId()));  
		}
		if(StringUtils.isNotBlank(getDesignImageId())){
			criteria.createAlias("designImages", "di")  
		    .add(Restrictions.eq("di.id", getDesignImageId()));  
		}
		if(StringUtils.isNotBlank(getDesignRingId())){
			criteria.createAlias("designRings", "dr")  
		    .add(Restrictions.eq("dr.id", getDesignRingId()));  
		}
		return criteria;
	}
}
