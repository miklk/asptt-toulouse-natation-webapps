package com.asptttoulousenatation.core.shared.swimmer;

import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtml;



public class SwimmerStatComputeUi implements ISwimmerStatUi {

	private String swimmer;
	private int distance;
	private int bodybuilding;
	private List<String> comment;
	
	public SwimmerStatComputeUi() {
		
	}

	public String getSwimmer() {
		return swimmer;
	}

	public void setSwimmer(String pSwimmer) {
		swimmer = pSwimmer;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int pDistance) {
		distance = pDistance;
	}

	public Long getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUser(Long pUser) {
		// TODO Auto-generated method stub
		
	}

	public int getBodybuilding() {
		return bodybuilding;
	}

	public void setBodybuilding(int pBodybuilding) {
		bodybuilding = pBodybuilding;
	}

	public List<String> getComment() {
		return comment;
	}

	public void setComment(List<String> pComment) {
		comment = pComment;
	}
	
}