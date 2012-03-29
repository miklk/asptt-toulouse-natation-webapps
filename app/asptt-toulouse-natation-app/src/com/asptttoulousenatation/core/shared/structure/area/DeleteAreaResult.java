package com.asptttoulousenatation.core.shared.structure.area;

import com.asptttoulousenatation.core.shared.AbstractDeleteResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;

public class DeleteAreaResult extends AbstractDeleteResult {

	private AreaUi area;
	
	public DeleteAreaResult() {
		
	}

	public DeleteAreaResult(AreaUi pArea) {
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