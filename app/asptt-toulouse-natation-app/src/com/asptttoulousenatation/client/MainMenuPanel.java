package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class MainMenuPanel extends Composite {

	private InitResult initResult;
	private PopupManager popupManager;
	private UserUi user;

	private EventBus eventBus;

	private FlowPanel panel;

	public MainMenuPanel(InitResult pInitResult, UserUi pUser,
			EventBus pEventBus) {
		initResult = pInitResult;
		eventBus = pEventBus;
		user = pUser;
		panel = new FlowPanel();
		initWidget(panel);
		panel.setStyleName(CSS.menuG());
		int space = 6;
		List<AreaUi> lAreaUis = new ArrayList<AreaUi>(initResult.getArea()
				.values());
		// First
		final AreaUi lFirstArea = lAreaUis.get(0);
		lAreaUis.remove(0);
		Label lFirstAreaTitle = new Label(lFirstArea.getTitle());
		lFirstAreaTitle.setStyleName(CSS.menuGTitleFirst());
		panel.add(lFirstAreaTitle);
		// Build menu
		for (final MenuUi lMenu : lFirstArea.getMenuSet().values()) {
			Label lMenuLabel = new Label(lMenu.getTitle());
			lMenuLabel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent pEvent) {
					loadContent(lFirstArea.getTitle(), lMenu.getTitle());
				}
			});
			lMenuLabel.setStyleName(CSS.menuGSub());
			addMenuGSubStyle(lMenuLabel);
			panel.add(lMenuLabel);
			space += 5;
		}
		for (final AreaUi lArea : lAreaUis) {
			Label lAreaTitle = new Label(lArea.getTitle());
			lAreaTitle.setStyleName(CSS.menuGTitle());
			panel.add(lAreaTitle);
			space += 6;
			// Build menu
			for (final MenuUi lMenu : lArea.getMenuSet().values()) {
				Label lMenuLabel = new Label(lMenu.getTitle());
				lMenuLabel.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						loadContent(lArea.getTitle(), lMenu.getTitle());
					}
				});
				lMenuLabel.setStyleName(CSS.menuGSub());
				addMenuGSubStyle(lMenuLabel);
				panel.add(lMenuLabel);
				space += 5;
			}
		}
	}

	private void loadContent(final String pAreaTitle, final String pMenuTitle) {
		AreaUi lAreaUi = initResult.getArea(pAreaTitle);
		if (lAreaUi != null) {
			MenuUi lMenu = lAreaUi.getMenu(pMenuTitle);
			eventBus.fireEvent(new LoadContentEvent(lMenu.getId(), pAreaTitle, pMenuTitle));
		}

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

	public void setPopupManager(PopupManager pPopupManager) {
		popupManager = pPopupManager;
	}
}