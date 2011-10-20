package com.asptttoulousenatation.core.shared.competition;

import net.customware.gwt.dispatch.shared.Action;

public class OfficielDayAction implements Action<OfficielDayResult> {

	private Long user;
	private Long day;
	private boolean add;
	
	public OfficielDayAction() {
		
	}

	public OfficielDayAction(Long pUser, Long pDay, boolean pAdd) {
		super();
		user = pUser;
		day = pDay;
		add = pAdd;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long pUser) {
		user = pUser;
	}

	public Long getDay() {
		return day;
	}

	public void setDay(Long pDay) {
		day = pDay;
	}

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean pAdd) {
		add = pAdd;
	}
}