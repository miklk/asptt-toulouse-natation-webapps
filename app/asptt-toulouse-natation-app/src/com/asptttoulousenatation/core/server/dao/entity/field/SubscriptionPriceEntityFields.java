package com.asptttoulousenatation.core.server.dao.entity.field;

public enum SubscriptionPriceEntityFields implements IEntityFields {

	GROUP(Long.class),
	ORDER(Long.class);

	private Class<? extends Object> clazz;
	
	private SubscriptionPriceEntityFields(Class<? extends Object> pClazz) {
		clazz = pClazz;
	}

	public Class<? extends Object> getEntityClass() {
		return clazz;
	}
}