package com.asptttoulousenatation.client.userspace.admin.user;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEvent;
import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.core.client.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.club.slot.GetAllSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.GetAllSlotResult;
import com.asptttoulousenatation.shared.userspace.admin.user.CreateUserAction;
import com.asptttoulousenatation.shared.userspace.admin.user.CreateUserResult;
import com.asptttoulousenatation.shared.userspace.admin.user.DeleteUserAction;
import com.asptttoulousenatation.shared.userspace.admin.user.DeleteUserResult;
import com.asptttoulousenatation.shared.userspace.admin.user.GetAllUserAction;
import com.asptttoulousenatation.shared.userspace.admin.user.GetAllUserResult;
import com.asptttoulousenatation.shared.userspace.admin.user.UpdateUserAction;
import com.asptttoulousenatation.shared.userspace.admin.user.UpdateUserResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public class UserActivity extends MyAbstractActivity<UserPlace> {

	private boolean edition;

	public UserActivity(UserPlace pPlace, ClientFactory pClientFactory,
			boolean pEdition) {
		super(pPlace, pClientFactory);
		edition = pEdition;
	}

	public void start(AcceptsOneWidget pPanel, final EventBus pEventBus) {
		final SimplePanel lPanel = new SimplePanel();
		if (edition) {
			dispatchAsync.execute(new GetAllUserAction(),
					new AsyncCallback<GetAllUserResult>() {

						public void onFailure(Throwable pCaught) {
							Window.alert("Erreur " + pCaught);
						}

						public void onSuccess(GetAllUserResult pResult) {
							final UserEditionView lUserEditionView = clientFactory
									.getUserEditionView(pResult.getUsers(),
											pResult.getSlots());
							lUserEditionView.getUpdateButton().addClickHandler(
									new ClickHandler() {
										public void onClick(ClickEvent pEvent) {
											dispatchAsync.execute(
													new UpdateUserAction(
															lUserEditionView
																	.getUser(),
															lUserEditionView
																	.getEmailAddress()
																	.getValue(),
															lUserEditionView
																	.getValidated()
																	.getValue(),
															lUserEditionView
																	.getProfiles(),
															lUserEditionView
																	.getSlots(),
															lUserEditionView
																	.getLastName()
																	.getValue(),
															lUserEditionView
																	.getFirstName()
																	.getValue(),
															lUserEditionView
																	.getBirthday()
																	.getValue(),
															lUserEditionView
																	.getPhoneNumber()
																	.getValue(),
															lUserEditionView
																	.getAddressRoad()
																	.getValue(),
															lUserEditionView
																	.getAddressAdditional()
																	.getValue(),
															lUserEditionView
																	.getAddressCode()
																	.getValue(),
															lUserEditionView
																	.getAddressCity()
																	.getValue(),
															lUserEditionView
																	.getGender(),
															lUserEditionView
																	.getMeasurementSwimsuit()
																	.getValue(),
															lUserEditionView
																	.getMeasurementTshirt()
																	.getValue(),
															lUserEditionView
																	.getMeasurementShort()
																	.getValue()),
													new AsyncCallback<UpdateUserResult>() {
														public void onFailure(
																Throwable pCaught) {
															Window.alert("Erreur: "
																	+ pCaught);
														}

														public void onSuccess(
																UpdateUserResult pResult) {
															Window.alert("Mis à jour !");
														}
													});
										}
									});
							lUserEditionView.getDeleteButton().addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent pEvent) {
									dispatchAsync.execute(new DeleteUserAction(lUserEditionView.getUser()), new AsyncCallback<DeleteUserResult>() {
										public void onFailure(Throwable pCaught) {
											Window.alert("Erreur: "
													+ pCaught);
										}

										public void onSuccess(
												DeleteUserResult pResult) {
											Window.alert("Supprimé avec succès");
											pEventBus.fireEvent(new UpdateContentEvent(MenuItems.USER_EDITION));
										}
									});
								}
							});
							lPanel.setWidget(lUserEditionView);
						}
					});
		} else {
			dispatchAsync.execute(new GetAllSlotAction(),
					new AsyncCallback<GetAllSlotResult>() {

						public void onFailure(Throwable pCaught) {
							Window.alert("Erreur: " + pCaught.getMessage());
						}

						public void onSuccess(GetAllSlotResult pResult) {
							final UserCreationView lCreationView = clientFactory
									.getUserCreationView(pResult.getSlots());
							lCreationView.getCreateButton().addClickHandler(
									new ClickHandler() {
										public void onClick(ClickEvent pEvent) {
											dispatchAsync
													.execute(
															new CreateUserAction(
																	lCreationView
																			.getEmailAddress()
																			.getValue(),
																	lCreationView
																			.getValidated()
																			.getValue(),
																	lCreationView
																			.getProfiles(),
																	lCreationView
																			.getSlots(),
																	lCreationView
																			.getLastName()
																			.getValue(),
																	lCreationView
																			.getFirstName()
																			.getValue(),
																	lCreationView
																			.getBirthday()
																			.getValue(),
																	lCreationView
																			.getPhonenumber()
																			.getValue(),
																	lCreationView
																			.getAddressRoad()
																			.getValue(),
																	lCreationView
																			.getAddressAdditional()
																			.getValue(),
																	lCreationView
																			.getAddressCode()
																			.getValue(),
																	lCreationView
																			.getAddressCity()
																			.getValue(),
																	lCreationView
																			.getGender(),
																	lCreationView
																			.getMeasurementSwimsuit()
																			.getValue(),
																	lCreationView
																			.getMeasurementTshirt()
																			.getValue(),
																	lCreationView
																			.getMeasurementShort()
																			.getValue()),
															new AsyncCallback<CreateUserResult>() {
																public void onFailure(
																		Throwable pCaught) {
																	Window.alert("Erreur: "
																			+ pCaught);
																}

																public void onSuccess(
																		CreateUserResult pResult) {
																	Window.alert("Utilisateur créé !");
																}
															});
										}
									});
							lPanel.setWidget(lCreationView);
						}
					});
		}
		pPanel.setWidget(lPanel);
	}
}