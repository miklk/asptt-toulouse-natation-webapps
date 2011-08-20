package com.asptttoulousenatation.client.userspace.admin.actu;

import java.util.Date;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface ActuView extends IsWidget {

	public HasClickHandlers getCloseButton();
	public HasClickHandlers getSaveButton();
	public HasClickHandlers getPublishButton();
	public HasValue<String> getTitre();
	public HasValue<String> getSummary();
	public String getContent();
	public HasValue<Date> getCreationDate();
}