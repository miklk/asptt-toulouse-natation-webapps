package com.asptttoulousenatation.server.userspace.admin.actu;

import org.apache.commons.lang3.StringUtils;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateAction;
import com.asptttoulousenatation.shared.userspace.admin.actu.UpdateActuAction;
import com.asptttoulousenatation.shared.userspace.admin.actu.UpdateActuResult;
import com.google.appengine.api.datastore.Text;

public class UpdateActuActionHandler implements
		ActionHandler<UpdateActuAction, UpdateActuResult> {

	public UpdateActuResult execute(UpdateActuAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Load actu
		ActuDao lActuDao = new ActuDao();
		ActuEntity lActu = lActuDao.get(pAction.getId());
		lActu.setTitle(pAction.getTitle());
		lActu.setSummary(pAction.getSummary());
		lActu.setCreationDate(pAction.getCreationDate());
		lActu.setContent(new Text(pAction.getContent()));
		lActu.setImageUrl(StringUtils.defaultString(pAction.getImageUrl(), "img/actu_defaut.jpg"));
		lActu.setCompetition(pAction.isCompetition());
		lActuDao.save(lActu);
		pContext.execute(new SetDataUpdateAction(ActuEntity.class, true));
		return new UpdateActuResult();
	}

	public Class<UpdateActuAction> getActionType() {
		return UpdateActuAction.class;
	}

	public void rollback(UpdateActuAction pArg0, UpdateActuResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
		// TODO Auto-generated method stub

	}

}
