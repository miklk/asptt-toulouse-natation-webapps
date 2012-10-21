package com.asptttoulousenatation.core.server.swimmer;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerStatEntity;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerStatDao;
import com.asptttoulousenatation.core.shared.swimmer.UpdateSwimmerStatAction;
import com.asptttoulousenatation.core.shared.swimmer.UpdateSwimmerStatActionData;
import com.asptttoulousenatation.core.shared.swimmer.UpdateSwimmerStatResult;
import com.asptttoulousenatation.server.util.Utils;

public class UpdateSwimmerStatActionHandler implements
		ActionHandler<UpdateSwimmerStatAction, UpdateSwimmerStatResult> {

	private SwimmerStatDao dao = new SwimmerStatDao();

	public UpdateSwimmerStatResult execute(UpdateSwimmerStatAction pAction,
			ExecutionContext pContext) throws DispatchException {
		// Get entity
		for (UpdateSwimmerStatActionData lData : pAction.getData()) {
			final SwimmerStatEntity entity;
			if(lData.getId() == null) {
				entity = new SwimmerStatEntity();
			} else {
				entity = dao.get(lData.getId());
			}
			entity.setSwimmer(lData.getUser());
			entity.setDay(Utils.format(lData.getDay(), SwimmerStatEntity.DAY_FORMAT));
			entity.setDayTime(lData.getDayTime().name());
			entity.setDistance(lData.getDistance());
			entity.setComment(lData.getComment());
			dao.save(entity);
		}
		return new UpdateSwimmerStatResult();
	}

	public Class<UpdateSwimmerStatAction> getActionType() {
		return UpdateSwimmerStatAction.class;
	}

	public void rollback(UpdateSwimmerStatAction pAction,
			UpdateSwimmerStatResult pResult, ExecutionContext pContext)
			throws DispatchException {
	}
}