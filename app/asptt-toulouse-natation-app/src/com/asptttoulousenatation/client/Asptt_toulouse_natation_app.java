package com.asptttoulousenatation.client;

import com.asptttoulousenatation.client.resources.ASPTT_ProtoCss;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class Asptt_toulouse_natation_app implements EntryPoint {

	public static ASPTT_ProtoCss CSS;

	public static PopupManager popupManager = new PopupManager();

	public void onModuleLoad() {
		ApplicationLaunch applicationLaunch = GWT.create(ApplicationLaunch.class);
		applicationLaunch.init();
	}
}