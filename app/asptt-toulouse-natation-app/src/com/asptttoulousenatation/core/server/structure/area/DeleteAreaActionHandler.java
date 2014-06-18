package com.asptttoulousenatation.core.server.structure.area;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.AbstractDeleteActionHandler;
import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateAction;
import com.asptttoulousenatation.core.shared.structure.area.DeleteAreaAction;
import com.asptttoulousenatation.core.shared.structure.area.DeleteAreaResult;
import com.asptttoulousenatation.core.shared.structure.menu.DeleteMenuAction;

public class DeleteAreaActionHandler
		extends
		AbstractDeleteActionHandler<AreaEntity, DeleteAreaAction, DeleteAreaResult> {

	private MenuDao menuDao = new MenuDao();

	public Class<DeleteAreaAction> getActionType() {
		return DeleteAreaAction.class;
	}

	@Override
	protected void doBeforeDelete(DeleteAreaAction pAction, AreaEntity pEntity, ExecutionContext pContext)
			throws DispatchException {
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lAreaCriterion = new CriterionDao<Long>();
		lAreaCriterion.setEntityField(MenuEntityFields.AREA);
		lAreaCriterion.setOperator(Operator.EQUAL);
		lCriteria.add(lAreaCriterion);
		lAreaCriterion.setValue(pEntity.getId());
		List<MenuEntity> lMenuEntities = menuDao.find(lCriteria);
		for (MenuEntity lMenuEntity : lMenuEntities) {
			pContext.execute(new DeleteMenuAction(lMenuEntity.getId()));
		}
		pContext.execute(new SetDataUpdateAction(AreaEntity.class, true));
	}

	@Override
	protected DaoBase<AreaEntity> createDao() {
		return new AreaDao();
	}

	@Override
	protected DeleteAreaResult getResult() {
		return new DeleteAreaResult();
	}

	@Override
	protected void fillResult(DeleteAreaAction pAction,
			DeleteAreaResult pResult, ExecutionContext pContext)
			throws DispatchException {		
	}
}