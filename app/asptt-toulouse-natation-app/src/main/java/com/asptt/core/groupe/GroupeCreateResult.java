package com.asptt.core.groupe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupeCreateResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean creation;
	
	private GroupUi groupe;

	public boolean isCreation() {
		return creation;
	}

	public void setCreation(boolean creation) {
		this.creation = creation;
	}

	public GroupUi getGroupe() {
		return groupe;
	}

	public void setGroupe(GroupUi groupe) {
		this.groupe = groupe;
	}
	
}
