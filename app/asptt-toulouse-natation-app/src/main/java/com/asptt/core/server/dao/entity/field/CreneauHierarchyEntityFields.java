package com.asptt.core.server.dao.entity.field;

public enum CreneauHierarchyEntityFields implements IEntityFields {

	ID(Long.class),
	PARENT(Long.class),
	;
	
	private Class<? extends Object> clazz;
	private String fieldName;

	private CreneauHierarchyEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
		fieldName = name().toLowerCase();
	}
	
	private CreneauHierarchyEntityFields(Class<? extends Object> pClazz, String pFieldName) {
		clazz = pClazz;
		fieldName = pFieldName;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}
}
