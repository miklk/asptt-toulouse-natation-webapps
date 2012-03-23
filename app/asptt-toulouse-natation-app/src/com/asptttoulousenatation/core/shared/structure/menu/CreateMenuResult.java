package com.asptttoulousenatation.core.shared.structure.menu;

import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;

import net.customware.gwt.dispatch.shared.Result;

public class CreateMenuResult implements Result {

	private boolean exists;
	
	private AreaUi area;
	
	public CreateMenuResult() {
		exists = false;
	}

	public CreateMenuResult(boolean pExists, AreaUi pArea) {
		super();
		exists = pExists;
		area = pArea;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean pExists) {
		exists = pExists;
	}

	public AreaUi getArea() {
		return area;
	}

	public void setArea(AreaUi pArea) {
		area = pArea;
	}
	
}