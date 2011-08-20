package com.asptttoulousenatation.core.server.competition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.competition.CompetitionDao;
import com.asptttoulousenatation.core.server.dao.competition.CompetitionDayDao;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionDayEntity;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.server.dao.user.UserDataDao;
import com.asptttoulousenatation.core.server.entity.UserDataTransformer;
import com.asptttoulousenatation.core.server.entity.UserTransformer;
import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;
import com.asptttoulousenatation.core.shared.competition.CompetitionUi;
import com.asptttoulousenatation.core.shared.competition.GetAllCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.GetAllCompetitionResult;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.server.userspace.admin.entity.CompetitionDayTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.CompetitionTransformer;

public class GetAllCompetitionActionHandler implements
		ActionHandler<GetAllCompetitionAction, GetAllCompetitionResult> {

	private CompetitionDao dao = new CompetitionDao();
	private CompetitionDayDao competitionDayDao = new CompetitionDayDao();
	private UserDao userDao = new UserDao();
	private UserDataDao userDataDao = new UserDataDao();
	private CompetitionTransformer transformer = new CompetitionTransformer();
	private CompetitionDayTransformer competitionDayTransformer = new CompetitionDayTransformer();
	private UserTransformer userTransformer = new UserTransformer();
	private UserDataTransformer userDataTransformer = new UserDataTransformer();

	public GetAllCompetitionResult execute(GetAllCompetitionAction pAction,
			ExecutionContext pContext) throws DispatchException {
		List<CompetitionEntity> lEntities = dao.getAll();
		List<CompetitionUi> lUis = new ArrayList<CompetitionUi>(
				lEntities.size());
		for (CompetitionEntity lEntity : lEntities) {
			Set<CompetitionDayUi> lCompetitionDayUis = new HashSet<CompetitionDayUi>(
					lEntity.getDays().size());
			for (Long lDay : lEntity.getDays()) {
				CompetitionDayEntity lDayEntity = competitionDayDao.get(lDay);
				Set<UserUi> lOfficielEntities = new HashSet<UserUi>(lDayEntity
						.getOfficiels().size());
				for (Long lOfficielId : lDayEntity.getOfficiels()) {
					UserEntity lOfficielEntity = userDao.get(lOfficielId);
					UserUi lUserUi = userTransformer.toUi(lOfficielEntity);
					UserDataEntity lUserDataEntity = userDataDao
							.get(lOfficielEntity.getUserData());
					lUserUi.setUserData(userDataTransformer
							.toUi(lUserDataEntity));
					lOfficielEntities.add(lUserUi);
				}
				CompetitionDayUi lDayUi = competitionDayTransformer
						.toUi(lDayEntity);
				lDayUi.setOfficiels(lOfficielEntities);
				lCompetitionDayUis.add(lDayUi);
			}
			CompetitionUi lUi = transformer.toUi(lEntity);
			lUi.setDays(lCompetitionDayUis);
			lUis.add(lUi);
		}
		return new GetAllCompetitionResult(lUis);
	}

	public Class<GetAllCompetitionAction> getActionType() {
		return GetAllCompetitionAction.class;
	}

	public void rollback(GetAllCompetitionAction pArg0,
			GetAllCompetitionResult pArg1, ExecutionContext pArg2)
			throws DispatchException {
	}

}
