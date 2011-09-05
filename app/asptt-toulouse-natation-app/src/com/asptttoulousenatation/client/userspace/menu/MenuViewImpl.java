package com.asptttoulousenatation.client.userspace.menu;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MenuViewImpl extends Composite implements MenuView {

	private Map<String, ButtonBase> menus;
	private StackPanel panel;
	private SimplePanel areaContent;
	
	private InitUserSpaceResult initUserSpaceResult;
	
	public MenuViewImpl(InitUserSpaceResult pInitUserSpaceResult) {
		initUserSpaceResult = pInitUserSpaceResult;
		panel = new StackPanel();
		initWidget(panel);
		panel.addStyleName(CSS.userSpaceMenu());
		menus = new HashMap<String, ButtonBase>();
		for(AreaUi lArea: initUserSpaceResult.getArea().values()) {
			if("Structure du site".equals(lArea.getTitle())) {
				panel.add(buildAreaContent(), lArea.getTitle());
			}
			else {
				panel.add(build(lArea), lArea.getTitle());
			}
		}
	}
	
	private Widget build(AreaUi pArea) {
		VerticalPanel lPanel = new VerticalPanel();
		int lPct = 0;
		for(MenuUi lMenu: pArea.getMenuSet().values()) {
			Button lButton = new Button(lMenu.getTitle());
			lButton.setStyleName(CSS.userSpaceMenuButton());
			menus.put(MenuItems.valueOf(lMenu.getMenuKey()).toString(), lButton);
			lPanel.add(lButton);
//			lPanel.setWidgetTopHeight(lButton, lPct, Unit.PCT, 7, Unit.PCT);
			lPct+= 7;
		}
		
		return lPanel;
	}
	
	private Panel buildAreaContent() {
		areaContent = new SimplePanel();
		setArea(initUserSpaceResult.getAreaResult().getArea());
		return areaContent;
	}
	
	public HasClickHandlers getItem(MenuItems pMenuItems) {
		return menus.get(pMenuItems.toString());
	}

	private void setArea(List<AreaUi> pArea) {
		areaContent.clear();
		VerticalPanel lPanel = new VerticalPanel();
		int lPct = 0;
		for(AreaUi lAreaUi: pArea) {
			Button lAreaButton = new Button(lAreaUi.getTitle());
			lAreaButton.setStyleName(CSS.userSpaceMenuButton());
			lPanel.add(lAreaButton);
			MenuItems lMenuItems = MenuItems.STRUCTURE;
			lMenuItems.setSalt(lAreaUi.getTitle());
			menus.put(lMenuItems.toString(), lAreaButton);
//			lPanel.setWidgetTopHeight(lAreaButton, lPct, Unit.PCT, 7, Unit.PCT);
			lPct+= 7;
		}
		areaContent.add(lPanel);
	}
}