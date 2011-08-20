package com.asptttoulousenatation.core.client;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.Place;

public abstract class MyAbstractActivity<P extends Place> extends AbstractActivity {
	
	protected P place;
	protected ClientFactory clientFactory;
	protected DispatchAsync dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());
	
	public MyAbstractActivity(P pPlace, ClientFactory pClientFactory) {
		place = pPlace;
		clientFactory = pClientFactory;
	}

}