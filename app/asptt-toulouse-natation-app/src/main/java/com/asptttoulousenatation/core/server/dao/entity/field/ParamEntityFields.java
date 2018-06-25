package com.asptttoulousenatation.core.server.dao.entity.field;



public enum ParamEntityFields implements IEntityFields {
	KEY(String.class),
	GROUPE(String.class),
	;
	
	private Class<? extends Object> clazz;
	
	private String fieldName;

	private ParamEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
		fieldName = name().toLowerCase();
	}
	
	private ParamEntityFields(Class<? extends Object> pClazz, String pFieldName) {
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