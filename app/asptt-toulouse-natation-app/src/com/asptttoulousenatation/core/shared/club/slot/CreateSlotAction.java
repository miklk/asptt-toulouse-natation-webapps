package com.asptttoulousenatation.core.shared.club.slot;

import net.customware.gwt.dispatch.shared.Action;

public class CreateSlotAction implements Action<CreateSlotResult> {

	private String dayOfWeek;
	private int begin;
	private int end;
	private Long group;
	private String swimmingPool;
	
	public CreateSlotAction() {
	}

	public CreateSlotAction(String pDayOfWeek, int pBegin, int pEnd,
			Long pGroup, String pSwimmingPool) {
		super();
		dayOfWeek = pDayOfWeek;
		begin = pBegin;
		end = pEnd;
		group = pGroup;
		swimmingPool = pSwimmingPool;
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