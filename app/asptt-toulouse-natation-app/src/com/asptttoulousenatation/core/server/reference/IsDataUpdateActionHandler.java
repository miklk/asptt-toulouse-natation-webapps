package com.asptttoulousenatation.core.server.reference;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.entity.field.DataUpdateEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.reference.DataUpdateEntity;
import com.asptttoulousenatation.core.server.dao.reference.DataUpdateDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.reference.IsDataUpdateAction;
import com.asptttoulousenatation.core.shared.reference.IsDataUpdateResult;

public class IsDataUpdateActionHandler implements
		ActionHandler<IsDataUpdateAction, IsDataUpdateResult> {

	private DataUpdateDao dao = new DataUpdateDao();

	public IsDataUpdateResult execute(IsDataUpdateAction pAction,
			ExecutionContext pResult) throws DispatchException {
		final boolean result;
		// Check if exists
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		lCriteria.add(new CriterionDao<String>(DataUpdateEntityFields.KIND,
				pAction.getKind().getSimpleName(), Operator.EQUAL));
		List<DataUpdateEntity> lDataUpdateEntities = dao.find(lCriteria);
		if (CollectionUtils.isNotEmpty(lDataUpdateEntities)) {
			DataUpdateEntity lDataUpdateEntity = lDataUpdateEntities.get(0);
			result = lDataUpdateEntity.getUpdated();
		} else {
			result = true;
		}
		return new IsDataUpdateResult(result);
	}

	public Class<IsDataUpdateAction> getActionType() {
		return IsDataUpdateAction.class;
	}

	public void rollback(IsDataUpdateAction pAction,
			IsDataUpdateResult pResult, ExecutionContext pContext)
			throws DispatchException {

	}
}