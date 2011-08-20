package com.asptttoulousenatation.core.shared.club.group;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupUi implements IsSerializable {

	private Long id;
	private String title;
	
	public GroupUi() {
		
	}
	
	public GroupUi(Long pId, String pTitle) {
		super();
		id = pId;
		title = pTitle;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long pId) {
		id = pId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String pTitle) {
		title = pTitle;
	}
}