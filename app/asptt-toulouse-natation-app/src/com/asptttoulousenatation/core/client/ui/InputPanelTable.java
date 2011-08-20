package com.asptttoulousenatation.core.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class InputPanelTable extends ResizeComposite {

	private LayoutPanel panel;
	
	public InputPanelTable(final String pLabel, final Widget pBox) {
		panel = new LayoutPanel();
		initWidget(panel);
		
		InlineLabel lLabel = new InlineLabel(pLabel + ": ");
		panel.add(lLabel);
		panel.add(pBox);
		panel.setWidgetLeftWidth(lLabel, 0, Unit.PCT, 20, Unit.PCT);
		panel.setWidgetLeftWidth(pBox, 20, Unit.PCT, 100, Unit.PCT);
	}
}
