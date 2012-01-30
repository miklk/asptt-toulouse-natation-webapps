package com.asptttoulousenatation.client.userspace.admin.club.slot;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEvent;
import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.core.shared.club.slot.CreateSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.CreateSlotResult;
import com.asptttoulousenatation.core.shared.club.slot.DeleteSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.DeleteSlotResult;
import com.asptttoulousenatation.core.shared.club.slot.GetAllSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.GetAllSlotResult;
import com.asptttoulousenatation.core.shared.club.slot.UpdateSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.UpdateSlotResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public class SlotActivity extends MyAbstractActivity<SlotPlace> {

	public SlotActivity(SlotPlace pPlace, ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, final EventBus pEventBus) {
		final SimplePanel lPanel = new SimplePanel();
		dispatchAsync.execute(new GetAllSlotAction(),
				new AsyncCallback<GetAllSlotResult>() {

					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur: " + pCaught.getMessage());
					}

					public void onSuccess(GetAllSlotResult pResult) {
						final SlotView lView = clientFactory.getSlotView(
								pResult.getSlots(), pResult.getGroups());
						lView.getCreateButton().addClickHandler(
								new ClickHandler() {
									public void onClick(ClickEvent event) {
										CreateSlotAction lAction = new CreateSlotAction(
												lView.getDayOfWeek(), lView
														.getHourBegin(), lView
														.getHourEnd(), lView
														.getGroup(), lView
														.getSwimmingPool()
														.getValue(), lView
														.getEducateur()
														.getValue());
										dispatchAsync
												.execute(
														lAction,
														new AsyncCallback<CreateSlotResult>() {

															public void onFailure(
																	Throwable pCaught) {
																Window.alert("Erreur "
																		+ pCaught
																				.getMessage());
															}

															public void onSuccess(
																	CreateSlotResult pResult) {
																Window.alert("Créé");
																pEventBus.fireEvent(new UpdateContentEvent(MenuItems.CLUB_SLOT_EDITION));
															}
														});
									}
								});
						lView.getUpdateButton().addClickHandler(
								new ClickHandler() {
									public void onClick(ClickEvent event) {
										UpdateSlotAction lAction = new UpdateSlotAction(
												lView.getSlot(), lView
														.getDayOfWeek(), lView
														.getHourBegin(), lView
														.getHourEnd(), lView
														.getGroup(), lView
														.getSwimmingPool()
														.getValue(), lView
														.getEducateur()
														.getValue());
										dispatchAsync
												.execute(
														lAction,
														new AsyncCallback<UpdateSlotResult>() {

															public void onFailure(
																	Throwable pCaught) {
																Window.alert("Erreur "
																		+ pCaught
																				.getMessage());
															}

															public void onSuccess(
																	UpdateSlotResult pResult) {
																Window.alert("Mis à jour !");
															}
														});
									}
								});
						lView.getDeleteButton().addClickHandler(
								new ClickHandler() {
									public void onClick(ClickEvent pEvent) {
										dispatchAsync.execute(
												new DeleteSlotAction(lView
														.getSlot()),
												new AsyncCallback<DeleteSlotResult>() {
													public void onFailure(
															Throwable pCaught) {
														Window.alert("Erreur "
																+ pCaught
																		.getMessage());
													}

													public void onSuccess(
															DeleteSlotResult pResult) {
														Window.alert("Supprimé avec succès.");
														pEventBus.fireEvent(new UpdateContentEvent(MenuItems.CLUB_SLOT_EDITION));
													}
												});

									}
								});
						lPanel.setWidget(lView);
					}
				});
		pPanel.setWidget(lPanel);

	}

}
