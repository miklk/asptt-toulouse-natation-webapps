package com.asptttoulousenatation.client.userspace.admin.user.password;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface ChangePasswordView extends IsWidget {

	public HasValue<String> getOldPassword();
	public HasValue<String> getNewPassword();
	
	public HasClickHandlers getValidButton();
}