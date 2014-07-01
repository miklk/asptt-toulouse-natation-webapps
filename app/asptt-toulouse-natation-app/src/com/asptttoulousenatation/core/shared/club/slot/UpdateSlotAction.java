package com.asptttoulousenatation.core.shared.club.slot;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateSlotAction implements Action<UpdateSlotResult> {

	private Long id;
	private String dayOfWeek;
	private int begin;
	private int end;
	private Long group;
	private String swimmingPool;
	private String educateur;
	private String placeDisponible;
	private boolean second;
	
	public UpdateSlotAction() {
	}

	public UpdateSlotAction(Long pId, String pDayOfWeek, int pBegin, int pEnd,
			Long pGroup, String pSwimmingPool, String pEducateur, String pPlaceDisponible, boolean pSecond) {
		super();
		id = pId;
		dayOfWeek = pDayOfWeek;
		begin = pBegin;
		end = pEnd;
		group = pGroup;
		swimmingPool = pSwimmingPool;
		educateur = pEducateur;
		placeDisponible = pPlaceDisponible;
		second = pSecond;
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

	public String getEducateur() {
		return educateur;
	}

	public void setEducateur(String pEducateur) {
		educateur = pEducateur;
	}

	public String getPlaceDisponible() {
		return placeDisponible;
	}

	public void setPlaceDisponible(String pPlaceDisponible) {
		placeDisponible = pPlaceDisponible;
	}

	public boolean isSecond() {
		return second;
	}

	public void setSecond(boolean pSecond) {
		second = pSecond;
	}
	
}