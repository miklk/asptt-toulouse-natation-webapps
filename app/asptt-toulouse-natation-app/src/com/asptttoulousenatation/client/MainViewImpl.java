package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import com.asptttoulousenatation.core.client.ui.PopupValidateAction;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainViewImpl extends Composite implements MainView {

	private InitResult initResult;
	private DockPanel panel;
	private MainBottomPanel bottomPanel;
	private MainToolPanel toolPanel;
	private MainContentPanel contentPanel;
	private MainHeaderPanel headerPanel;
	private MainMenuPanel menuPanel;

	private PopupManager popupManager;

	private UserUi user;
	
	private EventBus eventBus;

	public MainViewImpl(InitResult pInitResult, UserUi pUser, EventBus pEventBus) {
		initResult = pInitResult;
		eventBus = pEventBus;
		user = pUser;
		panel = new DockPanel();
		initWidget(panel);
//		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.PCT);
		panel.setStyleName(CSS.page());
//		panel.add(dockLayoutPanel);
//		panel.setWidgetTopHeight(dockLayoutPanel, 0.0, Unit.PX, 100, Unit.PCT);
//		panel.setWidgetLeftWidth(dockLayoutPanel, 0.0, Unit.PCT, 100, Unit.PCT);

		headerPanel = new MainHeaderPanel(initResult, user, eventBus, popupManager);
		panel.add(headerPanel, DockPanel.NORTH);
//		dockLayoutPanel.addNorth(headerPanel, 20);

		bottomPanel = new MainBottomPanel();
		panel.add(bottomPanel, DockPanel.SOUTH);
//		dockLayoutPanel.addSouth(bottomPanel, 3);

		menuPanel = new MainMenuPanel(initResult, user, eventBus);
		panel.add(menuPanel, DockPanel.WEST);
//		dockLayoutPanel.addWest(menuPanel, 25.0);

		// TODO center
		HorizontalPanel layoutPanel_1 = new HorizontalPanel();
		panel.add(layoutPanel_1, DockPanel.CENTER);
		contentPanel = new MainContentPanel(initResult, user, eventBus);
		
		layoutPanel_1.add(contentPanel);
		toolPanel = new MainToolPanel(initResult, user, eventBus);
		layoutPanel_1.add(toolPanel);
//		layoutPanel_1.setWidgetLeftWidth(contentPanel, 1.0, Unit.PCT, 80.0,
//				Unit.PCT);
//		layoutPanel_1.setWidgetTopHeight(contentPanel, 10.0, Unit.PCT, 90.0,
//				Unit.PCT);

		// TODO content
		
	}

	public HasClickHandlers getAuthenticationButton() {
		return headerPanel.getAuthenticationButton();
	}

	public HasValue<String> getEmailAddress() {
		return headerPanel.getEmailAddress();
	}

	public HasValue<String> getPassword() {
		return headerPanel.getPassword();
	}

	public void setPopupManager(PopupManager pPopupManager) {
		popupManager = pPopupManager;
		headerPanel.setPopupManager(pPopupManager);
		menuPanel.setPopupManager(pPopupManager);
		toolPanel.setPopupManager(pPopupManager);
		contentPanel.setPopupManager(pPopupManager);
	}

	public HasKeyPressHandlers getAuthenticationButton2() {
		return headerPanel.getAuthenticationButton2();
	}

	public void connexionPopupHide() {
		popupManager.hide();

	}

	public void setConnexionAction(PopupValidateAction pAction) {
		headerPanel.setConnexionAction(pAction);
	}

	public HasClickHandlers getDisconnectButton() {
		return headerPanel.getDisconnectButton();
	}

	public HasClickHandlers getPriveSpaceButton() {
		return headerPanel.getPriveSpaceButton();
	}

	public HasClickHandlers getPasswordForgetButton() {
		return headerPanel.getPasswordForgetButton();
	}

	public void passwordSended() {
		FlowPanel lPanel = new FlowPanel();
		VerticalPanel lInnerPanel = new VerticalPanel();
		lInnerPanel.add(new Label(
				"Votre mot de passe vous a été envoyé par e-mail."));
		Button lCloseButton = new Button("Fermer");
		lCloseButton.addStyleName(CSS.loginButton());
		lCloseButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				popupManager.hide();
			}
		});
		lInnerPanel.add(lCloseButton);
		lPanel.add(lInnerPanel);
		popupManager.setPanel(lPanel);
	}

	public void passwordNotSended() {
		FlowPanel lPanel = new FlowPanel();
		VerticalPanel lInnerPanel = new VerticalPanel();
		lInnerPanel
				.add(new Label(
						"Vous devez être licencié au club pour obtenir un mot de passe. Si tel est le cas, votre inscription n'a pas encore été validée par nos soins. Merci de patientez, vous recevrez un e-mail d'information dès que votre inscription sera validée."));
		Button lCloseButton = new Button("Fermer");
		lCloseButton.addStyleName(CSS.loginButton());
		lCloseButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				popupManager.hide();
			}
		});
		lInnerPanel.add(lCloseButton);
		lPanel.add(lInnerPanel);
		popupManager.setPanel(lPanel);
	}

	public HasValue<String> getEmailAddressForget() {
		return headerPanel.getEmailAddressForget();
	}

	public void loadContent(byte[] pData) {
		contentPanel.loadContent(pData);
	}

	public void loadToolContent(byte[] pData) {
		toolPanel.loadToolContent(pData);
	}

	public void loadInscriptionContent(byte[] pData) {
		headerPanel.loadInscriptionContent(pData);
	}
}