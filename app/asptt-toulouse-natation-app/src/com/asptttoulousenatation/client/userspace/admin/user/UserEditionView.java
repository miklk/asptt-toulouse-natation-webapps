package com.asptttoulousenatation.client.userspace.admin.user;

import java.util.Date;
import java.util.Set;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface UserEditionView extends IsWidget {

	public Long getUser();
	public HasValue<String> getEmailAddress();
	public HasValue<Boolean> getValidated();
	public Set<String> getProfiles();
	public Set<Long> getSlots();
	public HasValue<String> getLastName();
	public HasValue<String> getFirstName();
	public HasValue<Date> getBirthday();
	public HasValue<String> getPhoneNumber();
	
	public HasClickHandlers getUpdateButton();
}