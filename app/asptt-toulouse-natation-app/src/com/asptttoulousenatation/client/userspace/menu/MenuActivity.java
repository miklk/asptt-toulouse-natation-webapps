package com.asptttoulousenatation.client.userspace.menu;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEvent;
import com.asptttoulousenatation.core.client.MyAbstractActivity;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class MenuActivity extends MyAbstractActivity<MenuPlace> {

	private InitUserSpaceResult initUserSpaceResult;

	public MenuActivity(MenuPlace pPlace, ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final EventBus lEventBus = pEventBus;
		final MenuView lMenuView = clientFactory
				.getMenuView(initUserSpaceResult);
		addAction(lEventBus, lMenuView, MenuItems.NEWS_PUBLICATION);
		addAction(lEventBus, lMenuView, MenuItems.NEWS_EDITION);
		
		for (AreaUi lAreaUi : initUserSpaceResult.getAreaResult().getArea()) {
			final AreaUi lAreaUiFinal = lAreaUi;
			MenuItems lMenuItems = MenuItems.STRUCTURE;
			lMenuItems.setSalt(lAreaUi.getTitle());
			HasClickHandlers lMenuObject = lMenuView
					.getItem(lMenuItems);
			if (lMenuObject != null) {
				lMenuObject
						.addClickHandler(new ClickHandler() {
							public void onClick(
									ClickEvent pEvent) {
								lEventBus
										.fireEvent(new UpdateContentEvent(
												MenuItems.STRUCTURE,
												lAreaUiFinal));
							}
						});
			}
		}
		addAction(lEventBus, lMenuView, MenuItems.USER_CREATION);
		addAction(lEventBus, lMenuView, MenuItems.USER_EDITION);
		addAction(lEventBus, lMenuView, MenuItems.CLUB_GROUP_EDITION);
		addAction(lEventBus, lMenuView, MenuItems.CLUB_SLOT_EDITION);
		addAction(lEventBus, lMenuView, MenuItems.COMPETITION_EDITION);
		addAction(lEventBus, lMenuView, MenuItems.OFFICIEL_VIEW);
		addAction(lEventBus, lMenuView, MenuItems.OFFICIEL_SUBSCRIBE);
		pPanel.setWidget(lMenuView);
	}

	private void addAction(final EventBus pEventBus, final MenuView pView,
			final MenuItems pMenu) {
		HasClickHandlers lMenuObject = pView.getItem(pMenu);
		if (lMenuObject != null) {
			lMenuObject.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent pEvent) {
					pEventBus.fireEvent(new UpdateContentEvent(pMenu));
				}
			});
		}
	}

	public void setInitUserSpaceResult(InitUserSpaceResult pInitUserSpaceResult) {
		initUserSpaceResult = pInitUserSpaceResult;
	}
}