package com.asptt.core.server.dao.entity.field;

public enum SwimmerEntityFields implements IEntityFields {
	STAT(Boolean.class),
	USER(Long.class);
	
	private Class<? extends Object> clazz;

	private SwimmerEntityFields(Class<? extends Object> pClazz) {
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