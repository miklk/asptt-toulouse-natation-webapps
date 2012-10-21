package com.asptttoulousenatation.core.server.dao.entity.field;


public enum SwimmerStatEntityFields implements IEntityFields {
	SWIMMER(Long.class),
	DAY(String.class),
	DAYTIME(String.class);
	
	private Class<? extends Object> clazz;

	private SwimmerStatEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}
}