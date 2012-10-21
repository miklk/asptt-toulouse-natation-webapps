package com.asptttoulousenatation.shared.userspace.admin.structure.area;

import net.customware.gwt.dispatch.shared.Action;

public class GetAreaAction implements Action<GetAreaResult> {

	private boolean onlyDisplay;
	
	private boolean addMenuContent;
	
	public GetAreaAction() {
		onlyDisplay = false;
		addMenuContent = true;
	}

	public GetAreaAction(boolean pOnlyDisplay, boolean pAddMenuContent) {
		this();
		onlyDisplay = pOnlyDisplay;
		addMenuContent = pAddMenuContent;
	}
	
	public GetAreaAction(boolean pOnlyDisplay) {
		this(pOnlyDisplay, false);
	}

	public boolean isOnlyDisplay() {
		return onlyDisplay;
	}

	public void setOnlyDisplay(boolean pOnlyDisplay) {
		onlyDisplay = pOnlyDisplay;
	}

	public boolean isAddMenuContent() {
		return addMenuContent;
	}

	public void setAddMenuContent(boolean pAddMenuContent) {
		addMenuContent = pAddMenuContent;
	}
	
}