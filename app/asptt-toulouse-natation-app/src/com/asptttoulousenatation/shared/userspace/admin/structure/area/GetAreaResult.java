package com.asptttoulousenatation.shared.userspace.admin.structure.area;

import java.util.Collections;
import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class GetAreaResult implements Result {

	private List<AreaUi> area;
	
	public GetAreaResult() {
		area = Collections.emptyList();
	}

	public GetAreaResult(List<AreaUi> pArea) {
		area = pArea;
	}

	public List<AreaUi> getArea() {
		return area;
	}

	public void setArea(List<AreaUi> pArea) {
		area = pArea;
	}
	
}
