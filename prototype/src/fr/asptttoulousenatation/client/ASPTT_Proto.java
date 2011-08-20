package fr.asptttoulousenatation.client;

import static fr.asptttoulousenatation.client.resources.ASPTT_ProtoResources.IMAGES;

import java.util.Arrays;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import fr.asptttoulousenatation.client.resources.ASPTT_ProtoCss;
import fr.asptttoulousenatation.client.resources.ASPTT_ProtoResources;

public class ASPTT_Proto implements EntryPoint {

	private static ASPTT_ProtoCss CSS = ASPTT_ProtoResources.RESOURCE.css();
	public void onModuleLoad() {
		CSS.ensureInjected();
		RootLayoutPanel rootPanel = RootLayoutPanel.get();
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.PCT);
		dockLayoutPanel.setStyleName(CSS.page());
		rootPanel.add(dockLayoutPanel);
		rootPanel.setWidgetTopHeight(dockLayoutPanel, 0.0, Unit.PX, 900.0, Unit.PX);
		rootPanel.setWidgetLeftWidth(dockLayoutPanel, 0.0, Unit.PX, 100, Unit.PCT);
		
		LayoutPanel layoutPanel = new LayoutPanel();
		layoutPanel.setStyleName(CSS.header());
		dockLayoutPanel.addNorth(layoutPanel, 15);
		
		Image image = new Image(IMAGES.logo());
		layoutPanel.add(image);
		layoutPanel.setWidgetLeftWidth(image, 0.0, Unit.PX, image.getWidth(), Unit.PX);
		layoutPanel.setWidgetTopHeight(image, 0.0, Unit.PX, 100.0, Unit.PCT);
		
		AlternateBanner alternateBanner = new AlternateBanner();
		layoutPanel.add(alternateBanner);
		layoutPanel.setWidgetLeftWidth(alternateBanner, 392.0, Unit.PX, 400.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(alternateBanner, 1.0, Unit.PX, 200.0, Unit.PX);
		
		LayoutPanel layoutPanel_2 = new LayoutPanel();
		layoutPanel_2.addStyleName(CSS.headerLogin());
		layoutPanel.add(layoutPanel_2);
		layoutPanel.setWidgetLeftWidth(layoutPanel_2, 70.0, Unit.PCT, 30.0, Unit.PCT);
		layoutPanel.setWidgetTopHeight(layoutPanel_2, 18.0, Unit.PX, 60.0, Unit.PCT);
		
		FlowPanel lInscriptionLabels = new FlowPanel();
		Label nlnlblInscription = new Label("Inscription");
		nlnlblInscription.addStyleName(CSS.headerLoginInscription());
		lInscriptionLabels.add(nlnlblInscription);
		Label lInscriptionSub = new Label("Retirer le dossier d'inscription");
		lInscriptionSub.addStyleName(CSS.headerLoginSubLine());
		lInscriptionLabels.add(lInscriptionSub);
		layoutPanel_2.add(lInscriptionLabels);
		layoutPanel_2.setWidgetLeftWidth(lInscriptionLabels, 12.0, Unit.PX, 50.0, Unit.PCT);
		layoutPanel_2.setWidgetTopHeight(lInscriptionLabels, 20.0, Unit.PX, 100.0, Unit.PCT);
		
		nlnlblInscription.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				final DecoratedPopupPanel lDecoratedPopupPanel = new DecoratedPopupPanel(true, true);
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
						lDecoratedPopupPanel.hide();
					}
				});
				lHeader.add(lClose);
				lHeader.setCellHorizontalAlignment(lTitle, HasHorizontalAlignment.ALIGN_LEFT);
				lHeader.setCellHorizontalAlignment(lClose, HasHorizontalAlignment.ALIGN_RIGHT);
				lClose.getElement().getStyle().setMarginLeft(50, Unit.PX);
				lFlowPanel.add(lHeader);
				FlowPanel lIdPanel = new FlowPanel();
				lIdPanel.addStyleName(CSS.loginContent());
				Label lIdLabel = new Label("Pour vous inscrire, veuillez télécharger le dossier ci-joint. Ce dossier doit être complété et renvoyé à l'association.");
				lIdPanel.add(lIdLabel);
				VerticalPanel lVerticalPanel = new VerticalPanel();
				lVerticalPanel.getElement().getStyle().setMarginTop(10, Unit.PX);
				lIdPanel.add(lVerticalPanel);
				lVerticalPanel.add(new Label("Informations utiles:"));
				HorizontalPanel lHorizontalPanel = new HorizontalPanel();
				lHorizontalPanel.getElement().getStyle().setMarginLeft(30, Unit.PX);
				lVerticalPanel.add(lHorizontalPanel);
				Anchor lGroupe = new Anchor("Groupes");
				lHorizontalPanel.add(lGroupe);
				lHorizontalPanel.setCellWidth(lGroupe, "70px");
				Anchor lLieux = new Anchor("Lieux d'entrainements");
				lHorizontalPanel.add(lLieux);
				lHorizontalPanel.setCellWidth(lLieux, "300px");
				
				lIdPanel.add(lHorizontalPanel);
				lFlowPanel.add(lIdPanel);
				
				ButtonBase lValidButton = new PushButton("Dossier d'inscription");
				lValidButton.addStyleName(CSS.loginButton());
				lFlowPanel.add(lValidButton);
				lDecoratedPopupPanel.add(lFlowPanel);
				lDecoratedPopupPanel.center();
			}
		});
		
		FlowPanel lConnexionLabels = new FlowPanel();
		Label nlnlblConnexion = new Label("Connexion");
		nlnlblConnexion.addStyleName(CSS.headerLoginConnexion());
		lConnexionLabels.add(nlnlblConnexion);
		Label lConnexionSub = new Label("Utiliser votre compte");
		lConnexionSub.addStyleName(CSS.headerLoginSubLine());
		lConnexionLabels.add(lConnexionSub);
		layoutPanel_2.add(lConnexionLabels);
		layoutPanel_2.setWidgetLeftWidth(lConnexionLabels, 60.0, Unit.PCT, 50.0, Unit.PCT);
		layoutPanel_2.setWidgetTopHeight(lConnexionLabels, 20.0, Unit.PX, 100.0, Unit.PCT);
		
		//Popup connexion
		nlnlblConnexion.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				final DecoratedPopupPanel lDecoratedPopupPanel = new DecoratedPopupPanel(true, true);
				FlowPanel lFlowPanel = new FlowPanel();
				HorizontalPanel lHeader = new HorizontalPanel();
				lHeader.addStyleName(CSS.loginHeader());
				Label lTitle = new Label("Connexion");
				lTitle.addStyleName(CSS.loginTitle());
				lHeader.add(lTitle);
				Label lClose = new Label("X");
				lClose.addStyleName(CSS.loginClose());
				lClose.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent pEvent) {
						lDecoratedPopupPanel.hide();
					}
				});
				lHeader.add(lClose);
				lHeader.setCellHorizontalAlignment(lTitle, HasHorizontalAlignment.ALIGN_LEFT);
				lHeader.setCellHorizontalAlignment(lClose, HasHorizontalAlignment.ALIGN_RIGHT);
				lClose.getElement().getStyle().setMarginLeft(50, Unit.PX);
				lFlowPanel.add(lHeader);
				FlowPanel lIdPanel = new FlowPanel();
				lIdPanel.addStyleName(CSS.loginContent());
				Label lIdLabel = new Label("Adresse e-mail: ");
				lIdPanel.add(lIdLabel);
				TextBox lIdInput = new TextBox();
				lIdInput.setWidth("300px");
				lIdPanel.add(lIdInput);
				lFlowPanel.add(lIdPanel);
				
				FlowPanel lPasswordPanel = new FlowPanel();
				lPasswordPanel.addStyleName(CSS.loginContent());
				Label lPasswordLabel = new Label("Mot de passe: ");
				lIdPanel.add(lPasswordLabel);
				TextBox lPasswordInput = new PasswordTextBox();
				lPasswordInput.setWidth("300px");
				lIdPanel.add(lPasswordInput);
				lFlowPanel.add(lPasswordPanel);
				ButtonBase lValidButton = new PushButton("Valider");
				lValidButton.addStyleName(CSS.loginButton());
				lFlowPanel.add(lValidButton);
				lDecoratedPopupPanel.add(lFlowPanel);
				lDecoratedPopupPanel.center();
			}
		});
		
		LayoutPanel flowPanel = new LayoutPanel();
		flowPanel.setStyleName(CSS.menuB());
		dockLayoutPanel.addSouth(flowPanel, 5);
		
		InlineLabel nlnlblContacts = new InlineLabel("Contacts");
		nlnlblContacts.setStyleName(CSS.menuBTitle());
		flowPanel.add(nlnlblContacts);
		flowPanel.setWidgetLeftWidth(nlnlblContacts, 223.0, Unit.PX, 1024.0, Unit.PX);
		flowPanel.setWidgetTopHeight(nlnlblContacts, 0.0, Unit.PX, 28.0, Unit.PX);
		
		InlineLabel nlnlblSuiveznousrss = new InlineLabel("Suivez-nous (RSS)");
		nlnlblSuiveznousrss.setStyleName(CSS.menuBTitle());
		flowPanel.add(nlnlblSuiveznousrss);
		flowPanel.setWidgetLeftWidth(nlnlblSuiveznousrss, 335.0, Unit.PX, 1024.0, Unit.PX);
		flowPanel.setWidgetTopHeight(nlnlblSuiveznousrss, 0.0, Unit.PX, 28.0, Unit.PX);
		
		InlineLabel nlnlblVisiteursDepuis = new InlineLabel("150 visiteurs depuis mars 2010");
		nlnlblVisiteursDepuis.setStyleName(CSS.menuBTitle());
		flowPanel.add(nlnlblVisiteursDepuis);
		flowPanel.setWidgetLeftWidth(nlnlblVisiteursDepuis, 80.0, Unit.PCT, 1024.0, Unit.PX);
		flowPanel.setWidgetTopHeight(nlnlblVisiteursDepuis, 0.0, Unit.PX, 28.0, Unit.PX);
		
		FlowPanel flowPanel_1 = new FlowPanel();
		flowPanel_1.setStyleName(CSS.menuG());
		dockLayoutPanel.addWest(flowPanel_1, 20.0);
		
		Label lblLeClub = new Label("Le club");
		lblLeClub.setStyleName(CSS.menuGTitleFirst());
		flowPanel_1.add(lblLeClub);
		
		Label lblHistoriqueDuClub = new Label("Historique du club");
		lblHistoriqueDuClub.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblHistoriqueDuClub);
		flowPanel_1.add(lblHistoriqueDuClub);
		
		Label lblOrganisationDuClub = new Label("Organisation du club");
		lblOrganisationDuClub.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblOrganisationDuClub);
		flowPanel_1.add(lblOrganisationDuClub);
		
		Label lblClub_3 = new Label("Lieux d'entrainements");
		lblClub_3.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblClub_3);
		flowPanel_1.add(lblClub_3);
		
		Label lblClub_4 = new Label("Officiels");
		lblClub_4.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblClub_4);
		flowPanel_1.add(lblClub_4);
		
		Label lblClub_5 = new Label("Vie du club");
		lblClub_5.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblClub_5);
		flowPanel_1.add(lblClub_5);
		
		Label lblGroupes = new Label("Groupes");
		lblGroupes.setStyleName(CSS.menuGTitle());
		flowPanel_1.add(lblGroupes);
		
		Label lblEcoleDeNatation = new Label("Ecole de natation");
		lblEcoleDeNatation.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblEcoleDeNatation);
		flowPanel_1.add(lblEcoleDeNatation);
		
		Label lblLoisirs = new Label("Loisirs");
		lblLoisirs.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblLoisirs);
		flowPanel_1.add(lblLoisirs);
		
		Label lblGroupe_3 = new Label("Compétitions");
		lblGroupe_3.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblGroupe_3);
		flowPanel_1.add(lblGroupe_3);
		
		Label lblGroupe_4 = new Label("Eau libre");
		lblGroupe_4.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblGroupe_4);
		flowPanel_1.add(lblGroupe_4);
		
		Label lblGroupe_5 = new Label("Aquagym");
		lblGroupe_5.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblGroupe_5);
		flowPanel_1.add(lblGroupe_5);
		
		Label lblGroupe_6 = new Label("Centre de formation");
		lblGroupe_6.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblGroupe_6);
		flowPanel_1.add(lblGroupe_6);
		
		Label lblComptitions = new Label("Compétitions");
		lblComptitions.setStyleName(CSS.menuGTitle());
		flowPanel_1.add(lblComptitions);
		
		Label lblCalendrier = new Label("Calendrier");
		lblCalendrier.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblCalendrier);
		flowPanel_1.add(lblCalendrier);
		
		Label lblRsultats = new Label("Résultats");
		lblRsultats.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lblRsultats);
		flowPanel_1.add(lblRsultats);
		
		Label lbl_3 = new Label("Records du club");
		lbl_3.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lbl_3);
		flowPanel_1.add(lbl_3);
		
		Label lbl_4 = new Label("Ranking");
		lbl_4.setStyleName(CSS.menuGSub());
		addMenuGSubStyle(lbl_4);
		flowPanel_1.add(lbl_4);
		
		Label lblBoutique = new Label("Boutique");
		lblBoutique.setStyleName(CSS.menuGTitle());
		flowPanel_1.add(lblBoutique);
		
		LayoutPanel layoutPanel_1 = new LayoutPanel();
		layoutPanel_1.setHeight("100%");
		dockLayoutPanel.add(layoutPanel_1);
		
		FlowPanel flowPanel_bloc_1 = new FlowPanel();
		flowPanel_bloc_1.setStyleName(CSS.bloc());
		layoutPanel_1.add(flowPanel_bloc_1);
		layoutPanel_1.setWidgetLeftWidth(flowPanel_bloc_1, 1.0, Unit.PCT, 80.0, Unit.PCT);
		layoutPanel_1.setWidgetTopHeight(flowPanel_bloc_1, 10.0, Unit.PCT, 90.0, Unit.PCT);
		
		Label lblALaUne = new Label("A la une");
		lblALaUne.setStyleName(CSS.blocTitle());
		flowPanel_bloc_1.add(lblALaUne);
		
		HeaderPanel lHeaderPanel_2 = new HeaderPanel("Mardi 15 mars 2011 - Championnat de France National 2 à Chalon sur Saône", "suivez les résultats sur http://www.liveffn.com/cgi-bin/index.php?competition=1643", true);
		lHeaderPanel_2.isOdd();
		flowPanel_bloc_1.add(lHeaderPanel_2);
		
		HeaderPanel lHeaderPanel_1 = new HeaderPanel("Mardi 08 mars 2011 - Championnat de France Master à Dunkerque", " suivez les résultats sur http://www.liveffn.com/cgi-bin/index.php?competition=1642", true);
		lHeaderPanel_1.isEven();
		flowPanel_bloc_1.add(lHeaderPanel_1);
		
		HeaderPanel lHeaderPanel_3 = new HeaderPanel("Mardi 01 mars 2011 - Résultats Meeting National de Tarbes", "Ci-joint, les résultats de la compétition qui s'est déroulée le week-end passé à Tarbes, où 27 nageurs (11 Filles et 16 Garçons) nous représentaient, des catégories Minimes jusqu'à Seniors, avec pour objectif premier de décrocher des qualifications pour les Championnats de France N2 de Chalon / Saône mi-Mars, des France Jeunes à Dijon mi-Avril ou encore des France Minimes et Cadets fin Juillet.", true);
		lHeaderPanel_3.isOdd();
		flowPanel_bloc_1.add(lHeaderPanel_3);

		//Arena
		DockLayoutPanel dockLayoutPanel_Arena = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel_Arena.setStyleName(CSS.bloc());
		layoutPanel_1.add(dockLayoutPanel_Arena);
		layoutPanel_1.setWidgetLeftWidth(dockLayoutPanel_Arena, 82.0, Unit.PCT, 17.0, Unit.PCT);
		layoutPanel_1.setWidgetTopHeight(dockLayoutPanel_Arena, 1.0, Unit.PCT, 120.0, Unit.PX);
		
		Label lblArena = new Label("Boutique");
		lblArena.addStyleName(CSS.blocTitle());
		lblArena.addStyleName(CSS.blocTitleLink());
		dockLayoutPanel_Arena.addNorth(lblArena, 1.8);
		Image lLogoArena = new Image(IMAGES.logoArena());
		dockLayoutPanel_Arena.add(lLogoArena);
		
		lblArena.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent pEvent) {
				final DecoratedPopupPanel lDecoratedPopupPanel = new DecoratedPopupPanel(true, true);
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
						lDecoratedPopupPanel.hide();
					}
				});
				lHeader.add(lClose);
				lHeader.setCellHorizontalAlignment(lTitle, HasHorizontalAlignment.ALIGN_LEFT);
				lHeader.setCellHorizontalAlignment(lClose, HasHorizontalAlignment.ALIGN_RIGHT);
				lClose.getElement().getStyle().setMarginLeft(50, Unit.PX);
				lFlowPanel.add(lHeader);
				FlowPanel lIdPanel = new FlowPanel();
				lIdPanel.addStyleName(CSS.loginContent());
				Label lIdLabel = new Label("Partenariat avec la boutique arena de blagnac 10% de remise en caisse sur tout le magasin et sur présentation d’un justificatif d’appartenance à l’ASPTT toulouse.");
				lIdPanel.add(lIdLabel);
				lFlowPanel.add(lIdPanel);
				lDecoratedPopupPanel.add(lFlowPanel);
				lDecoratedPopupPanel.center();	
			}
		});
		
		//Meteo
		DockLayoutPanel dockLayoutPanel_Meteo = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel_Meteo.setStyleName(CSS.bloc());
		HTML lMeteo = new HTML("<div id=\"cont_7b8dcd7d9f2587bff3518640f95fd205\"><h2 id=\"h_7b8dcd7d9f2587bff3518640f95fd205\"><a href=\"http://www.tameteo.com/\" title=\"M&eacute;t&eacute;o\">M&eacute;t&eacute;o</a></h2><a id=\"a_7b8dcd7d9f2587bff3518640f95fd205\" href=\"http://www.tameteo.com/meteo_Toulouse-Europe-France-Haute+Garonne-LFBO-1-26128.html\" target=\"_blank\" title=\"M&eacute;t&eacute;o Toulouse\" style=\"color:#656565;font-family:7;font-size:14px;\">M&eacute;t&eacute;o Toulouse</a><script type=\"text/javascript\" src=\"http://www.tameteo.com/wid_loader/7b8dcd7d9f2587bff3518640f95fd205\"></script></div>");
		layoutPanel_1.add(lMeteo);
		layoutPanel_1.setWidgetLeftWidth(lMeteo, 82.0, Unit.PCT, 180.0, Unit.PX);
		layoutPanel_1.setWidgetTopHeight(lMeteo, 19.0, Unit.PCT, 110.0, Unit.PX);
		
		DockLayoutPanel dockLayoutPanel_2 = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel_2.setStyleName(CSS.bloc());
		layoutPanel_1.add(dockLayoutPanel_2);
		layoutPanel_1.setWidgetLeftWidth(dockLayoutPanel_2, 82.0, Unit.PCT, 17.0, Unit.PCT);
		layoutPanel_1.setWidgetTopHeight(dockLayoutPanel_2, 36.0, Unit.PCT, 30.0, Unit.PCT);
		
		Label lblEvnements = new Label("Evènements");
		lblEvnements.setStyleName(CSS.blocTitle());
		dockLayoutPanel_2.addNorth(lblEvnements, 1.8);
		
		DatePicker lDatePicker = new DatePicker();
		DateTimeFormat lFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
		Date[] lDates = new Date[] {lFormat.parse("10/03/2011"), lFormat.parse("11/03/2011"), lFormat.parse("12/03/2011"), lFormat.parse("13/03/2011"), lFormat.parse("18/03/2011"), lFormat.parse("19/03/2011"), lFormat.parse("20/03/2011"), lFormat.parse("15/04/2011"), lFormat.parse("16/04/2011"), lFormat.parse("17/04/2011")};
		lDatePicker.addStyleToDates(CSS.calendarEvt(), Arrays.asList(lDates));
		lDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			public void onValueChange(ValueChangeEvent<Date> pEvent) {
				Window.alert("Il y a un évènement ce jour là.");
			}
		});
		lDatePicker.setSize("100%", "100%");
		dockLayoutPanel_2.add(lDatePicker);
		
		//Partenaires:
		DockLayoutPanel dockLayoutPanel_part = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel_part.setStyleName(CSS.bloc());
		layoutPanel_1.add(dockLayoutPanel_part);
		layoutPanel_1.setWidgetLeftWidth(dockLayoutPanel_part, 82.0, Unit.PCT, 17.0, Unit.PCT);
		layoutPanel_1.setWidgetTopHeight(dockLayoutPanel_part, 68.0, Unit.PCT, 30.0, Unit.PCT);
		
		Label lTitlePart = new Label("Partenaires");
		lTitlePart.setStyleName(CSS.blocTitle());
		dockLayoutPanel_part.addNorth(lTitlePart, 1.8);
		AlternateBanner_Part lAlternateBanner_Part = new AlternateBanner_Part();
		dockLayoutPanel_part.add(lAlternateBanner_Part);
		
		Label lblAspttGrandToulouse = new Label("ASPTT Grand Toulouse Natation");
		lblAspttGrandToulouse.setStyleName(CSS.title());
		layoutPanel_1.add(lblAspttGrandToulouse);
		layoutPanel_1.setWidgetLeftWidth(lblAspttGrandToulouse, 18.0, Unit.PX, 100.0, Unit.PCT);
		layoutPanel_1.setWidgetTopHeight(lblAspttGrandToulouse, 0.0, Unit.PCT, 10.0, Unit.PCT);
		
		Label lblAccueil = new Label("ASPTT Grand Toulouse Natation -> Accueil -> News");
		lblAccueil.setStyleName(CSS.tetiere());
		layoutPanel_1.add(lblAccueil);
		layoutPanel_1.setWidgetLeftWidth(lblAccueil, 10.0, Unit.PX, 510.0, Unit.PX);
		layoutPanel_1.setWidgetTopHeight(lblAccueil, 5.0, Unit.PCT, 18.0, Unit.PX);
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
}