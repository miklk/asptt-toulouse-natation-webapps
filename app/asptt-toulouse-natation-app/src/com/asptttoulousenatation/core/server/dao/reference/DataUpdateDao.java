package com.asptttoulousenatation.core.server.dao.reference;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.reference.DataUpdateEntity;

public class DataUpdateDao extends DaoBase<DataUpdateEntity> {

	@Override
	public Class<DataUpdateEntity> getEntityClass() {
		return DataUpdateEntity.class;
	}
}