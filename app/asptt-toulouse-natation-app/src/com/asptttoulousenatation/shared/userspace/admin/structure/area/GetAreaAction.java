package com.asptttoulousenatation.shared.userspace.admin.structure.area;

import net.customware.gwt.dispatch.shared.Action;

public class GetAreaAction implements Action<GetAreaResult> {

	private boolean onlyDisplay;
	
	public GetAreaAction() {
		this(false);
	}

	public GetAreaAction(boolean pOnlyDisplay) {
		super();
		onlyDisplay = pOnlyDisplay;
	}

	public boolean isOnlyDisplay() {
		return onlyDisplay;
	}

	public void setOnlyDisplay(boolean pOnlyDisplay) {
		onlyDisplay = pOnlyDisplay;
	}
	
}