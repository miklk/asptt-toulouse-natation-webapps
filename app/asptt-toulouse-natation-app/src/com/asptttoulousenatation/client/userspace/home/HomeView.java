package com.asptttoulousenatation.client.userspace.home;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;

public interface HomeView extends IsWidget {
	public HasClickHandlers getShorcutButton(String pKey);
}