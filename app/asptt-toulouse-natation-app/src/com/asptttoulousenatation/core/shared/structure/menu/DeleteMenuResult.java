package com.asptttoulousenatation.core.shared.structure.menu;

import com.asptttoulousenatation.core.shared.AbstractDeleteResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;

public class DeleteMenuResult extends AbstractDeleteResult {

private AreaUi area;
	
	public DeleteMenuResult() {
		
	}

	public DeleteMenuResult(AreaUi pArea) {
		super();
		area = pArea;
	}

	public AreaUi getArea() {
		return area;
	}

	public void setArea(AreaUi pArea) {
		area = pArea;
	}
	
}
