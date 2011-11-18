package com.asptttoulousenatation.server.userspace.admin.entity;

import java.util.Date;

import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionDayEntity;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEntity;
import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;
import com.asptttoulousenatation.shared.event.UiEvent;

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
	
	public UiEvent toUiEvent(CompetitionDayEntity pEntity, CompetitionEntity pCompetitionEntity) {
		UiEvent lUi = new UiEvent();
		Date lDate = new Date(Date.UTC(pEntity.getBegin().getYear(), pEntity.getBegin().getMonth(), pEntity.getBegin().getDate(), 0, 0, 0));
		lUi.setEventDate(lDate);
		lUi.setEventTitle(pCompetitionEntity.getTitle() + " - Journée " + pEntity.getDay());
		return lUi;
	}
}