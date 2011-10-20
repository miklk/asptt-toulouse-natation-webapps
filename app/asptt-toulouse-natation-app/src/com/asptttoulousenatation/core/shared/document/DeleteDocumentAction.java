package com.asptttoulousenatation.core.shared.document;

import net.customware.gwt.dispatch.shared.Action;

public class DeleteDocumentAction implements Action<DeleteDocumentResult> {

	private Long id;
	
	public DeleteDocumentAction() {
		
	}

	public DeleteDocumentAction(Long pId) {
		super();
		id = pId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}
}