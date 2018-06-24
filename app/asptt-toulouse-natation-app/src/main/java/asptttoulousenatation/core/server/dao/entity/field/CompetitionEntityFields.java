package com.asptttoulousenatation.core.server.dao.entity.field;

import java.util.Date;

public enum CompetitionEntityFields implements IEntityFields {

	BEGIN(Date.class),
	END(Date.class),
	;
	
	private Class<? extends Object> clazz;

	private CompetitionEntityFields(Class<? extends Object> pClazz) {
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
