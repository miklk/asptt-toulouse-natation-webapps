package com.asptttoulousenatation.core.server.dao.entity.field;

public enum InscriptionEntityFields implements IEntityFields {
	EMAIL(String.class),
	NOM(String.class),
	PRENOM(String.class),
	PRINCIPAL(Long.class);
	
	private Class<? extends Object> clazz;
	
	private InscriptionEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}
}