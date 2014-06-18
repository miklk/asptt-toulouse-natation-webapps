package com.asptttoulousenatation.core.server.club.slot;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.AbstractDeleteActionHandler;
import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.shared.club.slot.DeleteSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.DeleteSlotResult;

public class DeleteSlotActionHandler extends
		AbstractDeleteActionHandler<SlotEntity, DeleteSlotAction, DeleteSlotResult> {

	public Class<DeleteSlotAction> getActionType() {
		return DeleteSlotAction.class;
	}

	@Override
	protected void doBeforeDelete(DeleteSlotAction pAction, SlotEntity pEntity, ExecutionContext pContext)
			throws DispatchException {
	}

	@Override
	protected DaoBase<SlotEntity> createDao() {
		return new SlotDao();
	}

	@Override
	protected DeleteSlotResult getResult() {
		return new DeleteSlotResult();
	}

	@Override
	protected void fillResult(DeleteSlotAction pAction,
			DeleteSlotResult pResult, ExecutionContext pContext)
			throws DispatchException {
		
	}
}