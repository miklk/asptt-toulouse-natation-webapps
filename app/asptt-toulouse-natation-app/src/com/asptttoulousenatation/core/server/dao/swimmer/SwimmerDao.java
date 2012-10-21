package com.asptttoulousenatation.core.server.dao.swimmer;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerEntity;

public class SwimmerDao extends DaoBase<SwimmerEntity> {

	@Override
	public Class<SwimmerEntity> getEntityClass() {
		return SwimmerEntity.class;
	}

}