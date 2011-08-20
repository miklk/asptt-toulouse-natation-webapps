package com.asptttoulousenatation.client;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import static com.asptttoulousenatation.client.resources.ASPTT_ProtoResources.IMAGES;

import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.client.userspace.admin.event.LoadContentEvent;
import com.asptttoulousenatation.core.client.ui.InputPanel;
import com.asptttoulousenatation.core.client.ui.PopupValidateAction;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class MainViewImpl extends ResizeComposite implements MainView {

	private InitResult initResult;
	private LayoutPanel panel;
	private FlowPanel actuPanel;
	private SimpleLayoutPanel contentPanel;
	private LayoutPanel menuPanel;
	private LayoutPanel headerPanel;
	private LayoutPanel bottomPanel;

	private ButtonBase authenticationButton;
	private Label disconnectButton;
	private Label privateSpaceButton;
	private Button passwordForgetButton;

	private TextBox emailAddressInput;
	private PasswordTextBox passwordInput;
	private TextBox emailAddressForgetInput;

	private PopupManager popupManager;
	private PopupValidateAction popupAction;

	private UserUi user;
	
	private EventBus eventBus;

	public MainViewImpl(InitResult pInitResult, UserUi pUser, EventBus pEventBus) {
		initResult = pInitResult;
		eventBus = pEventBus;
		user = pUser;
		panel = new LayoutPanel();
		initWidget(panel);
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.PCT);
		dockLayoutPanel.setStyleName(CSS.page());
		panel.add(dockLayoutPanel);
		panel.setWidgetTopHeight(dockLayoutPanel, 0.0, Unit.PX, 100, Unit.PCT);
		panel.setWidgetLeftWidth(dockLayoutPanel, 0.0, Unit.PCT, 100, Unit.PCT);

		headerPanel = new LayoutPanel();
		headerPanel.setStyleName(CSS.header());
		dockLayoutPanel.addNorth(headerPanel, 20);

		bottomPanel = new LayoutPanel();
		bottomPanel.setStyleName(CSS.menuB());
		dockLayoutPanel.addSouth(bottomPanel, 3);

		menuPanel = new LayoutPanel();
		menuPanel.setStyleName(CSS.menuG());
		dockLayoutPanel.addWest(menuPanel, 25.0);

		// TODO center
		LayoutPanel layoutPanel_1 = new LayoutPanel();
		layoutPanel_1.setHeight("100%");
		dockLayoutPanel.add(layoutPanel_1);
		contentPanel = new SimpleLayoutPanel();
		layoutPanel_1.add(contentPanel);
		layoutPanel_1.setWidgetLeftWidth(contentPanel, 1.0, Unit.PCT, 80.0,
				Unit.PCT);
		layoutPanel_1.setWidgetTopHeight(contentPanel, 10.0, Unit.PCT, 90.0,
				Unit.PCT);

		authenticationButton = new PushButton("Se connecter");
		disconnectButton = new Label("Me déconnecter");
		privateSpaceButton = new Label("Mon espace");
		passwordForgetButton = new Button("Envoyer");

		// TODO Header
		Image image = new Image(IMAGES.logo());
		headerPanel.add(image);
		headerPanel.setWidgetLeftWidth(image, 0.0, Unit.PX, 40.0, Unit.PCT);
		headerPanel.setWidgetTopHeight(image, 0.0, Unit.PX, 96, Unit.PCT);

		AlternateBanner alternateBanner = new AlternateBanner();
		headerPanel.add(alternateBanner);
		headerPanel.setWidgetLeftWidth(alternateBanner, 392.0, Unit.PX, 400.0,
				Unit.PX);
		headerPanel.setWidgetTopHeight(alternateBanner, 1.0, Unit.PX, 200.0,
				Unit.PX);

		LayoutPanel layoutPanel_2 = new LayoutPanel();
		layoutPanel_2.addStyleName(CSS.headerLogin());
		headerPanel.add(layoutPanel_2);
		headerPanel.setWidgetLeftWidth(layoutPanel_2, 70.0, Unit.PCT, 30.0,
				Unit.PCT);
		headerPanel.setWidgetTopHeight(layoutPanel_2, 10, Unit.PCT, 60.0,
				Unit.PCT);

		FlowPanel lInscriptionLabels = new FlowPanel();
		Label nlnlblInscription = new Label("Inscription");
		nlnlblInscription.addStyleName(CSS.headerLoginInscription());
		lInscriptionLabels.add(nlnlblInscription);
		Label lInscriptionSub = new Label("Retirer le dossier d'inscription");
		lInscriptionSub.addStyleName(CSS.headerLoginSubLine());
		lInscriptionLabels.add(lInscriptionSub);
		layoutPanel_2.add(lInscriptionLabels);
		layoutPanel_2.setWidgetLeftWidth(lInscriptionLabels, 5.0, Unit.PCT,
				50.0, Unit.PCT);
		layoutPanel_2.setWidgetTopHeight(lInscriptionLabels, 30.0, Unit.PCT,
				100.0, Unit.PCT);

		nlnlblInscription.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
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
				Label lIdLabel = new HTML(new String(initResult
						.getArea("Inscription").getMenu("Informations")
						.getContentSet().get(0).getData()));
				lIdPanel.add(lIdLabel);
				VerticalPanel lVerticalPanel = new VerticalPanel();
				lVerticalPanel.getElement().getStyle()
						.setMarginTop(10, Unit.PX);
				lIdPanel.add(lVerticalPanel);
				lVerticalPanel.add(new Label("Informations utiles:"));
				HorizontalPanel lHorizontalPanel = new HorizontalPanel();
				lHorizontalPanel.getElement().getStyle()
						.setMarginLeft(30, Unit.PX);
				lHorizontalPanel.setSpacing(10);
				lVerticalPanel.add(lHorizontalPanel);
				Anchor lEcole = new Anchor("Ecole de natation");
				lEcole.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						loadContent("Groupe", "Ecole de natation");
					}
				});
				lHorizontalPanel.add(lEcole);
				lEcole = new Anchor("Loisirs");
				lEcole.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						loadContent("Groupes", "Loisirs");
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

				lEcole = new Anchor("Eau libre");
				lEcole.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						loadContent("Groupes", "Eau libre");
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

				ButtonBase lValidButton = new PushButton(
						"Dossier d'inscription");
				lValidButton.addStyleName(CSS.loginButton());
				lFlowPanel.add(lValidButton);
				popupManager.createPopup(true, true, lFlowPanel);
				popupManager.center();
			}
		});

		FlowPanel lConnexionLabels = new FlowPanel();
		// TODO Disconnect
		if (user != null) {
			buildDisconnexionPanel(lConnexionLabels);
		} else {
			// TODO Connexion
			buildConnexionPanel(lConnexionLabels);
		}
		layoutPanel_2.add(lConnexionLabels);
		layoutPanel_2.setWidgetLeftWidth(lConnexionLabels, 60.0, Unit.PCT,
				50.0, Unit.PCT);
		layoutPanel_2.setWidgetTopHeight(lConnexionLabels, 30.0, Unit.PCT,
				100.0, Unit.PCT);

		// TODO South
		InlineLabel nlnlblContacts = new InlineLabel("Contacts");
		nlnlblContacts.setStyleName(CSS.menuBTitle());
		bottomPanel.add(nlnlblContacts);
		bottomPanel.setWidgetLeftWidth(nlnlblContacts, 40.0, Unit.PCT, 20,
				Unit.PCT);

		InlineLabel nlnlblSuiveznousrss = new InlineLabel("Suivez-nous (RSS)");
		nlnlblSuiveznousrss.setStyleName(CSS.menuBTitle());
		bottomPanel.add(nlnlblSuiveznousrss);
		bottomPanel.setWidgetLeftWidth(nlnlblSuiveznousrss, 60, Unit.PCT, 20.0,
				Unit.PCT);

		// InlineLabel nlnlblVisiteursDepuis = new
		// InlineLabel("150 visiteurs depuis mars 2010");
		// nlnlblVisiteursDepuis.setStyleName(CSS.menuBTitle());
		// bottomPanel.add(nlnlblVisiteursDepuis);
		// bottomPanel.setWidgetLeftWidth(nlnlblVisiteursDepuis, 80.0, Unit.PCT,
		// 1024.0, Unit.PX);
		// bottomPanel.setWidgetTopHeight(nlnlblVisiteursDepuis, 0.0, Unit.PX,
		// 28.0, Unit.PX);

		// TODO Menu
		buildMenuPanel();

		// TODO content
		// TODO Actu
		actuPanel = new FlowPanel();
		actuPanel.setStyleName(CSS.bloc());
		contentPanel.setWidget(actuPanel);
		buildActuPanel();

		// Arena
		Image lLogoArena = new Image(IMAGES.logoArenaPart());
		lLogoArena.setAltText("Boutique");
		lLogoArena.setTitle("Boutique");
		lLogoArena.getElement().getStyle().setCursor(Cursor.POINTER);
		lLogoArena.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent pEvent) {
				FlowPanel lFlowPanel = new FlowPanel();
				HorizontalPanel lHeader = new HorizontalPanel();
				lHeader.addStyleName(CSS.loginHeader());
				Label lTitle = new Label("Boutique");
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
				Label lIdLabel = new HTML(new String(initResult
						.getArea("Boutique").getMenuSet().get("Informations")
						.getContentSet().get(0).getData()));
				lIdPanel.add(lIdLabel);
				lFlowPanel.add(lIdPanel);
				popupManager.createPopup(true, true, lFlowPanel);
				popupManager.center();

			}
		});
		SimpleLayoutPanel lImagePanel = new SimpleLayoutPanel();
		lImagePanel.setWidget(lLogoArena);
		layoutPanel_1.add(lImagePanel);
		layoutPanel_1.setWidgetRightWidth(lImagePanel, 0.0, Unit.PCT, 17.0,
				Unit.PCT);
		layoutPanel_1.setWidgetTopHeight(lImagePanel, 1.0, Unit.PCT, 120.0,
				Unit.PX);

		// Meteo
		DockLayoutPanel dockLayoutPanel_Meteo = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel_Meteo.setStyleName(CSS.bloc());
		HTML lMeteo = new HTML(
				"<div id=\"cont_7b8dcd7d9f2587bff3518640f95fd205\"><h2 id=\"h_7b8dcd7d9f2587bff3518640f95fd205\"><a href=\"http://www.tameteo.com/\" title=\"M&eacute;t&eacute;o\">M&eacute;t&eacute;o</a></h2><a id=\"a_7b8dcd7d9f2587bff3518640f95fd205\" href=\"http://www.tameteo.com/meteo_Toulouse-Europe-France-Haute+Garonne-LFBO-1-26128.html\" target=\"_blank\" title=\"M&eacute;t&eacute;o Toulouse\" style=\"color:#656565;font-family:7;font-size:14px;\">M&eacute;t&eacute;o Toulouse</a><script type=\"text/javascript\" src=\"http://www.tameteo.com/wid_loader/7b8dcd7d9f2587bff3518640f95fd205\"></script></div>");
		layoutPanel_1.add(lMeteo);
		layoutPanel_1
				.setWidgetLeftWidth(lMeteo, 82.0, Unit.PCT, 180.0, Unit.PX);
		layoutPanel_1
				.setWidgetTopHeight(lMeteo, 19.0, Unit.PCT, 110.0, Unit.PX);

		DockLayoutPanel dockLayoutPanel_2 = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel_2.setStyleName(CSS.bloc());
		layoutPanel_1.add(dockLayoutPanel_2);
		layoutPanel_1.setWidgetLeftWidth(dockLayoutPanel_2, 82.0, Unit.PCT,
				17.0, Unit.PCT);
		layoutPanel_1.setWidgetTopHeight(dockLayoutPanel_2, 36.0, Unit.PCT,
				30.0, Unit.PCT);

		Label lblEvnements = new Label("Evènements");
		lblEvnements.setStyleName(CSS.blocTitle());
		dockLayoutPanel_2.addNorth(lblEvnements, 1.8);

		DatePicker lDatePicker = new DatePicker();
		DateTimeFormat lFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
		// Date[] lDates = new Date[] {lFormat.parse("10/03/2011"),
		// lFormat.parse("11/03/2011"), lFormat.parse("12/03/2011"),
		// lFormat.parse("13/03/2011"), lFormat.parse("18/03/2011"),
		// lFormat.parse("19/03/2011"), lFormat.parse("20/03/2011"),
		// lFormat.parse("15/04/2011"), lFormat.parse("16/04/2011"),
		// lFormat.parse("17/04/2011")};
		// lDatePicker.addStyleToDates(CSS.calendarEvt(),
		// Arrays.asList(lDates));
		// lDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
		//
		// public void onValueChange(ValueChangeEvent<Date> pEvent) {
		// Window.alert("Il y a un évènement ce jour là.");
		// }
		// });
		lDatePicker.setSize("100%", "100%");
		dockLayoutPanel_2.add(lDatePicker);

		// Partenaires:
		DockLayoutPanel dockLayoutPanel_part = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel_part.setStyleName(CSS.bloc());
		layoutPanel_1.add(dockLayoutPanel_part);
		layoutPanel_1.setWidgetLeftWidth(dockLayoutPanel_part, 82.0, Unit.PCT,
				17.0, Unit.PCT);
		layoutPanel_1.setWidgetTopHeight(dockLayoutPanel_part, 68.0, Unit.PCT,
				30.0, Unit.PCT);

		Label lTitlePart = new Label("Partenaires");
		lTitlePart.setStyleName(CSS.blocTitle());
		dockLayoutPanel_part.addNorth(lTitlePart, 1.8);
		AlternateBanner_Part lAlternateBanner_Part = new AlternateBanner_Part();
		dockLayoutPanel_part.add(lAlternateBanner_Part);

		Label lblAspttGrandToulouse = new Label("ASPTT Grand Toulouse Natation");
		lblAspttGrandToulouse.setStyleName(CSS.title());
		layoutPanel_1.add(lblAspttGrandToulouse);
		layoutPanel_1.setWidgetLeftWidth(lblAspttGrandToulouse, 18.0, Unit.PX,
				60.0, Unit.PCT);
		layoutPanel_1.setWidgetTopHeight(lblAspttGrandToulouse, 0.0, Unit.PCT,
				10.0, Unit.PCT);

		Label lblAccueil = new Label("ASPTT Grand Toulouse Natation -> Accueil");
		lblAccueil.setStyleName(CSS.tetiere());
		lblAccueil.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				contentPanel.setWidget(actuPanel);
			}
		});
		layoutPanel_1.add(lblAccueil);
		layoutPanel_1.setWidgetLeftWidth(lblAccueil, 10.0, Unit.PX, 510.0,
				Unit.PX);
		layoutPanel_1.setWidgetTopHeight(lblAccueil, 5.0, Unit.PCT, 18.0,
				Unit.PX);
	}

	private void buildDisconnexionPanel(FlowPanel pConnexionLabels) {
		privateSpaceButton.addStyleName(CSS.logoutLabelPanel());
		disconnectButton.addStyleName(CSS.logoutLabelPanel());
		pConnexionLabels.add(privateSpaceButton);
		pConnexionLabels.add(disconnectButton);
		pConnexionLabels.addStyleName(CSS.logoutPanel());
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
						HTML lText = new HTML(new String(initResult
								.getArea("Inscription").getMenu("MotDePasse")
								.getContentSet().get(0).getData()));
						lText.addStyleName(CSS.loginContent());
						FlowPanel lPanel = new FlowPanel();
						VerticalPanel lInnerPanel = new VerticalPanel();
						lInnerPanel.add(lText);

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
				});
				lPasswordForget.addStyleName(CSS.loginForgetLabel());
				lPanel.add(lPasswordForget);

				// lPanel.setWidgetTopHeight(lEmailAddressInput, 0, Unit.PCT,
				// 35, Unit.PCT);
				// lPanel.setWidgetTopHeight(lPasswordInput, 35, Unit.PCT, 35,
				// Unit.PCT);
				// lPanel.setWidgetBottomHeight(authenticationButton, 3,
				// Unit.PCT, 20, Unit.PCT);
				popupManager.createValidatePopup(true, true, lPanel);
				popupManager.getPopupValidate().setValidateButton(
						authenticationButton);
				popupManager.getPopupValidate().setAction(popupAction);
				popupManager.setSize("300px", "100px");
				// PopupLayout lPopupLayout = new PopupLayout(true, "Connexion",
				// lPanel);
				popupManager.center();
			}
		});
	}

	private void addMenuGSubStyle(final Label pLabel) {
		pLabel.addMouseOverHandler(new MouseOverHandler() {

			public void onMouseOver(MouseOverEvent pEvent) {
				pLabel.addStyleName(CSS.menuGSubMouseOver());
			}
		});
		pLabel.addMouseOutHandler(new MouseOutHandler() {

			public void onMouseOut(MouseOutEvent pEvent) {
				pLabel.removeStyleName(CSS.menuGSubMouseOver());
			}
		});
	}

	private void buildMenuPanel() {
		int space = 6;
		List<AreaUi> lAreaUis = new ArrayList<AreaUi>(initResult.getArea()
				.values());
		// First
		final AreaUi lFirstArea = lAreaUis.get(0);
		lAreaUis.remove(0);
		Label lFirstAreaTitle = new Label(lFirstArea.getTitle());
		lFirstAreaTitle.setStyleName(CSS.menuGTitleFirst());
		menuPanel.add(lFirstAreaTitle);
		menuPanel.setWidgetTopHeight(lFirstAreaTitle, 0, Unit.PCT, 30, Unit.PX);
		// Build menu
		for (final MenuUi lMenu : lFirstArea.getMenuSet().values()) {
			Label lMenuLabel = new Label(lMenu.getTitle());
			lMenuLabel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent pEvent) {
					loadContent(lFirstArea.getTitle(), lMenu.getTitle());
				}
			});
			lMenuLabel.setStyleName(CSS.menuGSub());
			addMenuGSubStyle(lMenuLabel);
			menuPanel.add(lMenuLabel);
			menuPanel.setWidgetTopHeight(lMenuLabel, space, Unit.PCT, 25,
					Unit.PX);
			space += 5;
		}
		for (final AreaUi lArea : lAreaUis) {
			Label lAreaTitle = new Label(lArea.getTitle());
			lAreaTitle.setStyleName(CSS.menuGTitle());
			menuPanel.add(lAreaTitle);
			menuPanel.setWidgetTopHeight(lAreaTitle, space, Unit.PCT, 30,
					Unit.PX);
			space += 6;
			// Build menu
			for (final MenuUi lMenu : lArea.getMenuSet().values()) {
				Label lMenuLabel = new Label(lMenu.getTitle());
				lMenuLabel.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						loadContent(lArea.getTitle(), lMenu.getTitle());
					}
				});
				lMenuLabel.setStyleName(CSS.menuGSub());
				addMenuGSubStyle(lMenuLabel);
				menuPanel.add(lMenuLabel);
				menuPanel.setWidgetTopHeight(lMenuLabel, space, Unit.PCT, 25,
						Unit.PX);
				space += 5;
			}
		}
	}

	private void buildActuPanel() {
		Label lblALaUne = new Label("A la une");
		lblALaUne.setStyleName(CSS.blocTitle());
		actuPanel.add(lblALaUne);
		DateTimeFormat lDateTimeFormat = DateTimeFormat.getFullDateFormat();
		for (ActuUi lActuUi : initResult.getActu()) {
			HeaderPanel lHeaderPanel = new HeaderPanel(
					lDateTimeFormat.format(lActuUi.getCreationDate()) + " - "
							+ lActuUi.getTitle(), lActuUi.getSummary(), true);
			lHeaderPanel.isOdd();
			actuPanel.add(lHeaderPanel);
		}
	}

	private void loadContent(final String pAreaTitle, final String pMenuTitle) {
		AreaUi lAreaUi = initResult.getArea(pAreaTitle);
		if (lAreaUi != null) {
			MenuUi lMenu = lAreaUi.getMenu(pMenuTitle);
			eventBus.fireEvent(new LoadContentEvent(lMenu.getId()));
		}
		
	}
	public void loadContent(final byte[] pData) {
		if(pData != null) {
				contentPanel.setWidget(new HTML(new String(pData)));
				popupManager.hide();
		}
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

	public void setPopupManager(PopupManager pPopupManager) {
		popupManager = pPopupManager;
	}

	public HasKeyPressHandlers getAuthenticationButton2() {
		return authenticationButton;
	}

	public void connexionPopupHide() {
		popupManager.hide();

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
		return emailAddressForgetInput;
	}
}