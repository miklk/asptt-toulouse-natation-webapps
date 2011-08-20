package com.asptttoulousenatation.core.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;

public class PopupOkPanel extends DecoratedPopupPanel {

	private ButtonBase validateButton;
	private PopupValidateAction action;
	
	public PopupOkPanel(boolean pAutoHide, boolean pModal) {
		super(pModal, pAutoHide);
	}

	public void setValidateButton(ButtonBase pValidateButton) {
		validateButton = pValidateButton;
	}


	public void setAction(PopupValidateAction pAction) {
		action = pAction;
		if(validateButton != null) {
			validateButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent pEvent) {
					action.execute();
				}
			});
		}
	}


	@Override
	public void onBrowserEvent(Event pEvent) {
		int lKeyCode = pEvent.getKeyCode();
		if(KeyCodes.KEY_ENTER == lKeyCode) {
			action.execute();
		}
		super.onBrowserEvent(pEvent);
	}
}
