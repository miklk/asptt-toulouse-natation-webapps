package com.asptttoulousenatation.client.userspace.home;

import java.util.List;

import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomeViewImpl extends Composite implements HomeView {

	private VerticalPanel panel;
	
	public HomeViewImpl(List<MenuUi> pMenuList) {
		panel = new VerticalPanel();
		initWidget(panel);
	}
}