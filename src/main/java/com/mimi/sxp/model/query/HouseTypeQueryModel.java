package com.mimi.sxp.model.query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class HouseTypeQueryModel extends BasePriorityQueryModel {
	private String realEstateProjectId;
	private String insideAreaLimit;
	private String grossFloorAreaLimit;
	private Integer roomNum;

	public String getInsideAreaLimit() {
		return insideAreaLimit;
	}

	public void setInsideAreaLimit(String insideAreaLimit) {
		this.insideAreaLimit = insideAreaLimit;
	}

	public String getRealEstateProjectId() {
		return realEstateProjectId;
	}

	public void setRealEstateProjectId(String realEstateProjectId) {
		this.realEstateProjectId = realEstateProjectId;
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

	public Criteria setParameters(Criteria criteria) {
		criteria.createAlias("realEstateProject", "rep");
		criteria.addOrder(Order.desc("updateDate"));
		if (StringUtils.isNotBlank(getSearchKeyword())) {
			criteria.add(Restrictions.or(Restrictions.like("name",
					getSearchKeyword(), MatchMode.ANYWHERE), Restrictions.like(
					"rep.name", getSearchKeyword(), MatchMode.ANYWHERE)));
		}
		if (isOrderByPriority()) {
			criteria.addOrder(Order.desc("priority"));
		}
		if (StringUtils.isNotBlank(getRealEstateProjectId())) {
			criteria.add(Restrictions.eq("rep.id", getRealEstateProjectId()));
		}

		if (StringUtils.isNotBlank(insideAreaLimit)) {
			String[] values = insideAreaLimit.split(":");
			if (values.length > 1 && StringUtils.isNumeric(values[0])
					&& StringUtils.isNumeric(values[1])) {
				criteria.add(Restrictions.between("insideArea",
						Float.parseFloat(values[0]),
						Float.parseFloat(values[1])));
			}
		}

		if (StringUtils.isNotBlank(grossFloorAreaLimit)) {
			String[] values = grossFloorAreaLimit.split(":");
			if (values.length > 1 && StringUtils.isNumeric(values[0])
					&& StringUtils.isNumeric(values[1])) {
				criteria.add(Restrictions.between("grossFloorArea",
						Float.parseFloat(values[0]),
						Float.parseFloat(values[1])));
			}
		}

		if (roomNum != null) {
			criteria.add(Restrictions.eq("roomNum", roomNum));
		}
		return criteria;
	}
}
