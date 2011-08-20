package com.asptttoulousenatation.client.userspace;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface UserSpaceView extends IsWidget {

	public void setUserName(String pUserName);
	public void setMenu(Widget pWidget);
	public void setContent(Widget pWidget);
	public AcceptsOneWidget getMenuPanel();
	public AcceptsOneWidget getContentPanel();
	public HasClickHandlers getPublicButton();
}
