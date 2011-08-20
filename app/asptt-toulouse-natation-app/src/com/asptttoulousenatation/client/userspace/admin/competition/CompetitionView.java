package com.asptttoulousenatation.client.userspace.admin.competition;

import java.util.Date;
import java.util.Set;

import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface CompetitionView extends IsWidget {

	public HasClickHandlers getUpdateButton();
	public HasClickHandlers getCreateButton();
	
	public Long getCompetition();
	public HasValue<String> getCompetitionSaison();
	public HasValue<String> getCompetitionTitle();
	public HasValue<String> getCompetitionPlace();
	public HasValue<Date> getCompetitionBegin();
	public HasValue<Date> getCompetitionEnd();
	public Set<CompetitionDayUi> getCreateDays();
	public Set<CompetitionDayUi> getUpdateDays();
}