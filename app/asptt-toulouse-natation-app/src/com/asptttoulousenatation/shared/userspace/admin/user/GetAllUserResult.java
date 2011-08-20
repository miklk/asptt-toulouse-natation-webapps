package com.asptttoulousenatation.shared.userspace.admin.user;

import java.util.List;

import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.core.shared.user.UserUi;

import net.customware.gwt.dispatch.shared.Result;

public class GetAllUserResult implements Result {

	private List<UserUi> users;
	private List<SlotUi> slots;
	
	public GetAllUserResult() {
		
	}

	public GetAllUserResult(List<UserUi> pUsers, List<SlotUi> pSlots) {
		super();
		users = pUsers;
		slots = pSlots;
	}

	public List<UserUi> getUsers() {
		return users;
	}

	public void setUsers(List<UserUi> pUsers) {
		users = pUsers;
	}

	public List<SlotUi> getSlots() {
		return slots;
	}

	public void setSlots(List<SlotUi> pSlots) {
		slots = pSlots;
	}
}