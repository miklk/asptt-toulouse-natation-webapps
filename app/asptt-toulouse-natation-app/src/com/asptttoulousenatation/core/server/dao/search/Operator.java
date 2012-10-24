package com.asptttoulousenatation.core.server.dao.search;

import java.io.Serializable;

public enum Operator implements Serializable {

	EQUAL("=="),
	NOT_EQUAL("!="),
	GREATER(">"),
	GREATER_EQ(">="),
	LESS("<"),
	LESS_EQ("<="),
	AND("&&"),
	OR("||"),
	NULL("is null");
	
	private String value;
	
	private Operator(String pValue) {
		value = pValue;
	}
	
	public String toString() {
		return value;
	}
}
