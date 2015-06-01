package com.asptttoulousenatation.core.server.dao.inscription;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;

public class DossierDao extends DaoBase<DossierEntity> {

	@Override
	public Class<DossierEntity> getEntityClass() {
		return DossierEntity.class;
	}

	@Override
	public String getAlias() {
		return "dossierEntity";
	}

	@Override
	public Object getKey(DossierEntity pEntity) {
		return pEntity.getId();
	}
}