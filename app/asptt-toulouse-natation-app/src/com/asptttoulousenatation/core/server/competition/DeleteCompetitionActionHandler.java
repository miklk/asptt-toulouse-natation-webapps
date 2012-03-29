package com.asptttoulousenatation.core.server.competition;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.AbstractDeleteActionHandler;
import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.competition.CompetitionDao;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEntity;
import com.asptttoulousenatation.core.shared.competition.DeleteCompetitionAction;
import com.asptttoulousenatation.core.shared.competition.DeleteCompetitionDayAction;
import com.asptttoulousenatation.core.shared.competition.DeleteCompetitionResult;

public class DeleteCompetitionActionHandler extends
		AbstractDeleteActionHandler<CompetitionEntity, DeleteCompetitionAction, DeleteCompetitionResult> {

	public Class<DeleteCompetitionAction> getActionType() {
		return DeleteCompetitionAction.class;
	}

	@Override
	protected void doBeforeDelete(CompetitionEntity pEntity,
			ExecutionContext pContext) throws DispatchException {
		//Delete all days
		for(Long lDay: pEntity.getDays()) {
			pContext.execute(new DeleteCompetitionDayAction(lDay));
		}
	}

	@Override
	protected DaoBase<CompetitionEntity> createDao() {
		return new CompetitionDao();
	}

	@Override
	protected DeleteCompetitionResult getResult() {
		return new DeleteCompetitionResult();
	}

	@Override
	protected void fillResult(DeleteCompetitionAction pAction,
			DeleteCompetitionResult pResult, ExecutionContext pContext)
			throws DispatchException {
		
	}

}
