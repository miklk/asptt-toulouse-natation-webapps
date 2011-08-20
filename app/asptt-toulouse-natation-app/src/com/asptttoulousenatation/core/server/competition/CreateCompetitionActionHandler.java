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
import com.asptttoulousenatation.core.shared.competition.CreateCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.CreateCompetitionResult;
import com.asptttoulousenatation.core.shared.user.UserUi;

public class CreateCompetitionActionHandler implements
		ActionHandler<CreateCompetitionAction, CreateCompetitionResult> {

	private CompetitionDao dao = new CompetitionDao();
	private CompetitionDayDao competitionDayDao = new CompetitionDayDao();
	
	public CreateCompetitionResult execute(CreateCompetitionAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Build entity
		//Days
		Set<Long> lCompetitionDay = new HashSet<Long>(pAction.getDays().size());
		for(CompetitionDayUi lCompetitionDayUi: pAction.getDays()) {
			CompetitionDayEntity lCompetitionDayEntity = new CompetitionDayEntity();
			lCompetitionDayEntity.setDay(lCompetitionDayUi.getDay());
			lCompetitionDayEntity.setBegin(lCompetitionDayUi.getBegin());
			lCompetitionDayEntity.setEnd(lCompetitionDayUi.getEnd());
			lCompetitionDayEntity.setNeeded(lCompetitionDayUi.getNeeded());
			Set<Long> lOfficiel = new HashSet<Long>(lCompetitionDayUi.getOfficiels().size());
			for(UserUi lUserUi: lCompetitionDayUi.getOfficiels()) {
				lOfficiel.add(lUserUi.getId());
			}
			lCompetitionDayEntity.setOfficiels(lOfficiel);
			CompetitionDayEntity lCompetitionDayEntitySaved = competitionDayDao.save(lCompetitionDayEntity);
			lCompetitionDay.add(lCompetitionDayEntitySaved.getId());
		}
		//Entity
		CompetitionEntity lCompetitionEntity = new CompetitionEntity();
		lCompetitionEntity.setSaison(pAction.getSaison());
		lCompetitionEntity.setTitle(pAction.getTitle());
		lCompetitionEntity.setPlace(pAction.getPlace());
		lCompetitionEntity.setBegin(pAction.getBegin());
		lCompetitionEntity.setEnd(pAction.getEnd());
		lCompetitionEntity.setDays(lCompetitionDay);
		dao.save(lCompetitionEntity);
		return new CreateCompetitionResult();
	}

	public Class<CreateCompetitionAction> getActionType() {
		return CreateCompetitionAction.class;
	}

	public void rollback(CreateCompetitionAction pArg0,
			CreateCompetitionResult pArg1, ExecutionContext pArg2)
			throws DispatchException {
	}
}