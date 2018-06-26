package com.asptt.core.server.dao.entity.field;

public enum DocumentLibelleJEntityFields implements IEntityFields {

	DOCUMENT(Long.class),
	LIBELLE(Long.class),
	;	
	private Class<? extends Object> clazz;
	
	private DocumentLibelleJEntityFields(Class<? extends Object> pClazz) {
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
