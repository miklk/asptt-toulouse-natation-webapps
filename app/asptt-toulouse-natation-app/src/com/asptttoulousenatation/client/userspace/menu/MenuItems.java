package com.asptttoulousenatation.client.userspace.menu;

import java.util.ArrayList;
import java.util.List;


public enum MenuItems {

	NEWS_PUBLICATION(true),
	NEWS_EDITION,
	STRUCTURE,
	USER_CREATION(true),
	USER_EDITION(true),
	OFFICIEL_VIEW(true),
	CLUB_GROUP_EDITION,
	CLUB_SLOT_EDITION,
	COMPETITION_EDITION,
	ADMIN,
	PUBLIC,
	VIDE;
	
	private String salt;
	private boolean shortcut;
	
	private MenuItems() {
		salt = "";
		shortcut = false;
	}
	
	private MenuItems(boolean pShortcut) {
		this();
		shortcut = pShortcut;
	}
	
	private MenuItems(String pSalt) {
		this();
		salt = pSalt;
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

	public String toString() {
		String lResult  = name();
		if(!salt.isEmpty()) {
			return lResult + "_" + salt;
		}
		else {
			return lResult;
		}
	}
	
	public static List<MenuItems> getShortcutMenuItems() {
		List<MenuItems> lResult = new ArrayList<MenuItems>();
		for(MenuItems lMenuItems: values()) {
			if(lMenuItems.isShortcut()) {
				lResult.add(lMenuItems);
			}
		}
		return lResult;
	}
}
