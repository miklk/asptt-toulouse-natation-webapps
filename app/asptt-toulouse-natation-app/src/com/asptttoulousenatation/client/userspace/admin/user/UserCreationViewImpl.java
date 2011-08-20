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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.MultiSelectionModel;

public class UserCreationViewImpl extends ResizeComposite implements
		UserCreationView {

	private LayoutPanel panel;

	private Button creationButton;

	private TextBox emailAddress;
	private CheckBox validated;
	private ListBox profiles;
	private TextBox lastName;
	private TextBox firstName;
	private DateBox birthday;
	private TextBox phonenumber;

	private List<SlotUi> slotData;
	private CellList<SlotUi> slotCellList;
	private MultiSelectionModel<SlotUi> slotSelectionModel;

	public UserCreationViewImpl(List<SlotUi> pSlotData) {
		slotData = pSlotData;
		panel = new LayoutPanel();
		initWidget(panel);

		FlexTable lPanel = new FlexTable();
		lPanel.setCellSpacing(6);

		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lPanel.setHTML(0, 0, "Information du compte internet");
		lCellFormatter.setColSpan(0, 0, 2);
		lCellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		// Input
		// Email address
		emailAddress = new TextBox();
		lPanel.setHTML(1, 0, "E-mail");
		lPanel.setWidget(1, 1, emailAddress);

		// Validated
		validated = new CheckBox();
		lPanel.setHTML(2, 0, "Compte validé ?");
		lPanel.setWidget(2, 1, validated);

		// Profiles
		profiles = new ListBox(true);
		for (ProfileEnum lProfile : ProfileEnum.values()) {
			profiles.addItem(lProfile.toString());
		}
		lPanel.setHTML(3, 0, "Profiles");
		lPanel.setWidget(3, 1, profiles);

		lPanel.setHTML(4, 0, "Information licencié");
		lCellFormatter.setColSpan(4, 0, 2);
		lCellFormatter.setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		// Last Name
		lastName = new TextBox();
		lPanel.setHTML(5, 0, "Nom");
		lPanel.setWidget(5, 1, lastName);

		// First Name
		firstName = new TextBox();
		lPanel.setHTML(6, 0, "Prénom");
		lPanel.setWidget(6, 1, firstName);

		// Birthday
		birthday = new DateBox();
		lPanel.setHTML(7, 0, "Date de naissance");
		lPanel.setWidget(7, 1, birthday);

		// Phone number
		phonenumber = new TextBox();
		lPanel.setHTML(8, 0, "Téléphone");
		lPanel.setWidget(8, 1, phonenumber);

		// Slots
		lPanel.setHTML(9, 0, "Créneaux d'entrainement");
		lCellFormatter.setColSpan(9, 0, 2);
		lCellFormatter.setHorizontalAlignment(9, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		slotCellList = new CellList<SlotUi>(new SlotCell());
		slotCellList.setRowData(slotData);
		slotSelectionModel = new MultiSelectionModel<SlotUi>();
		slotCellList.setSelectionModel(slotSelectionModel);
		lPanel.setWidget(10, 1, slotCellList);

		creationButton = new Button("Créer");
		creationButton.setWidth("50%");
		lPanel.setWidget(11, 0, creationButton);
		lCellFormatter.setColSpan(11, 0, 2);
		lCellFormatter.setHorizontalAlignment(11, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

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

	public Set<Long> getSlots() {
		final Set<Long> lSlots = new HashSet<Long>(slotSelectionModel.getSelectedSet().size());
		for(SlotUi lSlot: slotSelectionModel.getSelectedSet()) {
			lSlots.add(lSlot.getId());
		}
		return lSlots;
	}
}