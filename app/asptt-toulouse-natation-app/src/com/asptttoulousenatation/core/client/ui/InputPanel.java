package com.asptttoulousenatation.core.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class InputPanel extends Composite {

	private HorizontalPanel panel;
	
	public InputPanel(final String pLabel, final Widget pBox, String pLabelWidth, String pWidgetWidth) {
		panel = new HorizontalPanel();
		initWidget(panel);
		
		InlineLabel lLabel = new InlineLabel(pLabel);
		panel.add(lLabel);
		panel.add(pBox);
		panel.setCellWidth(lLabel, pLabelWidth);
		panel.setCellWidth(pBox, pWidgetWidth);
	}
	
	public InputPanel(final String pLabel, final Widget pBox) {
		this(pLabel, pBox, "30%", "100%");
	}
}