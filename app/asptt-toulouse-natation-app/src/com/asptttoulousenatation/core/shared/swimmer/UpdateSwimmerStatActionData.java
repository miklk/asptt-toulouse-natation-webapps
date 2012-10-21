package com.asptttoulousenatation.core.shared.swimmer;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;


public class UpdateSwimmerStatActionData implements IsSerializable {

	private Long id;
	private Long user;
	private Date day;
	private DayTimeEnum dayTime;
	private int distance;
	private String comment;
	
	public UpdateSwimmerStatActionData() {
		
	}
	
	public UpdateSwimmerStatActionData(Long pId, Long pUser, Date pDay,
			DayTimeEnum pDayTime, int pDistance, String pComment) {
		id = pId;
		user = pUser;
		day = pDay;
		dayTime = pDayTime;
		distance = pDistance;
		comment = pComment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
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