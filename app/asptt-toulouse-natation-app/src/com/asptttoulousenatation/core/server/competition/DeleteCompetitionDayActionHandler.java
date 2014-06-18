package com.asptttoulousenatation.core.server.competition;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.AbstractDeleteActionHandler;
import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.competition.CompetitionDayDao;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionDayEntity;
import com.asptttoulousenatation.core.shared.competition.DeleteCompetitionDayAction;

public class DeleteCompetitionDayActionHandler extends
		AbstractDeleteActionHandler<CompetitionDayEntity, DeleteCompetitionDayAction, DeleteCompetitionDayResult> {

	public Class<DeleteCompetitionDayAction> getActionType() {
		return DeleteCompetitionDayAction.class;
	}

	@Override
	protected void doBeforeDelete(DeleteCompetitionDayAction pAction, CompetitionDayEntity pEntity,
			ExecutionContext pContext) throws DispatchException {
		//Retrieve its competition to update it.
		
	}

	@Override
	protected DaoBase<CompetitionDayEntity> createDao() {
		return new CompetitionDayDao();
	}

	@Override
	protected DeleteCompetitionDayResult getResult() {
		return new DeleteCompetitionDayResult();
	}

	@Override
	protected void fillResult(DeleteCompetitionDayAction pAction,
			DeleteCompetitionDayResult pResult, ExecutionContext pContext)
			throws DispatchException {
		
	}

}
