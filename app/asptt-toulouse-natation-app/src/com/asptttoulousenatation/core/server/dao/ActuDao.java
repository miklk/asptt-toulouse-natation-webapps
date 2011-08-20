package com.asptttoulousenatation.core.server.dao;

import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;

public class ActuDao extends DaoBase<ActuEntity> {

	@Override
	public Class<ActuEntity> getEntityClass() {
		return ActuEntity.class;
	}
}