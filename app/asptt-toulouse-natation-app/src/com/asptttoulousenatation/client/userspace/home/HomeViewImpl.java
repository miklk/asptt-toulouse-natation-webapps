package com.asptttoulousenatation.client.userspace.home;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomeViewImpl extends Composite implements HomeView {

	private VerticalPanel panel;
	
	private Map<String, HasClickHandlers> shortcuts;
	
	public HomeViewImpl(List<MenuUi> pMenuList) {
		panel = new VerticalPanel();
		initWidget(panel);
		
		buildShortcut(pMenuList);
		Button button = new Button("Gestion des adhérents");
		button.setStyleName(CSS.moreActuButton());
		button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent pEvent) {
				Window.open("http://www.asptt-toulouse-natation.com/v2/adherents.html", "Gestion des adhérents", "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
			}
		});
		panel.add(button);
		button = new Button("Inscription en ligne");
		button.setStyleName(CSS.moreActuButton());
		button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent pEvent) {
				Window.open("http://www.asptt-toulouse-natation.com/v2/inscription.html", "Gestion des adhérents", "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
			}
		});
		panel.add(button);
		button = new Button("Remplissage des créneaux");
		button.setStyleName(CSS.moreActuButton());
		button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent pEvent) {
				Window.open("http://www.asptt-toulouse-natation.com/v2/creneau.html", "Gestion des adhérents", "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
			}
		});
		panel.add(button);
		button = new Button("Fiche de présence");
		button.setStyleName(CSS.moreActuButton());
		button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent pEvent) {
				Window.open("http://www.asptt-toulouse-natation.com/v2/presence.html", "Gestion des adhérents", "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
			}
		});
		panel.add(button);
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
		
		panel.add(lShortcutPanel);
	}
	
	
	
	public HasClickHandlers getShorcutButton(String pKey) {
		return shortcuts.get(pKey);
	}
}