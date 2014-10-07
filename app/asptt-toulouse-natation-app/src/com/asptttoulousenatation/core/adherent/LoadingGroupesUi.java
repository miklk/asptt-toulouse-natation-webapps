package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.List;

import com.asptttoulousenatation.core.shared.club.group.GroupUi;

public class LoadingGroupesUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2079285252797120329L;
	private List<GroupUi> groups;
	
	public LoadingGroupesUi() {
	}

	public List<GroupUi> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupUi> pGroups) {
		groups = pGroups;
	}
	
}
