package com.asptttoulousenatation.core.server.competition;

import java.util.HashSet;
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
import com.asptttoulousenatation.core.shared.competition.GetAllCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.GetAllCompetitionResult;
import com.asptttoulousenatation.core.shared.competition.OfficielDayAction;
import com.asptttoulousenatation.core.shared.competition.OfficielDayResult;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.server.userspace.admin.entity.CompetitionDayTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.CompetitionTransformer;

public class OfficielDayActionHandler implements
		ActionHandler<OfficielDayAction, OfficielDayResult> {

	private CompetitionDayDao dao = new CompetitionDayDao();
	private CompetitionDayTransformer transformer = new CompetitionDayTransformer();
	
	private CompetitionDao competitionDao = new CompetitionDao();
	private CompetitionTransformer competitionTransformer = new CompetitionTransformer();
	
	private UserDao userDao = new UserDao();
	private UserDataDao userDataDao = new UserDataDao();
	private UserTransformer userTransformer = new UserTransformer();
	private UserDataTransformer userDataTransformer = new UserDataTransformer();
	
	public OfficielDayResult execute(OfficielDayAction pAction,
			ExecutionContext pContext) throws DispatchException {
		CompetitionDayEntity lCompetitionDay = dao.get(pAction.getDay());
		if(pAction.isAdd()) {
			lCompetitionDay.getOfficiels().add(pAction.getUser());
			lCompetitionDay.setNeeded(lCompetitionDay.getNeeded() - 1);
		} else {
			lCompetitionDay.getOfficiels().remove(pAction.getUser());
			lCompetitionDay.setNeeded(lCompetitionDay.getNeeded() + 1);
		}
		dao.save(lCompetitionDay);
		CompetitionDayEntity lDay = dao.get(pAction.getDay());
		CompetitionEntity lCompetitionEntity = competitionDao.get(pAction.getCompetition());
		
		Set<UserUi> lOfficielEntities = new HashSet<UserUi>(
				lDay.getOfficiels().size());
		for (Long lOfficielId : lDay.getOfficiels()) {
			UserEntity lOfficielEntity = userDao.get(lOfficielId);
			UserUi lUserUi = userTransformer.toUi(lOfficielEntity);
			UserDataEntity lUserDataEntity = userDataDao
					.get(lOfficielEntity.getUserData());
			lUserUi.setUserData(userDataTransformer
					.toUi(lUserDataEntity));
			lOfficielEntities.add(lUserUi);
		}
		
		CompetitionDayUi lCompetitionDayUi = transformer.toUi(lDay);
		lCompetitionDayUi.setOfficiels(lOfficielEntities);
		lCompetitionDayUi.setCompetitionUi(competitionTransformer.toUi(lCompetitionEntity));
		
		OfficielDayResult result = new OfficielDayResult(lCompetitionDayUi);
		
		//Load competions
		GetAllCompetitionResult competitionResult = pContext.execute(new GetAllCompetitionAction());
		result.setCompetitions(competitionResult.getCompetitions());
		return result;
	}

	public Class<OfficielDayAction> getActionType() {
		return OfficielDayAction.class;
	}

	public void rollback(OfficielDayAction pAction, OfficielDayResult pResult,
			ExecutionContext pContext) throws DispatchException {
	}
}