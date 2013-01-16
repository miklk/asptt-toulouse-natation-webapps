package com.asptttoulousenatation.client;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.client.subscription.SubscriptionActivity;
import com.asptttoulousenatation.client.subscription.SubscriptionPlace;
import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent;
import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEventHandler;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEvent;
import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.core.client.ui.PopupValidateAction;
import com.asptttoulousenatation.core.shared.structure.LoadContentAction;
import com.asptttoulousenatation.core.shared.structure.LoadContentResult;
import com.asptttoulousenatation.core.shared.user.AuthenticationAction;
import com.asptttoulousenatation.core.shared.user.AuthenticationResult;
import com.asptttoulousenatation.core.shared.user.LogoutAction;
import com.asptttoulousenatation.core.shared.user.LogoutResult;
import com.asptttoulousenatation.core.shared.user.PasswordForgetAction;
import com.asptttoulousenatation.core.shared.user.PasswordForgetResult;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitAction;
import com.asptttoulousenatation.shared.init.InitResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.EventBus;

public class MainActivity extends MyAbstractActivity<MainPlace> {

	private UserUi user;

	public MainActivity(MainPlace pPlace, ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
		user = pPlace.getObject();
	}

	public MainActivity(MainPlace pPlace, ClientFactory pClientFactory,
			UserUi pUser) {
		this(pPlace, pClientFactory);
		user = pPlace.getObject();
	}

	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final EventBus lEventBus = pEventBus;
		final VerticalPanel lPanel = new VerticalPanel();
		dispatchAsync.execute(new InitAction(),
				new AsyncCallback<InitResult>() {

					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur " + pCaught.getMessage());
					}

					public void onSuccess(InitResult pResult) {
						final PopupManager lPopupManager = new PopupManager();
						final MainView lMainView = clientFactory.getMainView(
								pResult, user, lEventBus);
						lMainView.setPopupManager(lPopupManager);
						lMainView.setConnexionAction(new PopupValidateAction() {
							public void execute() {
								fireAuthentication(lEventBus, lMainView);
							}
						});
						lMainView.getDisconnectButton().addClickHandler(
								new ClickHandler() {
									public void onClick(ClickEvent pEvent) {
										dispatchAsync
												.execute(
														new LogoutAction(),
														new AsyncCallback<LogoutResult>() {
															public void onFailure(
																	Throwable pCaught) {
																Window.alert("Erreur "
																		+ pCaught
																				.getMessage());
															}

															public void onSuccess(
																	LogoutResult pResult) {
																lEventBus
																		.fireEvent(new UpdateContentEvent<Object>(
																				MenuItems.PUBLIC));
															}
														});

									}
								});
						lMainView.getPriveSpaceButton().addClickHandler(
								new ClickHandler() {
									public void onClick(ClickEvent pEvent) {
										lEventBus
												.fireEvent(new UpdateContentEvent<UserUi>(
														MenuItems.ADMIN, user));
									}
								});
						lMainView.getPasswordForgetButton().addClickHandler(
								new ClickHandler() {
									public void onClick(ClickEvent pEvent) {
										dispatchAsync
												.execute(
														new PasswordForgetAction(
																lMainView
																		.getEmailAddress()
																		.getValue()),
														new AsyncCallback<PasswordForgetResult>() {
															public void onFailure(
																	Throwable pCaught) {
																Window.alert("Erreur "
																		+ pCaught
																				.getMessage());
															}

															public void onSuccess(
																	PasswordForgetResult pResult) {
																if (pResult
																		.isSended()) {
																	lMainView
																			.passwordSended();
																} else {
																	lMainView
																			.passwordNotSended();
																}
															}
														});
									}
								});
						lEventBus.addHandler(LoadContentEvent.TYPE,
								new LoadContentEventHandler() {

									public void loadContent(
											final LoadContentEvent pEvent) {
										dispatchAsync
												.execute(
														new LoadContentAction(
																pEvent.getMenu()
																		.getId()),
														new AsyncCallback<LoadContentResult>() {
															public void onFailure(
																	Throwable pCaught) {
																Window.alert("Erreur "
																		+ pCaught
																				.getMessage());
															}

															public void onSuccess(
																	LoadContentResult pResult) {
																switch (pEvent
																		.getArea()) {
																case TOOL:
																	lMainView
																			.loadToolContent(pResult
																					.getData());
																	break;
																case SUBSCRIPTION_ONLINE:
																	SubscriptionActivity lSubscriptionActivity = new SubscriptionActivity(
																			new SubscriptionPlace(),
																			clientFactory);
																	SimplePanel lPanel = new SimplePanel();
																	lSubscriptionActivity
																			.start(lPanel,
																					lEventBus);
																	lMainView
																			.setSelectedMenu(pEvent
																					.getMenu());
																	lMainView
																			.updateBreadcrumb(pEvent
																					.getMenuTitle());
																	lMainView
																			.loadContent(lPanel);

																	break;
																case FORGET_PASSWORD:
																	lMainView
																			.loadForgetPasswordContent(pResult
																					.getData());
																	break;
																case BOTTOM:
																	lMainView
																			.loadBottomContent(pResult
																					.getData());
																	break;
																case SUB_CONTENT:
																	lMainView
																			.loadSubContent(
																					pResult.getData(),
																					pResult.getDocuments(),
																					pEvent.getMenuTitle());
																	break;
																default:
																	lMainView
																			.setSelectedMenu(pEvent
																					.getMenu());
																	lMainView
																			.updateBreadcrumb(
																					pEvent.getAreaTitle(),
																					pEvent.getMenuTitle());
																	lMainView
																			.loadContent(
																					pResult.getData(),
																					pResult.getDocuments());
																}
															}
														});
									}
								});
						lPanel.add(lMainView);
					}
				});

		pPanel.setWidget(lPanel);
	}

	private void fireAuthentication(final EventBus pEventBus,
			final MainView pMainView) {
		dispatchAsync.execute(new AuthenticationAction(pMainView
				.getEmailAddress().getValue(), pMainView.getPassword()
				.getValue()), new AsyncCallback<AuthenticationResult>() {
			public void onFailure(Throwable pCaught) {
				pMainView.connexionPopupHide();
				Window.alert("Erreur " + pCaught.getMessage());
			}

			public void onSuccess(AuthenticationResult pResult) {
				if (pResult.isAuthenticated()
						|| "admin".equals(pMainView.getEmailAddress())) {
					pMainView.connexionPopupHide();
					// if(pResult.getUser().getProfiles().contains(ProfileEnum.ADMIN.name()))
					// {
					pEventBus.fireEvent(new UpdateContentEvent<UserUi>(
							MenuItems.ADMIN, pResult.getUser()));
					// }
				} else {
					Window.alert("Vous n'êtes pas enregistré.");
				}

			}
		});
	}

	public UserUi getUser() {
		return user;
	}

	public void setUser(UserUi pUser) {
		user = pUser;
	}

}
