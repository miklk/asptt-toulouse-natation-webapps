package com.asptttoulousenatation.core.server.club.group;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.shared.club.group.GetAllGroupAction;
import com.asptttoulousenatation.core.shared.club.group.GetAllGroupResult;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;

public class GetAllGroupActionHandler implements
		ActionHandler<GetAllGroupAction, GetAllGroupResult> {

	private GroupDao groupDao = new GroupDao();
	private GroupTransformer groupTransformer = new GroupTransformer();
	
	public GetAllGroupResult execute(GetAllGroupAction pAction,
			ExecutionContext pContext) throws DispatchException {
		List<GroupEntity> lEntities = new ArrayList<GroupEntity>(groupDao.getAll());
		List<GroupUi> lUis = new ArrayList<GroupUi>(groupTransformer.toUi(lEntities));
		return new GetAllGroupResult(lUis);
	}

	public Class<GetAllGroupAction> getActionType() {
		return GetAllGroupAction.class;
	}

	public void rollback(GetAllGroupAction pArg0, GetAllGroupResult pArg1,
			ExecutionContext pArg2) throws DispatchException {

	}

}
