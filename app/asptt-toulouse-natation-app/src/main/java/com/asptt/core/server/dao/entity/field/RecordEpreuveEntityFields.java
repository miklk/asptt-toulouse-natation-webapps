package com.asptt.core.server.dao.entity.field;

public enum RecordEpreuveEntityFields implements IEntityFields {
	BASSIN(String.class),
	SEXE(String.class),
	;
	
	private Class<? extends Object> clazz;
	
	private String fieldName;

	private RecordEpreuveEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
		fieldName = name().toLowerCase();
	}
	
	private RecordEpreuveEntityFields(Class<? extends Object> pClazz, String pFieldName) {
		clazz = pClazz;
		fieldName = pFieldName;
	}
	
	@Override
	public String getFieldName() {
		return fieldName;
	}

	public Class<? extends Object> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends Object> pClazz) {
		clazz = pClazz;
	}

	public void setFieldName(String pFieldName) {
		fieldName = pFieldName;
	}

	@Override
	public Class<? extends Object> getEntityClass() {
		return clazz;
	}
	
}