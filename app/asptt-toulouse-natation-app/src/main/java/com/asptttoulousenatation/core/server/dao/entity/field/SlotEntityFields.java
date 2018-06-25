package com.asptttoulousenatation.core.server.dao.entity.field;

public enum SlotEntityFields implements IEntityFields {

	ID(Long.class),
	GROUP(Long.class),
	DAYOFWEEK(String.class, "dayOfWeek"),
	SWIMMINGPOOL(String.class, "swimmingPool"),
	SECOND(Boolean.class, "second"),
	;
	
	private Class<? extends Object> clazz;
	private String fieldName;

	private SlotEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
		fieldName = name().toLowerCase();
	}
	
	private SlotEntityFields(Class<? extends Object> pClazz, String pFieldName) {
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
