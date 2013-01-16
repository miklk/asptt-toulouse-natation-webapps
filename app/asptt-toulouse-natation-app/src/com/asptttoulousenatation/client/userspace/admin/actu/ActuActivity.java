package com.asptttoulousenatation.client.userspace.admin.actu;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEvent;
import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.actu.DeleteActuAction;
import com.asptttoulousenatation.core.shared.actu.DeleteActuResult;
import com.asptttoulousenatation.core.shared.actu.GetAllActuAction;
import com.asptttoulousenatation.core.shared.actu.GetAllActuResult;
import com.asptttoulousenatation.shared.userspace.admin.actu.PublishActionResult;
import com.asptttoulousenatation.shared.userspace.admin.actu.PublishActuAction;
import com.asptttoulousenatation.shared.userspace.admin.actu.UpdateActuAction;
import com.asptttoulousenatation.shared.userspace.admin.actu.UpdateActuResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public class ActuActivity extends MyAbstractActivity<ActuPlace> {

	private boolean editionMode;

	public ActuActivity(ActuPlace pPlace, ClientFactory pClientFactory,
			boolean pEditionMode) {
		super(pPlace, pClientFactory);
		editionMode = pEditionMode;
	}

	public void start(AcceptsOneWidget pPanel, final EventBus pEventBus) {
		if (editionMode) {
			final SimplePanel lPanel = new SimplePanel();
			dispatchAsync.execute(new GetAllActuAction(),
					new AsyncCallback<GetAllActuResult>() {

						public void onFailure(Throwable pCaught) {
							Window.alert(pCaught.getMessage());
						}

						public void onSuccess(GetAllActuResult pResult) {
							final ActuEditionView lActuEditionView = clientFactory
									.getActuEditionView(pResult.getResult());
							lActuEditionView.getUpdateButton().addClickHandler(
									new ClickHandler() {
										public void onClick(ClickEvent pEvent) {
											dispatchAsync.execute(
													new UpdateActuAction(
															lActuEditionView
																	.getActu(),
															lActuEditionView
																	.getTitre()
																	.getValue(),
															lActuEditionView
																	.getSummary()
																	.getValue(),
															lActuEditionView
																	.getCreationDate()
																	.getValue(),
															lActuEditionView
																	.getContent()),
													new AsyncCallback<UpdateActuResult>() {

														public void onFailure(
																Throwable pCaught) {
															Window.alert("Erreur "
																	+ pCaught
																			.getMessage());
														}

														public void onSuccess(
																UpdateActuResult pResult) {
															Window.alert("Mis à jour !");
														}
													});
										}
									});
							lActuEditionView.getDeleteButton().addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent pEvent) {
									dispatchAsync.execute(new DeleteActuAction(lActuEditionView.getActu()), new AsyncCallback<DeleteActuResult>() {
										public void onFailure(Throwable pCaught) {
											Window.alert("Erreur "
													+ pCaught
															.getMessage());
										}

										public void onSuccess(
												DeleteActuResult pResult) {
											Window.alert("Supprimé avec succès.");
											pEventBus.fireEvent(new UpdateContentEvent(MenuItems.NEWS_EDITION));
										}
									});
								}
							});
							lPanel.setWidget(lActuEditionView);
						}
					});
			pPanel.setWidget(lPanel);
		} else {
			final ActuView lActuView = clientFactory.getActuView();
			lActuView.getPublishButton().addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent pEvent) {
					PublishActuAction lPublishActuAction = new PublishActuAction(
							lActuView.getTitre().getValue(), lActuView
									.getSummary().getValue(), lActuView
									.getContent(), lActuView.getCreationDate()
									.getValue());
					dispatchAsync.execute(lPublishActuAction,
							new AsyncCallback<PublishActionResult>() {

								public void onFailure(Throwable pCaught) {
									Window.alert(pCaught.getMessage());
								}

								public void onSuccess(
										PublishActionResult pResult) {
									Window.alert("Succès");
								}
							});
				}
			});
			pPanel.setWidget(lActuView);
		}
	}

}
