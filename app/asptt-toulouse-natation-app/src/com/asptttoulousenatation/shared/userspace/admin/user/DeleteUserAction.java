package com.asptttoulousenatation.shared.userspace.admin.user;

import net.customware.gwt.dispatch.shared.Action;

public class DeleteUserAction implements Action<DeleteUserResult> {

	private Long id;
	
	public DeleteUserAction() {
	}

	public DeleteUserAction(Long pId) {
		id = pId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}
}