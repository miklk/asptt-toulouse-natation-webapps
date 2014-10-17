package com.asptttoulousenatation.core.server.dao.entity.structure;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class MenuEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6502404237783976169L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
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
	
	@Persistent
	private Set<Long> subMenu;
	
	@Persistent
	private Long parent;
	
	@Persistent
	private Boolean divider;
	
	@Persistent
	private Boolean alone;
	
	@Persistent
	private String identifier;
	
	public MenuEntity() {
		subMenu = new HashSet<Long>();
	}
	
	
	public MenuEntity(String pMenuKey, String pTitle, Long pArea, boolean pShortcut, boolean pDisplay, int pOrder, Set<Long> pSubMenu, Long pParent) {
		this();
		menuKey = pMenuKey;
		title = pTitle;
		area = pArea;
		shortcut = pShortcut;
		display = pDisplay;
		order = pOrder;
		subMenu = pSubMenu;
		parent = pParent;
		divider = false;
		alone = false;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
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


	public Set<Long> getSubMenu() {
		return subMenu;
	}


	public void setSubMenu(Set<Long> pSubMenu) {
		subMenu = pSubMenu;
	}


	public Long getParent() {
		return parent;
	}


	public void setParent(Long pParent) {
		parent = pParent;
	}


	public Boolean getDivider() {
		return divider;
	}


	public void setDivider(Boolean pDivider) {
		divider = pDivider;
	}


	public Boolean getAlone() {
		return alone;
	}


	public void setAlone(Boolean pAlone) {
		alone = pAlone;
	}


	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String pIdentifier) {
		identifier = pIdentifier;
	}
}