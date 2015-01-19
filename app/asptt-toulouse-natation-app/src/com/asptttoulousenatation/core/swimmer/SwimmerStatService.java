package com.asptttoulousenatation.core.swimmer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SwimmerStatEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerStatEntity;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerStatDao;
import com.asptttoulousenatation.core.shared.swimmer.DayTimeEnum;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatDataUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatMonthUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatWeekUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatYearUi;
import com.asptttoulousenatation.server.userspace.admin.entity.SwimmerStatDayTransformer;
import com.asptttoulousenatation.web.creneau.CoupleValue;

@Path("/swimmerStats")
@Produces("application/json")
public class SwimmerStatService {

	private Inscription2Dao inscriptionDao = new Inscription2Dao();
	private SwimmerStatDao dao = new SwimmerStatDao();
	private SwimmerStatDayTransformer dayTransformer = new SwimmerStatDayTransformer();

	@Path("/days/{groupe}")
	@GET
	public SwimmerStatListResult getDays(@PathParam("groupe") Long groupe,
			@QueryParam("day") Long day) {
		SwimmerStatListResult result = new SwimmerStatListResult();

		// Set to midnight
		Calendar lCalendar = GregorianCalendar.getInstance();
		lCalendar.setTimeInMillis(day);
		lCalendar.set(Calendar.HOUR_OF_DAY, 0);
		lCalendar.set(Calendar.MINUTE, 0);
		lCalendar.set(Calendar.SECOND, 0);
		lCalendar.set(Calendar.MILLISECOND, 0);
		Long dayToMindnight = lCalendar.getTimeInMillis();

		// Recuperer les nageur du groupe
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(
				InscriptionEntityFields.NOUVEAUGROUPE, groupe, Operator.EQUAL));
		List<InscriptionEntity2> entities = inscriptionDao.find(criteria);

		for (InscriptionEntity2 nageur : entities) {
			// Recuperer la stat du jour du nageur
			List<CriterionDao<? extends Object>> statCriteria = new ArrayList<CriterionDao<? extends Object>>(
					2);
			statCriteria
					.add(new CriterionDao<Long>(SwimmerStatEntityFields.DAY,
							dayToMindnight, Operator.EQUAL));
			statCriteria.add(new CriterionDao<Long>(
					SwimmerStatEntityFields.SWIMMER, nageur.getId(),
					Operator.EQUAL));
			List<SwimmerStatEntity> statEntities = dao.find(statCriteria);
			SwimmerStatUi dayStat = new SwimmerStatUi();
			dayStat.setAdherent(nageur.getId());
			dayStat.setNom(nageur.getNom());
			dayStat.setPrenom(nageur.getPrenom());
			for (SwimmerStatEntity swimmerStat : statEntities) {
				dayTransformer.update(dayStat, swimmerStat);
			}
			result.addStat(dayStat);
		}
		return result;
	}

	@PUT
	@Path("{nageur}")
	@Consumes("application/json")
	public SwimmerStatUpdateResult update(@PathParam("nageur") Long nageur,
			SwimmerStatUpdateAction action) {
		SwimmerStatUpdateResult result = new SwimmerStatUpdateResult();
		SwimmerStatUi stat = action.getStat();
		updateStat(DayTimeEnum.valueOf(action.getDayTime()), action.getDay(),
				stat);
		return result;
	}

	private void updateStat(DayTimeEnum dayTime, Long day, SwimmerStatUi stat) {
		final SwimmerStatEntity entity;
		if (stat.getId() != null) {
			entity = dao.get(stat.getId());
		} else {
			entity = new SwimmerStatEntity();
		}
		entity.setSwimmer(stat.getAdherent());

		SwimmerStatDataUi statData = null;
		switch (dayTime) {
		case MATIN:
			statData = stat.getMorning();
			break;
		case MIDI:
			statData = stat.getMidday();
			break;
		case SOIR:
			statData = stat.getNight();
			break;
		case MUSCU:
			statData = stat.getBodybuilding();
			break;
		case PRESENCE:
			statData = stat.getPresence();
			break;
		default: //
		}

		entity.setId(statData.getId());
		entity.setDaytime(dayTime.name());
		entity.setDistance(statData.getDistance());
		entity.setPresence(statData.isPresence() || statData.getDistance() > 0);
		entity.setComment(statData.getComment());

		Calendar lCalendar = GregorianCalendar.getInstance();
		lCalendar.setTimeInMillis(day);
		lCalendar.set(Calendar.HOUR_OF_DAY, 0);
		lCalendar.set(Calendar.MINUTE, 0);
		lCalendar.set(Calendar.SECOND, 0);
		lCalendar.set(Calendar.MILLISECOND, 0);
		entity.setDay(lCalendar.getTimeInMillis());

		dao.save(entity);
	}

	@POST
	@Consumes("application/json")
	public SwimmerStatUpdateResult updateAll(SwimmerStatUpdateAllAction action) {
		SwimmerStatUpdateResult result = new SwimmerStatUpdateResult();
		for (SwimmerStatUi stat : action.getSwimmers().values()) {
			if (stat.isSelected()) {
				updateStat(action.getDayTime(), action.getDay(), stat);
			}
		}
		return result;
	}

	@Path("/weeks/{groupe}")
	@GET
	public SwimmerStatWeekListResult getWeeks(@PathParam("groupe") Long groupe,
			@QueryParam("week") String week) {
		SwimmerStatWeekListResult result = new SwimmerStatWeekListResult();

		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-'W'ww");
		Date weekDate = null;
		try {
			weekDate = formatter.parse(week);
			Calendar lCalendar = GregorianCalendar.getInstance();
			lCalendar.setTime(weekDate);
			lCalendar.setFirstDayOfWeek(Calendar.MONDAY);
			lCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			Long dayBeginToMindnight = lCalendar.getTimeInMillis();

			// Get end of week to midnight
			lCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			Long dayEndToMindnight = lCalendar.getTimeInMillis();

			// Recuperer les nageur du groupe
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(
					InscriptionEntityFields.NOUVEAUGROUPE, groupe,
					Operator.EQUAL));
			List<InscriptionEntity2> entities = inscriptionDao.find(criteria);

			for (InscriptionEntity2 nageur : entities) {
				// Recuperer la stat du jour du nageur
				List<CriterionDao<? extends Object>> statCriteria = new ArrayList<CriterionDao<? extends Object>>(
						2);
				statCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.DAY, dayBeginToMindnight,
						Operator.GREATER_EQ));
				statCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.DAY, dayEndToMindnight,
						Operator.LESS_EQ));
				statCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.SWIMMER, nageur.getId(),
						Operator.EQUAL));
				List<SwimmerStatEntity> statEntities = dao.find(statCriteria);
				SwimmerStatWeekUi dayStat = new SwimmerStatWeekUi();
				dayStat.setNom(nageur.getNom());
				dayStat.setPrenom(nageur.getPrenom());
				for (SwimmerStatEntity swimmerStat : statEntities) {
					int index = 0;
					switch (DayTimeEnum.valueOf(swimmerStat.getDaytime())) {
					case MATIN:
						index = 0;
						break;
					case MIDI:
						index = 1;
						break;
					case SOIR:
						index = 2;
						break;
					default://
					}
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTimeInMillis(swimmerStat.getDay());
					int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
					switch (dayOfWeek) {
					case Calendar.MONDAY: {
						dayStat.addDistance(0, index, swimmerStat.getDistance());
						dayStat.addPresence(0, swimmerStat.getPresence());
					}
						break;
					case Calendar.TUESDAY: {
						dayStat.addDistance(1, index, swimmerStat.getDistance());
						dayStat.addPresence(1, swimmerStat.getPresence());
					}
						break;
					case Calendar.WEDNESDAY: {
						dayStat.addDistance(2, index, swimmerStat.getDistance());
						dayStat.addPresence(2, swimmerStat.getPresence());
					}
						break;
					case Calendar.THURSDAY: {
						dayStat.addDistance(3, index, swimmerStat.getDistance());
						dayStat.addPresence(3, swimmerStat.getPresence());
					}
						break;
					case Calendar.FRIDAY: {
						dayStat.addDistance(4, index, swimmerStat.getDistance());
						dayStat.addPresence(4, swimmerStat.getPresence());
					}
						break;
					case Calendar.SATURDAY: {
						dayStat.addDistance(5, index, swimmerStat.getDistance());
						dayStat.addPresence(5, swimmerStat.getPresence());
					}
						break;
					case Calendar.SUNDAY: {
						dayStat.addDistance(6, index, swimmerStat.getDistance());
						dayStat.addPresence(6, swimmerStat.getPresence());
					}
						break;
					default://
					}
				}
				dayStat.computeTotalDistance();
				dayStat.computeTotalPresence();
				result.addNageur(dayStat);
			}
			result.setBegin(dayBeginToMindnight);
			result.setEnd(dayEndToMindnight);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Path("/months/{groupe}")
	@GET
	public SwimmerStatMonthListResult getMonths(
			@PathParam("groupe") Long groupe, @QueryParam("month") String month) {
		SwimmerStatMonthListResult result = new SwimmerStatMonthListResult();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		Date monthDate = null;
		try {
			monthDate = formatter.parse(month);
			Long dayBeginToMindnight = monthDate.getTime();

			// Get end of week to midnight
			Calendar lCalendar = GregorianCalendar.getInstance();
			lCalendar.setTime(monthDate);
			int endDayOfWeek = lCalendar
					.getActualMaximum(Calendar.DAY_OF_MONTH);
			lCalendar.set(Calendar.DAY_OF_MONTH, endDayOfWeek);
			Long dayEndToMindnight = lCalendar.getTimeInMillis();

			// Recuperer les nageur du groupe
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(
					InscriptionEntityFields.NOUVEAUGROUPE, groupe,
					Operator.EQUAL));
			List<InscriptionEntity2> entities = inscriptionDao.find(criteria);

			Set<Integer> weeks = new LinkedHashSet<>(); 
			for (InscriptionEntity2 nageur : entities) {
				// Recuperer la stat du jour du nageur
				List<CriterionDao<? extends Object>> statCriteria = new ArrayList<CriterionDao<? extends Object>>(
						2);
				statCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.DAY, dayBeginToMindnight,
						Operator.GREATER_EQ));
				statCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.DAY, dayEndToMindnight,
						Operator.LESS_EQ));
				statCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.SWIMMER, nageur.getId(),
						Operator.EQUAL));
				List<SwimmerStatEntity> statEntities = dao.find(statCriteria);
				SwimmerStatMonthUi dayStat = new SwimmerStatMonthUi();
				dayStat.setNom(nageur.getNom());
				dayStat.setPrenom(nageur.getPrenom());
				for (SwimmerStatEntity swimmerStat : statEntities) {
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTimeInMillis(swimmerStat.getDay());
					int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
					weeks.add(weekOfMonth);
					dayStat.addDistance(weekOfMonth - 1,
							swimmerStat.getDistance());
					dayStat.addPresence(weekOfMonth - 1,
							swimmerStat.getPresence() ? 1 : 0);
				}
				dayStat.computeTotalDistance();
				dayStat.computeTotalPresence();
				result.addNageur(dayStat);
			}
			//Set weeks
			Calendar calendarBegin = GregorianCalendar.getInstance();
			calendarBegin.setTime(monthDate);
			calendarBegin.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			Calendar calendarEnd = GregorianCalendar.getInstance();
			calendarEnd.setTime(monthDate);
			calendarEnd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			for(Integer week: weeks) {
				calendarBegin.set(Calendar.WEEK_OF_MONTH, week);
				calendarEnd.set(Calendar.WEEK_OF_MONTH, week);
				result.addWeek(week -1, new CoupleValue<Long, Long>(calendarBegin.getTimeInMillis(), calendarEnd.getTimeInMillis()));
			}
			
			result.setBegin(dayBeginToMindnight);
			result.setEnd(dayEndToMindnight);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Path("/years/{groupe}")
	@GET
	public SwimmerStatYearListResult getYears(@PathParam("groupe") Long groupe,
			@QueryParam("year") String year) {
		SwimmerStatYearListResult result = new SwimmerStatYearListResult();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Date yearDate = null;
		try {
			yearDate = formatter.parse(year);
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(yearDate);
			calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
			calendar.add(Calendar.YEAR, -1);
			Long dayBeginToMindnight = calendar.getTimeInMillis();

			// Get end of week to midnight
			Calendar lCalendar = GregorianCalendar.getInstance();
			lCalendar.setTime(yearDate);
			lCalendar.set(Calendar.MONTH, Calendar.AUGUST);
			lCalendar.set(Calendar.DAY_OF_MONTH, 31);
			Long dayEndToMindnight = lCalendar.getTimeInMillis();

			// Recuperer les nageur du groupe
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(
					InscriptionEntityFields.NOUVEAUGROUPE, groupe,
					Operator.EQUAL));
			List<InscriptionEntity2> entities = inscriptionDao.find(criteria);

			for (InscriptionEntity2 nageur : entities) {
				// Recuperer la stat du jour du nageur
				List<CriterionDao<? extends Object>> statCriteria = new ArrayList<CriterionDao<? extends Object>>(
						2);
				statCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.DAY, dayBeginToMindnight,
						Operator.GREATER_EQ));
				statCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.DAY, dayEndToMindnight,
						Operator.LESS_EQ));
				statCriteria.add(new CriterionDao<Long>(
						SwimmerStatEntityFields.SWIMMER, nageur.getId(),
						Operator.EQUAL));
				List<SwimmerStatEntity> statEntities = dao.find(statCriteria);
				SwimmerStatYearUi dayStat = new SwimmerStatYearUi();
				dayStat.setNom(nageur.getNom());
				dayStat.setPrenom(nageur.getPrenom());
				for (SwimmerStatEntity swimmerStat : statEntities) {
					calendar = GregorianCalendar.getInstance();
					calendar.setTimeInMillis(swimmerStat.getDay());
					int monthOfYear = calendar.get(Calendar.MONTH);
					switch (monthOfYear) {
					case Calendar.JANUARY: {
						dayStat.addDistance(0, swimmerStat.getDistance());
						dayStat.addPresence(0, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					case Calendar.FEBRUARY: {
						dayStat.addDistance(1, swimmerStat.getDistance());
						dayStat.addPresence(1, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					case Calendar.MARCH: {
						dayStat.addDistance(2, swimmerStat.getDistance());
						dayStat.addPresence(2, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					case Calendar.APRIL: {
						dayStat.addDistance(3, swimmerStat.getDistance());
						dayStat.addPresence(3, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					case Calendar.MAY: {
						dayStat.addDistance(4, swimmerStat.getDistance());
						dayStat.addPresence(4, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					case Calendar.JUNE: {
						dayStat.addDistance(5, swimmerStat.getDistance());
						dayStat.addPresence(5, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					case Calendar.JULY: {
						dayStat.addDistance(6, swimmerStat.getDistance());
						dayStat.addPresence(6, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					case Calendar.AUGUST: {
						dayStat.addDistance(7, swimmerStat.getDistance());
						dayStat.addPresence(7, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					case Calendar.SEPTEMBER: {
						dayStat.addDistance(8, swimmerStat.getDistance());
						dayStat.addPresence(8, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					case Calendar.OCTOBER: {
						dayStat.addDistance(9, swimmerStat.getDistance());
						dayStat.addPresence(9, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					case Calendar.NOVEMBER: {
						dayStat.addDistance(10, swimmerStat.getDistance());
						dayStat.addPresence(10, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					case Calendar.DECEMBER: {
						dayStat.addDistance(11, swimmerStat.getDistance());
						dayStat.addPresence(11, swimmerStat.getPresence() ? 1
								: 0);
					}
						break;
					default://
					}
				}
				dayStat.computeTotalDistance();
				dayStat.computeTotalPresence();
				result.addNageur(dayStat);
			}
			result.setBegin(dayBeginToMindnight);
			result.setEnd(dayEndToMindnight);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
