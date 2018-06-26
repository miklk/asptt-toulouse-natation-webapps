package com.asptt.core.server.dao.entity.field;

public enum LibelleEntityFields implements IEntityFields {

	INTITULE(String.class),
	;	
	private Class<? extends Object> clazz;
	
	private LibelleEntityFields(Class<? extends Object> pClazz) {
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
