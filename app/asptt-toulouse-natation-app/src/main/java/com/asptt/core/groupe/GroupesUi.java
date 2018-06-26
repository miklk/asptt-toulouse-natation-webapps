package com.asptt.core.groupe;

import java.io.Serializable;
import java.util.List;

public class GroupesUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2079285252797120329L;
	private List<GroupUi> groups;
	
	public GroupesUi() {
	}

	public List<GroupUi> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupUi> pGroups) {
		groups = pGroups;
	}
	
}
