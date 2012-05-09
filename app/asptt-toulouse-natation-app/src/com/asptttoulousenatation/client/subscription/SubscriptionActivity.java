package com.asptttoulousenatation.client.subscription;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.core.client.MyAbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SubscriptionActivity extends MyAbstractActivity<SubscriptionPlace> {

	public SubscriptionActivity(SubscriptionPlace pPlace,
			ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final SubscriptionView lView = clientFactory.getSubscriptionView();
		pPanel.setWidget(lView);
	}

}
