package com.asptttoulousenatation.core.shared.club.group;

import net.customware.gwt.dispatch.shared.Action;

public class CreateGroupAction implements Action<CreateGroupResult> {

	private String title;
	private boolean licenceFfn;
	private boolean inscription;
	
	public CreateGroupAction() {
	}

	public CreateGroupAction(String pTitle, boolean pLicenceFfn, boolean pInscription) {
		super();
		title = pTitle;
		licenceFfn = pLicenceFfn;
		inscription = pInscription;
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

	public boolean isInscription() {
		return inscription;
	}

	public void setInscription(boolean pInscription) {
		inscription = pInscription;
	}
	
}