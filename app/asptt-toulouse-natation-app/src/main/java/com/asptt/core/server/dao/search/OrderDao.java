package com.asptt.core.server.dao.search;

import com.asptt.core.server.dao.entity.field.IEntityFields;

public class OrderDao {

	public enum OrderOperator {
		ASC,
		DESC;
	}
	
	private IEntityFields field;
	private OrderOperator operator;

	public OrderDao(IEntityFields pField, OrderOperator pOperator) {
		super();
		field = pField;
		operator = pOperator;
	}
	
	public IEntityFields getField() {
		return field;
	}

	public void setField(IEntityFields pField) {
		field = pField;
	}

	public OrderOperator getOperator() {
		return operator;
	}
	public void setOperator(OrderOperator pOperator) {
		operator = pOperator;
	}
}