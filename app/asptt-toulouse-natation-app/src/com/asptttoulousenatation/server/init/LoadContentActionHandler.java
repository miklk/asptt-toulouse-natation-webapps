package com.asptttoulousenatation.server.init;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DocumentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.structure.LoadContentAction;
import com.asptttoulousenatation.core.shared.structure.LoadContentResult;
import com.asptttoulousenatation.server.userspace.admin.entity.DocumentTransformer;

public class LoadContentActionHandler implements
		ActionHandler<LoadContentAction, LoadContentResult> {

	private ContentDao contentDao = new ContentDao();
	private DocumentDao documentDao = new DocumentDao();
	private DocumentTransformer documentTransformer = new DocumentTransformer();
	
	public LoadContentResult execute(LoadContentAction pAction,
			ExecutionContext pContext) throws DispatchException {
		List<CriterionDao<? extends Object>> lContentCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lContentCriterion = new CriterionDao<Long>();
		lContentCriterion.setEntityField(ContentEntityFields.MENU);
		lContentCriterion.setOperator(Operator.EQUAL);
		lContentCriterion.setValue(pAction.getMenu());
		lContentCriteria.add(lContentCriterion);
		List<ContentEntity> lContentEntities = contentDao.find(lContentCriteria);
		
		final LoadContentResult lResult = new LoadContentResult();
		if(!lContentEntities.isEmpty()) {
			lResult.setData(lContentEntities.get(0).getData().getBytes());
		}
		
		//Get documents
		List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lDocumentCriterion = new CriterionDao<Long>(DocumentEntityFields.MENU, pAction.getMenu(), Operator.EQUAL);
		lDocumentCriteria.add(lDocumentCriterion);
		List<DocumentEntity> lDocumentEntities = documentDao.find(lDocumentCriteria);
		List<DocumentUi> lDocumentUis = documentTransformer.toUi(lDocumentEntities);
		lResult.setDocuments(lDocumentUis);
		return lResult;
	}

	public Class<LoadContentAction> getActionType() {
		return LoadContentAction.class;
	}

	public void rollback(LoadContentAction pArg0, LoadContentResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
	}

}
