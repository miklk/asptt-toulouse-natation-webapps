package com.asptttoulousenatation.server.userspace.admin.actu;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEntity;
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateAction;
import com.asptttoulousenatation.shared.userspace.admin.actu.PublishActionResult;
import com.asptttoulousenatation.shared.userspace.admin.actu.PublishActuAction;
import com.google.appengine.api.datastore.Text;

public class PublishActuActionHandler implements
		ActionHandler<PublishActuAction, PublishActionResult> {

	public PublishActionResult execute(PublishActuAction pAction,
			ExecutionContext pContext) throws DispatchException {
		ActuEntity lActuEntity = new ActuEntity(null, pAction.getTitle(), pAction.getSummary(), new Text(pAction.getContent()), pAction.getCreationDate());
		ActuDao lActuDao = new ActuDao();
		lActuDao.save(lActuEntity);
		pContext.execute(new SetDataUpdateAction(ActuEntity.class, true));
		return new PublishActionResult();
	}

	public Class<PublishActuAction> getActionType() {
		return PublishActuAction.class;
	}

	public void rollback(PublishActuAction pArg0, PublishActionResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
	}
}