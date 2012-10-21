package com.asptttoulousenatation.core.server.club.group;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.group.GetAllGroupResult;
import com.asptttoulousenatation.core.shared.club.group.GetGroupSlotAction;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;

public class GetGroupSlotActionHandler implements
		ActionHandler<GetGroupSlotAction, GetAllGroupResult> {

	private GroupDao groupDao = new GroupDao();
	private GroupTransformer groupTransformer = new GroupTransformer();
	
	private SlotDao slotDao = new SlotDao();
	private SlotTransformer slotTransformer = new SlotTransformer();
	
	public GetAllGroupResult execute(GetGroupSlotAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Retrieve groups
		List<GroupEntity> lEntities = new ArrayList<GroupEntity>(groupDao.getAll());
		//Retrieve slots
		List<CriterionDao<? extends Object>> lSlotCriteria = new ArrayList<CriterionDao<? extends Object>>(1);
		CriterionDao<Long> lSlotCriterion = new CriterionDao<Long>(SlotEntityFields.GROUP, 0l, Operator.EQUAL);
		lSlotCriteria.add(lSlotCriterion);
		List<GroupUi> lUis = new ArrayList<GroupUi>(lEntities.size());
		for(GroupEntity lGroupEntity: lEntities) {
			GroupUi lGroupUi = groupTransformer.toUi(lGroupEntity);
			lSlotCriterion.setValue(lGroupEntity.getId());
			List<SlotEntity> lSlotEntity = slotDao.find(lSlotCriteria);
			if(lSlotEntity != null) {
				lGroupUi.addSlots(slotTransformer.toUi(lSlotEntity));
			}
			lUis.add(lGroupUi);
		}
		return new GetAllGroupResult(lUis);
	}
	public Class<GetGroupSlotAction> getActionType() {
		return GetGroupSlotAction.class;
	}
	public void rollback(GetGroupSlotAction pAction, GetAllGroupResult pResult,
			ExecutionContext pContext) throws DispatchException {
		
	}
	
}