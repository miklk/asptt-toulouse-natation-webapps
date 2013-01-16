package com.asptttoulousenatation.client.config;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public abstract class MyAbstractActivity<P extends Place> extends AbstractActivity {
	
	protected P place;
	protected ClientFactory clientFactory;
	protected DispatchAsync dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());
	
	public MyAbstractActivity(P pPlace, ClientFactory pClientFactory) {
		place = pPlace;
		clientFactory = pClientFactory;
	}

	
	public void start(AcceptsOneWidget pPanel,
			com.google.gwt.event.shared.EventBus pEventBus) {
		start(pPanel, (com.google.web.bindery.event.shared.EventBus) pEventBus);
	}


	public abstract void start(AcceptsOneWidget pPanel, EventBus pEventBus);
}