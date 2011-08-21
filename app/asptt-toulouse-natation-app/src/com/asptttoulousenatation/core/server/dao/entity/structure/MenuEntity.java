package com.asptttoulousenatation.core.server.dao.entity.structure;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class MenuEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6502404237783976169L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String title;
	
	@Persistent
	private Long area;
	
	@Persistent
	private String menuKey;
	
	@Persistent
	private boolean shortcut;
	
	@Persistent
	private boolean display;
	
	@Persistent
	private int order;
	
	public MenuEntity() {
	}
	
	
	public MenuEntity(String pMenuKey, String pTitle, Long pArea, boolean pShortcut, boolean pDisplay, int pOrder) {
		menuKey = pMenuKey;
		title = pTitle;
		area = pArea;
		shortcut = pShortcut;
		display = pDisplay;
		order = pOrder;
	}
	
	public Key getId() {
		return id;
	}

	public void setId(Key pId) {
		id = pId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long pArea) {
		area = pArea;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String pMenuKey) {
		menuKey = pMenuKey;
	}


	public boolean isShortcut() {
		return shortcut;
	}


	public void setShortcut(boolean pShortcut) {
		shortcut = pShortcut;
	}


	public boolean isDisplay() {
		return display;
	}


	public void setDisplay(boolean pDisplay) {
		display = pDisplay;
	}


	public int getOrder() {
		return order;
	}


	public void setOrder(int pOrder) {
		order = pOrder;
	}
	
}