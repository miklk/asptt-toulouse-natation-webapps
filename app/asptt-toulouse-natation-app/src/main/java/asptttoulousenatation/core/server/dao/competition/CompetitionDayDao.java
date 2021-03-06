package com.asptttoulousenatation.core.server.dao.competition;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionDayEntity;

public class CompetitionDayDao extends DaoBase<CompetitionDayEntity> {

	@Override
	public Class<CompetitionDayEntity> getEntityClass() {
		return CompetitionDayEntity.class;
	}

	@Override
	public String getAlias() {
		return "competitionDay";
	}
	
	@Override
	public Object getKey(CompetitionDayEntity pEntity) {
		return pEntity.getId();
	}
}