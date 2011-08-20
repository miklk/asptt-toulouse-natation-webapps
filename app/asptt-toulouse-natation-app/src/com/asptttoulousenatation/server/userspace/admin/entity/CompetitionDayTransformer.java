package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionDayEntity;
import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;

public class CompetitionDayTransformer extends
		AbstractEntityTransformer<CompetitionDayUi, CompetitionDayEntity> {

	@Override
	public CompetitionDayUi toUi(CompetitionDayEntity pEntity) {
		CompetitionDayUi lUi = new CompetitionDayUi();
		lUi.setId(pEntity.getId());
		lUi.setDay(pEntity.getDay());
		lUi.setBegin(pEntity.getBegin());
		lUi.setEnd(pEntity.getEnd());
		lUi.setNeeded(pEntity.getNeeded());
		return lUi;
	}
}