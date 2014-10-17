package com.asptttoulousenatation.core.server.dao.entity.field;

public enum ActuEntityFields implements IEntityFields {
	COMPETITION(Boolean.class);
	
	private Class<? extends Object> clazz;
	
	private ActuEntityFields(Class<? extends Object> pClazz) {
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
