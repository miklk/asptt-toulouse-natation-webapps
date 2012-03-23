package com.asptttoulousenatation.core.shared.structure.menu;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateMenuAction implements Action<UpdateMenuResult> {

	private Long menu;
	private String title;
	private short order;
	
	public UpdateMenuAction() {
		
	}

	public UpdateMenuAction(Long pMenu, String pTitle, short pOrder) {
		super();
		menu = pMenu;
		title = pTitle;
		order = pOrder;
	}

	public Long getMenu() {
		return menu;
	}

	public void setMenu(Long pMenu) {
		menu = pMenu;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public short getOrder() {
		return order;
	}

	public void setOrder(short pOrder) {
		order = pOrder;
	}
}