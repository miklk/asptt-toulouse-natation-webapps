package com.asptttoulousenatation.core.server.dao.salarie;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.salarie.SalarieHeureEntity;

public class SalarieHeureDao extends DaoBase<SalarieHeureEntity> {

	@Override
	public Class<SalarieHeureEntity> getEntityClass() {
		return SalarieHeureEntity.class;
	}

	@Override
	public String getAlias() {
		return "salarieH";
	}
	
	@Override
	public Object getKey(SalarieHeureEntity pEntity) {
		return pEntity.getId();
	}
}