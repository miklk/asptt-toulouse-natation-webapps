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
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateAction;
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateResult;

public class SetDataUpdateActionHandler implements
		ActionHandler<SetDataUpdateAction, SetDataUpdateResult> {

	private DataUpdateDao dao = new DataUpdateDao();
	
	public SetDataUpdateResult execute(SetDataUpdateAction pAction,
			ExecutionContext pContext) throws DispatchException {
		final DataUpdateEntity lDataUpdateEntity;
		//Check if exists
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		lCriteria.add(new CriterionDao<String>(DataUpdateEntityFields.KIND,
				pAction.getKind().getSimpleName(), Operator.EQUAL));
		List<DataUpdateEntity> lDataUpdateEntities = dao.find(lCriteria);
		if(CollectionUtils.isNotEmpty(lDataUpdateEntities)) {
			lDataUpdateEntity = lDataUpdateEntities.get(0);
		} else {
			lDataUpdateEntity = new DataUpdateEntity();
			lDataUpdateEntity.setKind(pAction.getKind().getSimpleName());
		}
		//Set update time (current time)
		lDataUpdateEntity.setUpdated(pAction.getUpdated());
		dao.save(lDataUpdateEntity);
		return new SetDataUpdateResult();
	}

	public Class<SetDataUpdateAction> getActionType() {
		return SetDataUpdateAction.class;
	}

	public void rollback(SetDataUpdateAction pAction,
			SetDataUpdateResult pResult, ExecutionContext pContext)
			throws DispatchException {
	}
}