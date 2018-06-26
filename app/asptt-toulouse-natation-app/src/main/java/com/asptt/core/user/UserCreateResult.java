package com.asptt.core.user;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserCreateResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6619823742864984152L;
	private boolean exists;
	private boolean success;
	
	public UserCreateResult() {
		exists = false;
		success = false;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean pExists) {
		exists = pExists;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean pSuccess) {
		success = pSuccess;
	}
}