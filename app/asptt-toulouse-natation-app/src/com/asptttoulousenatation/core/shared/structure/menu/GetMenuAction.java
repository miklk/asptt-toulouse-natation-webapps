package com.asptttoulousenatation.core.shared.structure.menu;

import net.customware.gwt.dispatch.shared.Action;

public class GetMenuAction implements Action<GetMenuResult> {

	private Long menuId;
	
	public GetMenuAction() {
		
	}

	public GetMenuAction(Long pMenuId) {
		super();
		menuId = pMenuId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long pMenuId) {
		menuId = pMenuId;
	}

}