package com.asptttoulousenatation.core.server.club.slot;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.shared.club.slot.CreateSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.CreateSlotResult;

public class CreateSlotActionHandler implements
		ActionHandler<CreateSlotAction, CreateSlotResult> {

	private SlotDao dao = new SlotDao();
	
	public CreateSlotResult execute(CreateSlotAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Build entity
		SlotEntity lEntity = new SlotEntity();
		lEntity.setDayOfWeek(pAction.getDayOfWeek());
		lEntity.setBegin(pAction.getBegin());
		lEntity.setEnd(pAction.getEnd());
		lEntity.setSwimmingPool(pAction.getSwimmingPool());
		lEntity.setGroup(pAction.getGroup());
		dao.save(lEntity);
		return new CreateSlotResult();
	}

	public Class<CreateSlotAction> getActionType() {
		return CreateSlotAction.class;
	}

	public void rollback(CreateSlotAction arg0, CreateSlotResult arg1,
			ExecutionContext arg2) throws DispatchException {
	}
}