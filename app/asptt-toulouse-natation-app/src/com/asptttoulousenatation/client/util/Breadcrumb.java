package com.asptttoulousenatation.client.util;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class Breadcrumb extends Composite {

	private static final String DEFAULT_VALUE = "ASPTT Grand Toulouse Natation";
	private static final String SEPARATOR = " ~ ";
	private SimplePanel panel;
	
	private Label value;
	
	public Breadcrumb() {
		panel = new SimplePanel();
		initWidget(panel);
		value = new Label(DEFAULT_VALUE);
		panel.setWidget(value);
	}
	
	public void update(String pAreaName, String pMenuName) {
		StringBuilder lValue = new StringBuilder(DEFAULT_VALUE);
		lValue.append(SEPARATOR).append(pAreaName);
		if(!pMenuName.isEmpty()) {
			lValue.append(SEPARATOR).append(pMenuName);
		}
		value.setText(lValue.toString());
	}
	
	public HandlerRegistration addClickHandler(ClickHandler pHandler) {
		return value.addClickHandler(pHandler);
	}
}