package com.asptttoulousenatation.core.client.ui;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupManager {

	private static PopupManager POPUP_MANAGER;

	private PopupPanel popup;

	private PopupManager() {
	}

	public static PopupManager getInstance() {
		if (POPUP_MANAGER == null) {
			POPUP_MANAGER = new PopupManager();
		}
		return POPUP_MANAGER;
	}

	public PopupPanel getPopup(boolean pAutoHide, boolean pModal,
			final String pHeaderText, final Widget pWidget) {
		popup = new PopupPanel(pAutoHide, pModal);
		popup.setWidget(pWidget);
		return popup;
	}
	
	public void center() {
		popup.center();
	}

	public void hide() {
		if(popup != null) {
			popup.hide();
		}
	}
	
	public static void loading() {
		PopupManager.getInstance().getPopup(false, true, "Chargement", new Label("Chargement en cours..."));
		PopupManager.getInstance().center();
	}
}
