package com.asptttoulousenatation.core.server.swimmer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.asptttoulousenatation.core.server.dao.entity.field.SwimmerEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SwimmerStatEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerEntity;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerStatEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerDao;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerStatDao;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.server.dao.user.UserDataDao;
import com.asptttoulousenatation.core.shared.swimmer.DayTimeEnum;
import com.asptttoulousenatation.core.shared.swimmer.GetAllSwimmerStatAction;
import com.asptttoulousenatation.core.shared.swimmer.GetAllSwimmerStatResult;
import com.asptttoulousenatation.core.shared.swimmer.ISwimmerStatUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatComputeUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatDataUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatEnum;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;
import com.asptttoulousenatation.server.util.Utils;

public class GetAllSwimmerStatActionHandler implements
		ActionHandler<GetAllSwimmerStatAction, GetAllSwimmerStatResult<?>> {

	private SwimmerStatDao dao = new SwimmerStatDao();
	private UserDao userDao = new UserDao();
	private UserDataDao userDataDao = new UserDataDao();
	private SwimmerDao swimmerDao = new SwimmerDao();

	public GetAllSwimmerStatResult<? extends ISwimmerStatUi> execute(GetAllSwimmerStatAction pAction,
			ExecutionContext pContext) throws DispatchException {
		// Swimmer concerned
		List<CriterionDao<? extends Object>> lSwimmerCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		lSwimmerCriteria.add(new CriterionDao<Object>(SwimmerEntityFields.STAT,
				Boolean.TRUE, Operator.EQUAL));
		List<SwimmerEntity> lSwimmerEntities = swimmerDao
				.find(lSwimmerCriteria);
		List<Long> lSwimmers = new ArrayList<Long>(lSwimmerEntities.size());
		for (SwimmerEntity lSwimmerEntity : lSwimmerEntities) {
			lSwimmers.add(lSwimmerEntity.getUser());
		}

		List<ISwimmerStatUi> lResults = new ArrayList<ISwimmerStatUi>();
		final Date beginDate;
		final String beginDay;
		final String endDay;
		final String currentDayText;

		// Determine times
		switch (pAction.getPeriod()) {
		case DAY: {
			if (pAction.getPreviousNext() == null) {
				beginDate = pAction.getBeginDate();
				beginDay = Utils.format(pAction.getBeginDate(),
						SwimmerStatEntity.DAY_FORMAT);
			} else {
				Calendar lCalendar = GregorianCalendar.getInstance();
				lCalendar.setTime(pAction.getBeginDate());
				lCalendar
						.roll(Calendar.DAY_OF_MONTH, pAction.getPreviousNext());
				beginDate = lCalendar.getTime();
				beginDay = Utils.format(lCalendar.getTime(),
						SwimmerStatEntity.DAY_FORMAT);
			}
			currentDayText = beginDay;
			endDay = null;
		}
			break;
		case WEEK: {
			Date lCurrentDate = pAction.getBeginDate();
			Calendar lCalendar = GregorianCalendar.getInstance();
			lCalendar.setFirstDayOfWeek(Calendar.MONDAY);
			lCalendar.setTime(lCurrentDate);
			
			lCalendar.set(Calendar.DAY_OF_WEEK, lCalendar.getFirstDayOfWeek());
			Date lBeginDate = lCalendar.getTime();
			lCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			Date lEndDate = lCalendar.getTime();
			
			beginDay = Utils.format(lBeginDate,
					SwimmerStatEntity.DAY_FORMAT);
			endDay = Utils.format(lEndDate,
					SwimmerStatEntity.DAY_FORMAT);
			currentDayText = beginDay + " - " + endDay;
			beginDate = pAction.getBeginDate();
		}
			break;
		case MONTH: {
			Date lCurrentDate = pAction.getBeginDate();
			Calendar lCalendar = GregorianCalendar.getInstance();
			lCalendar.setTime(lCurrentDate);
			int beginDayOfWeek = lCalendar.getActualMinimum(Calendar.DAY_OF_MONTH);
			int endDayOfWeek = lCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			lCalendar.set(Calendar.DAY_OF_MONTH, beginDayOfWeek);
			Date lBeginDate = lCalendar.getTime();
			lCalendar.set(Calendar.DAY_OF_MONTH, endDayOfWeek);
			Date lEndDate = lCalendar.getTime();
			
			beginDay = Utils.format(lBeginDate,
					SwimmerStatEntity.DAY_FORMAT);
			endDay = Utils.format(lEndDate,
					SwimmerStatEntity.DAY_FORMAT);
			currentDayText = beginDay + " - " + endDay;
			beginDate = pAction.getBeginDate();
		}
		break;
		default: {
			beginDate = null;
			beginDay = null;
			endDay = null;
			currentDayText = null;
		}
		}

		// Retrieve
		for (Long lSwimmer : lSwimmers) {
			final ISwimmerStatUi lSwimmerStatUi;
			List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
					2);
			lCriteria.add(new CriterionDao<Object>(
					SwimmerStatEntityFields.SWIMMER, lSwimmer, Operator.EQUAL));
			switch (pAction.getPeriod()) {
			case DAY:
				lCriteria.add(new CriterionDao<String>(
						SwimmerStatEntityFields.DAY, beginDay, Operator.EQUAL));
				lSwimmerStatUi = new SwimmerStatUi();

				break;
			case WEEK:
			case MONTH:

				lCriteria
						.add(new CriterionDao<String>(
								SwimmerStatEntityFields.DAY, beginDay,
								Operator.GREATER));
				lCriteria.add(new CriterionDao<String>(
						SwimmerStatEntityFields.DAY, endDay, Operator.LESS_EQ));
				lSwimmerStatUi = new SwimmerStatComputeUi();
				break;
			default:// Do nothing
				lSwimmerStatUi = null;
			}
			List<SwimmerStatEntity> entities = dao.find(lCriteria);
			// Sum distance
			int distance = 0;
			int bodybuildingCount = 0;
			List<String> comments = new ArrayList<String>();
			List<SwimmerStatDataUi> lSwimmerStatDataList = new ArrayList<SwimmerStatDataUi>(
					entities.size());
			for (SwimmerStatEntity entity : entities) {
				if (SwimmerStatEnum.DAY.equals(pAction.getPeriod())) {
					SwimmerStatDataUi lSwimmerStatDataUi = new SwimmerStatDataUi();
					lSwimmerStatDataUi.setId(entity.getId());
					lSwimmerStatDataUi.setDistance(entity.getDistance());
					lSwimmerStatDataUi.setComment(entity.getComment());
					lSwimmerStatDataList.add(lSwimmerStatDataUi);
				} else {
					if(DayTimeEnum.MUSCU.name().equals(entity.getDayTime())) {
						bodybuildingCount+= entity.getDistance();
					} else {
						distance += entity.getDistance();
					}
					comments.add(entity.getComment());
				}
			}
			// Get user name
			UserEntity userEntity = userDao.get(lSwimmer);
			UserDataEntity userDataEntity = userDataDao.get(userEntity
					.getUserData());

			lSwimmerStatUi.setUser(userEntity.getId());
			lSwimmerStatUi.setSwimmer(userDataEntity.getLastName() + " "
					+ userDataEntity.getFirstName());

			if (SwimmerStatEnum.DAY.equals(pAction.getPeriod())) {
				((SwimmerStatUi) lSwimmerStatUi)
						.setDistances(lSwimmerStatDataList);
			} else {
				((SwimmerStatComputeUi) lSwimmerStatUi).setDistance(distance);
				((SwimmerStatComputeUi) lSwimmerStatUi).setBodybuilding(bodybuildingCount);
				if (!comments.isEmpty()) {
					StrBuilder lComment = new StrBuilder();
					lComment = lComment
							.appendWithSeparators(comments, "<br />");
					((SwimmerStatComputeUi) lSwimmerStatUi).setComment(lComment
							.toString());
				}
			}
			lResults.add(lSwimmerStatUi);
		}

		Collections.sort(lResults, new Comparator<ISwimmerStatUi>() {

			public int compare(ISwimmerStatUi pO1, ISwimmerStatUi pO2) {
				return pO1.getSwimmer().compareTo(pO2.getSwimmer());
			}
		});
		return new GetAllSwimmerStatResult<ISwimmerStatUi>(beginDate, currentDayText, lResults);
	}

	public void rollback(GetAllSwimmerStatAction pAction,
			GetAllSwimmerStatResult<?> pResult, ExecutionContext pContext)
			throws DispatchException {
	}

	public Class<GetAllSwimmerStatAction> getActionType() {
		return GetAllSwimmerStatAction.class;
	}
}