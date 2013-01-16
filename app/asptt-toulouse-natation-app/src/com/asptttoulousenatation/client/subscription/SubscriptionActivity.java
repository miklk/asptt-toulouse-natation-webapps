package com.asptttoulousenatation.client.subscription;


import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.club.group.GetAllGroupResult;
import com.asptttoulousenatation.core.shared.club.group.GetGroupSlotAction;
import com.asptttoulousenatation.core.shared.club.subscription.GetPriceAction;
import com.asptttoulousenatation.core.shared.club.subscription.GetPriceResult;
import com.asptttoulousenatation.core.shared.payment.PaymentAction;
import com.asptttoulousenatation.core.shared.payment.PaymentResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

public class SubscriptionActivity extends MyAbstractActivity<SubscriptionPlace> {

	public SubscriptionActivity(SubscriptionPlace pPlace,
			ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final SimplePanel lPanel = new SimplePanel();
		pPanel.setWidget(lPanel);
		dispatchAsync.execute(new GetGroupSlotAction(), new AsyncCallback<GetAllGroupResult>() {
			public void onFailure(Throwable pCaught) {
				Window.alert("Erreur " + pCaught.getMessage());
			}
			public void onSuccess(GetAllGroupResult pResult) {
				final SubscriptionView lView = clientFactory.getSubscriptionView(pResult.getGroups());
				lView.getPriceButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						dispatchAsync.execute(new GetPriceAction(lView.getGroup(), lView.getNumberSubscribers()), new AsyncCallback<GetPriceResult>() {

							public void onFailure(Throwable pCaught) {
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(GetPriceResult pResult) {
								lView.setPrice(pResult.getPrice());
							}
						});
					}
				});
				lView.getPaymentProcessButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						dispatchAsync.execute(new GetPriceAction(lView.getGroup(), lView.getNumberSubscribers()), new AsyncCallback<GetPriceResult>() {

							public void onFailure(Throwable pCaught) {
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(GetPriceResult pResult) {
								lView.setPaymentInfo(pResult.getPrice());
							}
						});
					}
				});
				lView.getPaymentButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						dispatchAsync.execute(new PaymentAction("192.168.0.2", lView.getCardType(), 
								lView.getCardNumber().getValue(), 
								lView.getCardExpDate().getValue(), 
								lView.getCardCCV().getValue(),
								lView.getCardOwnerLastName().getValue(),
								lView.getCardOwnerFirstName().getValue(),
								lView.getAddressRoad().getValue(),
								lView.getAddressCity().getValue(),
								lView.getAddressZipCode().getValue()), new AsyncCallback<PaymentResult>() {
							public void onFailure(Throwable pCaught) {
								Window.alert("Erreur " + pCaught.getMessage());
							}

							public void onSuccess(PaymentResult pResult) {
								PopupPanel lPanel = new PopupPanel(true);
								lPanel.add(new HTML(pResult.getMessage()));
								lPanel.center();
							}
						});
					}
				});
				lPanel.setWidget(lView);
			}
		});
	}
}