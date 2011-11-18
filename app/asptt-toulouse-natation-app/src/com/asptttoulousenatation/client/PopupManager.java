package com.asptttoulousenatation.client;

import java.util.Stack;

import com.asptttoulousenatation.core.client.ui.PopupOkPanel;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class PopupManager {

	private Stack<PopupPanel> popups = new Stack<PopupPanel>();

	public void createPopup(boolean pModal, boolean pAutoHide, Widget pContent) {
		PopupPanel lPopup = new DecoratedPopupPanel(pAutoHide, pModal);
		lPopup.setWidget(pContent);
		popups.push(lPopup);
	}

	public void createValidatePopup(boolean pModal, boolean pAutoHide,
			Widget pContent) {
		PopupOkPanel lPopup = new PopupOkPanel(pAutoHide, pModal);
		lPopup.setWidget(pContent);
		popups.push(lPopup);
	}

	public PopupOkPanel getPopupValidate() {
		return (PopupOkPanel) popups.peek();
	}

	public void setSize(final String pWidth, final String pHeight) {
		popups.peek().setSize(pWidth, pHeight);
	}

	public void show() {
		popups.peek().show();
	}
	
	public void showRelativeTo(UIObject pTarget) {
		popups.peek().showRelativeTo(pTarget);
	}

	public void center() {
		popups.peek().center();
	}

	public void hide() {
		if (!popups.isEmpty()) {
			PopupPanel lPopup = popups.pop();
			if (lPopup != null) {
				lPopup.hide();
			}
		}
	}

	public void setPanel(Widget pPanel) {
		popups.peek().setWidget(pPanel);
	}
}
