package com.asptttoulousenatation.core.server.dao.entity.field;

public enum DocumentEntityFields implements IEntityFields {
	
	MENU(Long.class);
	
	private Class<? extends Object> clazz;
	
	private DocumentEntityFields(Class<? extends Object> pClazz) {
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
