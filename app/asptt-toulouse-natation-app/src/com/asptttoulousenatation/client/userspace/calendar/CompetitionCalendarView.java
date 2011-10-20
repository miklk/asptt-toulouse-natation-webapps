package com.asptttoulousenatation.client.userspace.calendar;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;

public interface CompetitionCalendarView extends IsWidget {
	public HasClickHandlers addOfficielButton();
	public HasClickHandlers removeOfficielButton();
	
	public Long getCompetitionDayId();
}