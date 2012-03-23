package com.asptttoulousenatation.core.server.dao.entity.field;

public enum SlotEntityFields implements IEntityFields {

	GROUP(Long.class);
	
	private Class<? extends Object> clazz;

	private SlotEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}
}
