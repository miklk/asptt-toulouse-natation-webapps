package com.asptttoulousenatation.core.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class InputPanel extends Composite {

	private HorizontalPanel panel;
	
	public InputPanel(final String pLabel, final Widget pBox, String pLabelWidth, String pWidgetWidth) {
		panel = new HorizontalPanel();
		initWidget(panel);
		
		InlineLabel lLabel = new InlineLabel(pLabel);
		
		if(pBox instanceof TextBox) {
		lLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				((TextBox) pBox).setFocus(true);
			}
		});
		}
		panel.add(lLabel);
		panel.add(pBox);
		panel.setCellWidth(lLabel, pLabelWidth);
		panel.setCellWidth(pBox, pWidgetWidth);
	}
	
	public InputPanel(final String pLabel, final Widget pBox) {
		this(pLabel, pBox, "30%", "100%");
	}
}