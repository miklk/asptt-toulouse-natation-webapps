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

	public GetAllSwimmerStatResult<? extends ISwimmerStatUi> execute(
			GetAllSwimmerStatAction pAction, ExecutionContext pContext)
			throws DispatchException {
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
		final Long beginDay;
		final Long endDay;
		final String currentDayText;
		final Date currentDate;
		// Determine times
		switch (pAction.getPeriod()) {
		case DAY: {
			if (pAction.getPreviousNext() == null) {
				currentDate = pAction.getBeginDate();
			} else {
				Calendar lCalendar = GregorianCalendar.getInstance();
				lCalendar.setTime(pAction.getBeginDate());
				lCalendar
						.roll(Calendar.DAY_OF_YEAR, pAction.getPreviousNext());
				currentDate = lCalendar.getTime();
			}
			// Set to midnight
			Calendar lCalendar = GregorianCalendar.getInstance();
			lCalendar.setTime(currentDate);
			lCalendar.set(Calendar.HOUR_OF_DAY, 0);
			beginDay = lCalendar.getTimeInMillis();

			lCalendar.set(Calendar.HOUR_OF_DAY, 23);
			endDay = lCalendar.getTimeInMillis();

			currentDayText = Utils.format(new Date(beginDay),
					SwimmerStatEntity.DAY_FORMAT);
		}
			break;
		case WEEK: {
			if (pAction.getPreviousNext() == null) {
				currentDate = pAction.getBeginDate();
			} else {
				Calendar lCalendar = GregorianCalendar.getInstance();
				lCalendar.setTime(pAction.getBeginDate());
				lCalendar.roll(Calendar.WEEK_OF_YEAR,
						pAction.getPreviousNext());
				currentDate = lCalendar.getTime();
			}
			Calendar lCalendar = GregorianCalendar.getInstance();
			lCalendar.setFirstDayOfWeek(Calendar.MONDAY);
			lCalendar.setTime(currentDate);
			lCalendar.set(Calendar.HOUR, 0);
			lCalendar.set(Calendar.MINUTE, 0);
			lCalendar.set(Calendar.SECOND, 0);
			lCalendar.set(Calendar.MILLISECOND, 0);
			lCalendar.set(Calendar.DAY_OF_WEEK, lCalendar.getFirstDayOfWeek());
			Date lBeginDate = lCalendar.getTime();
			lCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			Date lEndDate = lCalendar.getTime();

			beginDay = lBeginDate.getTime();
			endDay = lEndDate.getTime();
			currentDayText = Utils.format(lBeginDate,
					SwimmerStatEntity.DAY_FORMAT)
					+ " - "
					+ Utils.format(lEndDate, SwimmerStatEntity.DAY_FORMAT);
		}
			break;
		case MONTH: {
			if (pAction.getPreviousNext() == null) {
				currentDate = pAction.getBeginDate();
			} else {
				Calendar lCalendar = GregorianCalendar.getInstance();
				lCalendar.setTime(pAction.getBeginDate());
				lCalendar.roll(Calendar.MONTH, pAction.getPreviousNext());
				currentDate = lCalendar.getTime();
			}
			Calendar lCalendar = GregorianCalendar.getInstance();
			lCalendar.setTime(currentDate);
			int beginDayOfWeek = lCalendar
					.getActualMinimum(Calendar.DAY_OF_MONTH);
			int endDayOfWeek = lCalendar
					.getActualMaximum(Calendar.DAY_OF_MONTH);

			lCalendar.set(Calendar.HOUR, 0);
			lCalendar.set(Calendar.MINUTE, 0);
			lCalendar.set(Calendar.SECOND, 0);
			lCalendar.set(Calendar.MILLISECOND, 0);
			
			lCalendar.set(Calendar.DAY_OF_MONTH, beginDayOfWeek);
			Date lBeginDate = lCalendar.getTime();
			lCalendar.set(Calendar.DAY_OF_MONTH, endDayOfWeek);
			Date lEndDate = lCalendar.getTime();

			beginDay = lBeginDate.getTime();
			endDay = lEndDate.getTime();
			currentDayText = Utils.format(lBeginDate,
					SwimmerStatEntity.DAY_FORMAT)
					+ " - "
					+ Utils.format(lEndDate, SwimmerStatEntity.DAY_FORMAT);
		}
			break;
		case YEAR: {
			if (pAction.getPreviousNext() == null) {
				currentDate = pAction.getBeginDate();
			} else {
				Calendar lCalendar = GregorianCalendar.getInstance();
				lCalendar.setTime(pAction.getBeginDate());
				lCalendar.roll(Calendar.YEAR, pAction.getPreviousNext());
				currentDate = lCalendar.getTime();
			}
			Calendar lCalendar = GregorianCalendar.getInstance();
			lCalendar.setTime(currentDate);
			
			lCalendar.set(Calendar.HOUR, 0);
			lCalendar.set(Calendar.MINUTE, 0);
			lCalendar.set(Calendar.SECOND, 0);
			lCalendar.set(Calendar.MILLISECOND, 0);
			
			lCalendar.set(Calendar.DAY_OF_MONTH, 01);
			lCalendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
			Date lBeginDate = lCalendar.getTime();
			lCalendar.set(Calendar.DAY_OF_MONTH, 31);
			lCalendar.set(Calendar.MONTH, Calendar.AUGUST);
			lCalendar.add(Calendar.YEAR, 1);
			Date lEndDate = lCalendar.getTime();

			beginDay = lBeginDate.getTime();
			endDay = lEndDate.getTime();
			currentDayText = Utils.format(lBeginDate,
					SwimmerStatEntity.DAY_FORMAT)
					+ " - "
					+ Utils.format(lEndDate, SwimmerStatEntity.DAY_FORMAT);
		}
			break;
		default: {
			beginDay = null;
			endDay = null;
			currentDayText = null;
			currentDate = null;
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
				lCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.DAY, beginDay,
						Operator.GREATER_EQ));
				lCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.DAY, endDay, Operator.LESS_EQ));
				lSwimmerStatUi = new SwimmerStatUi();

				break;
			case WEEK:
			case MONTH:
			case YEAR:
				lCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.DAY, beginDay,
						Operator.GREATER_EQ));
				lCriteria.add(new CriterionDao<Long>(
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
			StringBuilder lComment = new StringBuilder();
			for (SwimmerStatEntity entity : entities) {
				if (SwimmerStatEnum.DAY.equals(pAction.getPeriod())) {
					SwimmerStatUi lDaySwimmerStatUi = (SwimmerStatUi) lSwimmerStatUi;
					SwimmerStatDataUi lSwimmerStatDataUi = new SwimmerStatDataUi();
					lSwimmerStatDataUi.setId(entity.getId());
					lSwimmerStatDataUi.setDayTime(DayTimeEnum.valueOf(entity
							.getDaytime()));
					lSwimmerStatDataUi.setDistance(entity.getDistance());
					if (StringUtils.isNotBlank(entity.getComment())) {
						lSwimmerStatDataUi.setComment(entity.getComment());
						lComment.append(entity.getComment());
					}
					switch (DayTimeEnum.valueOf(entity.getDaytime())) {
					case MATIN:
						lDaySwimmerStatUi.setMorning(lSwimmerStatDataUi);
						break;
					case MIDI:
						lDaySwimmerStatUi.setMidday(lSwimmerStatDataUi);
						break;
					case SOIR:
						lDaySwimmerStatUi.setNight(lSwimmerStatDataUi);
						break;
					case MUSCU:
						lDaySwimmerStatUi.setBodybuilding(lSwimmerStatDataUi);
						break;
					default:// Do nothing
					}
				} else {
					if (DayTimeEnum.MUSCU.name().equals(entity.getDaytime())) {
						bodybuildingCount += entity.getDistance();
					} else {
						distance += entity.getDistance();
					}
					if (StringUtils.isNotEmpty(entity.getComment())) {
						comments.add(entity.getComment());
					}
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
						.setComment(lComment.toString());
			} else {
				((SwimmerStatComputeUi) lSwimmerStatUi).setDistance(distance);
				((SwimmerStatComputeUi) lSwimmerStatUi)
						.setBodybuilding(bodybuildingCount);
				((SwimmerStatComputeUi) lSwimmerStatUi).setComment(comments);
			}
			lResults.add(lSwimmerStatUi);
		}

		Collections.sort(lResults, new Comparator<ISwimmerStatUi>() {

			public int compare(ISwimmerStatUi pO1, ISwimmerStatUi pO2) {
				return pO1.getSwimmer().compareTo(pO2.getSwimmer());
			}
		});
		return new GetAllSwimmerStatResult<ISwimmerStatUi>(currentDate,
				currentDayText, lResults);
	}

	public void rollback(GetAllSwimmerStatAction pAction,
			GetAllSwimmerStatResult<?> pResult, ExecutionContext pContext)
			throws DispatchException {
	}

	public Class<GetAllSwimmerStatAction> getActionType() {
		return GetAllSwimmerStatAction.class;
	}
}