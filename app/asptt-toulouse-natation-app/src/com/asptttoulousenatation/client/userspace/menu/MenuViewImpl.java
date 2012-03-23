package com.asptttoulousenatation.client.userspace.menu;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asptttoulousenatation.core.client.ui.StackAwarePanel;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MenuViewImpl extends Composite implements MenuView {

	private Map<String, ButtonBase> menus;
	private StackAwarePanel panel;
	private SimplePanel areaContent;
	private Button createAreaButton;
	
	
	private TextBox areaTitle;
	private TextBox areaOrder;
	private Button createArea;
	private PopupPanel popup;
	
	private InitUserSpaceResult initUserSpaceResult;
	
	private Command onShowCommand;
	
	public MenuViewImpl(InitUserSpaceResult pInitUserSpaceResult, Command pStackPanelShowCommand) {
		initUserSpaceResult = pInitUserSpaceResult;
		onShowCommand = pStackPanelShowCommand;
		panel = new StackAwarePanel();
		initWidget(panel);
		panel.addStyleName(CSS.userSpaceMenu());
		menus = new HashMap<String, ButtonBase>();
		int stackIndex = 0;
		for(AreaUi lArea: initUserSpaceResult.getArea().values()) {
			if("Structure du site".equals(lArea.getTitle())) {
				panel.insert(buildAreaContent(), stackIndex);
				panel.setStackText(stackIndex, lArea.getTitle());
				panel.addCommand(stackIndex, pStackPanelShowCommand);
			}
			else {
				panel.insert(build(lArea), stackIndex);
				panel.setStackText(stackIndex, lArea.getTitle());
				panel.addCommand(stackIndex, pStackPanelShowCommand);
			}
			stackIndex++;
		}
		createArea = new Button("Créer un menu");
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
		createAreaButton = new Button("Créer un menu");
		createAreaButton.setStyleName(CSS.userSpaceMenuButton());
		createAreaButton.addStyleName(CSS.createMenuButton());
		createAreaButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				if(onShowCommand != null) {
					onShowCommand.execute();
				}
				createAreaCreationPanel();
			}
		});
		lPanel.add(createAreaButton);
		
		areaContent.add(lPanel);
	}
	
	private void createAreaCreationPanel() {
		FlexTable lPanel = new FlexTable();
		int lRowIndex = 0;
		
		//Area title
		areaTitle = new TextBox();
		areaTitle.setWidth("300px");
		lPanel.setWidget(lRowIndex, 0, createLabel("Intitulé du menu"));
		lPanel.setWidget(lRowIndex, 1, areaTitle);
		lRowIndex++;
		
		//Area order
		areaOrder = new TextBox();
		areaOrder.setWidth("30px");
		lPanel.setWidget(lRowIndex, 0, createLabel("Ordre d'affichage"));
		lPanel.setWidget(lRowIndex, 1, areaOrder);
		lRowIndex++;
		
		lPanel.setWidget(lRowIndex, 0, createArea);
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lCellFormatter.setColSpan(lRowIndex, 0, 2);
		lCellFormatter.setHorizontalAlignment(lRowIndex, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		popup = new PopupPanel(true, true);
		popup.setWidget(lPanel);
		popup.center();
	}
	
	private Label createLabel(String pLabel) {
		Label lLabel = new Label(pLabel);
		lLabel.setStyleName(CSS.userSpaceContentLabel());
		return lLabel;
	}

	public HasValue<String> getAreaTitle() {
		return areaTitle;
	}

	public Short getAreaOrder() {
		return Short.valueOf(areaOrder.getValue());
	}

	public HasClickHandlers getCreateAreaButton() {
		return createArea;
	}

	public void hidePopup() {
		popup.hide();
	}
}