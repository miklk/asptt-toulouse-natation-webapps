package com.asptttoulousenatation.core.server.dao.competition;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEntity;

public class CompetitionDao extends DaoBase<CompetitionEntity> {

	@Override
	public Class<CompetitionEntity> getEntityClass() {
		return CompetitionEntity.class;
	}
}