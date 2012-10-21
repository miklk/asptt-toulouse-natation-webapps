package com.asptttoulousenatation.client.userspace.admin.user;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.asptttoulousenatation.client.userspace.admin.club.slot.SlotCell;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.MultiSelectionModel;

public class UserCreationViewImpl extends Composite implements
		UserCreationView {

	private HorizontalPanel panel;

	private Button creationButton;

	private TextBox emailAddress;
	private CheckBox validated;
	private ListBox profiles;
	private TextBox lastName;
	private TextBox firstName;
	private DateBox birthday;
	private TextBox phonenumber;
	private TextBox addressRoad;
	private TextBox addressAdditional;
	private TextBox addressCode;
	private TextBox addressCity;
	
	private RadioButton genderFemale;
	private RadioButton genderMale;
	private TextBox measurementSwimsuit;
	private TextBox measurementTshirt;
	private TextBox measurementShort;
	

	private List<SlotUi> slotData;
	private CellList<SlotUi> slotCellList;
	private MultiSelectionModel<SlotUi> slotSelectionModel;
	private CheckBox swimmerStat;

	public UserCreationViewImpl(List<SlotUi> pSlotData) {
		slotData = pSlotData;
		panel = new HorizontalPanel();
		initWidget(panel);

		VerticalPanel lPanel = new VerticalPanel();
		HorizontalPanel lUserPanel = new HorizontalPanel();
		lPanel.add(lUserPanel);
		FlexTable lUserInternetPanel = new FlexTable();
		lUserInternetPanel.setCellSpacing(6);

		int index = 0;
		FlexCellFormatter lCellFormatter = lUserInternetPanel.getFlexCellFormatter();
		lUserInternetPanel.setHTML(index, 0, "Information du compte internet");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		index++;
		// Input
		// Email address
		emailAddress = new TextBox();
		lUserInternetPanel.setHTML(1, 0, "E-mail");
		lUserInternetPanel.setWidget(1, 1, emailAddress);
		index++;
		
		// Validated
		validated = new CheckBox();
		lUserInternetPanel.setHTML(index, 0, "Compte validé ?");
		lUserInternetPanel.setWidget(index, 1, validated);
		index++;

		// Profiles
		profiles = new ListBox(true);
		for (ProfileEnum lProfile : ProfileEnum.values()) {
			profiles.addItem(lProfile.toString());
		}
		lUserInternetPanel.setHTML(index, 0, "Profiles");
		lUserInternetPanel.setWidget(index, 1, profiles);
		index++;
		lUserPanel.add(lUserInternetPanel);
		
		//Info 
		FlexTable lUserInfoPanel = new FlexTable();
		lUserInfoPanel.setCellSpacing(6);

		index = 0;
		lCellFormatter = lUserInfoPanel.getFlexCellFormatter();
		
		lUserInfoPanel.setHTML(index, 0, "Information licencié");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		index++;
		
		// Last Name
		lastName = new TextBox();
		lUserInfoPanel.setHTML(index, 0, "Nom");
		lUserInfoPanel.setWidget(index, 1, lastName);
		index++;

		// First Name
		firstName = new TextBox();
		lUserInfoPanel.setHTML(index, 0, "Prénom");
		lUserInfoPanel.setWidget(index, 1, firstName);
		index++;

		// Birthday
		birthday = new DateBox();
		birthday.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
		lUserInfoPanel.setHTML(index, 0, "Date de naissance");
		lUserInfoPanel.setWidget(index, 1, birthday);
		index++;

		// Phone number
		phonenumber = new TextBox();
		lUserInfoPanel.setHTML(index, 0, "Téléphone");
		lUserInfoPanel.setWidget(index, 1, phonenumber);
		index++;
		
		//Address
		lUserInfoPanel.setHTML(index, 0, "Adresse");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		index++;
		
		//Address road
		addressRoad = new TextBox();
		lUserInfoPanel.setHTML(index, 0, "Rue");
		lUserInfoPanel.setWidget(index, 1, addressRoad);
		index++;
		
		//Address additional
		addressAdditional = new TextBox();
		lUserInfoPanel.setHTML(index, 0, "Complément");
		lUserInfoPanel.setWidget(index, 1, addressAdditional);
		index++;
		
		//Address code postale
		addressCode = new TextBox();
		lUserInfoPanel.setHTML(index, 0, "Code postale");
		lUserInfoPanel.setWidget(index, 1, addressCode);
		index++;
		
		//Address city
		addressCity = new TextBox();
		lUserInfoPanel.setHTML(index, 0, "Ville");
		lUserInfoPanel.setWidget(index, 1, addressCity);
		index++;
		lUserPanel.add(lUserInfoPanel);
		
		// Other
		FlexTable lUserOtherPanel = new FlexTable();
		lUserOtherPanel.setCellSpacing(6);

		index = 0;
		lCellFormatter = lUserOtherPanel.getFlexCellFormatter();
		lUserOtherPanel.setHTML(index, 0, "Autres informations");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		index++;
		
		//Gender
		lUserOtherPanel.setHTML(index, 0, "Sexe");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		index++;
		genderFemale = new RadioButton("gender");
		genderMale = new RadioButton("gender");
		Panel lGender = new HorizontalPanel();
		lGender.add(genderFemale);
		lGender.add(genderMale);
		lUserOtherPanel.setHTML(index, 0, "Féminin/Masculin");
		lUserOtherPanel.setWidget(index, 1, lGender);
		index++;
		
		//Measurement
		lUserOtherPanel.setHTML(index, 0, "Mensurations");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		index++;
		//Maillot de bain
		measurementSwimsuit = new TextBox();
		lUserOtherPanel.setHTML(index, 0, "Maillot de bain");
		lUserOtherPanel.setWidget(index, 1, measurementSwimsuit);
		index++;
		
		//T-shirt
		measurementTshirt = new TextBox();
		lUserOtherPanel.setHTML(index, 0, "T-shirt");
		lUserOtherPanel.setWidget(index, 1, measurementTshirt);
		index++;
		
		//Short
		measurementShort = new TextBox();
		lUserOtherPanel.setHTML(index, 0, "Short");
		lUserOtherPanel.setWidget(index, 1, measurementShort);
		index++;
		
		lUserPanel.add(lUserOtherPanel);

		// Slots
		FlexTable lUserSlotPanel = new FlexTable();
		lUserSlotPanel.setCellSpacing(6);

		index = 0;
		lCellFormatter = lUserSlotPanel.getFlexCellFormatter();
		lUserSlotPanel.setHTML(index, 0, "Créneaux d'entrainement");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		index++;

		slotCellList = new CellList<SlotUi>(new SlotCell());
		slotCellList.setRowData(slotData);
		slotSelectionModel = new MultiSelectionModel<SlotUi>();
		slotCellList.setSelectionModel(slotSelectionModel);
		lUserSlotPanel.setWidget(index, 1, slotCellList);
		index++;
		
		swimmerStat = new CheckBox();
		lUserSlotPanel.setHTML(index, 0, "Kilométrage ?");
		lUserSlotPanel.setWidget(index, 1, swimmerStat);
		index++;
		
		lUserPanel.add(lUserSlotPanel);

		creationButton = new Button("Créer");
		creationButton.setWidth("50%");
		lPanel.add(creationButton);
		lPanel.setCellHorizontalAlignment(creationButton, HasHorizontalAlignment.ALIGN_CENTER);
//		lCellFormatter.setColSpan(index, 0, 2);
//		lCellFormatter.setHorizontalAlignment(index, 0,
//				HasHorizontalAlignment.ALIGN_CENTER);
//		index++;

		panel.add(lPanel);
	}

	public HasValue<String> getEmailAddress() {
		return emailAddress;
	}

	public HasValue<Boolean> getValidated() {
		return validated;
	}

	public Set<String> getProfiles() {
		final Set<String> selected = new HashSet<String>(
				profiles.getItemCount());
		for (int i = 0; i < profiles.getItemCount(); i++) {
			if (profiles.isItemSelected(i)) {
				selected.add(profiles.getItemText(i));
			}
		}
		return selected;
	}

	public HasValue<String> getLastName() {
		return lastName;
	}

	public HasValue<String> getFirstName() {
		return firstName;
	}

	public HasClickHandlers getCreateButton() {
		return creationButton;
	}

	public HasValue<Date> getBirthday() {
		return birthday;
	}

	public HasValue<String> getPhonenumber() {
		return phonenumber;
	}
	
	public HasValue<String> getAddressRoad() {
		return addressRoad;
	}

	public HasValue<String> getAddressCode() {
		return addressCode;
	}

	public HasValue<String> getAddressCity() {
		return addressCity;
	}
	

	public Set<Long> getSlots() {
		final Set<Long> lSlots = new HashSet<Long>(slotSelectionModel.getSelectedSet().size());
		for(SlotUi lSlot: slotSelectionModel.getSelectedSet()) {
			lSlots.add(lSlot.getId());
		}
		return lSlots;
	}

	public HasValue<String> getAddressAdditional() {
		return addressAdditional;
	}

	public String getGender() {
		String gender = "";
		if(genderFemale.getValue()) {
			gender = "FEMALE";
		}
		if(genderMale.getValue()) {
			gender = "MALE";
		}
		return gender;
	}

	public HasValue<String> getMeasurementSwimsuit() {
		return measurementSwimsuit;
	}

	public HasValue<String> getMeasurementTshirt() {
		return measurementTshirt;
	}

	public HasValue<String> getMeasurementShort() {
		return measurementShort;
	}

	public HasValue<Boolean> getSwimmerStat() {
		return swimmerStat;
	}
}