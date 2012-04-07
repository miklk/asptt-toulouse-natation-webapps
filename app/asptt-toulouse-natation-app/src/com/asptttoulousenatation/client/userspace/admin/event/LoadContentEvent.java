package com.asptttoulousenatation.client.userspace.admin.event;

import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.google.gwt.event.shared.GwtEvent;

public class LoadContentEvent extends GwtEvent<LoadContentEventHandler> {

	public static final Type<LoadContentEventHandler> TYPE = new Type<LoadContentEventHandler>();
	
	private MenuUi menu;
	private LoadContentAreaEnum area;
	
	private String areaTitle;
	private String menuTitle;
	
	public LoadContentEvent() {
		super();
		area = LoadContentAreaEnum.CONTENT;
	}
	
	public LoadContentEvent(MenuUi pMenu, String pAreaTitle, String pMenuTitle) {
		this();
		menu = pMenu;
		areaTitle = pAreaTitle;
		menuTitle = pMenuTitle;
	}
	
	public LoadContentEvent(MenuUi pMenu, LoadContentAreaEnum pArea,
			String pAreaTitle, String pMenuTitle) {
		this(pMenu, pAreaTitle, pMenuTitle);
		area = pArea;
	}
	
	public MenuUi getMenu() {
		return menu;
	}

	public void setMenu(MenuUi pMenu) {
		menu = pMenu;
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
		BOTTOM,
		SUB_CONTENT;
	}
}