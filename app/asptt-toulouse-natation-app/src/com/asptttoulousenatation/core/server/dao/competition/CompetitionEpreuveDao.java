package com.asptttoulousenatation.core.server.dao.competition;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEpreuveEntity;

public class CompetitionEpreuveDao extends DaoBase<CompetitionEpreuveEntity> {

	@Override
	public Class<CompetitionEpreuveEntity> getEntityClass() {
		return CompetitionEpreuveEntity.class;
	}

	@Override
	public String getAlias() {
		return "competitionEpreuve";
	}

	@Override
	public Object getKey(CompetitionEpreuveEntity pEntity) {
		return pEntity.getId();
	}

	
}
