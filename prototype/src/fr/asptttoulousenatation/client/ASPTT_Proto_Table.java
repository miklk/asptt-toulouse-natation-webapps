package fr.asptttoulousenatation.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ASPTT_Proto_Table implements EntryPoint {

	public void onModuleLoad() {
		RootLayoutPanel rootPanel = RootLayoutPanel.get();
		
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.PCT);
		dockLayoutPanel.setStyleName("page");
		rootPanel.add(dockLayoutPanel);
		rootPanel.setWidgetTopHeight(dockLayoutPanel, 0.0, Unit.PX, 700.0, Unit.PX);
		rootPanel.setWidgetLeftWidth(dockLayoutPanel, 0.0, Unit.PX, 100, Unit.PCT);
		
		LayoutPanel layoutPanel = new LayoutPanel();
		layoutPanel.setStyleName("header");
		dockLayoutPanel.addNorth(layoutPanel, 13.4);
		
		Image image = new Image("images/logoaspttsection.jpg");
		image.setHeight("80px");
		layoutPanel.add(image);
		layoutPanel.setWidgetLeftWidth(image, 29.0, Unit.PX, 300.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(image, 2.0, Unit.PX, 98.0, Unit.PX);
		
		Label lblPartenaires = new Label("PARTENAIRES");
		layoutPanel.add(lblPartenaires);
		layoutPanel.setWidgetLeftWidth(lblPartenaires, 392.0, Unit.PX, 269.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblPartenaires, 18.0, Unit.PX, 67.0, Unit.PX);
		
		LayoutPanel layoutPanel_2 = new LayoutPanel();
		layoutPanel_2.addStyleName("header-login");
		layoutPanel.add(layoutPanel_2);
		layoutPanel.setWidgetLeftWidth(layoutPanel_2, 877.0, Unit.PX, 231.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(layoutPanel_2, 18.0, Unit.PX, 58.0, Unit.PX);
		
		InlineLabel nlnlblInscription = new InlineLabel("Inscription");
		nlnlblInscription.addStyleName("header-login-inscription");
		layoutPanel_2.add(nlnlblInscription);
		layoutPanel_2.setWidgetLeftWidth(nlnlblInscription, 12.0, Unit.PX, 88.0, Unit.PX);
		layoutPanel_2.setWidgetTopHeight(nlnlblInscription, 20.0, Unit.PX, 18.0, Unit.PX);
		
		InlineLabel nlnlblConnexion = new InlineLabel("Connexion");
		nlnlblConnexion.addStyleName("header-login-connexion");
		layoutPanel_2.add(nlnlblConnexion);
		layoutPanel_2.setWidgetLeftWidth(nlnlblConnexion, 132.0, Unit.PX, 79.0, Unit.PX);
		layoutPanel_2.setWidgetTopHeight(nlnlblConnexion, 20.0, Unit.PX, 18.0, Unit.PX);
		
		LayoutPanel flowPanel = new LayoutPanel();
		flowPanel.setStyleName("menu-b");
		dockLayoutPanel.addSouth(flowPanel, 5);
		
		InlineLabel nlnlblContacts = new InlineLabel("Contacts");
		nlnlblContacts.setStyleName("menu-b-title");
		flowPanel.add(nlnlblContacts);
		flowPanel.setWidgetLeftWidth(nlnlblContacts, 223.0, Unit.PX, 1024.0, Unit.PX);
		flowPanel.setWidgetTopHeight(nlnlblContacts, 0.0, Unit.PX, 28.0, Unit.PX);
		
		InlineLabel nlnlblSuiveznousrss = new InlineLabel("Suivez-nous (RSS)");
		nlnlblSuiveznousrss.setStyleName("menu-b-title");
		flowPanel.add(nlnlblSuiveznousrss);
		flowPanel.setWidgetLeftWidth(nlnlblSuiveznousrss, 335.0, Unit.PX, 1024.0, Unit.PX);
		flowPanel.setWidgetTopHeight(nlnlblSuiveznousrss, 0.0, Unit.PX, 28.0, Unit.PX);
		
		FlowPanel flowPanel_1 = new FlowPanel();
		flowPanel_1.setStyleName("menu-g");
		dockLayoutPanel.addWest(flowPanel_1, 16.0);
		
		Label lblLeClub = new Label("Le club");
		lblLeClub.setStyleName("menu-g-title");
		flowPanel_1.add(lblLeClub);
		
		Label lblHistoriqueDuClub = new Label("Historique du club");
		lblHistoriqueDuClub.setStyleName("menu-g-sub");
		flowPanel_1.add(lblHistoriqueDuClub);
		
		Label lblOrganisationDuClub = new Label("Organisation du club");
		lblOrganisationDuClub.setStyleName("menu-g-sub");
		flowPanel_1.add(lblOrganisationDuClub);
		
		Label lblGroupes = new Label("Groupes");
		lblGroupes.setStyleName("menu-g-title");
		flowPanel_1.add(lblGroupes);
		
		Label lblEcoleDeNatation = new Label("Ecole de natation");
		lblEcoleDeNatation.setStyleName("menu-g-sub");
		flowPanel_1.add(lblEcoleDeNatation);
		
		Label lblLoisirs = new Label("Loisirs");
		lblLoisirs.setStyleName("menu-g-sub");
		flowPanel_1.add(lblLoisirs);
		
		Label lblComptitions = new Label("Compétitions");
		lblComptitions.setStyleName("menu-g-title");
		flowPanel_1.add(lblComptitions);
		
		Label lblCalendrier = new Label("Calendrier");
		lblCalendrier.setStyleName("menu-g-sub");
		flowPanel_1.add(lblCalendrier);
		
		Label lblRsultats = new Label("Résultats");
		lblRsultats.setStyleName("menu-g-sub");
		flowPanel_1.add(lblRsultats);
		
		Label lblInscriptions = new Label("Inscriptions");
		lblInscriptions.setStyleName("menu-g-title");
		flowPanel_1.add(lblInscriptions);
		
		LayoutPanel layoutPanel_1 = new LayoutPanel();
		layoutPanel_1.setHeight("100%");
		dockLayoutPanel.add(layoutPanel_1);
		
		FlowPanel flowPanel_2 = new FlowPanel();
		flowPanel_2.setStyleName("menu-h");
		flowPanel_2.setSize("100%", "30%");
		layoutPanel_1.add(flowPanel_2);
		layoutPanel_1.setWidgetLeftWidth(flowPanel_2, 0.0, Unit.PX, 100, Unit.PCT);
		layoutPanel_1.setWidgetTopHeight(flowPanel_2, 0.0, Unit.PX, 100.0, Unit.PX);
		
		Image image_1 = new Image("images/icon_home.gif");
		flowPanel_2.add(image_1);
		image_1.addStyleName("menu-h-image");
		image_1.setSize("16px", "16px");
		
		InlineLabel nlnlblLeClub = new InlineLabel("Le club");
		nlnlblLeClub.setStyleName("menu-h-title");
		flowPanel_2.add(nlnlblLeClub);
		
		InlineLabel nlnlblGroupes = new InlineLabel("Groupes");
		nlnlblGroupes.setStyleName("menu-h-title");
		flowPanel_2.add(nlnlblGroupes);
		
		InlineLabel nlnlblComptitions = new InlineLabel("Compétitions");
		nlnlblComptitions.setStyleName("menu-h-title");
		flowPanel_2.add(nlnlblComptitions);
		
		InlineLabel nlnlblBoutique = new InlineLabel("Boutique");
		nlnlblBoutique.setStyleName("menu-h-title");
		flowPanel_2.add(nlnlblBoutique);
		
		InlineLabel nlnlblJournalDuClub = new InlineLabel("Journal du club");
		nlnlblJournalDuClub.setStyleName("menu-h-title");
		flowPanel_2.add(nlnlblJournalDuClub);
		nlnlblJournalDuClub.setSize("800px", "84px");
		
		DockLayoutPanel dockLayoutPanel_1 = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel_1.setStyleName("bloc");
		layoutPanel_1.add(dockLayoutPanel_1);
		layoutPanel_1.setWidgetLeftWidth(dockLayoutPanel_1, 56.0, Unit.PX, 344.0, Unit.PX);
		layoutPanel_1.setWidgetTopHeight(dockLayoutPanel_1, 154.0, Unit.PX, 168.0, Unit.PX);
		
		Label lblComptitions_1 = new Label("Compétitions");
		lblComptitions_1.setStyleName("bloc-title");
		dockLayoutPanel_1.addNorth(lblComptitions_1, 1.8);
		lblComptitions_1.setWidth("100%");
		
		Label lblLoremIpsumDolor = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam quis nisl purus. Vivamus et ultrices quam. Sed porttitor mi eu neque vehicula dictum. Fusce pharetra pellentesque magna eget sollicitudin. In et risus non mauris mattis congue id et quam. Praesent vel ante eget sem vehicula luctus non ac tortor. Phasellus bibendum scelerisque augue, non bibendum tortor auctor et. Suspendisse sollicitudin, diam sed rutrum auctor, libero ante tincidunt libero, fringilla venenatis nisi massa ut libero. Duis euismod accumsan velit. Fusce risus arcu, faucibus vel pulvinar ut, bibendum sit amet arcu. Sed quis nibh at risus pulvinar vestibulum. Maecenas at dui a sapien pulvinar scelerisque. Vivamus egestas scelerisque dui, in pulvinar orci gravida quis.");
		lblLoremIpsumDolor.setStyleName("bloc-text");
		dockLayoutPanel_1.add(lblLoremIpsumDolor);
		
		DockLayoutPanel dockLayoutPanel_2 = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel_2.setStyleName("bloc");
		layoutPanel_1.add(dockLayoutPanel_2);
		layoutPanel_1.setWidgetLeftWidth(dockLayoutPanel_2, 525.0, Unit.PX, 327.0, Unit.PX);
		layoutPanel_1.setWidgetTopHeight(dockLayoutPanel_2, 154.0, Unit.PX, 135.0, Unit.PX);
		
		Label lblALaUne = new Label("A la une");
		lblALaUne.setStyleName("bloc-title");
		dockLayoutPanel_2.addNorth(lblALaUne, 2.2);
		
		Label lblLoremIpsumDolor_1 = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam quis nisl purus. Vivamus et ultrices quam. Sed porttitor mi eu neque vehicula dictum. Fusce pharetra pellentesque magna eget sollicitudin. In et risus non mauris mattis congue id et quam. Praesent vel ante eget sem vehicula luctus non ac tortor. Phasellus bibendum scelerisque augue, non bibendum tortor auctor et. Suspendisse sollicitudin, diam sed rutrum auctor, libero ante tincidunt libero, fringilla venenatis nisi massa ut libero. Duis euismod accumsan velit. Fusce risus arcu, faucibus vel pulvinar ut, bibendum sit amet arcu. Sed quis nibh at risus pulvinar vestibulum. Maecenas at dui a sapien pulvinar scelerisque. Vivamus egestas scelerisque dui, in pulvinar orci gravida quis.");
		lblLoremIpsumDolor_1.setStyleName("bloc-text");
		dockLayoutPanel_2.add(lblLoremIpsumDolor_1);
		
		DockLayoutPanel dockLayoutPanel_3 = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel_3.setStyleName("bloc");
		layoutPanel_1.add(dockLayoutPanel_3);
		layoutPanel_1.setWidgetLeftWidth(dockLayoutPanel_3, 56.0, Unit.PX, 344.0, Unit.PX);
		layoutPanel_1.setWidgetTopHeight(dockLayoutPanel_3, 349.0, Unit.PX, 135.0, Unit.PX);
		
		Label lblVieDuClub = new Label("Vie du club");
		lblVieDuClub.setStyleName("bloc-title");
		dockLayoutPanel_3.addNorth(lblVieDuClub, 1.7);
		
		Label lblLoremIpsumDolor_2 = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam quis nisl purus. Vivamus et ultrices quam. Sed porttitor mi eu neque vehicula dictum. Fusce pharetra pellentesque magna eget sollicitudin. In et risus non mauris mattis congue id et quam. Praesent vel ante eget sem vehicula luctus non ac tortor. Phasellus bibendum scelerisque augue, non bibendum tortor auctor et. Suspendisse sollicitudin, diam sed rutrum auctor, libero ante tincidunt libero, fringilla venenatis nisi massa ut libero. Duis euismod accumsan velit. Fusce risus arcu, faucibus vel pulvinar ut, bibendum sit amet arcu. Sed quis nibh at risus pulvinar vestibulum. Maecenas at dui a sapien pulvinar scelerisque. Vivamus egestas scelerisque dui, in pulvinar orci gravida quis.");
		lblLoremIpsumDolor_2.setStyleName("bloc-text");
		dockLayoutPanel_3.add(lblLoremIpsumDolor_2);
		
		DockLayoutPanel dockLayoutPanel_4 = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel_4.setStyleName("bloc");
		layoutPanel_1.add(dockLayoutPanel_4);
		layoutPanel_1.setWidgetLeftWidth(dockLayoutPanel_4, 525.0, Unit.PX, 327.0, Unit.PX);
		layoutPanel_1.setWidgetTopHeight(dockLayoutPanel_4, 356.0, Unit.PX, 116.0, Unit.PX);
		
		Label lblEvnements = new Label("Evènements");
		lblEvnements.setStyleName("bloc-title");
		dockLayoutPanel_4.addNorth(lblEvnements, 1.8);
		
		Label lblLoremIpsumDolor_3 = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam quis nisl purus. Vivamus et ultrices quam. Sed porttitor mi eu neque vehicula dictum. Fusce pharetra pellentesque magna eget sollicitudin. In et risus non mauris mattis congue id et quam. Praesent vel ante eget sem vehicula luctus non ac tortor. Phasellus bibendum scelerisque augue, non bibendum tortor auctor et. Suspendisse sollicitudin, diam sed rutrum auctor, libero ante tincidunt libero, fringilla venenatis nisi massa ut libero. Duis euismod accumsan velit. Fusce risus arcu, faucibus vel pulvinar ut, bibendum sit amet arcu. Sed quis nibh at risus pulvinar vestibulum. Maecenas at dui a sapien pulvinar scelerisque. Vivamus egestas scelerisque dui, in pulvinar orci gravida quis.");
		lblLoremIpsumDolor_3.setStyleName("bloc-text");
		dockLayoutPanel_4.add(lblLoremIpsumDolor_3);
		
		Label lblAspttGrandToulouse = new Label("ASPTT Grand Toulouse Natation");
		lblAspttGrandToulouse.setStyleName("title");
		layoutPanel_1.add(lblAspttGrandToulouse);
		layoutPanel_1.setWidgetLeftWidth(lblAspttGrandToulouse, 18.0, Unit.PX, 282.0, Unit.PX);
		layoutPanel_1.setWidgetTopHeight(lblAspttGrandToulouse, 84.0, Unit.PX, 32.0, Unit.PX);
		
		Label lblAccueil = new Label("ASPTT Grand Toulouse Natation -> Accueil -> News");
		lblAccueil.setStyleName("tetiere");
		layoutPanel_1.add(lblAccueil);
		layoutPanel_1.setWidgetLeftWidth(lblAccueil, 10.0, Unit.PX, 510.0, Unit.PX);
		layoutPanel_1.setWidgetTopHeight(lblAccueil, 46.0, Unit.PX, 18.0, Unit.PX);
		
		InlineLabel nlnlblVisiteursDepuis = new InlineLabel("150 visiteurs depuis mars 2010");
		nlnlblVisiteursDepuis.setStyleName("counter");
		rootPanel.add(nlnlblVisiteursDepuis);
		rootPanel.setWidgetLeftWidth(nlnlblVisiteursDepuis, 37.0, Unit.PX, 199.0, Unit.PX);
		rootPanel.setWidgetTopHeight(nlnlblVisiteursDepuis, 0.0, Unit.PX, 18.0, Unit.PX);
		
	}
}
