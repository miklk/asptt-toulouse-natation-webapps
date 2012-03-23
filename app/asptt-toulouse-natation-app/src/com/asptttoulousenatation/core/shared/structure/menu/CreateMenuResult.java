package com.asptttoulousenatation.core.shared.structure.menu;

import net.customware.gwt.dispatch.shared.Result;

public class CreateMenuResult implements Result {

	private boolean exists;
	
	public CreateMenuResult() {
		exists = false;
	}

	public CreateMenuResult(boolean pExists) {
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