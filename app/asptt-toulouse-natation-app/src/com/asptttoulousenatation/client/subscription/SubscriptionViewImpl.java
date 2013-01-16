package com.asptttoulousenatation.client.subscription;

import gwtupload.client.SingleUploader;

import java.util.List;

import com.asptttoulousenatation.client.subscription.ui.GroupSlotTreeViewModel;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.core.shared.club.subscription.SubscriptionPriceUi;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class SubscriptionViewImpl extends Composite implements SubscriptionView {

	private List<GroupUi> groups;
	
	private VerticalPanel panel;
	
	private VerticalPanel userPanel;
	private VerticalPanel sportPanel;
	private VerticalPanel groupPanel;
	private VerticalPanel otherInfoPanel;
	private VerticalPanel licencePanel;
	private VerticalPanel paymentPanel;
	private VerticalPanel paymentProcessPanel;
	
	private RadioButton man;
	private RadioButton woman;
	private RadioButton girl;
	
	private TextBox lastName;
	private TextBox firstName;
	private DateBox birthday;
	private TextBox birthdayCity;
	private TextBox nationality;
	private TextBox phonenumber;
	private TextBox phonenumberFather;
	private TextBox phonenumberFatherPro;
	private TextBox phonenumberMother;
	private TextBox phonenumberMotherPro;
	
	private TextBox addressRoad;
	private TextBox addressAdditional;
	private TextBox addressZipCode;
	private TextBox addressCity;
	private TextBox measurementSwimsuit;
	private TextBox measurementTshirt;
	private TextBox measurementShort;
	private TextBox emailAddress;
	
	private CheckBox licenceFFN;
	private TextBox licenceFFNNumber;
	private CheckBox competition;
	private SingleUploader certificatMedical;
	
	private CellBrowser group;
	private SingleSelectionModel<GroupUi> groupSelectionModel;
	private MultiSelectionModel<SlotUi> slotMultiSelectionModel;
	
	private CheckBox functionPresident;
	private CheckBox functionSecretaire;
	private CheckBox functionTresorier;
	private CheckBox functionBureau;
	private CheckBox functionTechnique;
	private CheckBox functionOfficiel;
	private CheckBox functionCA;
	
	private RadioButton situationScolaire;
	private RadioButton situationEtudiant;
	private RadioButton situationSalarie;
	private RadioButton situationRetraite;
	private RadioButton situationDemandeur;
	
	private TextBox profession;
	
	private TextBox soussigne;
	private CheckBox assurance;
	private CheckBox droitImage1;
	private CheckBox droitImage2;
	private CheckBox droitImage3;
	
	private TextBox mineurSoussigne;
	private TextBox mineur;
	private TextBox contactLastName1;
	private TextBox contactFirstName1;
	private TextBox contactPhoneNumber1;
	private TextBox contactLastName2;
	private TextBox contactFirstName2;
	private TextBox contactPhoneNumber2;
	
	
	private RadioButton licenceLoisir;
	private RadioButton licenceAdhesion;
	private CheckBox justificatif;
	
	private ListBox cardType;
	private TextBox cardNumber;
	private TextBox cardExpDate;
	private TextBox cardCCV;
	private TextBox ownerLastName;
	private TextBox ownerFirstName;
	
	private Button paymentButton;
	private Button paymentProcessButton;
	
	private Button priceButton;
	private Label priceText;
	private Button otherPaymentButton;
	
	public SubscriptionViewImpl(List<GroupUi> pGroups) {
		panel = new VerticalPanel();
		Anchor anchor = new Anchor("Ouvrir le formulaire dans une nouvelle page");
		anchor.setHref(GWT.getHostPageBaseURL() + "#SubscriptionPlace:null");
		anchor.setTarget("_blank");
		panel.add(anchor);
		groups = pGroups;
		initWidget(panel);
		buildEtatCivil();
		paymentButton = new Button("Payer");
	}
	
	private void buildEtatCivil() {
		userPanel = new VerticalPanel();
		userPanel.add(new Label("Information du licencé"));
		CaptionPanel lPanel = new CaptionPanel("Etat civil");
		FlexTable lInnerPanel = new FlexTable();
		FlexCellFormatter lCellFormatter = lInnerPanel.getFlexCellFormatter();
		lPanel.add(lInnerPanel);
		userPanel.add(lPanel);
		int lRowIndex = 0;
		
		
		man = new RadioButton("etatcivil", "Mr");
		lInnerPanel.setWidget(lRowIndex, 0, man);
		woman = new RadioButton("etatcivil", "Mme");
		lInnerPanel.setWidget(lRowIndex, 1, woman);
		girl = new RadioButton("etatcivil", "Mlle");
		lInnerPanel.setWidget(lRowIndex, 2, girl);
		lRowIndex++;
		
		//Name
		lastName = new TextBox();
		lastName.setWidth("400px");
		lInnerPanel.setHTML(lRowIndex, 0, "Nom");
		lInnerPanel.setWidget(lRowIndex, 1, lastName);
		lCellFormatter.setColSpan(lRowIndex, 1, 2);
		lRowIndex++;
		
		//First name
		firstName = new TextBox();
		firstName.setWidth("400px");
		lInnerPanel.setHTML(lRowIndex, 0, "Prénom");
		lInnerPanel.setWidget(lRowIndex, 1, firstName);
		lCellFormatter.setColSpan(lRowIndex, 1, 2);
		lRowIndex++;
		
		//Birthday
		birthday = new DateBox();
		birthday.setFormat(new DateBox.DefaultFormat 
				(DateTimeFormat.getFormat("dd/MM/yyyy")));
		birthday.setWidth("80px");
		lInnerPanel.setHTML(lRowIndex, 0, "Date de naissance");
		lInnerPanel.setWidget(lRowIndex, 1, birthday);
		birthdayCity = new TextBox();
		birthdayCity.setWidth("300px");
		lInnerPanel.setHTML(lRowIndex, 2, "Lieu de naissance");
		lInnerPanel.setWidget(lRowIndex, 3, birthdayCity);
		lCellFormatter.setColSpan(lRowIndex, 3, 2);
		lRowIndex++;
		nationality = new TextBox();
		nationality.setWidth("300px");
		lInnerPanel.setHTML(lRowIndex, 2, "Nationalité");
		lInnerPanel.setWidget(lRowIndex, 3, nationality);
		lRowIndex++;
		
		//Address
		lPanel = new CaptionPanel("Adresse");
		lInnerPanel = new FlexTable();
		lPanel.add(lInnerPanel);
		userPanel.add(lPanel);
		lRowIndex = 0;
		
		//Address road
		addressRoad = new TextBox();
		lInnerPanel.setWidget(lRowIndex, 0, addressRoad);
		lRowIndex++;
		
		//Address additional
		addressAdditional = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Compléments d'adresse (appartement, batiment,...");
		lInnerPanel.setWidget(lRowIndex, 1, addressAdditional);
		lRowIndex++;
		
		//Address code postal
		addressZipCode = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Code postal");
		lInnerPanel.setWidget(lRowIndex, 1, addressZipCode);
		
		//Address city
		addressCity = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 2, "Ville");
		lInnerPanel.setWidget(lRowIndex, 3, addressCity);
		
		//Phone
		lPanel = new CaptionPanel("Contact");
		lInnerPanel = new FlexTable();
		lPanel.add(lInnerPanel);
		userPanel.add(lPanel);
		lRowIndex = 0;
		
		//Phone number
		phonenumber = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Numéro de téléphone principal");
		lInnerPanel.setWidget(lRowIndex, 1, phonenumber);
		lRowIndex++;
		
		//Phone number father
		phonenumberFather = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Numéro de téléphone du père");
		lInnerPanel.setWidget(lRowIndex, 1, phonenumberFather);
		phonenumberFatherPro = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 2, "Numéro de téléphone professionel du père");
		lInnerPanel.setWidget(lRowIndex, 3, phonenumberFatherPro);
		lRowIndex++;
		
		//Phone number mother
		phonenumberMother = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Numéro de téléphone de la mère");
		lInnerPanel.setWidget(lRowIndex, 1, phonenumberMother);
		phonenumberMotherPro = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 2, "Numéro de téléphone professionel de la mère");
		lInnerPanel.setWidget(lRowIndex, 3, phonenumberMotherPro);
		lRowIndex++;
		
		//Email
		emailAddress = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Adresse e-mail de contact");
		lInnerPanel.setWidget(lRowIndex, 1, emailAddress);
		
//		//Follow button
//		HorizontalPanel lButtonBar = new HorizontalPanel();
//		lButtonBar.setStyleName(CSS.subscriptionButtonBar());
//		Button lFollowButton = new Button("");
//		lFollowButton.setTitle("Suivant");
//		lFollowButton.setStyleName(CSS.subscriptionNextButton());
//		lFollowButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent pEvent) {
//				panel.remove(userPanel);
//				buildSportPanel();
//			}
//		});
//		lButtonBar.add(lFollowButton);
//		lButtonBar.setCellHorizontalAlignment(lFollowButton, HasHorizontalAlignment.ALIGN_RIGHT);
//		userPanel.add(lButtonBar);
		
		panel.add(userPanel);
		buildSportPanel();
	}
	
	private void buildSportPanel() {
		sportPanel = new VerticalPanel();
		
		sportPanel.add(new Label("Information sportive"));
		CaptionPanel lPanel = new CaptionPanel("Licence");
		final FlexTable lInnerPanel = new FlexTable();
		lPanel.add(lInnerPanel);
		sportPanel.add(lPanel);
		int lRowIndex = 0;
		
		//Licence
		licenceFFN = new CheckBox("licenceFFN");
		lInnerPanel.setHTML(lRowIndex, 0, "Licence fédérale FFN (Oui/Non)");
		lInnerPanel.setWidget(lRowIndex, 1, licenceFFN);
		final int lCurrentRowIndex = lRowIndex;
		licenceFFN.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> pEvent) {
				if(pEvent.getValue()) {
					licenceFFNNumber = new TextBox();
					lInnerPanel.setHTML(lCurrentRowIndex, 2, "Numéro de licence");
					lInnerPanel.setWidget(lCurrentRowIndex, 3, licenceFFNNumber);
				}
				
			}
		});
		lRowIndex++;
		
		//Competitions
		competition = new CheckBox("competition");
		lInnerPanel.setHTML(lRowIndex, 0, "Compétition (Oui/Non)");
		lInnerPanel.setWidget(lRowIndex, 1, competition);
		lRowIndex++;
		
		//Certificat medical
		certificatMedical = new SingleUploader();
		lInnerPanel.setHTML(lRowIndex, 0, "Certificat médical");
		lInnerPanel.setWidget(lRowIndex, 1, certificatMedical);
		lRowIndex++;
		
		//Mensuration
		lPanel = new CaptionPanel("Equipement - Mensuration");
		FlexTable lInnerPanel2 = new FlexTable();
		lPanel.add(lInnerPanel2);
		sportPanel.add(lPanel);
		lRowIndex = 0;
		
		//Maillot de bain
		measurementSwimsuit = new TextBox();
		lInnerPanel2.setHTML(lRowIndex, 0, "Maillot de bain");
		lInnerPanel2.setWidget(lRowIndex, 1, measurementSwimsuit);
		
		//T-shirt
		measurementTshirt = new TextBox();
		lInnerPanel2.setHTML(lRowIndex, 2, "T-shirt");
		lInnerPanel2.setWidget(lRowIndex, 3, measurementTshirt);
		
		//Short
		measurementShort = new TextBox();
		lInnerPanel2.setHTML(lRowIndex, 4, "Short");
		lInnerPanel2.setWidget(lRowIndex, 5, measurementShort);
		
		
		//Actions
//		HorizontalPanel lButtonBar = new HorizontalPanel();
//		lButtonBar.setStyleName(CSS.subscriptionButtonBar());
//		Button lPreviousButton = new Button("");
//		lPreviousButton.setStyleName(CSS.subscriptionPreviousButton());
//		lPreviousButton.setTitle("Précédent");
//		lPreviousButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent pEvent) {
//				panel.remove(sportPanel);
//				panel.setWidget(userPanel);
//			}
//		});
//		
//		lButtonBar.add(lPreviousButton);
//		lButtonBar.setCellHorizontalAlignment(lPreviousButton, HasHorizontalAlignment.ALIGN_LEFT);
//		Button lFollowButton = new Button("");
//		lFollowButton.setTitle("Suivant");
//		lFollowButton.setStyleName(CSS.subscriptionNextButton());
//		lFollowButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent pEvent) {
//				panel.remove(sportPanel);
//				buildGroupPanel();
//			}
//		});
//		lButtonBar.add(lFollowButton);
//		lButtonBar.setCellHorizontalAlignment(lFollowButton, HasHorizontalAlignment.ALIGN_RIGHT);
//		sportPanel.add(lButtonBar);
		panel.add(sportPanel);
		buildGroupPanel();
	}
	
	private void buildGroupPanel() {
		groupPanel = new VerticalPanel();
		
		groupPanel.add(new Label("Groupe - Créneaux"));
		CaptionPanel lPanel = new CaptionPanel("Groupe");
		final FlexTable lInnerPanel = new FlexTable();
		lPanel.add(lInnerPanel);
		groupPanel.add(lPanel);
		int lRowIndex = 0;
		
		//Group
		lInnerPanel.setHTML(lRowIndex, 0, "Sélectionner votre groupe d'appartement et les créneaux auxquelles vous souhaitez participer.");
		lRowIndex++;
		
		groupSelectionModel = new SingleSelectionModel<GroupUi>();
		slotMultiSelectionModel = new MultiSelectionModel<SlotUi>();
		group = new CellBrowser(new GroupSlotTreeViewModel(groupSelectionModel, slotMultiSelectionModel, groups), null);
		group.setWidth("900px");
		group.setHeight("300px");
		lInnerPanel.setWidget(lRowIndex, 0, group);
		
		//Fonction dans la section
		CaptionPanel lPanel2 = new CaptionPanel("Fonction dans la section");
		final FlexTable lInnerPanel2 = new FlexTable();
		lPanel2.add(lInnerPanel2);
		groupPanel.add(lPanel2);
		lRowIndex = 0;
		
		functionPresident = new CheckBox("Président(e)");
		lInnerPanel2.setWidget(lRowIndex, 0, functionPresident);
		functionSecretaire = new CheckBox("Secrétaire");
		lInnerPanel2.setWidget(lRowIndex, 1, functionSecretaire);
		functionTresorier = new CheckBox("Trésorier(e)");
		lInnerPanel2.setWidget(lRowIndex, 2, functionTresorier);
		functionBureau = new CheckBox("Membre Bureau Section");
		lInnerPanel2.setWidget(lRowIndex, 3, functionBureau);
		functionTechnique = new CheckBox("Cadre technique");
		lInnerPanel2.setWidget(lRowIndex, 4, functionTechnique);
		functionOfficiel = new CheckBox("Arbitre/Officiel");
		lInnerPanel2.setWidget(lRowIndex, 5, functionOfficiel);
		functionCA = new CheckBox("Membre Conseil Administration ASPTT");
		lInnerPanel2.setWidget(lRowIndex, 6, functionCA);
		
		//Actions
//		HorizontalPanel lButtonBar = new HorizontalPanel();
//		lButtonBar.setStyleName(CSS.subscriptionButtonBar());
//		Button lPreviousButton = new Button("");
//		lPreviousButton.setStyleName(CSS.subscriptionPreviousButton());
//		lPreviousButton.setTitle("Précédent");
//		lPreviousButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent pEvent) {
//				panel.remove(groupPanel);
//				panel.setWidget(sportPanel);
//			}
//		});
//		lButtonBar.add(lPreviousButton);
//		lButtonBar.setCellHorizontalAlignment(lPreviousButton, HasHorizontalAlignment.ALIGN_LEFT);
//		Button lFollowButton = new Button("");
//		lFollowButton.setTitle("Suivant");
//		lFollowButton.setStyleName(CSS.subscriptionNextButton());
//		lFollowButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent pEvent) {
//				panel.remove(groupPanel);
//				buildOtherInfo();
//				
//			}
//		});
//		lButtonBar.add(lFollowButton);
//		lButtonBar.setCellHorizontalAlignment(lFollowButton, HasHorizontalAlignment.ALIGN_RIGHT);
//		groupPanel.add(lButtonBar);
		panel.add(groupPanel);
		buildOtherInfo();
	}
	
	private void buildOtherInfo() {
		otherInfoPanel = new VerticalPanel();
		
		CaptionPanel lPanel = new CaptionPanel("Renseignements confidentiels et facultatifs");
		final FlexTable lInnerPanel = new FlexTable();
		lPanel.add(lInnerPanel);
		otherInfoPanel.add(lPanel);
		int lRowIndex = 0;
		
		//Situation
		situationScolaire = new RadioButton("situation", "Scolaire");
		lInnerPanel.setWidget(lRowIndex, 0, situationScolaire);
		situationEtudiant = new RadioButton("situation", "Etudiant");
		lInnerPanel.setWidget(lRowIndex, 1, situationEtudiant);
		situationSalarie = new RadioButton("situation", "Salarié");
		lInnerPanel.setWidget(lRowIndex, 2, situationSalarie);
		situationRetraite = new RadioButton("situation", "Retraité");
		lInnerPanel.setWidget(lRowIndex, 3, situationRetraite);
		situationDemandeur = new RadioButton("situation", "Demandeur d'emploi");
		lInnerPanel.setWidget(lRowIndex, 4, situationDemandeur);
		lRowIndex++;
		
		//Profession
		profession = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Profession");
		lInnerPanel.setWidget(lRowIndex, 1, profession);
		lRowIndex++;
		
		//Actions
//		HorizontalPanel lButtonBar = new HorizontalPanel();
//		lButtonBar.setStyleName(CSS.subscriptionButtonBar());
//		Button lPreviousButton = new Button("");
//		lPreviousButton.setStyleName(CSS.subscriptionPreviousButton());
//		lPreviousButton.setTitle("Précédent");
//		lPreviousButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent pEvent) {
//				panel.remove(otherInfoPanel);
//				panel.setWidget(groupPanel);
//			}
//		});
//		lButtonBar.add(lPreviousButton);
//		lButtonBar.setCellHorizontalAlignment(lPreviousButton, HasHorizontalAlignment.ALIGN_LEFT);
//		Button lFollowButton = new Button("");
//		lFollowButton.setTitle("Suivant");
//		lFollowButton.setStyleName(CSS.subscriptionNextButton());
//		lFollowButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent pEvent) {
//				panel.remove(otherInfoPanel);
//				buildLicencePanel();
//			}
//		});
//		lButtonBar.add(lFollowButton);
//		lButtonBar.setCellHorizontalAlignment(lFollowButton, HasHorizontalAlignment.ALIGN_RIGHT);
//		otherInfoPanel.add(lButtonBar);
		panel.add(otherInfoPanel);
		buildLicencePanel();
	}
	
	private void buildLicencePanel() {
		licencePanel = new VerticalPanel();
		
		CaptionPanel lPanel = new CaptionPanel("Accord de licence");
		final FlexTable lInnerPanel = new FlexTable();
		lPanel.add(lInnerPanel);
		licencePanel.add(lPanel);
		int lRowIndex = 0;
		
		//Soussigne
		soussigne = new TextBox();
		HorizontalPanel lSoussignePanel = new HorizontalPanel();
		lSoussignePanel.add(new Label("Je, soussigné NOM Prénom"));
		lSoussignePanel.add(soussigne);
		lSoussignePanel.add(new Label("reconnais avoir pris connaissance:"));
		lInnerPanel.setWidget(lRowIndex, 0, lSoussignePanel);
		lRowIndex++;
		
		HTML lAssuranceText = new HTML("Assurance");
		lInnerPanel.setWidget(lRowIndex, 0, lAssuranceText);
		lRowIndex++;
		assurance = new CheckBox("Je ne souhaite pas souscrire d'assurance Dommage Corporels (voir conséquences au renvoi (1) Assurance");
		lInnerPanel.setWidget(lRowIndex, 0, assurance);
		lRowIndex++;
		
		HTML lInformatiqueEtLiberteText = new HTML("Informatique et liberté");
		lInnerPanel.setWidget(lRowIndex, 0, lInformatiqueEtLiberteText);
		lRowIndex++;
		
		droitImage1 = new CheckBox("1");
		lInnerPanel.setWidget(lRowIndex, 0, droitImage1);
		lRowIndex++;
		droitImage2 = new CheckBox("2");
		lInnerPanel.setWidget(lRowIndex, 0, droitImage2);
		lRowIndex++;
		droitImage3 = new CheckBox("3");
		lInnerPanel.setWidget(lRowIndex, 0, droitImage3);
		lRowIndex++;
		
		//Mineurs
		CaptionPanel lPanel2 = new CaptionPanel("Autorisation parentale pour les mineurs");
		final FlexTable lInnerPanel2 = new FlexTable();
		lPanel2.add(lInnerPanel2);
		licencePanel.add(lPanel2);
		lRowIndex = 0;
		
		HorizontalPanel lMineurPanel = new HorizontalPanel();
		lMineurPanel.add(new Label("Je, soussigné NOM Prénom"));
		mineurSoussigne = new TextBox();
		lMineurPanel.add(mineurSoussigne);
		lMineurPanel.add(new Label("représentant légal de: "));
		mineur = new TextBox();
		lMineurPanel.add(mineur);
		lInnerPanel2.setWidget(lRowIndex, 0, lMineurPanel);
		
		
		//Contact
		CaptionPanel lPanel3 = new CaptionPanel("Personnes à contacter en cas d'accident");
		final FlexTable lInnerPanel3 = new FlexTable();
		lPanel3.add(lInnerPanel3);
		licencePanel.add(lPanel3);
		lRowIndex = 0;
		
		contactLastName1 = new TextBox();
		contactFirstName1 = new TextBox();
		contactPhoneNumber1 = new TextBox();
		lInnerPanel3.setHTML(lRowIndex, 0, "Nom");
		lInnerPanel3.setWidget(lRowIndex, 1, contactLastName1);
		lInnerPanel3.setHTML(lRowIndex, 2, "Prénom");
		lInnerPanel3.setWidget(lRowIndex, 3, contactFirstName1);
		lInnerPanel3.setHTML(lRowIndex, 4, "Téléphone");
		lInnerPanel3.setWidget(lRowIndex, 5, contactPhoneNumber1);
		lRowIndex++;
		
		contactLastName2 = new TextBox();
		contactFirstName2 = new TextBox();
		contactPhoneNumber2 = new TextBox();
		lInnerPanel3.setHTML(lRowIndex, 0, "Nom");
		lInnerPanel3.setWidget(lRowIndex, 1, contactLastName2);
		lInnerPanel3.setHTML(lRowIndex, 2, "Prénom");
		lInnerPanel3.setWidget(lRowIndex, 3, contactFirstName2);
		lInnerPanel3.setHTML(lRowIndex, 4, "Téléphone");
		lInnerPanel3.setWidget(lRowIndex, 5, contactPhoneNumber2);
		lRowIndex++;
		
		
		//Actions
//		HorizontalPanel lButtonBar = new HorizontalPanel();
//		lButtonBar.setStyleName(CSS.subscriptionButtonBar());
//		Button lPreviousButton = new Button("");
//		lPreviousButton.setStyleName(CSS.subscriptionPreviousButton());
//		lPreviousButton.setTitle("Précédent");
//		lPreviousButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent pEvent) {
//				panel.remove(licencePanel);
//				panel.setWidget(otherInfoPanel);
//			}
//		});
//		lButtonBar.add(lPreviousButton);
//		lButtonBar.setCellHorizontalAlignment(lPreviousButton, HasHorizontalAlignment.ALIGN_LEFT);
//		Button lFollowButton = new Button("");
//		lFollowButton.setTitle("Suivant");
//		lFollowButton.setStyleName(CSS.subscriptionNextButton());
//		lFollowButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent pEvent) {
//				panel.remove(licencePanel);
//				buildPaymentPanel();
//			}
//		});
//		lButtonBar.add(lFollowButton);
//		lButtonBar.setCellHorizontalAlignment(lFollowButton, HasHorizontalAlignment.ALIGN_RIGHT);
//		licencePanel.add(lButtonBar);
		panel.add(licencePanel);
		buildPaymentPanel();
	}
	
	private void buildPaymentPanel() {
		paymentPanel = new VerticalPanel();
		
		CaptionPanel lPanel = new CaptionPanel("Type de licences Fédération Sportive des ASPTT");
		final FlexTable lInnerPanel = new FlexTable();
		lPanel.add(lInnerPanel);
		paymentPanel.add(lPanel);
		int lRowIndex = 0;
		
		licenceLoisir = new RadioButton("typeLicence", "Licence Loisir: 8 euros");
		licenceAdhesion = new RadioButton("typeLicence", "Licence Adhésion: 4 euros");
		lInnerPanel.setHTML(lRowIndex, 0, "Licence SANS autre fédération");
		lInnerPanel.setWidget(lRowIndex, 1, licenceLoisir);
		lInnerPanel.setHTML(lRowIndex, 2, "Licence AVEC autre fédération");
		lInnerPanel.setWidget(lRowIndex, 3, licenceAdhesion);
		
		//Facture
		CaptionPanel lPanel2 = new CaptionPanel("");
		final FlexTable lInnerPanel2 = new FlexTable();
		lPanel2.add(lInnerPanel2);
		paymentPanel.add(lPanel2);
		lRowIndex = 0;
		
		HorizontalPanel lPricePanel = new HorizontalPanel();
		priceButton = new Button("Calculer mon tarif");
		lPricePanel.add(priceButton);
		priceText = new Label();
		lPricePanel.add(priceText);
		lInnerPanel2.setWidget(lRowIndex, 0, lPricePanel);
		lRowIndex++;
		
		justificatif = new CheckBox("Je souhaite avoir une facture de paiement:");
		lInnerPanel2.setWidget(lRowIndex, 0, justificatif);
		lRowIndex++;
		
		paymentProcessButton = new Button("Payer par CB");
		lInnerPanel2.setWidget(lRowIndex, 0, paymentProcessButton);
		
		otherPaymentButton = new Button("Autre moyen de paiement");
		lInnerPanel2.setWidget(lRowIndex, 1, otherPaymentButton);
		
		//Actions
//		HorizontalPanel lButtonBar = new HorizontalPanel();
//		lButtonBar.setStyleName(CSS.subscriptionButtonBar());
//		Button lPreviousButton = new Button("");
//		lPreviousButton.setStyleName(CSS.subscriptionPreviousButton());
//		lPreviousButton.setTitle("Précédent");
//		lPreviousButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent pEvent) {
//				panel.remove(paymentPanel);
//				panel.setWidget(licencePanel);
//			}
//		});
//		lButtonBar.add(lPreviousButton);
//		paymentPanel.add(lButtonBar);
		panel.add(paymentPanel);
	}
	
	private void buildPaymentProcessingPanel(SubscriptionPriceUi pPriceUi) {
		paymentProcessPanel = new VerticalPanel();
		
		CaptionPanel lPanel = new CaptionPanel("Paiement");
		final FlexTable lInnerPanel = new FlexTable();
		FlexCellFormatter lCellFormatter = lInnerPanel.getFlexCellFormatter();
		lPanel.add(lInnerPanel);
		paymentProcessPanel.add(lPanel);
		int lRowIndex = 0;
		
		Label priceLabel = new Label("La cotisation annuelle est de " + pPriceUi.getPrice() + " euros");
		lInnerPanel.setWidget(lRowIndex, 0, priceLabel);
		lCellFormatter.setColSpan(lRowIndex, 0, 3);
		lCellFormatter.setHorizontalAlignment(lRowIndex, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		lRowIndex++;
		
		cardType = new ListBox();
		cardType.addItem("Visa");
		cardType.addItem("MasterCard");
		cardType.addItem("AmericanExpress");
		lInnerPanel.setHTML(lRowIndex, 0, "Type de carte");
		lInnerPanel.setWidget(lRowIndex, 1, cardType);
		lRowIndex++;
		
		cardNumber = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Numéro de carte");
		lInnerPanel.setWidget(lRowIndex, 1, cardNumber);
		lRowIndex++;
		
		cardExpDate = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Date d'expiration");
		lInnerPanel.setWidget(lRowIndex, 1, cardExpDate);
		lRowIndex++;
		
		cardCCV = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Code CCV");
		lInnerPanel.setWidget(lRowIndex, 1, cardCCV);
		
		ownerLastName = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Nom du titulaire");
		lInnerPanel.setWidget(lRowIndex, 1, ownerLastName);
		lRowIndex++;
		
		ownerFirstName = new TextBox();
		lInnerPanel.setHTML(lRowIndex, 0, "Prénom du titulaire");
		lInnerPanel.setWidget(lRowIndex, 1, ownerFirstName);
		lRowIndex++;
		
		lInnerPanel.setWidget(lRowIndex, 0, paymentButton);
		lCellFormatter.setColSpan(lRowIndex, 0, 3);
		lCellFormatter.setHorizontalAlignment(lRowIndex, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	public void setGroupData(List<GroupUi> pGroups) {
		groups.addAll(pGroups);
	}

	public HasClickHandlers getPaymentButton() {
		return paymentButton;
	}

	public HasValue<String> getAddressRoad() {
		return addressRoad;
	}

	public HasValue<String> getAddressZipCode() {
		return addressZipCode;
	}

	public HasValue<String> getAddressCity() {
		return addressCity;
	}

	public String getCardType() {
		return cardType.getItemText(cardType.getSelectedIndex());
	}

	public HasValue<String> getCardNumber() {
		return cardNumber;
	}

	public HasValue<String> getCardExpDate() {
		return cardExpDate;
	}

	public HasValue<String> getCardCCV() {
		return cardCCV;
	}

	public HasValue<String> getCardOwnerLastName() {
		return ownerLastName;
	}

	public HasValue<String> getCardOwnerFirstName() {
		return ownerFirstName;
	}

	public HasClickHandlers getPriceButton() {
		return priceButton;
	}

	public HasClickHandlers getOtherPaymentButton() {
		return otherPaymentButton;
	}

	public void setPrice(SubscriptionPriceUi pPriceUi) {
		priceText.setText("Votre tarif est de " + pPriceUi.getPrice() + " euros.");
	}

	public Long getGroup() {
		return groupSelectionModel.getSelectedObject().getId();
	}

	public int getNumberSubscribers() {
		return 1;
	}

	public void setPaymentInfo(SubscriptionPriceUi pPriceUi) {
		
				PopupPanel lPopup = new PopupPanel(true);
				buildPaymentProcessingPanel(pPriceUi);
				lPopup.setWidget(paymentProcessPanel);
				lPopup.center();
	}

	public HasClickHandlers getPaymentProcessButton() {
		return paymentProcessButton;
	}
}