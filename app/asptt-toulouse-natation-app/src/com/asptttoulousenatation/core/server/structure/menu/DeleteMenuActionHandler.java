package com.asptttoulousenatation.core.server.structure.menu;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.AbstractDeleteActionHandler;
import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.structure.menu.DeleteMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.DeleteMenuResult;

public class DeleteMenuActionHandler extends AbstractDeleteActionHandler<MenuEntity, DeleteMenuAction, DeleteMenuResult> {

	private ContentDao contentDao = new ContentDao();
	
	public Class<DeleteMenuAction> getActionType() {
		return DeleteMenuAction.class;
	}
	
	@Override
	protected DaoBase<MenuEntity> createDao() {
		return new MenuDao();
	}

	@Override
	protected DeleteMenuResult getResult() {
		return new DeleteMenuResult();
	}

	@Override
	protected void doBeforeDelete(MenuEntity pEntity, ExecutionContext pContext) throws DispatchException {
		List<CriterionDao<? extends Object>> lContentCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lContentCriterion = new CriterionDao<Long>();
		lContentCriterion.setEntityField(ContentEntityFields.MENU);
		lContentCriterion.setOperator(Operator.EQUAL);
		lContentCriterion.setValue(pEntity.getId().getId());
		lContentCriteria.add(lContentCriterion);
		List<ContentEntity> lContentEntities = contentDao.find(lContentCriteria);
		for(ContentEntity lContent: lContentEntities) {
			contentDao.delete(lContent);
		}
	}
}