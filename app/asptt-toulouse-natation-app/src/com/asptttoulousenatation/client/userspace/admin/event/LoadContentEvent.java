package com.asptttoulousenatation.client.userspace.admin.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoadContentEvent extends GwtEvent<LoadContentEventHandler> {

	public static final Type<LoadContentEventHandler> TYPE = new Type<LoadContentEventHandler>();
	
	private Long menuId;
	private LoadContentAreaEnum area;
	
	public LoadContentEvent() {
		super();
		area = LoadContentAreaEnum.CONTENT;
	}
	
	public LoadContentEvent(Long pMenuId) {
		this();
		menuId = pMenuId;
	}

	public LoadContentEvent(Long pMenuId, LoadContentAreaEnum pArea) {
		super();
		menuId = pMenuId;
		area = pArea;
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

	public LoadContentAreaEnum getArea() {
		return area;
	}

	public void setArea(LoadContentAreaEnum pArea) {
		area = pArea;
	}

	public enum LoadContentAreaEnum {
		CONTENT,
		TOOL,
		INSCRIPTION;
	}
}