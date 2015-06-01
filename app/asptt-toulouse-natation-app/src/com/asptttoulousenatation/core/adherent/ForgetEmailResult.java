package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;

public class ForgetEmailResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean found;

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}
	
}
