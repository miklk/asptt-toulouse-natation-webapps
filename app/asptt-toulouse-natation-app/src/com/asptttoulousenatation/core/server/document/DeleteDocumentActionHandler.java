package com.asptttoulousenatation.core.server.document;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.shared.document.DeleteDocumentAction;
import com.asptttoulousenatation.core.shared.document.DeleteDocumentResult;

public class DeleteDocumentActionHandler implements
		ActionHandler<DeleteDocumentAction, DeleteDocumentResult> {

	private DocumentDao dao = new DocumentDao();
	
	public DeleteDocumentResult execute(DeleteDocumentAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Retrieve entity
		DocumentEntity document = dao.get(pAction.getId());
		//Delete
		dao.delete(document);
		return new DeleteDocumentResult();
	}

	public Class<DeleteDocumentAction> getActionType() {
		return DeleteDocumentAction.class;
	}

	public void rollback(DeleteDocumentAction pAction,
			DeleteDocumentResult pResult, ExecutionContext pContext)
			throws DispatchException {
	}
}