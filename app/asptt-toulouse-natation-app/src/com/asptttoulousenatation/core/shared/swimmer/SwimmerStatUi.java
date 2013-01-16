package com.asptttoulousenatation.core.shared.swimmer;



public class SwimmerStatUi implements ISwimmerStatUi {

	private Long id;
	private Long user;
	private String swimmer;
	
	private SwimmerStatDataUi morning;
	private SwimmerStatDataUi midday;
	private SwimmerStatDataUi night;
	private SwimmerStatDataUi bodybuilding;
	
	private String comment;
	
	public SwimmerStatUi() {
		
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long pId) {
		id = pId;
	}

	public String getSwimmer() {
		return swimmer;
	}

	public void setSwimmer(String pSwimmer) {
		swimmer = pSwimmer;
	}
	

	public Long getUser() {
		return user;
	}


	public void setUser(Long pUser) {
		user = pUser;
	}


	public SwimmerStatDataUi getMorning() {
		return morning;
	}


	public void setMorning(SwimmerStatDataUi pMorning) {
		morning = pMorning;
	}


	public SwimmerStatDataUi getMidday() {
		return midday;
	}


	public void setMidday(SwimmerStatDataUi pMidday) {
		midday = pMidday;
	}


	public SwimmerStatDataUi getNight() {
		return night;
	}


	public void setNight(SwimmerStatDataUi pNight) {
		night = pNight;
	}


	public SwimmerStatDataUi getBodybuilding() {
		return bodybuilding;
	}


	public void setBodybuilding(SwimmerStatDataUi pBodybuilding) {
		bodybuilding = pBodybuilding;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String pComment) {
		comment = pComment;
	}
}