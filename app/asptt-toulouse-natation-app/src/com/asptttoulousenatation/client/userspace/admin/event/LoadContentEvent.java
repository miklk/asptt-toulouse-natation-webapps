package com.asptttoulousenatation.client.userspace.admin.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoadContentEvent extends GwtEvent<LoadContentEventHandler> {

	public static final Type<LoadContentEventHandler> TYPE = new Type<LoadContentEventHandler>();
	
	private Long menuId;
	
	public LoadContentEvent() {
		
	}
	
	public LoadContentEvent(Long pMenuId) {
		super();
		menuId = pMenuId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long pMenuId) {
		menuId = pMenuId;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoadContentEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoadContentEventHandler pHandler) {
		pHandler.loadContent(this);
	}
	
}