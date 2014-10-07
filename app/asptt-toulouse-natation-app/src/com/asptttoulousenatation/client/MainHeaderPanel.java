package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import static com.asptttoulousenatation.client.resources.ASPTT_ProtoResources.IMAGES;

import java.util.List;

import com.asptttoulousenatation.core.client.ui.InputPanel;
import com.asptttoulousenatation.core.client.ui.PopupValidateAction;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.EventBus;

public class MainHeaderPanel extends Composite {

	private HorizontalPanel panel;
	private InitResult initResult;
	private PopupManager popupManager;
	private UserUi user;

	private EventBus eventBus;

	private ButtonBase authenticationButton;
	private Label disconnectButton;
	private Label privateSpaceButton;
	private Button passwordForgetButton;

	private TextBox emailAddressInput;
	private PasswordTextBox passwordInput;
	private TextBox emailAddressForgetInput;

	private PopupValidateAction popupAction;

	private HTML inscriptionData;
	private List<DocumentUi> inscriptionDocuments;
	
	private HTML forgetPasswordData;

	public MainHeaderPanel(InitResult pInitResult, UserUi pUser,
			EventBus pEventBus, PopupManager pPopupManager) {
		initResult = pInitResult;
		popupManager = pPopupManager;
		user = pUser;
		eventBus = pEventBus;
		panel = new HorizontalPanel();
		initWidget(panel);
		panel.setStyleName(CSS.header());

		Image image = new Image(IMAGES.logo());
		image.setStyleName(CSS.headerLogo());
		panel.add(image);
		panel.setCellWidth(image, "320px");

		VerticalPanel lRightPanel = new VerticalPanel();
		HorizontalPanel layoutPanel_2 = new HorizontalPanel();
		lRightPanel.add(layoutPanel_2);
		layoutPanel_2.addStyleName(CSS.headerLogin());
		panel.add(lRightPanel);
		panel.setCellWidth(lRightPanel, "250px");

		authenticationButton = new PushButton("Se connecter");
		disconnectButton = new Label("Me déconnecter");
		privateSpaceButton = new Label("Mon espace");
		passwordForgetButton = new Button("Envoyer");

		FlowPanel lInscriptionLabels = new FlowPanel();
		lInscriptionLabels.setStyleName(CSS.headerLoginInscriptionPanel());
		Label nlnlblInscription = new Label("Inscription");
		nlnlblInscription.addStyleName(CSS.headerLoginInscription());
		lInscriptionLabels.add(nlnlblInscription);
		Label lInscriptionSub = new Label("Retirer le dossier d'inscription");
		lInscriptionSub.addStyleName(CSS.headerLoginSubLine());
		lInscriptionLabels.add(lInscriptionSub);
		layoutPanel_2.add(lInscriptionLabels);

		FlowPanel lConnexionLabels = new FlowPanel();
		lConnexionLabels.setStyleName(CSS.headerLoginInscriptionPanel());
		// TODO Disconnect
		if (user != null) {
			buildDisconnexionPanel(lConnexionLabels);
		} else {
			// TODO Connexion
			buildConnexionPanel(lConnexionLabels);
		}
		layoutPanel_2.add(lConnexionLabels);
		
		Image lLogoOmnisport = new Image(IMAGES.logoOmnisportHaut());
		lLogoOmnisport.setStyleName(CSS.headerLoginOmnisport());
		lRightPanel.add(lLogoOmnisport);
	}

	private void buildConnexionPanel(FlowPanel pConnexionLabels) {
		Label nlnlblConnexion = new Label("Connexion");
		nlnlblConnexion.addStyleName(CSS.headerLoginConnexion());
		pConnexionLabels.add(nlnlblConnexion);
		Label lConnexionSub = new Label("Utiliser votre compte");
		lConnexionSub.addStyleName(CSS.headerLoginSubLine());
		pConnexionLabels.add(lConnexionSub);
		// Popup connexion
		nlnlblConnexion.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				FlowPanel lPanel = new FlowPanel();

				HorizontalPanel lHeader = new HorizontalPanel();
				lHeader.addStyleName(CSS.loginHeader());
				Label lTitle = new Label("Se connecter");
				lTitle.addStyleName(CSS.loginTitle());
				lHeader.add(lTitle);
				lPanel.add(lHeader);

				emailAddressInput = new TextBox();
				InputPanel lEmailAddressInput = new InputPanel(
						"Adresse e-mail", emailAddressInput, "50%", "50%");
				lEmailAddressInput.addStyleName(CSS.loginContent());
				lPanel.add(lEmailAddressInput);

				passwordInput = new PasswordTextBox();
				InputPanel lPasswordInput = new InputPanel("Mot de passe",
						passwordInput, "50%", "50%");
				lPasswordInput.addStyleName(CSS.loginContent());
				lPanel.add(lPasswordInput);
				authenticationButton.addStyleName(CSS.loginButton());
				lPanel.add(authenticationButton);

				popupManager.createValidatePopup(true, true, lPanel);
				popupManager.getPopupValidate().setValidateButton(
						authenticationButton);
				popupManager.getPopupValidate().setAction(popupAction);
				popupManager.setSize("300px", "100px");
				popupManager.center();
				emailAddressInput.setFocus(true);
			}
		});
	}

	private void buildDisconnexionPanel(FlowPanel pConnexionLabels) {
		privateSpaceButton.addStyleName(CSS.logoutLabelPanel());
		disconnectButton.addStyleName(CSS.logoutLabelPanel());
		pConnexionLabels.add(privateSpaceButton);
		pConnexionLabels.add(disconnectButton);
		pConnexionLabels.addStyleName(CSS.logoutPanel());
	}

	public HasClickHandlers getAuthenticationButton() {
		return authenticationButton;
	}

	public HasValue<String> getEmailAddress() {
		return emailAddressInput;
	}

	public HasValue<String> getPassword() {
		return passwordInput;
	}

	public HasKeyPressHandlers getAuthenticationButton2() {
		return authenticationButton;
	}

	public void setConnexionAction(PopupValidateAction pAction) {
		popupAction = pAction;
	}

	public HasClickHandlers getDisconnectButton() {
		return disconnectButton;
	}

	public HasClickHandlers getPriveSpaceButton() {
		return privateSpaceButton;
	}

	public HasClickHandlers getPasswordForgetButton() {
		return passwordForgetButton;
	}

	public HasValue<String> getEmailAddressForget() {
		return emailAddressForgetInput;
	}

	public void setPopupManager(PopupManager pPopupManager) {
		popupManager = pPopupManager;
	}

	public void loadForgetPasswordContent(final byte[] pData) {
		forgetPasswordData = new HTML(new String(pData));
		buildForgetPasswordPopup();
	}
	
	private void buildForgetPasswordPopup() {
		forgetPasswordData.addStyleName(CSS.loginContent());
		FlowPanel lPanel = new FlowPanel();
		VerticalPanel lInnerPanel = new VerticalPanel();
		lInnerPanel.add(forgetPasswordData);

		emailAddressForgetInput = new TextBox();
		InputPanel lEmailAddressInput = new InputPanel(
				"Adresse e-mail", emailAddressForgetInput,
				"50%", "50%");
		lEmailAddressInput.addStyleName(CSS.loginContent());
		lInnerPanel.add(lEmailAddressInput);

		HorizontalPanel lButtonPanel = new HorizontalPanel();
		lButtonPanel.getElement().getStyle()
				.setMarginLeft(40, Unit.PCT);
		passwordForgetButton.addStyleName(CSS
				.loginForgetButton());
		passwordForgetButton.getElement().getStyle()
				.setMarginLeft(50, Unit.PX);
		lButtonPanel.add(passwordForgetButton);
		Button lCloseButton = new Button("Fermer");
		lCloseButton.addStyleName(CSS.loginForgetButton());
		lCloseButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				popupManager.hide();
			}
		});
		lButtonPanel.add(lCloseButton);
		lInnerPanel.add(lButtonPanel);
		lPanel.add(lInnerPanel);
		popupManager.createValidatePopup(true, true, lPanel);
		popupManager.setSize("600px", "100px");
		popupManager.center();
	}
}
