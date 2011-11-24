package com.asptttoulousenatation.client.userspace.admin.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoadContentEvent extends GwtEvent<LoadContentEventHandler> {

	public static final Type<LoadContentEventHandler> TYPE = new Type<LoadContentEventHandler>();
	
	private Long menuId;
	private LoadContentAreaEnum area;
	
	private String areaTitle;
	private String menuTitle;
	
	public LoadContentEvent() {
		super();
		area = LoadContentAreaEnum.CONTENT;
	}
	
	public LoadContentEvent(Long pMenuId, String pAreaTitle, String pMenuTitle) {
		this();
		menuId = pMenuId;
		areaTitle = pAreaTitle;
		menuTitle = pMenuTitle;
	}

	public LoadContentEvent(Long pMenuId, LoadContentAreaEnum pArea,
			String pAreaTitle, String pMenuTitle) {
		this(pMenuId, pAreaTitle, pMenuTitle);
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

	public String getAreaTitle() {
		return areaTitle;
	}

	public void setAreaTitle(String pAreaTitle) {
		areaTitle = pAreaTitle;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String pMenuTitle) {
		menuTitle = pMenuTitle;
	}


	public enum LoadContentAreaEnum {
		CONTENT,
		TOOL,
		INSCRIPTION,
		FORGET_PASSWORD,
		BOTTOM;
	}
}