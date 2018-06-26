package com.asptt.core.server.dao.entity.field;

import java.util.Date;

public enum ActuEntityFields implements IEntityFields {
	COMPETITION(Boolean.class),
	EXPIRATION(Date.class),
	;
	
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
