package com.asptttoulousenatation.core.server.structure.area;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.entity.field.AreaEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateAction;
import com.asptttoulousenatation.core.shared.structure.area.CreateAreaAction;
import com.asptttoulousenatation.core.shared.structure.area.CreateAreaResult;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;

public class CreateAreaActionHandler implements
		ActionHandler<CreateAreaAction, CreateAreaResult> {

	private AreaDao dao = new AreaDao();
	
	public CreateAreaResult execute(CreateAreaAction pAction,
			ExecutionContext pContext) throws DispatchException {
		final CreateAreaResult result = new CreateAreaResult();
		//Test existence
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(1);
		CriterionDao<String> lCriterion = new CriterionDao<String>(AreaEntityFields.TITLE, pAction.getTitle(), Operator.EQUAL);
		lCriteria.add(lCriterion);
		List<AreaEntity> existenceTest = dao.find(lCriteria);
		if(CollectionUtils.isEmpty(existenceTest)) {//doesn't exist
			//Creation
			AreaEntity lArea = new AreaEntity(null, pAction.getTitle(), ProfileEnum.PUBLIC, pAction.getOrder());
			dao.save(lArea);
		} else {
			result.setExists(true);
		}
		pContext.execute(new SetDataUpdateAction(AreaEntity.class, true));
		return result;
	}

	public Class<CreateAreaAction> getActionType() {
		return CreateAreaAction.class;
	}

	public void rollback(CreateAreaAction pAction, CreateAreaResult pResult,
			ExecutionContext pContext) throws DispatchException {
	}
}