package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import static com.asptttoulousenatation.client.resources.ASPTT_ProtoResources.IMAGES;

import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent;
import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent.LoadContentAreaEnum;
import com.asptttoulousenatation.core.client.ui.InputPanel;
import com.asptttoulousenatation.core.client.ui.PopupValidateAction;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

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

		AlternateBanner alternateBanner = new AlternateBanner();
		alternateBanner.setStyleName(CSS.headerPhoto());
		panel.add(alternateBanner);
		panel.setCellWidth(alternateBanner, "610px");
		HorizontalPanel layoutPanel_2 = new HorizontalPanel();
		layoutPanel_2.addStyleName(CSS.headerLogin());
		panel.add(layoutPanel_2);
		panel.setCellWidth(layoutPanel_2, "250px");

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

		nlnlblInscription.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				if (inscriptionData == null) {
					AreaUi lAreaUi = initResult.getArea("Inscription");
					if (lAreaUi != null) {
						MenuUi lMenu = lAreaUi.getMenu("Informations");
						eventBus.fireEvent(new LoadContentEvent(lMenu.getId(),
								LoadContentAreaEnum.INSCRIPTION, lAreaUi.getTitle(), lMenu.getTitle()));
					}
				} else {
					buildInscriptionPopup();
				}
			}
		});

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
	}

	private void loadContent(final String pAreaTitle, final String pMenuTitle) {
		AreaUi lAreaUi = initResult.getArea(pAreaTitle);
		if (lAreaUi != null) {
			MenuUi lMenu = lAreaUi.getMenu(pMenuTitle);
			eventBus.fireEvent(new LoadContentEvent(lMenu.getId(), pAreaTitle, pMenuTitle));
		}

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

				Label lPasswordForget = new Label(
						"J'ai oublié mon mot de passe");
				lPasswordForget.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						if (forgetPasswordData == null) {
							AreaUi lAreaUi = initResult.getArea("Inscription");
							if (lAreaUi != null) {
								MenuUi lMenu = lAreaUi.getMenu("MotDePasse");
								eventBus.fireEvent(new LoadContentEvent(lMenu.getId(),
										LoadContentAreaEnum.FORGET_PASSWORD, lAreaUi.getTitle(), lMenu.getTitle()));
							}
						} else {
							buildForgetPasswordPopup();
						}
					}
				});
				lPasswordForget.addStyleName(CSS.loginForgetLabel());
				lPanel.add(lPasswordForget);

				popupManager.createValidatePopup(true, true, lPanel);
				popupManager.getPopupValidate().setValidateButton(
						authenticationButton);
				popupManager.getPopupValidate().setAction(popupAction);
				popupManager.setSize("300px", "100px");
				popupManager.center();
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

	public void loadInscriptionContent(final byte[] pData, List<DocumentUi> pDocuments) {
		inscriptionData = new HTML(new String(pData));
		inscriptionDocuments = pDocuments;
		buildInscriptionPopup();
	}

	private void buildInscriptionPopup() {
		FlowPanel lFlowPanel = new FlowPanel();
		HorizontalPanel lHeader = new HorizontalPanel();
		lHeader.addStyleName(CSS.loginHeader());
		Label lTitle = new Label("Inscription");
		lTitle.addStyleName(CSS.loginTitle());
		lHeader.add(lTitle);
		Label lClose = new Label("X");
		lClose.addStyleName(CSS.loginClose());
		lClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				popupManager.hide();
			}
		});
		lHeader.add(lClose);
		lHeader.setCellHorizontalAlignment(lTitle,
				HasHorizontalAlignment.ALIGN_LEFT);
		lHeader.setCellHorizontalAlignment(lClose,
				HasHorizontalAlignment.ALIGN_RIGHT);
		lClose.getElement().getStyle().setMarginLeft(50, Unit.PX);
		lFlowPanel.add(lHeader);
		FlowPanel lIdPanel = new FlowPanel();
		lIdPanel.addStyleName(CSS.loginContent());
		lIdPanel.add(inscriptionData);
		VerticalPanel lVerticalPanel = new VerticalPanel();
		lVerticalPanel.getElement().getStyle().setMarginTop(10, Unit.PX);
		lIdPanel.add(lVerticalPanel);
		lVerticalPanel.add(new Label("Informations utiles:"));
		HorizontalPanel lHorizontalPanel = new HorizontalPanel();
		lHorizontalPanel.getElement().getStyle().setMarginLeft(30, Unit.PX);
		lHorizontalPanel.setSpacing(10);
		lVerticalPanel.add(lHorizontalPanel);
		Anchor lEcole = new Anchor("Ecole de natation");
		lEcole.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				loadContent("Groupes", "Ecole de natation");
			}
		});
		lHorizontalPanel.add(lEcole);
		lEcole = new Anchor("Loisirs adultes");
		lEcole.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				loadContent("Groupes", "Loisirs adultes");
			}
		});
		lHorizontalPanel.add(lEcole);
		
		lEcole = new Anchor("Aquagym");
		lEcole.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				loadContent("Groupes", "Aquagym");
			}
		});
		lHorizontalPanel.add(lEcole);
		
		lEcole = new Anchor("Perfectionnement");
		lEcole.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				loadContent("Groupes", "Perfectionnement");
			}
		});
		lHorizontalPanel.add(lEcole);

		lEcole = new Anchor("Compétitions");
		lEcole.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				loadContent("Groupes", "Compétitions");
			}
		});
		lHorizontalPanel.add(lEcole);
		
		lEcole = new Anchor("Masters");
		lEcole.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				loadContent("Groupes", "Masters");
			}
		});
		lHorizontalPanel.add(lEcole);

		lEcole = new Anchor("Eau libre");
		lEcole.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				loadContent("Groupes", "Eau libre");
			}
		});
		lHorizontalPanel.add(lEcole);

		lEcole = new Anchor("Centre de formation");
		lEcole.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				loadContent("Groupes", "Centre de formation");
			}
		});
		lHorizontalPanel.add(lEcole);

		Anchor lLieux = new Anchor("Lieux d'entrainements");
		lLieux.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				loadContent("Club", "Lieux d'entrainements");
			}
		});
		lHorizontalPanel.add(lLieux);

		lIdPanel.add(lHorizontalPanel);
		lFlowPanel.add(lIdPanel);

		lFlowPanel.add(getDocumentPanel(inscriptionDocuments));
		popupManager.createPopup(true, true, lFlowPanel);
		popupManager.center();
	}
	
	private Panel getDocumentPanel(List<DocumentUi> pDocuments) {
		Grid lPanel = new Grid(2, 1);
		lPanel.setStyleName(CSS.areaDocumentList());
		// Header
		Label lTitle = new Label("Documents à télécharger");
		lTitle.setStyleName(CSS.areaDocumentListTitle());
		lPanel.setWidget(0, 0, lTitle);

		// Documents
		FlowPanel lDocumentPanel = new FlowPanel();
		for (DocumentUi lDocument : pDocuments) {
			Anchor lAnchor = new Anchor(lDocument.getTitle());
			lAnchor.setTitle(lDocument.getSummary());
			lAnchor.setHref(GWT.getHostPageBaseURL()
					+ "downloadDocument?documentId=" + lDocument.getId()
					+ "&fileId=" + lDocument.getData());
			lAnchor.addStyleName(CSS.areaDocumentItem());
			lDocumentPanel.add(lAnchor);
		}
		lPanel.setWidget(1, 0, lDocumentPanel);
		return lPanel;
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
