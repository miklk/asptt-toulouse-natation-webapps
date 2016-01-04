package com.asptttoulousenatation.client;

import java.util.List;

import com.asptttoulousenatation.core.client.ui.PopupValidateAction;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;

public interface MainView extends IsWidget {

	public HasValue<String> getEmailAddress();
	public HasValue<String> getPassword();
	public HasClickHandlers getAuthenticationButton();
	public HasKeyPressHandlers getAuthenticationButton2();
	public void setPopupManager(PopupManager pPopupManager);
	public void connexionPopupHide();
	public void setConnexionAction(PopupValidateAction pAction);
	public HasClickHandlers getDisconnectButton();
	public HasClickHandlers getPriveSpaceButton();
	public HasClickHandlers getPasswordForgetButton();
	public void passwordSended();
	public void passwordNotSended();
	public HasValue<String> getEmailAddressForget();
	public void loadContent(final byte[] pData, List<DocumentUi> pDocuments);
	public void loadToolContent(final byte[] pData);
	public void loadForgetPasswordContent(final byte[] pData);
	public void setSelectedMenu(MenuUi pMenu);
	public void loadSubContent(final byte[] pData, List<DocumentUi> pDocuments, String pSubMenuName);
	
	public void loadContent(final Panel pPanel);
	
	public HasClickHandlers getMoreActuButton();
	public long getMoreActuEnd();
}
