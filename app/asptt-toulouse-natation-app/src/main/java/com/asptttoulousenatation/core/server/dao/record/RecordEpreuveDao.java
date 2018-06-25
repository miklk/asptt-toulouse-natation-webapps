package com.asptttoulousenatation.core.server.dao.record;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.record.RecordEpreuveEntity;

public class RecordEpreuveDao extends DaoBase<RecordEpreuveEntity> {

	@Override
	public Class<RecordEpreuveEntity> getEntityClass() {
		return RecordEpreuveEntity.class;
	}

	@Override
	public String getAlias() {
		return "recordepreuve";
	}

	@Override
	public Object getKey(RecordEpreuveEntity pEntity) {
		return pEntity.getId();
	}

}
