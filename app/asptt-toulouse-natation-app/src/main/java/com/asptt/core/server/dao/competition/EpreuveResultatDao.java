package com.asptt.core.server.dao.competition;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.competition.EpreuveResultatEntity;

public class EpreuveResultatDao extends DaoBase<EpreuveResultatEntity> {

	@Override
	public Class<EpreuveResultatEntity> getEntityClass() {
		return EpreuveResultatEntity.class;
	}

	@Override
	public String getAlias() {
		return "epreuveResultat";
	}

	@Override
	public Object getKey(EpreuveResultatEntity pEntity) {
		return pEntity.getId();
	}

}
