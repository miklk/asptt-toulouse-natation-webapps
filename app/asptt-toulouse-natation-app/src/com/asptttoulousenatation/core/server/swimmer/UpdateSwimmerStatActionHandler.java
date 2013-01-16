package com.asptttoulousenatation.core.server.swimmer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.entity.field.SwimmerStatEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerStatEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerStatDao;
import com.asptttoulousenatation.core.shared.swimmer.UpdateSwimmerStatAction;
import com.asptttoulousenatation.core.shared.swimmer.UpdateSwimmerStatActionData;
import com.asptttoulousenatation.core.shared.swimmer.UpdateSwimmerStatResult;

public class UpdateSwimmerStatActionHandler implements
		ActionHandler<UpdateSwimmerStatAction, UpdateSwimmerStatResult> {

	private SwimmerStatDao dao = new SwimmerStatDao();

	public UpdateSwimmerStatResult execute(UpdateSwimmerStatAction pAction,
			ExecutionContext pContext) throws DispatchException {
		// Get entity
		for (UpdateSwimmerStatActionData lData : pAction.getData()) {
			final SwimmerStatEntity entity;
			Calendar lDateCalendar = GregorianCalendar.getInstance();
			lDateCalendar.setTime(lData.getDay());
			lDateCalendar.set(Calendar.HOUR, 0);
			lDateCalendar.set(Calendar.MINUTE, 0);
			lDateCalendar.set(Calendar.SECOND, 0);
			lDateCalendar.set(Calendar.MILLISECOND, 0);
			if (lData.getId() == null) {
				List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
						3);
				lCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.SWIMMER, lData.getUser(),
						Operator.EQUAL));
				lCriteria.add(new CriterionDao<String>(
						SwimmerStatEntityFields.DAYTIME, lData.getDayTime()
								.name(), Operator.EQUAL));
				lCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.DAY, lDateCalendar.getTime()
								.getTime(), Operator.EQUAL));
				List<SwimmerStatEntity> existingEntities = dao.find(lCriteria);
				if (CollectionUtils.isEmpty(existingEntities)) {
					entity = new SwimmerStatEntity();
				} else {
					entity = existingEntities.get(0);
				}
			} else {
				entity = dao.get(lData.getId());
			}
			entity.setSwimmer(lData.getUser());
			entity.setDay(lDateCalendar.getTime().getTime());
			entity.setDaytime(lData.getDayTime().name());
			entity.setDistance(lData.getDistance());
			entity.setComment(lData.getComment());
			dao.save(entity);
		}
		return new UpdateSwimmerStatResult();
	}

	public Class<UpdateSwimmerStatAction> getActionType() {
		return UpdateSwimmerStatAction.class;
	}

	public void rollback(UpdateSwimmerStatAction pAction,
			UpdateSwimmerStatResult pResult, ExecutionContext pContext)
			throws DispatchException {
	}
}