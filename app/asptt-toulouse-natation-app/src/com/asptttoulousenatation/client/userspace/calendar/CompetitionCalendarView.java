package com.asptttoulousenatation.client.userspace.calendar;

import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;

public interface CompetitionCalendarView extends IsWidget {
	public HasClickHandlers addOfficielButton();
	public HasClickHandlers removeOfficielButton();
	public void switchOfficielButton(CompetitionDayUi pCompetitionDayUi);
	
	public Long getCompetitionId();
	public Long getCompetitionDayId();
}