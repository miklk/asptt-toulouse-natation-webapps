package com.asptttoulousenatation.core.server.dao.entity.field;

public enum DataUpdateEntityFields implements IEntityFields {
	KIND(String.class);
	
	private Class<? extends Object> clazz;

	private DataUpdateEntityFields(Class<? extends Object> pClazz) {
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