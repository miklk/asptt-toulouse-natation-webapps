package com.asptttoulousenatation.core.server.club.group;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.shared.club.group.CreateGroupAction;
import com.asptttoulousenatation.core.shared.club.group.CreateGroupResult;

public class CreateGroupActionHandler implements
		ActionHandler<CreateGroupAction, CreateGroupResult> {

	private GroupDao dao = new GroupDao();
	
	public CreateGroupResult execute(CreateGroupAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Build entity
		GroupEntity lEntity = new GroupEntity();
		lEntity.setTitle(pAction.getTitle());
		lEntity.setLicenceFfn(pAction.isLicenceFfn());
		dao.save(lEntity);
		return new CreateGroupResult();
	}

	public Class<CreateGroupAction> getActionType() {
		return CreateGroupAction.class;
	}

	public void rollback(CreateGroupAction arg0, CreateGroupResult arg1,
			ExecutionContext arg2) throws DispatchException {
	}

}
