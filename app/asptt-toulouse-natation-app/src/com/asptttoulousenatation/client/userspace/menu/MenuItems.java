package com.asptttoulousenatation.client.userspace.menu;

import java.util.ArrayList;
import java.util.List;


public enum MenuItems {

	NEWS_PUBLICATION(false),
	NEWS_EDITION,
	STRUCTURE,
	CLUB_GROUP_EDITION,
	CLUB_SLOT_EDITION,
	ADMIN,
	PUBLIC,
	VIDE("Pas de valeur"),
	REFRESH_ADMIN,
	;
	
	private String salt;
	private boolean shortcut;
	private String i18n;
	
	private MenuItems() {
		salt = "";
		shortcut = false;
		i18n = "";
	}
	
	private MenuItems(boolean pShortcut) {
		this();
		shortcut = pShortcut;
	}
	
	private MenuItems(String pI18n) {
		this();
		i18n = pI18n;
	}
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String pSalt) {
		salt = pSalt;
	}
	
	
	public boolean isShortcut() {
		return shortcut;
	}

	public void setShortcut(boolean pShortcut) {
		shortcut = pShortcut;
	}

	public String getI18n() {
		return i18n;
	}

	public void setI18n(String pI18n) {
		i18n = pI18n;
	}

	public String toString() {
		String lResult  = name();
		if(!salt.isEmpty()) {
			return lResult + "_" + salt;
		}
		else {
			return lResult;
		}
	}
	
	public static List<MenuItems> getSelectableMenuItems() {
		List<MenuItems> lResult = new ArrayList<MenuItems>();
		lResult.add(MenuItems.VIDE);
		return lResult;
	}
}
