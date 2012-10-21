package com.asptttoulousenatation.client.subscription.ui;

import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.sun.corba.se.pept.transport.ContactInfo;

public class GroupSlotTreeViewModel implements TreeViewModel {
	
	private SingleSelectionModel<GroupUi> groupSelectionModel;
	private MultiSelectionModel<SlotUi> multiSelectionModel;
	private final DefaultSelectionEventManager<SlotUi> selectionManager =
	      DefaultSelectionEventManager.createCheckboxManager();
	private ListDataProvider<GroupUi> groupProvider;
	
	public GroupSlotTreeViewModel(SingleSelectionModel<GroupUi> pGroupSelectionModel, MultiSelectionModel<SlotUi> pSelectionModel, List<GroupUi> pList) {
		super();
		groupSelectionModel = pGroupSelectionModel;
		multiSelectionModel = pSelectionModel;
		groupProvider = new ListDataProvider<GroupUi>(pList);
	}

	public <T> NodeInfo<?> getNodeInfo(T pValue) {
		final NodeInfo<?> result;
		if(pValue == null) {//Its top
			result = new DefaultNodeInfo<GroupUi>(groupProvider, new GroupCell(), groupSelectionModel, null);
		} else if(pValue instanceof GroupUi) {
			GroupUi lGroup = (GroupUi) pValue;
			result = new DefaultNodeInfo<SlotUi>(new ListDataProvider<SlotUi>(lGroup.getSlots()), new SlotSelectionCell(getSlotCell()), multiSelectionModel, selectionManager, null);
		} else {
			throw new IllegalArgumentException("Error unsupported element");
		}
		return result;
	}

	public boolean isLeaf(Object pValue) {
		final boolean result;
		if(pValue instanceof GroupUi) {
			result = ((GroupUi) pValue).getSlots().isEmpty();
		} else {
			result = true;
		} 
		return result;
	}

	private List<HasCell<SlotUi, ?>> getSlotCell() {
		List<HasCell<SlotUi, ?>> lCells = new ArrayList<HasCell<SlotUi,?>>(2);
		lCells.add(new HasCell<SlotUi, Boolean>() {
			
			private CheckboxCell cell = new CheckboxCell(true, false);
			
			public Cell<Boolean> getCell() {
				return cell;
			}

			public FieldUpdater<SlotUi, Boolean> getFieldUpdater() {
				return null;
			}

			public Boolean getValue(SlotUi pObject) {
				return multiSelectionModel.isSelected(pObject);
			}
		});
		lCells.add(new HasCell<SlotUi, SlotUi>() {

			private SlotCell cell = new SlotCell();
			
			public Cell<SlotUi> getCell() {
				return cell;
			}

			public FieldUpdater<SlotUi, SlotUi> getFieldUpdater() {
				return null;
			}

			public SlotUi getValue(SlotUi pObject) {
				return pObject;
			}
		});
		return lCells;
	}
}
