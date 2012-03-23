package com.asptttoulousenatation.core.shared.structure.area;

import net.customware.gwt.dispatch.shared.Result;

public class CreateAreaResult implements Result {

	private boolean exists;
	
	public CreateAreaResult() {
		exists = false;
	}

	public CreateAreaResult(boolean pExists) {
		super();
		exists = pExists;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean pExists) {
		exists = pExists;
	}
}
