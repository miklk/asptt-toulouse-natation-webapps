package com.asptt.core.server.dao.entity.field;

import java.util.Date;

public enum DossierEntityFields implements IEntityFields {
	EMAIL(String.class),
	MOTDEPASSE(String.class),
	UPDATED(Date.class),
	STATUT(String.class),
	EMAILSECONDAIRE(String.class),
	SAISON(Long.class),
	PAIEMENT(String.class),
	;
	
	private Class<? extends Object> clazz;
	
	private String fieldName;

	private DossierEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
		fieldName = name().toLowerCase();
	}
	
	private DossierEntityFields(Class<? extends Object> pClazz, String pFieldName) {
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