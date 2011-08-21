package com.asptttoulousenatation.core.server.club.slot;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.shared.club.slot.UpdateSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.UpdateSlotResult;

public class UpdateSlotActionHandler implements
		ActionHandler<UpdateSlotAction, UpdateSlotResult> {

	private SlotDao dao = new SlotDao();
	
	public UpdateSlotResult execute(UpdateSlotAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Load entity
		SlotEntity lEntity = dao.get(pAction.getId());
		lEntity.setDayOfWeek(pAction.getDayOfWeek());
		lEntity.setBegin(pAction.getBegin());
		lEntity.setEnd(pAction.getEnd());
		lEntity.setSwimmingPool(pAction.getSwimmingPool());
		lEntity.setEducateur(pAction.getEducateur());
		lEntity.setGroup(pAction.getGroup());
		dao.save(lEntity);
		return new UpdateSlotResult();
	}

	public Class<UpdateSlotAction> getActionType() {
		return UpdateSlotAction.class;
	}

	public void rollback(UpdateSlotAction arg0, UpdateSlotResult arg1,
			ExecutionContext arg2) throws DispatchException {
	}
}