package com.asptttoulousenatation.core.shared.swimmer;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SwimmerStatDataUi implements IsSerializable {
	private Long id;
	private int distance;
	private String comment;

	public SwimmerStatDataUi() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
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