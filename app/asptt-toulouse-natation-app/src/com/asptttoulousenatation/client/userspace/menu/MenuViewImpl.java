package com.asptttoulousenatation.client.userspace.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class MenuViewImpl extends ResizeComposite implements MenuView {

	private Map<String, ButtonBase> menus;
	private StackLayoutPanel panel;
	private SimpleLayoutPanel areaContent;
	private Button areaLoadButton;
	
	private InitUserSpaceResult initUserSpaceResult;
	
	public MenuViewImpl(InitUserSpaceResult pInitUserSpaceResult) {
		initUserSpaceResult = pInitUserSpaceResult;
		panel = new StackLayoutPanel(Unit.PCT);
		initWidget(panel);
		
		menus = new HashMap<String, ButtonBase>();
		for(AreaUi lArea: initUserSpaceResult.getArea().values()) {
			if("Structure".equals(lArea.getTitle())) {
				panel.add(buildAreaContent(), buildAreaHeader(), 5);
			}
			else {
				panel.add(build(lArea), lArea.getTitle(), 5);
			}
		}
	}
	
	private Widget build(AreaUi pArea) {
		LayoutPanel lPanel = new LayoutPanel();
		int lPct = 0;
		for(MenuUi lMenu: pArea.getMenuSet().values()) {
			Button lButton = new Button(lMenu.getTitle());
			lButton.setWidth("100%");
			menus.put(MenuItems.valueOf(lMenu.getMenuKey()).toString(), lButton);
			lPanel.add(lButton);
			lPanel.setWidgetTopHeight(lButton, lPct, Unit.PCT, 7, Unit.PCT);
			lPct+= 7;
		}
		
		return lPanel;
	}
	
	public IsWidget buildAreaHeader() {
		areaLoadButton = new Button("Structure");
		return areaLoadButton;
	}
	
	private Panel buildAreaContent() {
		areaContent = new SimpleLayoutPanel();
		return areaContent;
	}
	
	public HasClickHandlers getItem(MenuItems pMenuItems) {
		return menus.get(pMenuItems.toString());
	}

	public HasClickHandlers getAreaLoadButton() {
		return areaLoadButton;
	}

	public void setArea(List<AreaUi> pArea) {
		areaContent.clear();
		LayoutPanel lPanel = new LayoutPanel();
		int lPct = 0;
		for(AreaUi lAreaUi: pArea) {
			Button lAreaButton = new Button(lAreaUi.getTitle());
			lAreaButton.setWidth("100%");
			lPanel.add(lAreaButton);
			MenuItems lMenuItems = MenuItems.STRUCTURE;
			lMenuItems.setSalt(lAreaUi.getTitle());
			menus.put(lMenuItems.toString(), lAreaButton);
			lPanel.setWidgetTopHeight(lAreaButton, lPct, Unit.PCT, 7, Unit.PCT);
			lPct+= 7;
		}
		areaContent.add(lPanel);
	}
}