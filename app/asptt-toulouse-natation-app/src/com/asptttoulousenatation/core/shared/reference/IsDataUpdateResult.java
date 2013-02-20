package com.asptttoulousenatation.core.shared.reference;

import net.customware.gwt.dispatch.shared.Result;

public class IsDataUpdateResult implements Result {

	private boolean isDataUpdated;
	
	public IsDataUpdateResult() {
		
	}
	
	public IsDataUpdateResult(boolean pIsDataUpdated) {
		isDataUpdated = pIsDataUpdated;
	}

	public boolean isDataUpdated() {
		return isDataUpdated;
	}

	public void setDataUpdated(boolean pIsDataUpdated) {
		isDataUpdated = pIsDataUpdated;
	}
}