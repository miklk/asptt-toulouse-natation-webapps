package com.asptttoulousenatation.core.shared.structure.menu;

import com.asptttoulousenatation.core.shared.AbstractDeleteAction;

public class DeleteMenuAction extends AbstractDeleteAction<DeleteMenuResult> {

	private Long areaId;
	public DeleteMenuAction() {
		
	}

	public DeleteMenuAction(Long pId) {
		super(pId);
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long pAreaId) {
		areaId = pAreaId;
	}
	
}