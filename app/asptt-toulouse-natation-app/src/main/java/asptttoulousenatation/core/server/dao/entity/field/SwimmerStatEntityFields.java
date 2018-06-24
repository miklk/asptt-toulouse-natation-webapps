package com.asptttoulousenatation.core.server.dao.entity.field;


public enum SwimmerStatEntityFields implements IEntityFields {
	SWIMMER(Long.class),
	DAY(Long.class),
	DAYTIME(String.class),
	TYPE(String.class);
	
	private Class<? extends Object> clazz;

	private SwimmerStatEntityFields(Class<? extends Object> pClazz) {
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