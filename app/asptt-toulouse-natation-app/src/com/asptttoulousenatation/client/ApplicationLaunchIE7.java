package com.asptttoulousenatation.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationLaunchIE7 extends ApplicationLaunch {

	public ApplicationLaunchIE7() {
		
	}

	@Override
	public void init() {
		DOM.removeChild(RootPanel.getBodyElement(),
				DOM.getElementById("loading"));
		SimplePanel panel = new SimplePanel();
		RootPanel.get("gwt-container").add((Widget) panel);
		DOM.setStyleAttribute(RootPanel.get("gwt-container")
				.getElement(), "visibility", "visible");
	}
	
}