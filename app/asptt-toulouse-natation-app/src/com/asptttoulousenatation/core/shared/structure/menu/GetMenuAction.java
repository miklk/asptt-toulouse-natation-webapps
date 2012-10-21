package com.asptttoulousenatation.core.shared.structure.menu;

import net.customware.gwt.dispatch.shared.Action;

public class GetMenuAction implements Action<GetMenuResult> {

	private Long menuId;
	private boolean addContent;
	
	public GetMenuAction() {
		
	}

	public GetMenuAction(Long pMenuId, boolean pAddContent) {
		menuId = pMenuId;
		addContent = pAddContent;
	}
	
	public GetMenuAction(Long pMenuId) {
		this(pMenuId, true);
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long pMenuId) {
		menuId = pMenuId;
	}

	public boolean isAddContent() {
		return addContent;
	}

	public void setAddContent(boolean pAddContent) {
		addContent = pAddContent;
	}
	

}