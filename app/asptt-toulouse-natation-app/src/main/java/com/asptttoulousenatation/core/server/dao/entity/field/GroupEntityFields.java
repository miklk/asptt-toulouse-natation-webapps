package com.asptttoulousenatation.core.server.dao.entity.field;

public enum GroupEntityFields implements IEntityFields {

	TITLE(String.class),
	INSCRIPTION(Boolean.class),
	NOUVEAU(Boolean.class),
	ENF(Boolean.class),
	LICENCEFFN(Boolean.class, "licenceFfn"),
	COMPETITION(Boolean.class),
	;
	
	private Class<? extends Object> clazz;
	
	private String fieldName;

	private GroupEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
	}
	
	private GroupEntityFields(Class<? extends Object> pClazz, String pFieldName) {
		clazz = pClazz;
		fieldName = pFieldName;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}
	
	@Override
	public String getFieldName() {
		return name().toLowerCase();
	}
}
