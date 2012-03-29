package com.asptttoulousenatation.core.server.club.group;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.AbstractDeleteActionHandler;
import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.group.DeleteGroupAction;
import com.asptttoulousenatation.core.shared.club.group.DeleteGroupResult;

public class DeleteGroupActionHandler extends
		AbstractDeleteActionHandler<GroupEntity, DeleteGroupAction, DeleteGroupResult> {

	private SlotDao slotDao = new SlotDao();
	
	public Class<DeleteGroupAction> getActionType() {
		return DeleteGroupAction.class;
	}

	@Override
	protected void doBeforeDelete(GroupEntity pEntity, ExecutionContext pContext)
			throws DispatchException {
		//Retrieve slots
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lCriterion = new CriterionDao<Long>(SlotEntityFields.GROUP, pEntity.getId(), Operator.EQUAL);
		lCriteria.add(lCriterion);
		List<SlotEntity> lSlotEntities = slotDao.find(lCriteria);
		for(SlotEntity lSlotEntity: lSlotEntities) {
			lSlotEntity.setGroup(null);
			slotDao.save(lSlotEntity);
		}
		
	}

	@Override
	protected DaoBase<GroupEntity> createDao() {
		return new GroupDao();
	}

	@Override
	protected DeleteGroupResult getResult() {
		return new DeleteGroupResult();
	}

	@Override
	protected void fillResult(DeleteGroupAction pAction,
			DeleteGroupResult pResult, ExecutionContext pContext)
			throws DispatchException {
		
	}

}
