package com.asptttoulousenatation.core.shared.structure;

import net.customware.gwt.dispatch.shared.Action;

public class LoadContentAction implements Action<LoadContentResult> {

	private Long menu;
	
	public LoadContentAction() {
		
	}

	public LoadContentAction(Long pMenu) {
		super();
		menu = pMenu;
	}

	public Long getMenu() {
		return menu;
	}

	public void setMenu(Long pMenu) {
		menu = pMenu;
	}
}