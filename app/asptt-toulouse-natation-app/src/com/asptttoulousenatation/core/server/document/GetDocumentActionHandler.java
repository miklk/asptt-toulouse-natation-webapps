package com.asptttoulousenatation.core.server.document;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DocumentEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.document.GetDocumentAction;
import com.asptttoulousenatation.core.shared.document.GetDocumentResult;
import com.asptttoulousenatation.server.userspace.admin.entity.DocumentTransformer;

public class GetDocumentActionHandler implements
		ActionHandler<GetDocumentAction, GetDocumentResult> {

	private DocumentDao dao = new DocumentDao();
	private DocumentTransformer transformer = new DocumentTransformer();
	
	public GetDocumentResult execute(GetDocumentAction pAction,
			ExecutionContext pContext) throws DispatchException {
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(1);
		CriterionDao<Long> lCriterion = new CriterionDao<Long>(DocumentEntityFields.MENU, pAction.getMenuId(), Operator.EQUAL);
		lCriteria.add(lCriterion);
		List<DocumentEntity> entities = dao.find(lCriteria);
		List<DocumentUi> lUis = transformer.toUi(entities);
		
		return new GetDocumentResult(lUis);
	}

	public Class<GetDocumentAction> getActionType() {
		return GetDocumentAction.class;
	}

	public void rollback(GetDocumentAction pAction, GetDocumentResult pResult,
			ExecutionContext pContext) throws DispatchException {
	}

}
