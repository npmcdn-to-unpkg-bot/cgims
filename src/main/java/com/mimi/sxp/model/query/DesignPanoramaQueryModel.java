package com.mimi.sxp.model.query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class DesignPanoramaQueryModel extends BasePriorityQueryModel {
	private String houseTypeId;
	private String productId;
	private String insideAreaLimit;
	private String grossFloorAreaLimit;
	private Integer roomNum;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getInsideAreaLimit() {
		return insideAreaLimit;
	}

	public void setInsideAreaLimit(String insideAreaLimit) {
		this.insideAreaLimit = insideAreaLimit;
	}

	public String getGrossFloorAreaLimit() {
		return grossFloorAreaLimit;
	}

	public void setGrossFloorAreaLimit(String grossFloorAreaLimit) {
		this.grossFloorAreaLimit = grossFloorAreaLimit;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public String getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(String houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	public Criteria setParameters(Criteria criteria) {
		super.setParameters(criteria);
		if(StringUtils.isNotBlank(productId)){
			criteria.createAlias("products", "pd");
			criteria.add(Restrictions.eq("pd.id", productId));  
		}
		criteria.createAlias("houseType", "ht");
		if(StringUtils.isNotBlank(getHouseTypeId())){
			criteria.add(Restrictions.eq("ht.id", getHouseTypeId()));  
		}

		if (StringUtils.isNotBlank(insideAreaLimit)) {
			String[] values = insideAreaLimit.split(":");
			if (values.length > 1 && StringUtils.isNumeric(values[0])
					&& StringUtils.isNumeric(values[1])) {
				criteria.add(Restrictions.between("ht.insideArea",
						Float.parseFloat(values[0]),
						Float.parseFloat(values[1])));
			}
		}

		if (StringUtils.isNotBlank(grossFloorAreaLimit)) {
			String[] values = grossFloorAreaLimit.split(":");
			if (values.length > 1 && StringUtils.isNumeric(values[0])
					&& StringUtils.isNumeric(values[1])) {
				criteria.add(Restrictions.between("ht.grossFloorArea",
						Float.parseFloat(values[0]),
						Float.parseFloat(values[1])));
			}
		}

		if (roomNum != null) {
			criteria.add(Restrictions.eq("ht.roomNum", roomNum));
		}
		return criteria;
	}
}
