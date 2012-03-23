package com.asptttoulousenatation.core.shared;

import net.customware.gwt.dispatch.shared.Action;

public abstract class AbstractDeleteAction<R extends AbstractDeleteResult> implements
		Action<R> {

	private Long id;
	
	public AbstractDeleteAction() {
		
	}
	
	public AbstractDeleteAction(Long pId) {
		id = pId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}
}