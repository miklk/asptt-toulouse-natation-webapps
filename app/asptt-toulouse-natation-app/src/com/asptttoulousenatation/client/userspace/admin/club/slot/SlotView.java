package com.asptttoulousenatation.client.userspace.admin.club.slot;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface SlotView extends IsWidget {
	public String getDayOfWeek();
	public int getHourBegin();
	public int getHourEnd();
	public Long getGroup();
	public HasValue<String> getSwimmingPool();
	public Long getSlot();
	
	public HasClickHandlers getCreateButton();
	public HasClickHandlers getUpdateButton();
} 
