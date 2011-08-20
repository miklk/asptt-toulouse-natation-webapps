package com.asptttoulousenatation.core.client.ui;

import com.google.gwt.user.client.ui.Widget;

public class PopupManager {

	private static PopupManager POPUP_MANAGER;

	private PopupLayout popup;

	private PopupManager() {
	}

	public static PopupManager getInstance() {
		if (POPUP_MANAGER == null) {
			POPUP_MANAGER = new PopupManager();
		}
		return POPUP_MANAGER;
	}

	public PopupLayout getPopup(boolean pAutoHide, boolean pModal,
			final String pHeaderText, final Widget pWidget) {
		popup = new PopupLayout(pAutoHide, pModal, pHeaderText, pWidget);
		return popup;
	}
	
	public void center() {
		popup.center();
	}

	public void hide() {
		popup.hide();
	}
}
