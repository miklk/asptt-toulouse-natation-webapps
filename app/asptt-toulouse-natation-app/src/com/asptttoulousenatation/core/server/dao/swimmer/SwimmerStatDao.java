package com.asptttoulousenatation.core.server.dao.swimmer;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerStatEntity;

public class SwimmerStatDao extends DaoBase<SwimmerStatEntity> {

	@Override
	public Class<SwimmerStatEntity> getEntityClass() {
		return SwimmerStatEntity.class;
	}

}
