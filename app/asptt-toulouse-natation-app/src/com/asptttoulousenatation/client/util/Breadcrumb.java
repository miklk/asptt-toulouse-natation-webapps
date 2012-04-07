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
	
	private StringBuilder valueAsString;
	
	public Breadcrumb() {
		panel = new SimplePanel();
		initWidget(panel);
		value = new Label(DEFAULT_VALUE);
		panel.setWidget(value);
	}
	
	public void update(String pAreaName, String pMenuName) {
		valueAsString = new StringBuilder(DEFAULT_VALUE);
		valueAsString.append(SEPARATOR).append(pAreaName);
		if(!pMenuName.isEmpty()) {
			valueAsString.append(SEPARATOR).append(pMenuName);
		}
		value.setText(valueAsString.toString());
	}
	
	public void update(String pMenuName) {
		value.setText(valueAsString.append(SEPARATOR).append(pMenuName).toString());
	}
	
	public HandlerRegistration addClickHandler(ClickHandler pHandler) {
		return value.addClickHandler(pHandler);
	}
}