package com.asptttoulousenatation.core.server.swimmer;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerStatEntity;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerStatDao;
import com.asptttoulousenatation.core.shared.swimmer.CreateSwimmerStatAction;
import com.asptttoulousenatation.core.shared.swimmer.CreateSwimmerStatResult;
import com.asptttoulousenatation.server.util.Utils;

public class CreateSwimmerStatActionHandler implements
		ActionHandler<CreateSwimmerStatAction, CreateSwimmerStatResult> {

	private SwimmerStatDao dao = new SwimmerStatDao();
	
	public CreateSwimmerStatResult execute(CreateSwimmerStatAction pAction,
			ExecutionContext pContext) throws DispatchException {
		SwimmerStatEntity entity = new SwimmerStatEntity();
		entity.setSwimmer(pAction.getUser());
		entity.setDay(Utils.format(pAction.getDay(), SwimmerStatEntity.DAY_FORMAT));
		entity.setDayTime(pAction.getDayTime().name());
		entity.setDistance(pAction.getDistance());
		entity.setComment(pAction.getComment());
		dao.save(entity);
		return new CreateSwimmerStatResult();
	}

	public Class<CreateSwimmerStatAction> getActionType() {
		return CreateSwimmerStatAction.class;
	}

	public void rollback(CreateSwimmerStatAction pAction,
			CreateSwimmerStatResult pResult, ExecutionContext pContext)
			throws DispatchException {
	}
}