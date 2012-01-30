package com.asptttoulousenatation.client.userspace.admin.club.slot;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asptttoulousenatation.client.userspace.admin.util.CellListStyle;
import com.asptttoulousenatation.client.util.Utils;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.summatech.gwt.client.HourMinutePicker;
import com.summatech.gwt.client.HourMinutePicker.PickerFormat;

public class SlotViewImpl extends Composite implements SlotView {

	private static Map<String, Integer> dayOfWeekMap;
	
	private List<SlotUi> data;
	private List<GroupUi> groupData;
	private HorizontalPanel panel;
	
	private CellList<SlotUi> cellList;
	private SingleSelectionModel<SlotUi> selectionModel;
	private SimplePanel editionPanel;
	
	private Button createButton;
	private Button updateButton;
	private Button deleteButton;
	
	private ListBox dayOfWeek;
	private HourMinutePicker hourBegin;
	private HourMinutePicker hourEnd;
	private ListBox group;
	private TextBox swimmingPool;
	private TextBox educateur;
	
	static {
		dayOfWeekMap = new HashMap<String, Integer>(7);
		dayOfWeekMap.put("Lundi", 0);
		dayOfWeekMap.put("Mardi", 1);
		dayOfWeekMap.put("Mercredi", 2);
		dayOfWeekMap.put("Jeudi", 3);
		dayOfWeekMap.put("Vendredi", 4);
		dayOfWeekMap.put("Samedi", 5);
		dayOfWeekMap.put("Dimanche", 6);
	}
	
	public SlotViewImpl(List<SlotUi> pSlots, List<GroupUi> pGroups) {
		data = pSlots;
		groupData = pGroups;
		panel = new HorizontalPanel();
		initWidget(panel);
		
		cellList = new CellList<SlotUi>(new SlotCell(), new CellListStyle());
		cellList.setRowData(data);
		panel.add(cellList);
		selectionModel = new SingleSelectionModel<SlotUi>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			public void onSelectionChange(SelectionChangeEvent pEvent) {
				buildEditionPanel(selectionModel.getSelectedObject());
				
			}
		});
		editionPanel = new SimplePanel();
		editionPanel.setStyleName(CSS.userSpaceContentEdition());
		panel.add(editionPanel);
		
		createButton = new Button("");
		createButton.setStyleName(CSS.addButton());
		updateButton = new Button("");
		updateButton.setStyleName(CSS.editButton());
		deleteButton = new Button("");
		deleteButton.setStyleName(CSS.deleteButton());
		updateButton.setVisible(false);
		deleteButton.setVisible(false);
		
		buildCreationPanel();
	}
	
	private void buildEditionPanel(SlotUi pUi) {
		dayOfWeek.setSelectedIndex(dayOfWeekMap.get(pUi.getDayOfWeek()));
		int[] lTime = Utils.getTime(pUi.getBegin());
		hourBegin.setTime(null, lTime[0], lTime[1]);
		
		lTime = Utils.getTime(pUi.getEnd());
		hourEnd.setTime(null, lTime[0], lTime[1]);
		
		group = new ListBox();
		group.insertItem("Pas de groupe", "-1", 0);
		int index = 1;
		for(GroupUi lGroupUi: groupData) {
			group.insertItem(lGroupUi.getTitle(), Long.toString(lGroupUi.getId()), index);
			if(pUi.getGroup() != null && (lGroupUi.getId() == pUi.getGroup().getId())) {
				group.setSelectedIndex(index);
			}
			index++;
		}
		swimmingPool.setValue(pUi.getSwimmingPool());
		educateur.setValue(pUi.getEducateur());
		updateButton.setVisible(true);
		deleteButton.setVisible(true);
	}
	
	private void buildCreationPanel() {
		FlexTable lPanel = new FlexTable();
		lPanel.setCellSpacing(6);

		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lPanel.setHTML(0, 0, "Information du créneau");
		lCellFormatter.setColSpan(0, 0, 2);
		lCellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		//Input
		//Day of week
		dayOfWeek = new ListBox();
		for(Map.Entry<String, Integer> lDay: dayOfWeekMap.entrySet()) {
			dayOfWeek.insertItem(lDay.getKey(), lDay.getKey(), lDay.getValue());
		}
		lPanel.setHTML(1, 0, "Jour de la semaine");
		lPanel.setWidget(1, 1, dayOfWeek);
		
		//Hour range
		hourBegin = new HourMinutePicker(PickerFormat._24_HOUR);
		lPanel.setHTML(2, 0, "Plage horaire");
		lPanel.setWidget(2, 1, hourBegin);
		hourEnd = new HourMinutePicker(PickerFormat._24_HOUR);
		lPanel.setWidget(2, 2, hourEnd);
		
		//Group
		group = new ListBox();
		group.insertItem("Pas de groupe", "-1", 0);
		for(GroupUi lGroupUi: groupData) {
			group.addItem(lGroupUi.getTitle(), Long.toString(lGroupUi.getId()));
		}
		lPanel.setHTML(3, 0, "Groupe");
		lPanel.setWidget(3, 1, group);
		
		//Swimming pool
		swimmingPool = new TextBox();
		lPanel.setHTML(4, 0, "Piscine");
		lPanel.setWidget(4, 1, swimmingPool);
		
		//Educateur
		educateur = new TextBox();
		lPanel.setHTML(5, 0, "Educateur");
		lPanel.setWidget(5, 1, educateur);
		
		HorizontalPanel lButtonBar = new HorizontalPanel();
		lButtonBar.setStyleName(CSS.buttonBar());
		lButtonBar.add(updateButton);
		lButtonBar.add(deleteButton);
		lButtonBar.add(createButton);
		lPanel.setWidget(6, 0, lButtonBar);
		lCellFormatter.setColSpan(6, 0, 3);
		lCellFormatter.setHorizontalAlignment(6, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		editionPanel.clear();
		editionPanel.setWidget(lPanel);
	}
	
	public HasValue<String> getSwimmingPool() {
		return swimmingPool;
	}

	public String getDayOfWeek() {
		return dayOfWeek.getValue(dayOfWeek.getSelectedIndex());
	}

	public Long getGroup() {
		final Long result;
		if (group.getSelectedIndex() != -1) {
			result = Long.valueOf(group.getValue(group.getSelectedIndex()));
		} else {
			result = -1l;
		}
		return result;
	}

	public HasClickHandlers getCreateButton() {
		return createButton;
	}

	public HasClickHandlers getUpdateButton() {
		return updateButton;
	}

	public Long getSlot() {
		return selectionModel.getSelectedObject().getId();
	}

	public int getHourBegin() {
		return hourBegin.getMinutes();
	}

	public int getHourEnd() {
		return hourEnd.getMinutes();
	}

	public HasValue<String> getEducateur() {
		return educateur;
	}

	public HasClickHandlers getDeleteButton() {
		return deleteButton;
	}
}