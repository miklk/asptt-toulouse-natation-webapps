package com.asptttoulousenatation.core.server.dao.entity.field;

public enum GroupEntityFields implements IEntityFields {

	TITLE(String.class),
	INSCRIPTION(Boolean.class),
	;
	
	private Class<? extends Object> clazz;

	private GroupEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}
}
