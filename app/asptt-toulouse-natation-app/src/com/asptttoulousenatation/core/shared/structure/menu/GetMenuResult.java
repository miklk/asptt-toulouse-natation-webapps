package com.asptttoulousenatation.core.shared.structure.menu;

import com.asptttoulousenatation.core.shared.structure.MenuUi;

import net.customware.gwt.dispatch.shared.Result;

public class GetMenuResult implements Result {

	private MenuUi menu;
	
	public GetMenuResult() {
		
	}

	public GetMenuResult(MenuUi pMenu) {
		super();
		menu = pMenu;
	}

	public MenuUi getMenu() {
		return menu;
	}

	public void setMenu(MenuUi pMenu) {
		menu = pMenu;
	}
}