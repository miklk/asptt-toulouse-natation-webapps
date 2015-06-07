package com.asptttoulousenatation.core.server.dao.entity.field;

public enum PiscineEntityFields implements IEntityFields {

	INTITULE(String.class),
	;
	
	private Class<? extends Object> clazz;
	private String fieldName;

	private PiscineEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
		fieldName = name().toLowerCase();
	}
	
	private PiscineEntityFields(Class<? extends Object> pClazz, String pFieldName) {
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
