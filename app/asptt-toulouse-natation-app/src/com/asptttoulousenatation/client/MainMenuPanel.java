package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.EventBus;

public class MainMenuPanel extends Composite {

	private InitResult initResult;

	private EventBus eventBus;

	private FlowPanel panel;

	public MainMenuPanel(InitResult pInitResult, UserUi pUser,
			EventBus pEventBus) {
		initResult = pInitResult;
		eventBus = pEventBus;
		panel = new FlowPanel();
		initWidget(panel);
		panel.setStyleName(CSS.menuG());
	}

	private void loadContent(final String pAreaTitle, final String pMenuTitle,
			MenuUi pMenu) {
		eventBus.fireEvent(new LoadContentEvent(pMenu, pAreaTitle, pMenuTitle));
	}

	private void addMenuGSubStyle(final Label pLabel) {
		pLabel.addMouseOverHandler(new MouseOverHandler() {

			public void onMouseOver(MouseOverEvent pEvent) {
				pLabel.addStyleName(CSS.menuGSubMouseOver());
			}
		});
		pLabel.addMouseOutHandler(new MouseOutHandler() {

			public void onMouseOut(MouseOutEvent pEvent) {
				pLabel.removeStyleName(CSS.menuGSubMouseOver());
			}
		});
	}

	private void openMenuSubMenu(Label pLabel, MenuUi pMenu,
			final String lAreaTitle) {
		PopupPanel lPopupPanel = new PopupPanel(true);
		VerticalPanel lPanel = new VerticalPanel();
		for (final MenuUi lSubMenu : pMenu.getSubMenus()) {
			Label lLabel = new Label(lSubMenu.getTitle());
			lLabel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent pEvent) {
					loadContent(lAreaTitle, lSubMenu.getTitle(), lSubMenu);
				}
			});
			lPanel.add(lLabel);
			lLabel.setStyleName(CSS.menuGSub());
			addMenuGSubStyle(lLabel);
		}
		lPopupPanel.setWidget(lPanel);
		lPopupPanel.getElement().getStyle().setMarginLeft(150, Unit.PX);
		lPopupPanel.showRelativeTo(pLabel);
	}
}