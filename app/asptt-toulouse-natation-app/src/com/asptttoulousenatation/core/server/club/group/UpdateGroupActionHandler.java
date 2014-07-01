package com.asptttoulousenatation.core.server.club.group;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.shared.club.group.UpdateGroupAction;
import com.asptttoulousenatation.core.shared.club.group.UpdateGroupResult;

public class UpdateGroupActionHandler implements
		ActionHandler<UpdateGroupAction, UpdateGroupResult> {

	private GroupDao dao = new GroupDao();
	
	public UpdateGroupResult execute(UpdateGroupAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Load entity
		GroupEntity lEntity = dao.get(pAction.getId());
		lEntity.setTitle(pAction.getTitle());
		lEntity.setLicenceFfn(pAction.isLicenceFfn());
		lEntity.setInscription(pAction.isInscription());
		lEntity.setTarif(pAction.getTarif());
		lEntity.setTarif2(pAction.getTarif2());
		lEntity.setTarif3(pAction.getTarif3());
		lEntity.setTarif4(pAction.getTarif4());
		lEntity.setSeanceunique(pAction.isSeanceunique());
		dao.save(lEntity);
		return new UpdateGroupResult();
	}

	public Class<UpdateGroupAction> getActionType() {
		return UpdateGroupAction.class;
	}

	public void rollback(UpdateGroupAction arg0, UpdateGroupResult arg1,
			ExecutionContext arg2) throws DispatchException {
	}

}
