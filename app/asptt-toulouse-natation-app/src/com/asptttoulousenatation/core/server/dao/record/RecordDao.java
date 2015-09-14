package com.asptttoulousenatation.core.server.dao.record;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.record.RecordEntity;

public class RecordDao extends DaoBase<RecordEntity> {

	@Override
	public Class<RecordEntity> getEntityClass() {
		return RecordEntity.class;
	}

	@Override
	public String getAlias() {
		return "record";
	}

	@Override
	public Object getKey(RecordEntity pEntity) {
		return pEntity.getId();
	}

}
