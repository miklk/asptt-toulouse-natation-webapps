package com.asptttoulousenatation.client.userspace.admin.user;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.asptttoulousenatation.client.userspace.admin.club.slot.SlotCell;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.google.gwt.event.dom.client.HasClickHandlers;
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
import com.google.gwt.user.client.ui.TextBox;
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
	private TextBox addressCode;
	private TextBox addressCity;

	private List<SlotUi> slotData;
	private CellList<SlotUi> slotCellList;
	private MultiSelectionModel<SlotUi> slotSelectionModel;

	public UserCreationViewImpl(List<SlotUi> pSlotData) {
		slotData = pSlotData;
		panel = new HorizontalPanel();
		initWidget(panel);

		FlexTable lPanel = new FlexTable();
		lPanel.setCellSpacing(6);

		int index = 0;
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lPanel.setHTML(index, 0, "Information du compte internet");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		index++;
		// Input
		// Email address
		emailAddress = new TextBox();
		lPanel.setHTML(1, 0, "E-mail");
		lPanel.setWidget(1, 1, emailAddress);
		index++;
		
		// Validated
		validated = new CheckBox();
		lPanel.setHTML(index, 0, "Compte validé ?");
		lPanel.setWidget(index, 1, validated);
		index++;

		// Profiles
		profiles = new ListBox(true);
		for (ProfileEnum lProfile : ProfileEnum.values()) {
			profiles.addItem(lProfile.toString());
		}
		lPanel.setHTML(index, 0, "Profiles");
		lPanel.setWidget(index, 1, profiles);
		index++;

		lPanel.setHTML(index, 0, "Information licencié");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		index++;
		
		// Last Name
		lastName = new TextBox();
		lPanel.setHTML(index, 0, "Nom");
		lPanel.setWidget(index, 1, lastName);
		index++;

		// First Name
		firstName = new TextBox();
		lPanel.setHTML(index, 0, "Prénom");
		lPanel.setWidget(index, 1, firstName);
		index++;

		// Birthday
		birthday = new DateBox();
		lPanel.setHTML(index, 0, "Date de naissance");
		lPanel.setWidget(index, 1, birthday);
		index++;

		// Phone number
		phonenumber = new TextBox();
		lPanel.setHTML(index, 0, "Téléphone");
		lPanel.setWidget(index, 1, phonenumber);
		index++;
		
		//Address
		lPanel.setHTML(index, 0, "Adresse");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		index++;
		
		//Address road
		addressRoad = new TextBox();
		lPanel.setHTML(index, 0, "Rue");
		lPanel.setWidget(index, 1, addressRoad);
		index++;
		
		//Address code postale
		addressCode = new TextBox();
		lPanel.setHTML(index, 0, "Code postale");
		lPanel.setWidget(index, 1, addressCode);
		index++;
		
		//Address city
		addressCity = new TextBox();
		lPanel.setHTML(index, 0, "Ville");
		lPanel.setWidget(index, 1, addressCity);
		index++;

		// Slots
		lPanel.setHTML(index, 0, "Créneaux d'entrainement");
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		index++;

		slotCellList = new CellList<SlotUi>(new SlotCell());
		slotCellList.setRowData(slotData);
		slotSelectionModel = new MultiSelectionModel<SlotUi>();
		slotCellList.setSelectionModel(slotSelectionModel);
		lPanel.setWidget(index, 1, slotCellList);
		index++;

		creationButton = new Button("Créer");
		creationButton.setWidth("50%");
		lPanel.setWidget(index, 0, creationButton);
		lCellFormatter.setColSpan(index, 0, 2);
		lCellFormatter.setHorizontalAlignment(index, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		index++;

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
}