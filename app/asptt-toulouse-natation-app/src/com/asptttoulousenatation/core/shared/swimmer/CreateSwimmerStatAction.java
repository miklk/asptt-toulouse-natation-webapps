package com.asptttoulousenatation.core.shared.swimmer;

import java.util.Date;


import net.customware.gwt.dispatch.shared.Action;

public class CreateSwimmerStatAction implements Action<CreateSwimmerStatResult> {

	private Long user;
	private Date day;
	private DayTimeEnum dayTime;
	private int distance;
	private String comment;
	
	public CreateSwimmerStatAction() {
		
	}

	public CreateSwimmerStatAction(Long pUser, Date pDay,
			DayTimeEnum pDayTime, int pDistance, String pComment) {
		user = pUser;
		day = pDay;
		dayTime = pDayTime;
		distance = pDistance;
		comment = pComment;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long pUser) {
		user = pUser;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date pDay) {
		day = pDay;
	}

	public DayTimeEnum getDayTime() {
		return dayTime;
	}

	public void setDayTime(DayTimeEnum pDayTime) {
		dayTime = pDayTime;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int pDistance) {
		distance = pDistance;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String pComment) {
		comment = pComment;
	}
}