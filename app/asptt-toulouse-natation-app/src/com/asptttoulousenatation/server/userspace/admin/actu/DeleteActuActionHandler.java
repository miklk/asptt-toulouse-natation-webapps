package com.asptttoulousenatation.server.userspace.admin.actu;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.AbstractDeleteActionHandler;
import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.shared.actu.DeleteActuAction;
import com.asptttoulousenatation.core.shared.actu.DeleteActuResult;

public class DeleteActuActionHandler
		extends
		AbstractDeleteActionHandler<ActuEntity, DeleteActuAction, DeleteActuResult> {

	public Class<DeleteActuAction> getActionType() {
		return DeleteActuAction.class;
	}

	@Override
	protected void doBeforeDelete(ActuEntity pEntity, ExecutionContext pContext)
			throws DispatchException {
	}

	@Override
	protected DaoBase<ActuEntity> createDao() {
		return new ActuDao();

	}

	@Override
	protected DeleteActuResult getResult() {
		return new DeleteActuResult();
	}

}
