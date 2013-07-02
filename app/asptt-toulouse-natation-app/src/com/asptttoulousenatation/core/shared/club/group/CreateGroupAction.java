package com.asptttoulousenatation.core.shared.club.group;

import net.customware.gwt.dispatch.shared.Action;

public class CreateGroupAction implements Action<CreateGroupResult> {

	private String title;
	private boolean licenceFfn;
	
	public CreateGroupAction() {
	}

	public CreateGroupAction(String pTitle, boolean pLicenceFfn) {
		super();
		title = pTitle;
		licenceFfn = pLicenceFfn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isLicenceFfn() {
		return licenceFfn;
	}

	public void setLicenceFfn(boolean pLicenceFfn) {
		licenceFfn = pLicenceFfn;
	}
	
}