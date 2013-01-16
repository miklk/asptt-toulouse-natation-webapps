package com.asptttoulousenatation.server.userspace.admin.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEntity;
import com.asptttoulousenatation.core.shared.competition.CompetitionUi;
import com.asptttoulousenatation.shared.event.UiEvent;

public class CompetitionTransformer extends
		AbstractEntityTransformer<CompetitionUi, CompetitionEntity> {

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

	public List<UiEvent> toUiEvent(CompetitionEntity pEntity) {
		List<UiEvent> lEvents = new ArrayList<UiEvent>();
		Calendar lBeginDate = GregorianCalendar.getInstance();
		lBeginDate.setTime(pEntity.getBegin());
		Calendar lEndDate = GregorianCalendar.getInstance();
		lEndDate.setTime(pEntity.getEnd());

		int days = lEndDate.get(Calendar.DAY_OF_MONTH)
				- lBeginDate.get(Calendar.DAY_OF_MONTH);
		for (int i = 0; i <= days; i++) {
			Calendar lDate = GregorianCalendar.getInstance();
			lDate.setTime(pEntity.getBegin());
			lDate.add(Calendar.DAY_OF_MONTH, i);
			UiEvent lUi = new UiEvent();
			lUi.setEventDate(lDate.getTime());
			lUi.setEventTitle(pEntity.getTitle());
			lEvents.add(lUi);
		}
		return lEvents;
	}
}