package com.asptttoulousenatation.core.server.dao.inscription;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierStatEntity;

public class DossierStatDao extends DaoBase<DossierStatEntity> {
	
	@Override
	public Class<DossierStatEntity> getEntityClass() {
		return DossierStatEntity.class;
	}

	@Override
	public String getAlias() {
		return "dossierStatEntity";
	}

	@Override
	public Object getKey(DossierStatEntity pEntity) {
		return pEntity.getId();
	}
}