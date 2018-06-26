package com.asptt.core.server.dao.entity.field;

public enum MenuEntityFields implements IEntityFields {

	AREA(Long.class),
	TITLE(String.class),
	DISPLAY(Boolean.class),
	ORDER(Integer.class),
	PARENT(Long.class),
	IDENTIFIER(String.class);
	
	private Class<? extends Object> clazz;
	
	private MenuEntityFields(Class<? extends Object> pClazz) {
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
