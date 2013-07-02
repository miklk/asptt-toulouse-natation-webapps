package com.asptttoulousenatation.core.shared.club.group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupUi implements IsSerializable {

	private Long id;
	private String title;
	private boolean licenceFfn;
	private List<SlotUi> slots;
	
	public GroupUi() {
		super();
		slots = new ArrayList<SlotUi>(0);
	}
	
	public GroupUi(Long pId, String pTitle, boolean pLicenceFfn) {
		super();
		id = pId;
		title = pTitle;
		licenceFfn = pLicenceFfn;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long pId) {
		id = pId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public List<SlotUi> getSlots() {
		return slots;
	}

	public void setSlots(List<SlotUi> pSlots) {
		slots = pSlots;
	}

	public void addSlot(SlotUi pSlot) {
		slots.add(pSlot);
	}
	
	public void addSlots(Collection<SlotUi> pSlots) {
		slots.addAll(pSlots);
	}

	public boolean isLicenceFfn() {
		return licenceFfn;
	}

	public void setLicenceFfn(boolean pLicenceFfn) {
		licenceFfn = pLicenceFfn;
	}
	
}