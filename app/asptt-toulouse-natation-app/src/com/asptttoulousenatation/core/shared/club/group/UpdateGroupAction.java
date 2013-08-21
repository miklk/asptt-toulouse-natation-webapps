package com.asptttoulousenatation.core.shared.club.group;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateGroupAction implements Action<UpdateGroupResult> {

	private Long id;
	private String title;
	private boolean licenceFfn;
	private boolean inscription;
	
	public UpdateGroupAction() {
	}
	

	public UpdateGroupAction(Long pId, String pTitle, boolean pLicenceFfn, boolean pInscription) {
		super();
		id = pId;
		title = pTitle;
		licenceFfn = pLicenceFfn;
		inscription = pInscription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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