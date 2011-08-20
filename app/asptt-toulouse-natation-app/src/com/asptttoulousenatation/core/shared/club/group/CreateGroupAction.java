package com.asptttoulousenatation.core.shared.club.group;

import net.customware.gwt.dispatch.shared.Action;

public class CreateGroupAction implements Action<CreateGroupResult> {

	private String title;
	
	public CreateGroupAction() {
	}

	public CreateGroupAction(String title) {
		super();
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}