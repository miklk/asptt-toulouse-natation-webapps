package com.asptttoulousenatation.core.shared.user;

import net.customware.gwt.dispatch.shared.Action;

public class ChangePasswordAction implements Action<ChangePasswordResult> {

	private String userName;
	private String oldPassword;
	private String newPassword;
	
	public ChangePasswordAction() {
		
	}

	public ChangePasswordAction(String pUserName, String pOldPassword,
			String pNewPassword) {
		super();
		userName = pUserName;
		oldPassword = pOldPassword;
		newPassword = pNewPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String pUserName) {
		userName = pUserName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String pOldPassword) {
		oldPassword = pOldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String pNewPassword) {
		newPassword = pNewPassword;
	}
}