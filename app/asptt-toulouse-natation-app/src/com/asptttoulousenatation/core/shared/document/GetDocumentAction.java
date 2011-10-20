package com.asptttoulousenatation.core.shared.document;

import net.customware.gwt.dispatch.shared.Action;

public class GetDocumentAction implements Action<GetDocumentResult> {

	private Long menuId;
	
	public GetDocumentAction() {
		
	}

	public GetDocumentAction(Long pMenuId) {
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