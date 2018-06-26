package com.asptt.core.server.dao.search;

import java.io.Serializable;

public enum Operator implements Serializable {

	EQUAL("="),
	NOT_EQUAL("<>"),
	GREATER(">"),
	GREATER_EQ(">="),
	LESS("<"),
	LESS_EQ("<="),
	AND("AND"),
	OR("OR"),
	NULL("is null"),
	NOT_NULL("is not null");
	
	private String value;
	
	private Operator(String pValue) {
		value = pValue;
	}
	
	public String toString() {
		return value;
	}
}
