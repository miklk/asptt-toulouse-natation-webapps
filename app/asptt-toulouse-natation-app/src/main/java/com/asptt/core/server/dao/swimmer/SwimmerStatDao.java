package com.asptt.core.server.dao.swimmer;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.swimmer.SwimmerStatEntity;

public class SwimmerStatDao extends DaoBase<SwimmerStatEntity> {

	@Override
	public Class<SwimmerStatEntity> getEntityClass() {
		return SwimmerStatEntity.class;
	}

	@Override
	public String getAlias() {
		return "swimmerStat";
	}
	
	@Override
	public Object getKey(SwimmerStatEntity pEntity) {
		return pEntity.getId();
	}

}
