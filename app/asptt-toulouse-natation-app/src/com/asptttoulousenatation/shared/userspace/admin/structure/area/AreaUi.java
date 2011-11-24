package com.asptttoulousenatation.shared.userspace.admin.structure.area;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.google.gwt.user.client.rpc.IsSerializable;

public class AreaUi implements IsSerializable {

	private long id;
	
	private String title;
	
	private Map<String, MenuUi> menuSet;
	
	private boolean shortcut;
	
	public AreaUi() {
		
	}

	public AreaUi(long pId, String pTitle, boolean pShortcut) {
		id = pId;
		title = pTitle;
		menuSet = new LinkedHashMap<String, MenuUi>();
		shortcut = pShortcut;
	}

	public long getId() {
		return id;
	}

	public void setId(long pId) {
		id = pId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public Map<String, MenuUi> getMenuSet() {
		return menuSet;
	}

	public void setMenuSet(Map<String, MenuUi> pMenuSet) {
		menuSet = pMenuSet;
	}
	
	public MenuUi getMenu(final String pTitle) {
		return menuSet.get(pTitle);
	}

	public boolean isShortcut() {
		return shortcut;
	}

	public void setShortcut(boolean pShortcut) {
		shortcut = pShortcut;
	}
	
	public boolean canDisplay() {
		boolean lCanDisplay = false;
		Iterator<MenuUi> lIterator = menuSet.values().iterator();
		while(!lCanDisplay && lIterator.hasNext()) {
			lCanDisplay = lIterator.next().isDisplay();
		}
		return lCanDisplay;
	}
}