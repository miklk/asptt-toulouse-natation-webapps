package com.asptttoulousenatation.core.server.dao.entity.field;

public enum UserDataEntityFields implements IEntityFields {
	LASTNAME(String.class, "lastName"),
	FIRSTNAME(String.class, "firstName");
	
	private Class<? extends Object> clazz;
	
	private String fieldName;
	
	private UserDataEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
		fieldName = name().toLowerCase();
	}
	
	private UserDataEntityFields(Class<? extends Object> pClazz, String pFieldName) {
		clazz = pClazz;
		fieldName = pFieldName;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String pFieldName) {
		fieldName = pFieldName;
	}
}