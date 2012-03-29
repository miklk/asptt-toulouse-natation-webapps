package com.asptttoulousenatation.core.server.dao.search;

import java.util.Date;

import com.asptttoulousenatation.core.server.dao.entity.field.IEntityFields;

public class CriterionDao<T extends Object> {

	private IEntityFields entityField;
	private T value;
	private Operator operator;

	public CriterionDao(IEntityFields pEntityField, T pValue, Operator pOperator) {
		super();
		entityField = pEntityField;
		value = pValue;
		operator = pOperator;
	}

	public CriterionDao() {
		// TODO Auto-generated constructor stub
	}

	public IEntityFields getEntityField() {
		return entityField;
	}

	public void setEntityField(IEntityFields pEntityField) {
		entityField = pEntityField;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T pValue) {
		value = pValue;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator pOperator) {
		operator = pOperator;
	}

	public String asWhereClause() {
		StringBuilder lBuilder = new StringBuilder();
		lBuilder.append("this.").append(entityField.name().toLowerCase());
		lBuilder.append(" ").append(operator).append(" ");
		if (entityField.getEntityClass().equals(String.class)) {
			lBuilder.append("\"").append(value).append("\"");
		} else if (entityField.getEntityClass().equals(Date.class)) {
			lBuilder.append("\"").append(value).append("\"");
		} else {
			lBuilder.append(value);
		}
		return lBuilder.toString();
	}
}
