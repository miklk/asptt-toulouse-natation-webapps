package com.asptttoulousenatation.client.userspace.admin.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asptttoulousenatation.client.userspace.admin.club.slot.SlotCell;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.dom.client.Style.Unit;
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
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class UserEditionViewImpl extends ResizeComposite implements
		UserEditionView {

	private List<UserUi> data;
	private LayoutPanel panel;
	
	private CellList<UserUi> cellList;
	private SingleSelectionModel<UserUi> selectionModel;
	private SimpleLayoutPanel editionPanel;
	
	private Button updateButton;
	
	private TextBox emailAddress;
	private CheckBox validated;
	private ListBox profiles;
	private Map<String, Integer> profileMap;
	private TextBox lastName;
	private TextBox firstName;
	private DateBox birthday;
	private TextBox phonenumber;

	private Map<Long, SlotUi> slotData;
	private CellList<SlotUi> slotCellList;
	private MultiSelectionModel<SlotUi> slotSelectionModel;
	
	public UserEditionViewImpl(List<UserUi> pData, List<SlotUi> pSlotData) {
		data = pData;
		slotData = new LinkedHashMap<Long, SlotUi>(pSlotData.size());
		for(SlotUi lSlot: pSlotData) {
			slotData.put(lSlot.getId(), lSlot);
		}
		panel = new LayoutPanel();
		initWidget(panel);
		
		cellList = new CellList<UserUi>(new UserCell());
		cellList.setRowData(data);
		panel.add(cellList);
		selectionModel = new SingleSelectionModel<UserUi>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			public void onSelectionChange(SelectionChangeEvent pEvent) {
				buildEditionPanel(selectionModel.getSelectedObject());
				
			}
		});
		
		editionPanel = new SimpleLayoutPanel();
		panel.add(editionPanel);

		buildPanel();
		
		//Layout
		panel.setWidgetLeftWidth(cellList, 0, Unit.PCT, 30, Unit.PCT);
		panel.setWidgetLeftWidth(editionPanel, 32, Unit.PCT, 100, Unit.PCT);
	}
	
	private void buildPanel() {
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
		profileMap = new HashMap<String, Integer>();
		int index = 0;
		for (ProfileEnum lProfile : ProfileEnum.values()) {
			profiles.insertItem(lProfile.toString(), lProfile.toString(), index);
			profileMap.put(lProfile.toString(), index);
			index++;
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
		slotCellList.setRowData(new ArrayList<SlotUi>(slotData.values()));
		slotSelectionModel = new MultiSelectionModel<SlotUi>();
		slotCellList.setSelectionModel(slotSelectionModel);
		lPanel.setWidget(10, 1, slotCellList);

		updateButton = new Button("Modifier");
		lPanel.setWidget(11, 0, updateButton);
		lCellFormatter.setColSpan(11, 0, 2);
		lCellFormatter.setHorizontalAlignment(11, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		editionPanel.clear();
		editionPanel.setWidget(lPanel);
	}

	private void buildEditionPanel(UserUi pUi) {
		emailAddress.setValue(pUi.getEmailAddress());
		validated.setValue(pUi.isValidated());
		for(String lProfile: pUi.getProfiles()) {
			profiles.setSelectedIndex(profileMap.get(lProfile));
		}
		lastName.setValue(pUi.getUserData().getLastName());
		firstName.setValue(pUi.getUserData().getFirstName());
		birthday.setValue(pUi.getUserData().getBirthday());
		phonenumber.setValue(pUi.getUserData().getPhonenumber());
		for(SlotUi lSlot: slotData.values()) {
			boolean lSelected = pUi.getSlots().contains(lSlot.getId());
			slotSelectionModel.setSelected(lSlot, lSelected);
		}
	}
	
	public Long getUser() {
		return selectionModel.getSelectedObject().getId();
	}

	public HasValue<String> getEmailAddress() {
		return emailAddress;
	}

	public HasValue<Boolean> getValidated() {
		return validated;
	}

	public Set<String> getProfiles() {
		final Set<String> selected = new HashSet<String>(profiles.getItemCount());
		for(int i= 0; i < profiles.getItemCount(); i++) {
			if(profiles.isItemSelected(i)) {
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

	public HasClickHandlers getUpdateButton() {
		return updateButton;
	}

	public Set<Long> getSlots() {
		Set<Long> lResult = new HashSet<Long>(slotSelectionModel.getSelectedSet().size());
		for(SlotUi lSlot: slotSelectionModel.getSelectedSet()) {
			lResult.add(lSlot.getId());
		}
		return lResult;
	}

	public HasValue<Date> getBirthday() {
		return birthday;
	}

	public HasValue<String> getPhoneNumber() {
		return phonenumber;
	}
}