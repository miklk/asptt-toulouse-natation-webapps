package com.asptttoulousenatation.client.userspace.admin.actu;

import java.util.Date;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface ActuEditionView extends IsWidget {

	public Long getActu();
	public HasValue<String> getTitre();
	public HasValue<String> getSummary();
	public String getContent();
	public HasValue<String> getImageUrl();
	public HasValue<Date> getCreationDate();
	public HasValue<Boolean> getCompetition();
	public HasValue<Date> getExpirationDate();
	public HasClickHandlers getUpdateButton();
	public HasClickHandlers getDeleteButton();
}
