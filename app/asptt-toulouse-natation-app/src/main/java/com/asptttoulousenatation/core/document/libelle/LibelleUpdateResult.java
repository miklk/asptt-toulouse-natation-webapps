package com.asptttoulousenatation.core.document.libelle;

import java.io.Serializable;

public class LibelleUpdateResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3774575135592528091L;
	private boolean success;
	private boolean exists;
	
	public LibelleUpdateResult() {
		success = false;
		exists = false;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean pSuccess) {
		success = pSuccess;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean pExists) {
		exists = pExists;
	}
}