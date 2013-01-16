package com.asptttoulousenatation.server.userspace.admin.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.entity.field.SwimmerEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerDao;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.server.dao.user.UserDataDao;
import com.asptttoulousenatation.core.server.entity.UserDataTransformer;
import com.asptttoulousenatation.core.server.entity.UserTransformer;
import com.asptttoulousenatation.core.shared.club.slot.GetAllSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.GetAllSlotResult;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.server.userspace.admin.entity.SwimmerTransformer;
import com.asptttoulousenatation.shared.userspace.admin.user.GetAllUserAction;
import com.asptttoulousenatation.shared.userspace.admin.user.GetAllUserResult;

public class GetAllUserActionHandler implements
		ActionHandler<GetAllUserAction, GetAllUserResult> {

	private UserDao userDao = new UserDao();
	private UserDataDao userDataDao = new UserDataDao();
	private SwimmerDao swimmerDao = new SwimmerDao();
	private UserTransformer transformer = new UserTransformer();
	private UserDataTransformer userDataTransformer = new UserDataTransformer();
	private SwimmerTransformer swimmerTransformer = new SwimmerTransformer();
	
	public GetAllUserResult execute(GetAllUserAction pAction,
			ExecutionContext pContext) throws DispatchException {
//		createData();
		List<UserEntity> lUserEntities = userDao.getAll();
		List<UserUi> lUsers = new ArrayList<UserUi>(lUserEntities.size());
		for(UserEntity lUserEntity: lUserEntities) {
			UserUi lUserUi = transformer.toUi(lUserEntity);
			UserDataEntity lUserDataEntity = userDataDao.get(lUserEntity.getUserData());
			lUserUi.setUserData(userDataTransformer.toUi(lUserDataEntity));
			
			//Swimmer
			List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(1);
			lCriteria.add(new CriterionDao<Object>(SwimmerEntityFields.USER,
					lUserEntity.getId(), Operator.EQUAL));
			List<SwimmerEntity> swimmerEntities = swimmerDao.find(lCriteria);
			if(CollectionUtils.isNotEmpty(swimmerEntities)) {
				SwimmerEntity swimmerEntity = swimmerEntities.get(0);
				lUserUi.setSwimmer(swimmerTransformer.toUi(swimmerEntity));
			}
			lUsers.add(lUserUi);
		}
		Collections.sort(lUsers, new Comparator<UserUi>() {
			public int compare(UserUi pUser, UserUi pUser2) {
				int compare = 0;
				if(pUser.getUserData() != null && pUser2.getUserData() != null) {
					if(StringUtils.isNotEmpty(pUser.getUserData().getLastName()) && StringUtils.isNotEmpty(pUser2.getUserData().getLastName())) {
						compare = pUser.getUserData().getLastName().compareTo(pUser2.getUserData().getLastName()); 
					}
				}
				return compare;
			}
		});
		GetAllSlotResult lSlotResult = pContext.execute(new GetAllSlotAction());
		return new GetAllUserResult(lUsers, lSlotResult.getSlots());
	}

	public Class<GetAllUserAction> getActionType() {
		return GetAllUserAction.class;
	}

	public void rollback(GetAllUserAction pArg0, GetAllUserResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
	}
	
	private void createData() {
		Set<String> lProfiles = new HashSet<String>(2);
		lProfiles.add("NAGEUR");
		lProfiles.add("OFFICIEL");
		UserDataEntity lUserData = new UserDataEntity();
		UserEntity lUserEntity = new UserEntity(null, "toto@fgh.com", false, "netool", lProfiles, new HashSet<Long>(0), userDataDao.save(lUserData).getId());
		userDao.save(lUserEntity);
		
		
		Set<String> lProfiles2 = new HashSet<String>(2);
		lProfiles2.add("ADMIN");
		lProfiles2.add("OFFICIEL");
		UserDataEntity lUserData2 = new UserDataEntity();
		UserEntity lUserEntity2 = new UserEntity(null, "tata@fgh.com", false, "netool", lProfiles2, new HashSet<Long>(0), userDataDao.save(lUserData2).getId());
		userDao.save(lUserEntity2);
		
		Set<String> lProfiles3 = new HashSet<String>(2);
		lProfiles3.add("ADMIN");
		UserDataEntity lUserData3 = new UserDataEntity();
		UserEntity lUserEntity3 = new UserEntity(null, "titi@fgh.com", false, "netool", lProfiles3, new HashSet<Long>(0), userDataDao.save(lUserData3).getId());
		userDao.save(lUserEntity3);
		
		Set<String> lProfiles4 = new HashSet<String>(2);
		lProfiles4.add("ADMIN");
		lProfiles4.add("OFFICIEL");
		lProfiles4.add("NAGEUR");
		UserDataEntity lUserData4 = new UserDataEntity();
		UserEntity lUserEntity4 = new UserEntity(null, "tutu@fgh.com", false, "netool", lProfiles4, new HashSet<Long>(0), userDataDao.save(lUserData4).getId());
		userDao.save(lUserEntity4);
	}

}
