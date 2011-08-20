package com.asptttoulousenatation.client.userspace.admin.club.group;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface GroupView extends IsWidget {

	public HasClickHandlers getCreateButton();
	public HasClickHandlers getUpdateButton();
	public HasValue<String> getGroupTitle();
	public Long getGroupId();
}