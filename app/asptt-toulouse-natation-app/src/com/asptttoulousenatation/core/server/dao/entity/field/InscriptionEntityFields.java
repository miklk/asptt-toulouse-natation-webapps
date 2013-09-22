package com.asptttoulousenatation.core.server.dao.entity.field;


public enum InscriptionEntityFields implements IEntityFields {
	EMAIL(String.class),
	NOM(String.class),
	PRENOM(String.class),
	PRINCIPAL(Long.class),
	DATENAISSANCE(String.class),
	MOTDEPASSE(String.class),
	SAISIE(Boolean.class),
	NOUVEAUGROUPE(Long.class, "nouveauGroupe"),
	CRENEAUX(String.class),
	;
	
	private Class<? extends Object> clazz;
	
	private String fieldName;

	private InscriptionEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
		fieldName = name().toLowerCase();
	}
	
	private InscriptionEntityFields(Class<? extends Object> pClazz, String pFieldName) {
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