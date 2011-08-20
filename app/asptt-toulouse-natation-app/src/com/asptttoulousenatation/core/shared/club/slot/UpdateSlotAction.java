package com.asptttoulousenatation.core.shared.club.slot;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateSlotAction implements Action<UpdateSlotResult> {

	private Long id;
	private String dayOfWeek;
	private int begin;
	private int end;
	private Long group;
	private String swimmingPool;
	
	public UpdateSlotAction() {
	}

	public UpdateSlotAction(Long pId, String pDayOfWeek, int pBegin, int pEnd,
			Long pGroup, String pSwimmingPool) {
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

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
	}

	public String getSwimmingPool() {
		return swimmingPool;
	}

	public void setSwimmingPool(String swimmingPool) {
		this.swimmingPool = swimmingPool;
	}
}