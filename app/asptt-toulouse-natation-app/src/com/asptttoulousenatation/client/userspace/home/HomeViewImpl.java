	package com.asptttoulousenatation.client.userspace.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class HomeViewImpl extends Composite implements HomeView {

	private SimplePanel panel;
	
	private Map<String, HasClickHandlers> shortcuts;
	
	public HomeViewImpl(List<MenuUi> pMenuList) {
		panel = new SimplePanel();
		initWidget(panel);
		
		buildShortcut(pMenuList);
	}
	
	private void buildShortcut(List<MenuUi> pMenuList) {
		shortcuts = new HashMap<String, HasClickHandlers>(pMenuList.size());
		FlexTable lShortcutPanel = new FlexTable();
		int lMaxColumns = 3;
		int lRowIndex = 0;
		for(int i = 0; i < pMenuList.size(); i++) {
			MenuUi lMenu = pMenuList.get(i);
			Button lShortcut = new Button(lMenu.getTitle());
			lShortcutPanel.setWidget(lRowIndex, (i%lMaxColumns), lShortcut);
			shortcuts.put(lMenu.getMenuKey(), lShortcut);
		}
		panel.setWidget(lShortcutPanel);
	}
	
	
	
	public HasClickHandlers getShorcutButton(String pKey) {
		return shortcuts.get(pKey);
	}
}