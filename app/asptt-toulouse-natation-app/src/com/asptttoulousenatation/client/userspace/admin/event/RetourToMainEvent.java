package com.asptttoulousenatation.client.userspace.admin.event;

import com.asptttoulousenatation.client.userspace.menu.MenuItems;

public class RetourToMainEvent extends UpdateContentEvent {

	private String userName;
	
	public RetourToMainEvent() {
		super(MenuItems.PUBLIC);
	}

	public RetourToMainEvent(String pUserName) {
		this();
		userName = pUserName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String pUserName) {
		userName = pUserName;
	}
	

	
}
