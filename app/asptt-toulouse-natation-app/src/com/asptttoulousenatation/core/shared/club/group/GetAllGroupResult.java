package com.asptttoulousenatation.core.shared.club.group;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class GetAllGroupResult implements Result {

	private List<GroupUi> groups;
	
	public GetAllGroupResult() {
		
	}

	public GetAllGroupResult(List<GroupUi> pGroups) {
		super();
		groups = pGroups;
	}

	public List<GroupUi> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupUi> pGroups) {
		groups = pGroups;
	}
	
}
