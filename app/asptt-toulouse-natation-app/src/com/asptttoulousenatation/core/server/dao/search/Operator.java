package com.asptttoulousenatation.core.server.dao.search;

import java.io.Serializable;

public enum Operator implements Serializable {

	EQUAL("=="),
	GREATER(">"),
	LESS("<"),
	LESS_EQ("<="),
	AND("&&"),
	OR("||");
	
	private String value;
	
	private Operator(String pValue) {
		value = pValue;
	}
	
	public String toString() {
		return value;
	}
}
