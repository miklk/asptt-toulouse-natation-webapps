package com.asptt.core.server.dao.swimmer;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.swimmer.SwimmerEntity;

public class SwimmerDao extends DaoBase<SwimmerEntity> {

	@Override
	public Class<SwimmerEntity> getEntityClass() {
		return SwimmerEntity.class;
	}

	@Override
	public String getAlias() {
		return "swimmer";
	}
	
	@Override
	public Object getKey(SwimmerEntity pEntity) {
		return pEntity.getId();
	}

}