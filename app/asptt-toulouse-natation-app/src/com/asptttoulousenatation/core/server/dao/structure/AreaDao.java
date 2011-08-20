package com.asptttoulousenatation.core.server.dao.structure;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;

public class AreaDao extends DaoBase<AreaEntity> {

	@Override
	public Class<AreaEntity> getEntityClass() {
		return AreaEntity.class;
	}
}