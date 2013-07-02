package com.asptttoulousenatation.core.shared.club.group;

import net.customware.gwt.dispatch.shared.Action;

public class UpdateGroupAction implements Action<UpdateGroupResult> {

	private Long id;
	private String title;
	private boolean licenceFfn;
	
	public UpdateGroupAction() {
	}
	

	public UpdateGroupAction(Long pId, String pTitle, boolean pLicenceFfn) {
		super();
		id = pId;
		title = pTitle;
		licenceFfn = pLicenceFfn;
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
	
}