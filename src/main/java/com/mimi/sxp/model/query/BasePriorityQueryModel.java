package com.mimi.sxp.model.query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public class BasePriorityQueryModel extends BaseQueryModel {
	private boolean orderByPriority = false;

	public boolean isOrderByPriority() {
		return orderByPriority;
	}

	public void setOrderByPriority(boolean orderByPriority) {
		this.orderByPriority = orderByPriority;
	}

	public Criteria setParameters(Criteria criteria) {
		super.setParameters(criteria);
		if(orderByPriority){
			criteria.addOrder(Order.desc("priority"));
		}
		return criteria;
	}
}
