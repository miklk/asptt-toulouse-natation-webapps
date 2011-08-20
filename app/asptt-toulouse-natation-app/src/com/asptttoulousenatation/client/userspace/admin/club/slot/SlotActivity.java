package com.asptttoulousenatation.client.userspace.admin.club.slot;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.club.slot.CreateSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.CreateSlotResult;
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
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

public class SlotActivity extends MyAbstractActivity<SlotPlace> {

	public SlotActivity(SlotPlace pPlace, ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final SimpleLayoutPanel lPanel = new SimpleLayoutPanel();
		dispatchAsync.execute(new GetAllSlotAction(), new AsyncCallback<GetAllSlotResult>() {

			public void onFailure(Throwable pCaught) {
				Window.alert("Erreur: " + pCaught.getMessage());
			}

			public void onSuccess(GetAllSlotResult pResult) {
				final SlotView lView = clientFactory.getSlotView(pResult.getSlots(), pResult.getGroups());
				lView.getCreateButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						CreateSlotAction lAction = new CreateSlotAction(lView.getDayOfWeek(), lView.getHourBegin(), lView.getHourEnd(), lView.getGroup(), lView.getSwimmingPool().getValue());
						dispatchAsync.execute(lAction, new AsyncCallback<CreateSlotResult>() {

							public void onFailure(Throwable pCaught) {
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(CreateSlotResult pResult) {
								Window.alert("Créé");
							}
						});
					}
				});
				lView.getUpdateButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						UpdateSlotAction lAction = new UpdateSlotAction(lView.getSlot(), lView.getDayOfWeek(), lView.getHourBegin(), lView.getHourEnd(), lView.getGroup(), lView.getSwimmingPool().getValue());
						dispatchAsync.execute(lAction, new AsyncCallback<UpdateSlotResult>() {

							public void onFailure(Throwable pCaught) {
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(UpdateSlotResult pResult) {
								Window.alert("Mis à jour !");
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
