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
import com.asptttoulousenatation.core.shared.competition.CompetitionDayUi;
import com.asptttoulousenatation.core.shared.competition.DeleteCompetitionDayAction;
import com.asptttoulousenatation.core.shared.competition.UpdateCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.UpdateCompetitionResult;
import com.asptttoulousenatation.core.shared.user.UserUi;

public class UpdateCompetitionActionHandler implements
		ActionHandler<UpdateCompetitionAction, UpdateCompetitionResult> {

	private CompetitionDao dao = new CompetitionDao();
	private CompetitionDayDao competitionDayDao = new CompetitionDayDao();

	public UpdateCompetitionResult execute(UpdateCompetitionAction pAction,
			ExecutionContext pContext) throws DispatchException {
		// Load entity
		// Days
		Set<Long> lCompetitionDay = new HashSet<Long>(pAction.getDays().size());
		for (CompetitionDayUi lCompetitionDayUi : pAction.getDays()) {
			// Create or update
			final CompetitionDayEntity lCompetitionDayEntity;
			if (lCompetitionDayUi.getId() != null) {
				lCompetitionDayEntity = competitionDayDao.get(lCompetitionDayUi
						.getId());
			} else {
				lCompetitionDayEntity = new CompetitionDayEntity();
			}
			lCompetitionDayEntity.setDay(lCompetitionDayUi.getDay());
			lCompetitionDayEntity.setBegin(lCompetitionDayUi.getBegin());
			lCompetitionDayEntity.setEnd(lCompetitionDayUi.getEnd());
			lCompetitionDayEntity.setNeeded(lCompetitionDayUi.getNeeded());
			Set<Long> lOfficiel = new HashSet<Long>(lCompetitionDayUi
					.getOfficiels().size());
			for (UserUi lUserUi : lCompetitionDayUi.getOfficiels()) {
				lOfficiel.add(lUserUi.getId());
			}
			lCompetitionDayEntity.setOfficiels(lOfficiel);
			CompetitionDayEntity lCompetitionDaySaved = competitionDayDao
					.save(lCompetitionDayEntity);
			lCompetitionDay.add(lCompetitionDaySaved.getId());
		}
		//Delete day
		for(Long lDayId: pAction.getDayToDelete()) {
			pContext.execute(new DeleteCompetitionDayAction(lDayId));
		}
		// Entity
		CompetitionEntity lCompetitionEntity = dao.get(pAction.getId());
		lCompetitionEntity.setSaison(pAction.getSaison());
		lCompetitionEntity.setTitle(pAction.getTitle());
		lCompetitionEntity.setPlace(pAction.getPlace());
		lCompetitionEntity.setBegin(pAction.getBegin());
		lCompetitionEntity.setEnd(pAction.getEnd());
		lCompetitionEntity.setDays(lCompetitionDay);
		dao.save(lCompetitionEntity);
		return new UpdateCompetitionResult();
	}

	public Class<UpdateCompetitionAction> getActionType() {
		return UpdateCompetitionAction.class;
	}

	public void rollback(UpdateCompetitionAction pArg0,
			UpdateCompetitionResult pArg1, ExecutionContext pArg2)
			throws DispatchException {
	}
}