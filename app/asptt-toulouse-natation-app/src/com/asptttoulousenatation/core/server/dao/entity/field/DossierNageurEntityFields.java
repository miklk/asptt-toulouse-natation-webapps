package com.asptttoulousenatation.core.server.dao.entity.field;



public enum DossierNageurEntityFields implements IEntityFields {
	DOSSIER(Long.class),
	GROUPE(Long.class),
	NOM(String.class),
	PRENOM(String.class),
	CRENEAUX(String.class),
	CERTIFICAT(Boolean.class),
	SAISON(Long.class),
	;
	
	
	private Class<? extends Object> clazz;
	
	private String fieldName;

	private DossierNageurEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
		fieldName = name().toLowerCase();
	}
	
	private DossierNageurEntityFields(Class<? extends Object> pClazz, String pFieldName) {
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