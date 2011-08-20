package com.asptttoulousenatation.core.shared.club.slot;

import java.util.List;

import com.asptttoulousenatation.core.shared.club.group.GroupUi;

import net.customware.gwt.dispatch.shared.Result;

public class GetAllSlotResult implements Result {

	private List<GroupUi> groups;
	private List<SlotUi> slots;
	
	public GetAllSlotResult() {
		
	}

	public GetAllSlotResult(List<GroupUi> groups, List<SlotUi> slots) {
		super();
		this.groups = groups;
		this.slots = slots;
	}

	public List<GroupUi> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupUi> groups) {
		this.groups = groups;
	}

	public List<SlotUi> getSlots() {
		return slots;
	}

	public void setSlots(List<SlotUi> slots) {
		this.slots = slots;
	}
}