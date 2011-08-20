package com.asptttoulousenatation.core.client.ui;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class SimpleLayoutPanel extends LayoutPanel implements AcceptsOneWidget {

	public void setWidget(IsWidget pW) {
		clear();
		Widget lWidget = Widget.asWidgetOrNull(pW);
		if(lWidget != null) {
			add(lWidget);
		}
	}

}
