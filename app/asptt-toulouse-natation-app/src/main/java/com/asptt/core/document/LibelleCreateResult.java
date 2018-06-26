package com.asptt.core.document;

import java.io.Serializable;

public class LibelleCreateResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5737254549622203914L;
	private boolean exists;
	private boolean noTitle;
	
	public LibelleCreateResult() {
		exists = false;
		noTitle = false;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean pExists) {
		exists = pExists;
	}

	public boolean isNoTitle() {
		return noTitle;
	}

	public void setNoTitle(boolean pNoTitle) {
		noTitle = pNoTitle;
	}
	
}
