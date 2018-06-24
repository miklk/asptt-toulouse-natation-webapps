package com.asptttoulousenatation.core.server.dao.inscription.stage;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.inscription.stage.DossierStageEntity;

public class DossierStageDao extends DaoBase<DossierStageEntity> {

	@Override
	public Class<DossierStageEntity> getEntityClass() {
		return DossierStageEntity.class;
	}

	@Override
	public String getAlias() {
		return "dossierStageEntity";
	}

	@Override
	public Object getKey(DossierStageEntity pEntity) {
		return pEntity.getId();
	}
}