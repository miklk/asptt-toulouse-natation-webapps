package com.asptttoulousenatation.client.userspace.menu;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEvent;
import com.asptttoulousenatation.core.client.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.structure.area.CreateAreaAction;
import com.asptttoulousenatation.core.shared.structure.area.CreateAreaResult;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class MenuActivity extends MyAbstractActivity<MenuPlace> {

	private InitUserSpaceResult initUserSpaceResult;
	private UserUi user;

	public MenuActivity(MenuPlace pPlace, ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, final EventBus pEventBus) {
		final EventBus lEventBus = pEventBus;
		final MenuView lMenuView = clientFactory
				.getMenuView(initUserSpaceResult, new Command() {
					
					public void execute() {
						lEventBus.fireEvent(new UpdateContentEvent(
								MenuItems.VIDE,
								null));
					}
				});
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
		
		lMenuView.getCreateAreaButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				dispatchAsync.execute(new CreateAreaAction(lMenuView.getAreaTitle().getValue(), lMenuView.getAreaOrder()), new AsyncCallback<CreateAreaResult>() {

					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur " + pCaught.getMessage());
					}

					public void onSuccess(CreateAreaResult pResult) {
						Window.alert("La zone a été créée.");
						lMenuView.hidePopup();
						
						pEventBus.fireEvent(new UpdateContentEvent<UserUi>(
								MenuItems.ADMIN, user));
					}
				});
			}
		});
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

	public void setUser(UserUi pUser) {
		user = pUser;
	}
}