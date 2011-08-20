package com.asptttoulousenatation.shared.userspace.admin.structure.content;

import net.customware.gwt.dispatch.shared.Action;

public class GetContentAction implements Action<GetContentResult> {

	private long areaId;
	
	public GetContentAction() {
		
	}

	public GetContentAction(long pAreaId) {
		areaId = pAreaId;
	}

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long pAreaId) {
		areaId = pAreaId;
	}
}