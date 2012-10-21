package com.asptttoulousenatation.client.config;

import com.asptttoulousenatation.client.MainActivity;
import com.asptttoulousenatation.client.MainPlace;
import com.asptttoulousenatation.client.subscription.SubscriptionActivity;
import com.asptttoulousenatation.client.subscription.SubscriptionPlace;
import com.asptttoulousenatation.client.userspace.UserSpaceActivity;
import com.asptttoulousenatation.client.userspace.UserSpacePlace;
import com.asptttoulousenatation.client.userspace.admin.actu.ActuActivity;
import com.asptttoulousenatation.client.userspace.admin.actu.ActuPlace;
import com.asptttoulousenatation.client.userspace.home.HomeActivity;
import com.asptttoulousenatation.client.userspace.home.HomePlace;
import com.asptttoulousenatation.client.userspace.menu.MenuActivity;
import com.asptttoulousenatation.client.userspace.menu.MenuPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class MainActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;
	
	public MainActivityMapper(ClientFactory pClientFactory) {
		super();
		clientFactory = pClientFactory;
	}

	public Activity getActivity(Place pPlace) {
		final Activity activity;
		if(pPlace instanceof MainPlace) {
			MainPlace lPlace = (MainPlace) pPlace;
			activity = new MainActivity((MainPlace) pPlace, clientFactory, lPlace.getObject());
		}
		else if(pPlace instanceof UserSpacePlace) {
			UserSpacePlace lPlace = (UserSpacePlace) pPlace;
			activity = new UserSpaceActivity(lPlace, clientFactory, lPlace.getObject());
		}
		else if(pPlace instanceof HomePlace) {
			activity = new HomeActivity((HomePlace) pPlace, clientFactory);
		}
		else if(pPlace instanceof MenuPlace) {
			activity = new MenuActivity((MenuPlace) pPlace, clientFactory);
		}
		else if(pPlace instanceof ActuPlace) {
			activity = new ActuActivity((ActuPlace) pPlace, clientFactory, false);
		} else if(pPlace instanceof SubscriptionPlace) {
			activity = new SubscriptionActivity((SubscriptionPlace) pPlace, clientFactory);
		}
		else {
			activity = null;
		}
		return activity;
	}

}
