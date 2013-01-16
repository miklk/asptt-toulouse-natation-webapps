package com.asptttoulousenatation.client.userspace.admin.swimmer;

import java.util.Date;
import java.util.List;

import com.asptttoulousenatation.core.client.ui.SwimmerStatWidget;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;

public interface SwimmerStatView extends IsWidget {

	public List<SwimmerStatWidget> getData();
	public HasClickHandlers getValidButton();
	public Date getCurrentDay();
	public void setCurrentDay(Date pCurrentDay);
	public HasClickHandlers getPreviousButton();
	public HasClickHandlers getNextButton();
	public HasValueChangeHandlers<Date> getNewDate();
	public void setData(List<SwimmerStatUi> pSwimmerStats);
	public void setCurrentDayText(String pCurrentDayText);
}