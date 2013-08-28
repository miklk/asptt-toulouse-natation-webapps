package com.asptttoulousenatation.core.server.dao.entity.field;

public enum ContentEntityFields implements IEntityFields {

	MENU(Long.class),
	KIND(String.class);
	
	private Class<? extends Object> clazz;

	private ContentEntityFields(Class<? extends Object> pClazz) {
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