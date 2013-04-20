package com.asptttoulousenatation.server.userspace.admin.actu;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DocumentEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.actu.GetAllActuAction;
import com.asptttoulousenatation.core.shared.actu.GetAllActuResult;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.server.userspace.admin.entity.ActuTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.DocumentTransformer;

public class GetAllActuActionHandler implements
		ActionHandler<GetAllActuAction, GetAllActuResult> {

	private DocumentDao documentDao = new DocumentDao();
	private DocumentTransformer documentTransformer = new DocumentTransformer();
	private ActuTransformer transformer = new ActuTransformer();

	public GetAllActuResult execute(GetAllActuAction pAction,
			ExecutionContext pContext) throws DispatchException {
		ActuDao lActuDao = new ActuDao();
		List<ActuEntity> lEntities = lActuDao.getAll(pAction.getLimitStart(), pAction.getLimitEnd());
		List<ActuUi> lResult = new ArrayList<ActuUi>(lEntities.size());
		for (ActuEntity entity : lEntities) {
			ActuUi lUi = transformer.toUi(entity);
			// Get documents
			List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			CriterionDao<Long> lDocumentCriterion = new CriterionDao<Long>();
			lDocumentCriterion.setEntityField(DocumentEntityFields.MENU);
			lDocumentCriterion.setOperator(Operator.EQUAL);
			lDocumentCriteria.add(lDocumentCriterion);
			lDocumentCriterion.setValue(entity.getId());
			List<DocumentEntity> lDocumentEntities = documentDao
					.find(lDocumentCriteria);
			List<DocumentUi> lDocumentUis = documentTransformer
					.toUi(lDocumentEntities);
			lUi.setDocumentSet(lDocumentUis);
			lResult.add(lUi);
		}
		return new GetAllActuResult(lResult, pAction.getLimitStart(), pAction.getLimitEnd());
	}

	public Class<GetAllActuAction> getActionType() {
		return GetAllActuAction.class;
	}

	public void rollback(GetAllActuAction pArg0, GetAllActuResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
		// TODO Auto-generated method stub

	}

}
