package com.asptttoulousenatation.core.authorization;

import java.io.Serializable;

public class HasAccessResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean hasAccess;

	public boolean isHasAccess() {
		return hasAccess;
	}

	public void setHasAccess(boolean hasAccess) {
		this.hasAccess = hasAccess;
	}
}