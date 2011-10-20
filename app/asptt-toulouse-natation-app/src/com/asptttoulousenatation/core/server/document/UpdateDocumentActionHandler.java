package com.asptttoulousenatation.core.server.document;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.shared.document.UpdateDocumentAction;
import com.asptttoulousenatation.core.shared.document.UpdateDocumentResult;

public class UpdateDocumentActionHandler implements
		ActionHandler<UpdateDocumentAction, UpdateDocumentResult> {

	private DocumentDao dao = new DocumentDao();
	
	public UpdateDocumentResult execute(UpdateDocumentAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Retrieve document
		DocumentEntity document = dao.get(pAction.getId());
		//Fill entity
		document.setTitle(pAction.getTitle());
		document.setSummary(pAction.getSummary());
		//update entity
		dao.save(document);
		return new UpdateDocumentResult();
	}

	public Class<UpdateDocumentAction> getActionType() {
		return UpdateDocumentAction.class;
	}

	public void rollback(UpdateDocumentAction pAction,
			UpdateDocumentResult pResult, ExecutionContext pContext)
			throws DispatchException {
	}
}