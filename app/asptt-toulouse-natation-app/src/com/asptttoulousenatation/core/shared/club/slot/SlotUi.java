package com.asptttoulousenatation.core.shared.club.slot;

import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.google.gwt.user.client.rpc.IsSerializable;

public class SlotUi implements IsSerializable {

	private Long id;
	private String dayOfWeek;
	private int begin;
	private String beginStr;
	private int end;
	private String endStr;
	private GroupUi group;
	private String swimmingPool;
	private String educateur;
	private int placeDisponible;
	private int placeRestante;
	private boolean second;

	public SlotUi() {

	}

	public SlotUi(Long pId, String pDayOfWeek, int pBegin, String pBeginStr,
			int pEnd, String pEndStr, GroupUi pGroup, String pSwimmingPool,
			String pEducateur, int pPlaceDisponible, int pPlaceRestante, boolean pSecond) {
		super();
		id = pId;
		dayOfWeek = pDayOfWeek;
		begin = pBegin;
		beginStr = pBeginStr;
		end = pEnd;
		endStr = pEndStr;
		group = pGroup;
		swimmingPool = pSwimmingPool;
		educateur = pEducateur;
		placeDisponible = pPlaceDisponible;
		placeRestante = pPlaceRestante;
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

	public String getEducateur() {
		return educateur;
	}

	public void setEducateur(String pEducateur) {
		educateur = pEducateur;
	}

	public String getBeginStr() {
		return beginStr;
	}

	public void setBeginStr(String pBeginStr) {
		beginStr = pBeginStr;
	}

	public String getEndStr() {
		return endStr;
	}

	public void setEndStr(String pEndStr) {
		endStr = pEndStr;
	}

	public int getPlaceDisponible() {
		return placeDisponible;
	}

	public void setPlaceDisponible(int pPlaceDisponible) {
		placeDisponible = pPlaceDisponible;
	}

	public int getPlaceRestante() {
		return placeRestante;
	}

	public void setPlaceRestante(int pPlaceRestante) {
		placeRestante = pPlaceRestante;
	}

	public boolean isSecond() {
		return second;
	}

	public void setSecond(boolean pSecond) {
		second = pSecond;
	}
	
}