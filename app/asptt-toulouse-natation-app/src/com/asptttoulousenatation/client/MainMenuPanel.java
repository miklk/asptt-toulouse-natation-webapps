package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent;
import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
		List<AreaUi> lAreaUis = new ArrayList<AreaUi>(initResult.getMenu());
		// First
		final AreaUi lFirstArea = lAreaUis.get(0);
		lAreaUis.remove(0);
		Label lFirstAreaTitle = new Label(lFirstArea.getTitle());
		lFirstAreaTitle.setStyleName(CSS.menuGTitleFirst());
		panel.add(lFirstAreaTitle);
		// Build menu
		FlowPanel lSubMenu = new FlowPanel();
		lSubMenu.getElement().getStyle().setBackgroundColor("#FFFFFF");
		for (final MenuUi lMenu : lFirstArea.getMenuSet().values()) {
			if (lMenu.isDisplay()) {
				final Label lMenuLabel;
					lMenuLabel = new Label(lMenu.getTitle());
				lMenuLabel.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						if (CollectionUtils.isEmpty(lMenu.getSubMenus())) {
							loadContent(lFirstArea.getTitle(), lMenu.getTitle(), lMenu);
						} else {
							openMenuSubMenu(lMenuLabel, lMenu,
									lFirstArea.getTitle());
						}
					}
				});

				lMenuLabel.setStyleName(CSS.menuGSub());
				addMenuGSubStyle(lMenuLabel);
				lSubMenu.add(lMenuLabel);
				space += 5;
			}
		}
		panel.add(lSubMenu);
		for (final AreaUi lArea : lAreaUis) {
			if (lArea.canDisplay()) {
				Label lAreaTitle = new Label(lArea.getTitle());
				lAreaTitle.setStyleName(CSS.menuGTitle());
				panel.add(lAreaTitle);
				space += 6;
				// Build menu
				lSubMenu = new FlowPanel();
				lSubMenu.getElement().getStyle().setBackgroundColor("#FFFFFF");
				for (final MenuUi lMenu : lArea.getMenuSet().values()) {
					if (lMenu.isDisplay()) {
						final Label lMenuLabel = new Label(lMenu.getTitle());
						lMenuLabel.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent pEvent) {
									loadContent(lArea.getTitle(),
											lMenu.getTitle(), lMenu);
							}
						});

						lMenuLabel.setStyleName(CSS.menuGSub());
						addMenuGSubStyle(lMenuLabel);
						lSubMenu.add(lMenuLabel);
						space += 5;
					}
				}
				panel.add(lSubMenu);
			}
		}
	}

	private void loadContent(final String pAreaTitle, final String pMenuTitle, MenuUi pMenu) {
			eventBus.fireEvent(new LoadContentEvent(pMenu, pAreaTitle,
					pMenuTitle));
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

	public void setPopupManager(PopupManager pPopupManager) {
		popupManager = pPopupManager;
	}
}