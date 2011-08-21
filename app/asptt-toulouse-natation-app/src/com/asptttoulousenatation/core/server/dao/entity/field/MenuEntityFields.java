package com.asptttoulousenatation.core.server.dao.entity.field;

public enum MenuEntityFields implements IEntityFields {

	AREA(Long.class),
	DISPLAY(Boolean.class),
	ORDER(Integer.class);
	
	private Class<? extends Object> clazz;
	
	private MenuEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}
}
