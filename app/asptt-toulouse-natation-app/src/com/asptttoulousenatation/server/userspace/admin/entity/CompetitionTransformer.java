package com.asptttoulousenatation.server.userspace.admin.entity;

import java.util.Date;

import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEntity;
import com.asptttoulousenatation.core.shared.competition.CompetitionUi;
import com.asptttoulousenatation.shared.event.UiEvent;

public class CompetitionTransformer extends AbstractEntityTransformer<CompetitionUi, CompetitionEntity> {

	@Override
	public CompetitionUi toUi(CompetitionEntity pEntity) {
		CompetitionUi lUi = new CompetitionUi();
		lUi.setId(pEntity.getId());
		lUi.setTitle(pEntity.getTitle());
		lUi.setPlace(pEntity.getPlace());
		lUi.setBegin(pEntity.getBegin());
		lUi.setEnd(pEntity.getEnd());
		lUi.setSaison(pEntity.getSaison());
		return lUi;
	}
	
	public UiEvent toUiEvent(CompetitionEntity pEntity) {
		UiEvent lUi = new UiEvent();
		Date lDate = new Date(Date.UTC(pEntity.getBegin().getYear(), pEntity.getBegin().getMonth(), pEntity.getBegin().getDate(), 0, 0, 0));
		lUi.setEventDate(lDate);
		lUi.setEventTitle(pEntity.getTitle());
		return lUi;
	}
}