package com.asptttoulousenatation.core.server.dao.entity.field;

public enum UserEntityFields implements IEntityFields {
	EMAILADDRESS(String.class),
	PASSWORD(String.class),
	VALIDATED(Boolean.class);
	
	private Class<? extends Object> clazz;
	
	private UserEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}
	
	@Override
	public String getFieldName() {
		return name().toLowerCase();
	}
}