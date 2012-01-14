package com.asptttoulousenatation.core.server.dao.entity.field;

public enum AreaEntityFields implements IEntityFields {
	TITLE(String.class),
	PROFILE(String.class),
	ORDER(Short.class);
	
	private Class<? extends Object> clazz;
	
	private AreaEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}

}
