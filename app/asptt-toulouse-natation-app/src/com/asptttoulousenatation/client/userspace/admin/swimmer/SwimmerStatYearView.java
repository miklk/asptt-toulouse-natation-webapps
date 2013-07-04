package com.asptttoulousenatation.client.userspace.admin.swimmer;

import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatYearUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;

public interface SwimmerStatYearView extends IsWidget {
	public Date getCurrentDay();
	public void setCurrentDay(Date pCurrentDay);
	public HasClickHandlers getPreviousButton();
	public HasClickHandlers getNextButton();
	public void setData(List<SwimmerStatYearUi> pSwimmerStats);
	public void setCurrentDayText(String pCurrentDayText);
}