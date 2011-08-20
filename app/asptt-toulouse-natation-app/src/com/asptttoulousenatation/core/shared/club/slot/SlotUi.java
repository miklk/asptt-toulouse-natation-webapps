package com.asptttoulousenatation.core.shared.club.slot;

import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.google.gwt.user.client.rpc.IsSerializable;

public class SlotUi implements IsSerializable {

private Long id;
	private String dayOfWeek;
	private int begin;
	private int end;
	private GroupUi group;
	private String swimmingPool;
	
	public SlotUi() {
		
	}
	
	public SlotUi(Long pId, String pDayOfWeek, int pBegin, int pEnd,
			GroupUi pGroup, String pSwimmingPool) {
		super();
		id = pId;
		dayOfWeek = pDayOfWeek;
		begin = pBegin;
		end = pEnd;
		group = pGroup;
		swimmingPool = pSwimmingPool;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public int getBegin() {
		return begin;
	}

	public void setBegin(int pBegin) {
		begin = pBegin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int pEnd) {
		end = pEnd;
	}

	public GroupUi getGroup() {
		return group;
	}

	public void setGroup(GroupUi group) {
		this.group = group;
	}

	public String getSwimmingPool() {
		return swimmingPool;
	}
	public void setSwimmingPool(String swimmingPool) {
		this.swimmingPool = swimmingPool;
	}
	
}
