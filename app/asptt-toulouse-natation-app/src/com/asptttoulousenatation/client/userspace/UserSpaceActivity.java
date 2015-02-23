package com.asptttoulousenatation.client.userspace;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.client.userspace.admin.actu.ActuActivity;
import com.asptttoulousenatation.client.userspace.admin.actu.ActuPlace;
import com.asptttoulousenatation.client.userspace.admin.club.group.GroupActivity;
import com.asptttoulousenatation.client.userspace.admin.club.group.GroupPlace;
import com.asptttoulousenatation.client.userspace.admin.club.slot.SlotActivity;
import com.asptttoulousenatation.client.userspace.admin.club.slot.SlotPlace;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEvent;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEventHandler;
import com.asptttoulousenatation.client.userspace.admin.structure.area.AreaActivity;
import com.asptttoulousenatation.client.userspace.admin.structure.area.AreaPlace;
import com.asptttoulousenatation.client.userspace.home.HomeActivity;
import com.asptttoulousenatation.client.userspace.home.HomePlace;
import com.asptttoulousenatation.client.userspace.menu.MenuActivity;
import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.client.userspace.menu.MenuPlace;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitUserSpaceAction;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

public class UserSpaceActivity extends MyAbstractActivity<UserSpacePlace> {

	private UserUi user;

	public UserSpaceActivity(UserSpacePlace pPlace, ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
		user = pPlace.getObject();
	}

	public UserSpaceActivity(UserSpacePlace pPlace,
			ClientFactory pClientFactory, UserUi pUser) {
		super(pPlace, pClientFactory);
		user = pUser;
	}

	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		// TODO get user init user space
		final EventBus lEventBus = pEventBus;
		final SimplePanel lPanel = new SimplePanel();
		dispatchAsync.execute(
				new InitUserSpaceAction(ProfileEnum.getProfiles(user
						.getProfiles())),
				new AsyncCallback<InitUserSpaceResult>() {

					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur " + pCaught.getMessage());
					}

					public void onSuccess(final InitUserSpaceResult pResult) {

						final UserSpaceView lAdminView = clientFactory
								.getAdminView();
						lAdminView.setUserName(user.getEmailAddress());

						// Set menu
						MenuActivity lMenuActivity = new MenuActivity(
								new MenuPlace(), clientFactory);
						lMenuActivity.setInitUserSpaceResult(pResult);
						lMenuActivity.setUser(user);
						lMenuActivity.start(lAdminView.getMenuPanel(),
								lEventBus);

						// Set default content
						HomeActivity lHomeActivity = new HomeActivity(
								new HomePlace(), clientFactory);
						lHomeActivity.setUserSpaceResult(pResult);
						lHomeActivity.start(lAdminView.getContentPanel(),
								lEventBus);

						lEventBus.addHandler(UpdateContentEvent.TYPE,
								new UpdateContentEventHandler() {

									public void updateContent(
											UpdateContentEvent pEvent) {
										switch (pEvent.getAction()) {
										case NEWS_PUBLICATION:
											ActuActivity lActuActivity = new ActuActivity(
													new ActuPlace(),
													clientFactory, false);
											lActuActivity.start(lAdminView
													.getContentPanel(),
													lEventBus);
											break;
										case NEWS_EDITION:
											ActuActivity lActuEditionActivity = new ActuActivity(
													new ActuPlace(),
													clientFactory, true);
											lActuEditionActivity.start(
													lAdminView
															.getContentPanel(),
													lEventBus);
											break;
										case STRUCTURE:
											AreaUi lArea = (AreaUi) pEvent
													.getObject();
											AreaActivity lAreaActivity = new AreaActivity(
													new AreaPlace(),
													clientFactory);
											lAreaActivity.setArea(lArea);
											lAreaActivity.start(lAdminView
													.getContentPanel(),
													lEventBus);
											break;
										case CLUB_GROUP_EDITION:
											GroupActivity lGroupActivity = new GroupActivity(
													new GroupPlace(),
													clientFactory);
											lGroupActivity.start(lAdminView
													.getContentPanel(),
													lEventBus);
											break;
										case CLUB_SLOT_EDITION:
											SlotActivity lSlotActivity = new SlotActivity(
													new SlotPlace(),
													clientFactory);
											lSlotActivity.start(lAdminView
													.getContentPanel(),
													lEventBus);
											break;
										case VIDE:
											lAdminView.getContentPanel()
													.setWidget(
															new SimplePanel());
											break;
										default:
											HomeActivity lHomeActivity = new HomeActivity(
													new HomePlace(),
													clientFactory);
											lHomeActivity
													.setUserSpaceResult(pResult);
											lHomeActivity.start(lAdminView
													.getContentPanel(),
													lEventBus);
										}
									}
								});

						lAdminView.getPublicButton().addClickHandler(
								new ClickHandler() {
									public void onClick(ClickEvent pEvent) {
										lEventBus
												.fireEvent(new UpdateContentEvent<UserUi>(
														MenuItems.PUBLIC, user));
									}
								});
						lPanel.setWidget(lAdminView);
					}
				});
		pPanel.setWidget(lPanel);
	}
}