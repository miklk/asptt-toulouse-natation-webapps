package com.asptttoulousenatation.client.userspace.menu;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;

public interface MenuView extends IsWidget {

	public HasClickHandlers getItem(MenuItems pMenuItems);
}