package com.asptttoulousenatation.core.server.structure.area;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateAction;
import com.asptttoulousenatation.core.shared.structure.area.UpdateAreaAction;
import com.asptttoulousenatation.core.shared.structure.area.UpdateAreaResult;

public class UpdateAreaActionHandler implements
		ActionHandler<UpdateAreaAction, UpdateAreaResult> {

	private AreaDao dao = new AreaDao();
	public UpdateAreaResult execute(UpdateAreaAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Retrieve area
		AreaEntity lEntity = dao.get(pAction.getId());
		lEntity.setTitle(pAction.getTitle());
		lEntity.setOrder(pAction.getOrder());
		dao.save(lEntity);
		pContext.execute(new SetDataUpdateAction(AreaEntity.class, true));
		return new UpdateAreaResult();
	}

	public Class<UpdateAreaAction> getActionType() {
		return UpdateAreaAction.class;
	}

	public void rollback(UpdateAreaAction pArg0, UpdateAreaResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
	}
}