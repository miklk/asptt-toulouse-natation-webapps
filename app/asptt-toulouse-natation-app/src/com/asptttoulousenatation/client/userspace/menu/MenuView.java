package com.asptttoulousenatation.client.userspace.menu;

import java.util.List;

import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;

public interface MenuView extends IsWidget {

	public HasClickHandlers getItem(MenuItems pMenuItems);
	public void setArea(List<AreaUi> pArea);
	public HasClickHandlers getAreaLoadButton();
}