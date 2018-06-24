package com.asptttoulousenatation.core.swimmer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.BooleanUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.asptttoulousenatation.core.inscription.DossierService;
import com.asptttoulousenatation.core.lang.CoupleValue;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SwimmerStatEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerStatEntity;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerStatTypeEnum;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.search.OrderDao.OrderOperator;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerStatDao;

@Path("/swimmerStats")
@Produces("application/json")
public class SwimmerStatService {

	private DossierNageurDao nageurDao = new DossierNageurDao();
	private SwimmerStatDao dao = new SwimmerStatDao();
	private SwimmerStatDayTransformer dayTransformer = new SwimmerStatDayTransformer();
	
	@Path("/days")
	@GET
	public SwimmerStatListResult getDays(@QueryParam("groupes") Set<Long> groupes,
			@QueryParam("day") Long day) {
		SwimmerStatListResult result = new SwimmerStatListResult();

		// Set to midnight
		LocalDate dayAsDate = new LocalDate(day);
		Long dayToMindnight = dayAsDate.toDateTimeAtStartOfDay().getMillis();

		// Recuperer les nageur du groupe
		List<DossierNageurEntity> entities = getNageurs(groupes);
		for (DossierNageurEntity nageur : entities) {
			// Recuperer la stat du jour du nageur
			List<CriterionDao<? extends Object>> statCriteria = new ArrayList<CriterionDao<? extends Object>>(
					2);
			statCriteria
					.add(new CriterionDao<Long>(SwimmerStatEntityFields.DAY,
							dayToMindnight, Operator.EQUAL));
			statCriteria.add(new CriterionDao<Long>(
					SwimmerStatEntityFields.SWIMMER, nageur.getId(),
					Operator.EQUAL));
			statCriteria.add(new CriterionDao<String>(
					SwimmerStatEntityFields.TYPE, null,
					Operator.NULL));
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

	private List<DossierNageurEntity> getNageurs(Set<Long> groupes) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				groupes.size());
		for(Long groupe: groupes) {
		criteria.add(new CriterionDao<Long>(
				DossierNageurEntityFields.GROUPE, groupe, Operator.EQUAL));
		}
		List<DossierNageurEntity> entities = new ArrayList<>(nageurDao.find(criteria, Operator.OR, new OrderDao(DossierNageurEntityFields.NOM, OrderOperator.ASC)));
		CollectionUtils.filter(entities, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				DossierNageurEntity entity = (DossierNageurEntity) arg0;
				return entity.getSaison() == DossierService.NEW_SAISON;
			}
		});
		Collections.sort(entities, new Comparator<DossierNageurEntity>() {

			@Override
			public int compare(DossierNageurEntity pO1, DossierNageurEntity pO2) {
				return pO1.getNom().compareTo(pO2.getNom());
			}
		});
		return entities;
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

		LocalDate dayAsDate = new LocalDate(day);
		entity.setDay(dayAsDate.toDateTimeAtStartOfDay().getMillis());

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

	@Path("/weeks")
	@GET
	public SwimmerStatWeekListResult getWeeks(@QueryParam("groupes") Set<Long> groupes,
			@QueryParam("week") String week) {
		SwimmerStatWeekListResult result = new SwimmerStatWeekListResult();

		 DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-'W'ww");
			LocalDate weekAsDate = formatter.parseLocalDate(week);
			Long dayBeginToMindnight = weekAsDate.withDayOfWeek(DateTimeConstants.MONDAY).toDateTimeAtStartOfDay().getMillis();

			// Get end of week to midnight
			Long dayEndToMindnight = weekAsDate.withDayOfWeek(DateTimeConstants.SUNDAY).toDateTimeAtStartOfDay().getMillis();

			// Recuperer les nageur du groupe
			List<DossierNageurEntity> entities = getNageurs(groupes);

			for (DossierNageurEntity nageur : entities) {
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
				statCriteria.add(new CriterionDao<String>(
						SwimmerStatEntityFields.TYPE, null,
						Operator.NULL));
				List<SwimmerStatEntity> statEntities = dao.find(statCriteria);
				SwimmerStatWeekUi dayStat = new SwimmerStatWeekUi();
				dayStat.setNom(nageur.getNom());
				dayStat.setPrenom(nageur.getPrenom());
				for (SwimmerStatEntity swimmerStat : statEntities) {
					boolean muscu = false;
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
					case MUSCU: muscu = true;
					break;
					default://
					}
					LocalDate localDate = new LocalDate(swimmerStat.getDay());
					switch (localDate.getDayOfWeek()) {
					case DateTimeConstants.MONDAY: {
						if(muscu) {
							dayStat.addMuscu(0, swimmerStat.getPresence());	
						} else {
							dayStat.addDistance(0, index, swimmerStat.getDistance());
							dayStat.addPresence(0, swimmerStat.getPresence());
						}
						
					}
						break;
					case DateTimeConstants.TUESDAY: {
						if(muscu) {
							dayStat.addMuscu(1, swimmerStat.getPresence());	
						} else {
							dayStat.addDistance(1, index, swimmerStat.getDistance());
							dayStat.addPresence(1, swimmerStat.getPresence());
						}
					}
						break;
					case DateTimeConstants.WEDNESDAY: {
						if(muscu) {
							dayStat.addMuscu(2, swimmerStat.getPresence());	
						} else {
							dayStat.addDistance(2, index, swimmerStat.getDistance());
							dayStat.addPresence(2, swimmerStat.getPresence());
						}
					}
						break;
					case DateTimeConstants.THURSDAY: {
						if(muscu) {
							dayStat.addMuscu(3, swimmerStat.getPresence());	
						} else {
							dayStat.addDistance(3, index, swimmerStat.getDistance());
							dayStat.addPresence(3, swimmerStat.getPresence());
						}
					}
						break;
					case DateTimeConstants.FRIDAY: {
						if(muscu) {
							dayStat.addMuscu(4, swimmerStat.getPresence());	
						} else {
							dayStat.addDistance(4, index, swimmerStat.getDistance());
							dayStat.addPresence(4, swimmerStat.getPresence());
						}
					}
						break;
					case DateTimeConstants.SATURDAY: {
						if(muscu) {
							dayStat.addMuscu(5, swimmerStat.getPresence());	
						} else {
							dayStat.addDistance(5, index, swimmerStat.getDistance());
							dayStat.addPresence(5, swimmerStat.getPresence());
						}
					}
						break;
					case DateTimeConstants.SUNDAY: {
						if(muscu) {
							dayStat.addMuscu(6, swimmerStat.getPresence());	
						} else {
							dayStat.addDistance(6, index, swimmerStat.getDistance());
							dayStat.addPresence(6, swimmerStat.getPresence());
						}
					}
						break;
					default://
					}
				}
				dayStat.computeTotalDistance();
				dayStat.computeTotalPresence();
				dayStat.computeTotalMuscu();
				result.addNageur(dayStat);
			}
			result.setBegin(dayBeginToMindnight);
			result.setEnd(dayEndToMindnight);
			Collections.sort(result.getNageurs(), new Comparator<SwimmerStatWeekUi>() {

				@Override
				public int compare(SwimmerStatWeekUi pO1, SwimmerStatWeekUi pO2) {
					return pO1.getNom().compareTo(pO2.getNom());
				}
			});
		return result;
	}

	@Path("/months")
	@GET
	public SwimmerStatMonthListResult getMonths(
			@QueryParam("groupes") Set<Long> groupes, @QueryParam("month") String month) {
		SwimmerStatMonthListResult result = new SwimmerStatMonthListResult();

			LocalDate longDate = ISODateTimeFormat.yearMonth().parseLocalDate(month);
			Long dayBeginToMindnight = longDate.dayOfMonth().withMinimumValue().toDateTimeAtStartOfDay().getMillis();
			// Get end of week to midnight
			Long dayEndToMindnight = longDate.dayOfMonth().withMaximumValue().toDateTimeAtStartOfDay().hourOfDay().withMaximumValue().getMillis();

			// Recuperer les nageur du groupe
			Map<Integer, LocalDate> weeks = new LinkedHashMap<>();
			List<DossierNageurEntity> entities = getNageurs(groupes);
			for (DossierNageurEntity nageur : entities) {
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
				statCriteria.add(new CriterionDao<String>(
						SwimmerStatEntityFields.TYPE, null,
						Operator.NULL));
				List<SwimmerStatEntity> statEntities = dao.find(statCriteria);
				SwimmerStatMonthUi dayStat = new SwimmerStatMonthUi();
				dayStat.setNom(nageur.getNom());
				dayStat.setPrenom(nageur.getPrenom());
				for (SwimmerStatEntity swimmerStat : statEntities) {
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTimeInMillis(swimmerStat.getDay());
					int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
					weeks.put(weekOfMonth, new LocalDate(swimmerStat.getDay()));
					
					if(DayTimeEnum.valueOf(swimmerStat.getDaytime()).equals(DayTimeEnum.MUSCU)) {
						dayStat.addMuscu(weekOfMonth - 1,
								swimmerStat.getPresence() ? 1 : 0);
					} else {
						dayStat.addDistance(weekOfMonth - 1,
								swimmerStat.getDistance());
						dayStat.addPresence(weekOfMonth - 1,
								swimmerStat.getPresence() ? 1 : 0);
					}
				}
				dayStat.computeTotalDistance();
				dayStat.computeTotalPresence();
				dayStat.computeTotalMuscu();
				result.addNageur(dayStat);
			}
			Collections.sort(result.getNageurs(), new Comparator<SwimmerStatMonthUi>() {

				@Override
				public int compare(SwimmerStatMonthUi pO1, SwimmerStatMonthUi pO2) {
					return pO1.getNom().compareTo(pO2.getNom());
				}
			});
			
			//Set weeks
			LocalDate firstOfMonth = longDate.dayOfMonth().withMinimumValue();
			int beginWeek = firstOfMonth.weekOfWeekyear().get();
			int endWeek = longDate.dayOfMonth().withMaximumValue().weekOfWeekyear().get();
			for(int i = beginWeek; i <= endWeek; i++) {
				long firstDay = firstOfMonth.withWeekOfWeekyear(i).dayOfWeek().withMinimumValue().toDateTimeAtStartOfDay().getMillis();
				long lastDay = firstOfMonth.withWeekOfWeekyear(i).dayOfWeek().withMaximumValue().toDateTimeAtStartOfDay().getMillis();
				result.addWeek(new CoupleValue<Long, Long>(firstDay, lastDay));
			}
			
			result.setBegin(dayBeginToMindnight);
			result.setEnd(dayEndToMindnight);
		return result;
	}

	@Path("/years")
	@GET
	public SwimmerStatYearListResult getYears(@QueryParam("groupes") Set<Long> groupes,
			@QueryParam("year") String year) {
		SwimmerStatYearListResult result = new SwimmerStatYearListResult();

			DateTime yearDate = ISODateTimeFormat.year().parseDateTime(year);
			Long dayBeginToMindnight = yearDate.withMonthOfYear(DateTimeConstants.SEPTEMBER).minusYears(1).getMillis();
			// Get end of week to midnight
			Long dayEndToMindnight = yearDate.withMonthOfYear(DateTimeConstants.AUGUST).dayOfMonth().withMaximumValue().getMillis();

			// Recuperer les nageur du groupe
			List<DossierNageurEntity> entities = getNageurs(groupes);

			for (DossierNageurEntity nageur : entities) {
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
				statCriteria.add(new CriterionDao<String>(
						SwimmerStatEntityFields.TYPE, null,
						Operator.NULL));
				List<SwimmerStatEntity> statEntities = dao.find(statCriteria);
				SwimmerStatYearUi dayStat = new SwimmerStatYearUi();
				dayStat.setNom(nageur.getNom());
				dayStat.setPrenom(nageur.getPrenom());
				for (SwimmerStatEntity swimmerStat : statEntities) {
					boolean muscu = DayTimeEnum.valueOf(swimmerStat.getDaytime()).equals(DayTimeEnum.MUSCU);
					LocalDate localDate = new LocalDate(swimmerStat.getDay());
					switch (localDate.getMonthOfYear()) {
					case DateTimeConstants.JANUARY: {
						if(muscu) {
							dayStat.addMuscu(0, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(0, swimmerStat.getDistance());
							dayStat.addPresence(0, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					case DateTimeConstants.FEBRUARY: {
						if(muscu) {
							dayStat.addMuscu(1, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(1, swimmerStat.getDistance());
							dayStat.addPresence(1, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					case DateTimeConstants.MARCH: {
						if(muscu) {
							dayStat.addMuscu(2, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(2, swimmerStat.getDistance());
							dayStat.addPresence(2, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					case DateTimeConstants.APRIL: {
						if(muscu) {
							dayStat.addMuscu(3, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(3, swimmerStat.getDistance());
							dayStat.addPresence(3, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					case DateTimeConstants.MAY: {
						if(muscu) {
							dayStat.addMuscu(4, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(4, swimmerStat.getDistance());
							dayStat.addPresence(4, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					case DateTimeConstants.JUNE: {
						if(muscu) {
							dayStat.addMuscu(5, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(5, swimmerStat.getDistance());
							dayStat.addPresence(5, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					case DateTimeConstants.JULY: {
						if(muscu) {
							dayStat.addMuscu(6, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(6, swimmerStat.getDistance());
							dayStat.addPresence(6, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					case DateTimeConstants.AUGUST: {
						if(muscu) {
							dayStat.addMuscu(7, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(7, swimmerStat.getDistance());
							dayStat.addPresence(7, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					case DateTimeConstants.SEPTEMBER: {
						if(muscu) {
							dayStat.addMuscu(8, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(8, swimmerStat.getDistance());
							dayStat.addPresence(8, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					case DateTimeConstants.OCTOBER: {
						if(muscu) {
							dayStat.addMuscu(9, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(9, swimmerStat.getDistance());
							dayStat.addPresence(9, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					case DateTimeConstants.NOVEMBER: {
						if(muscu) {
							dayStat.addMuscu(10, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(10, swimmerStat.getDistance());
							dayStat.addPresence(10, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					case DateTimeConstants.DECEMBER: {
						if(muscu) {
							dayStat.addMuscu(11, swimmerStat.getPresence() ? 1
									: 0);
						} else {
							dayStat.addDistance(11, swimmerStat.getDistance());
							dayStat.addPresence(11, swimmerStat.getPresence() ? 1
									: 0);
						}
					}
						break;
					default://
					}
				}
				dayStat.computeTotalDistance();
				dayStat.computeTotalPresence();
				dayStat.computeTotalMuscu();
				result.addNageur(dayStat);
			}
			result.setBegin(dayBeginToMindnight);
			result.setEnd(dayEndToMindnight);
			Collections.sort(result.getNageurs(), new Comparator<SwimmerStatYearUi>() {

				@Override
				public int compare(SwimmerStatYearUi pO1, SwimmerStatYearUi pO2) {
					return pO1.getNom().compareTo(pO2.getNom());
				}
			});
		return result;
	}
	
	@Path("/periode")
	@GET
	public List<SwimmerStatPeriodeUi> getPeriode(@QueryParam("groupes") Set<Long> groupes,
			@QueryParam("beginDay") Long beginDay, @QueryParam("endDay") Long endDay) {
		List<SwimmerStatPeriodeUi> result = new ArrayList<>();

		// Set to midnight
		LocalDate beginDayAsDate = new LocalDate(beginDay);
		Long beginDayToMindnight = beginDayAsDate.toDateTimeAtStartOfDay().getMillis();

		LocalDate endDayAsDate = new LocalDate(endDay);
		Long endDayToMindnight = endDayAsDate.toDateTimeAtStartOfDay().getMillis();

		// Recuperer les nageur du groupe
		List<DossierNageurEntity> entities = getNageurs(groupes);
		for (DossierNageurEntity nageur : entities) {
			// Recuperer la stat du jour du nageur
			List<CriterionDao<? extends Object>> statCriteria = new ArrayList<CriterionDao<? extends Object>>(2);
			statCriteria
					.add(new CriterionDao<Long>(SwimmerStatEntityFields.DAY, beginDayToMindnight, Operator.GREATER_EQ));
			statCriteria.add(new CriterionDao<Long>(SwimmerStatEntityFields.DAY, endDayToMindnight, Operator.LESS_EQ));
			statCriteria.add(new CriterionDao<Long>(SwimmerStatEntityFields.SWIMMER, nageur.getId(), Operator.EQUAL));
			statCriteria.add(new CriterionDao<String>(
					SwimmerStatEntityFields.TYPE, null,
					Operator.NULL));
			List<SwimmerStatEntity> statEntities = dao.find(statCriteria);
			SwimmerStatPeriodeUi dayStat = new SwimmerStatPeriodeUi();
			dayStat.setNom(nageur.getNom());
			dayStat.setPrenom(nageur.getPrenom());
			for (SwimmerStatEntity swimmerStat : statEntities) {
				if (DayTimeEnum.valueOf(swimmerStat.getDaytime()).equals(DayTimeEnum.MUSCU)) {
					dayStat.addMusculation(swimmerStat);
				} else {
					dayStat.addStat(swimmerStat);
				}
			}
			result.add(dayStat);
		}
		return result;
	}
	
	@Path("/weeks-prevu")
	@GET
	public SwimmerStatWeekPrevuResult getWeeksPrevu(@QueryParam("groupes") Set<Long> groupes,
			@QueryParam("week") String week) {
		SwimmerStatWeekPrevuResult result = new SwimmerStatWeekPrevuResult();

		 DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-'W'ww");
			LocalDate weekAsDate = formatter.parseLocalDate(week);
			Long dayBeginToMindnight = weekAsDate.withDayOfWeek(DateTimeConstants.MONDAY).toDateTimeAtStartOfDay().getMillis();

			// Get end of week to midnight
			Long dayEndToMindnight = weekAsDate.withDayOfWeek(DateTimeConstants.SUNDAY).toDateTimeAtStartOfDay().getMillis();

			// Recuperer les nageur du groupe
			List<DossierNageurEntity> entities = getNageurs(groupes);

			for (DossierNageurEntity nageur : entities) {
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
				statCriteria.add(new CriterionDao<String>(
						SwimmerStatEntityFields.TYPE, SwimmerStatTypeEnum.PREVU.name(),
						Operator.EQUAL));
				List<SwimmerStatEntity> statEntities = dao.find(statCriteria);
				for (SwimmerStatEntity swimmerStat : statEntities) {
					int time = 0;
					switch (DayTimeEnum.valueOf(swimmerStat.getDaytime())) {
					case MATIN:
						time = 0;
						break;
					case MIDI:
						time = 1;
						break;
					case SOIR:
						time = 2;
						break;
					case MUSCU: time = 3;
					break;
					default://
					}
					LocalDate localDate = new LocalDate(swimmerStat.getDay());
					int day = 0;
					switch (localDate.getDayOfWeek()) {
					case DateTimeConstants.MONDAY: day = 0;
						break;
					case DateTimeConstants.TUESDAY: day = 1;
						break;
					case DateTimeConstants.WEDNESDAY: day = 2;
						break;
					case DateTimeConstants.THURSDAY: day = 3;
						break;
					case DateTimeConstants.FRIDAY: day = 4;
						break;
					case DateTimeConstants.SATURDAY: day = 5;
						break;
					case DateTimeConstants.SUNDAY: day = 6;
						break;
					default://
					}
					if(BooleanUtils.isTrue(swimmerStat.getPresence())) {
						result.addNageur(day, time, nageur.getNom(), nageur.getPrenom());
					}
				}
			}
		return result;
	}
}
