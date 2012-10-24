package com.asptttoulousenatation.server.userspace.admin.user;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.entity.field.SwimmerEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerDao;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.server.dao.user.UserDataDao;
import com.asptttoulousenatation.shared.userspace.admin.user.DeleteUserAction;
import com.asptttoulousenatation.shared.userspace.admin.user.DeleteUserResult;

public class DeleteUserActionHandler implements
		ActionHandler<DeleteUserAction, DeleteUserResult> {

	private UserDao userDao = new UserDao();
	private UserDataDao userDataDao = new UserDataDao();
	private SwimmerDao swimmerDao = new SwimmerDao();
	
	public DeleteUserResult execute(DeleteUserAction pAction,
			ExecutionContext pContext) throws DispatchException {
		UserEntity lUser = userDao.get(pAction.getId());
		UserDataEntity lUserData = userDataDao.get(lUser.getUserData());
		
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				2);
		lCriteria.add(new CriterionDao<Object>(SwimmerEntityFields.USER,
				lUser.getId(), Operator.EQUAL));
		List<SwimmerEntity> swimmerEntities = swimmerDao.find(lCriteria);
		for(SwimmerEntity lSwimmerEntity: swimmerEntities) {
			swimmerDao.delete(lSwimmerEntity);			
		}
		
		userDataDao.delete(lUserData);
		userDao.delete(lUser);
		return new DeleteUserResult();
	}

	public Class<DeleteUserAction> getActionType() {
		return DeleteUserAction.class;
	}

	public void rollback(DeleteUserAction pArg0, DeleteUserResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
		// TODO Auto-generated method stub

	}

}
