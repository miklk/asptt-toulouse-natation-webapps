package com.asptttoulousenatation.core.server.swimmer;

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
import com.asptttoulousenatation.core.shared.swimmer.GetSwimmerAction;
import com.asptttoulousenatation.core.shared.swimmer.GetSwimmerResult;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerUi;

public class GetSwimmerActionHandler implements
		ActionHandler<GetSwimmerAction, GetSwimmerResult> {

	private SwimmerDao dao = new SwimmerDao();
	private UserDao userDao = new UserDao();
	private UserDataDao userDataDao = new UserDataDao();
	
	public GetSwimmerResult execute(GetSwimmerAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Get swimmer with stat
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(1);
		lCriteria.add(new CriterionDao<Object>(SwimmerEntityFields.STAT, Boolean.TRUE, Operator.EQUAL));
		List<SwimmerEntity> lEntities = dao.find(lCriteria);
		List<SwimmerUi> lResults = new ArrayList<SwimmerUi>(lEntities.size());
		for(SwimmerEntity lEntity: lEntities) {
			SwimmerUi lSwimmer = new SwimmerUi();
			lSwimmer.setId(lEntity.getId());
			//Get user data
			UserEntity userEntity = userDao.get(lEntity.getUser());
			UserDataEntity userDataEntity = userDataDao.get(userEntity.getUserData());
			lSwimmer.setName(userDataEntity.getLastName() + " " + userDataEntity.getFirstName());
			lResults.add(lSwimmer);
		}
		GetSwimmerResult lResult = new GetSwimmerResult();
		lResult.setResults(lResults);
		return lResult;
	}

	public Class<GetSwimmerAction> getActionType() {
		return GetSwimmerAction.class;
	}

	public void rollback(GetSwimmerAction pArg0, GetSwimmerResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
	}
}