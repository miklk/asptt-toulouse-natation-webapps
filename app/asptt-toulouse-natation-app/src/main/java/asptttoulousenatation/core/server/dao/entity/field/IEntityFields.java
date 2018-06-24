package com.asptttoulousenatation.core.server.dao.entity.field;

import java.io.Serializable;

public interface IEntityFields extends Serializable {

	public String name();
	public Class<? extends Object> getEntityClass();
	public String getFieldName();
}
