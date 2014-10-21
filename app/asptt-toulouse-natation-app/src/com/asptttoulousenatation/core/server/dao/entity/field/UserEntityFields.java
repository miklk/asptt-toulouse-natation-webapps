package com.asptttoulousenatation.core.server.dao.entity.field;

public enum UserEntityFields implements IEntityFields {
	EMAILADDRESS(String.class),
	PASSWORD(String.class),
	VALIDATED(Boolean.class),
	DATA(Long.class, "userData");
	
	private Class<? extends Object> clazz;
	
	private String fieldName;

	private UserEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
		fieldName = name().toLowerCase();
	}
	
	private UserEntityFields(Class<? extends Object> pClazz, String pFieldName) {
		clazz = pClazz;
		fieldName = pFieldName;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}

	public Class<? extends Object> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends Object> pClazz) {
		clazz = pClazz;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String pFieldName) {
		fieldName = pFieldName;
	}
}