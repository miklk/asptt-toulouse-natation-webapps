package com.asptttoulousenatation.server.userspace.admin.actu;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.actu.GetAllActuAction;
import com.asptttoulousenatation.core.shared.actu.GetAllActuResult;
import com.asptttoulousenatation.server.userspace.admin.entity.ActuTransformer;

public class GetAllActuActionHandler implements
		ActionHandler<GetAllActuAction, GetAllActuResult> {

	private ActuTransformer transformer = new ActuTransformer();
	
	public GetAllActuResult execute(GetAllActuAction pAction,
			ExecutionContext pContext) throws DispatchException {
		ActuDao lActuDao = new ActuDao();
		List<ActuEntity> lEntities = lActuDao.getAll();
		List<ActuUi> lResult = new ArrayList<ActuUi>(transformer.toUi(lEntities));
		return new GetAllActuResult(lResult);
	}

	public Class<GetAllActuAction> getActionType() {
		return GetAllActuAction.class;
	}

	public void rollback(GetAllActuAction pArg0, GetAllActuResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
		// TODO Auto-generated method stub

	}

}
