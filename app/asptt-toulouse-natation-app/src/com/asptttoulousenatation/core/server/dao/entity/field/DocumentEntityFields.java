package com.asptttoulousenatation.core.server.dao.entity.field;

import java.util.Date;

public enum DocumentEntityFields implements IEntityFields {
	
	MENU(Long.class),
	TITLE(String.class),
	CREATIONDATE(Date.class, "creationDate"),
	;
	
	private Class<? extends Object> clazz;
	private String fieldName;
	
	private DocumentEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
		fieldName = name().toLowerCase();
	}
	
	private DocumentEntityFields(Class<? extends Object> pClazz, String pFieldName) {
		clazz = pClazz;
		fieldName = pFieldName;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}
	
	@Override
	public String getFieldName() {
		return fieldName;
	}
}