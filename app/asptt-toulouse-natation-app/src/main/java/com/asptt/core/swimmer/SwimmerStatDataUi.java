package com.asptt.core.swimmer;

import java.io.Serializable;

public class SwimmerStatDataUi implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private DayTimeEnum dayTime;
	private int distance;
	private String comment;
	private boolean presence;

	public SwimmerStatDataUi() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
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

	public boolean isPresence() {
		return presence;
	}

	public void setPresence(boolean pPresence) {
		presence = pPresence;
	}
	
}