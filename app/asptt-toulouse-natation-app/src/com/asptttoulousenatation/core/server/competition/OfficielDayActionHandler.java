package com.asptttoulousenatation.core.server.competition;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.competition.CompetitionDayDao;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionDayEntity;
import com.asptttoulousenatation.core.shared.competition.OfficielDayAction;
import com.asptttoulousenatation.core.shared.competition.OfficielDayResult;

public class OfficielDayActionHandler implements
		ActionHandler<OfficielDayAction, OfficielDayResult> {

	private CompetitionDayDao dao = new CompetitionDayDao();
	
	public OfficielDayResult execute(OfficielDayAction pAction,
			ExecutionContext pContext) throws DispatchException {
		CompetitionDayEntity lCompetitionDay = dao.get(pAction.getDay());
		if(pAction.isAdd()) {
			lCompetitionDay.getOfficiels().add(pAction.getUser());
		} else {
			lCompetitionDay.getOfficiels().remove(pAction.getUser());
		}
		dao.save(lCompetitionDay);
		return new OfficielDayResult();
	}

	public Class<OfficielDayAction> getActionType() {
		return OfficielDayAction.class;
	}

	public void rollback(OfficielDayAction pAction, OfficielDayResult pResult,
			ExecutionContext pContext) throws DispatchException {
	}
}