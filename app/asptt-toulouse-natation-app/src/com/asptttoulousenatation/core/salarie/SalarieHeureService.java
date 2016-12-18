package com.asptttoulousenatation.core.salarie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.asptttoulousenatation.core.authentication.TokenManager;
import com.asptttoulousenatation.core.boutique.BoutiqueService;
import com.asptttoulousenatation.core.server.dao.entity.salarie.SalarieActiviteEntity;
import com.asptttoulousenatation.core.server.dao.entity.salarie.SalarieHeureEntity;
import com.asptttoulousenatation.core.server.dao.salarie.SalarieActiviteDao;
import com.asptttoulousenatation.core.server.dao.salarie.SalarieHeureDao;

@Path("/salarie/heure")
@Produces("application/json")
public class SalarieHeureService {

	private static final Logger LOG = Logger.getLogger(SalarieHeureService.class.getName());
	private SalarieHeureDao dao = new SalarieHeureDao();
	private SalarieActiviteDao activiteDao = new SalarieActiviteDao();

	@Path("{week}/{token}")
	@GET
	public List<SalarieHeureDay> loadWeek(@PathParam("week") String week, @PathParam("token") String token) {
		Long user = TokenManager.getInstance().getUser(token);
		DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-'W'ww");
		LocalDate weekAsDate = formatter.parseLocalDate(week);
		DateTime dayBeginToMindnight = weekAsDate.withDayOfWeek(DateTimeConstants.MONDAY).toDateTimeAtStartOfDay();
		DateTime dayEndToMindnight = dayBeginToMindnight.plusWeeks(1);

		List<SalarieHeureEntity> heures = dao.findByBeginEndAndUser(dayBeginToMindnight.toDate(),
				dayEndToMindnight.toDate(), user);
		Map<Integer, SalarieHeureDay> days = new HashMap<>();
		for (SalarieHeureEntity heure : heures) {
			LocalDate day = new LocalDate(heure.getBegin().getTime());
			final SalarieHeureDay heureDay;
			if (days.containsKey(day.dayOfWeek().get())) {
				heureDay = days.get(day.dayOfWeek().get());
			} else {
				heureDay = new SalarieHeureDay();
				heureDay.setDay(day.toDate());
				days.put(day.dayOfWeek().get(), heureDay);
			}
			heureDay.addHeure(heure);
		}

		// Completer les vides avec 3 lignes
		LocalDate begin = dayBeginToMindnight.toLocalDate();
		for (int i = 0; i < 7; i++) {
			LocalDate dayPlus = begin.plusDays(i);
			if (!days.containsKey(dayPlus.dayOfWeek().get())) {
				SalarieHeureDay day = new SalarieHeureDay();
				day.setDay(dayPlus.toDate());
				day.addHeure(new SalarieHeureEntity());
				day.addHeure(new SalarieHeureEntity());
				day.addHeure(new SalarieHeureEntity());
				days.put(dayPlus.dayOfWeek().get(), day);
			}
		}

		return new ArrayList<>(days.values());
	}

	@Path("{token}")
	@POST
	@Consumes("application/json")
	public void valider(@PathParam("token") String token, List<SalarieHeureDay> days) {
		Long user = TokenManager.getInstance().getUser(token);
		if(user != null) {
			for (SalarieHeureDay day : days) {
				DateTime currentDay = new DateTime(day.getDay().getTime());
				for (SalarieHeureEntity heure : day.getHeures()) {
					String activiteTitle = StringUtils.upperCase(StringUtils.trimToEmpty(heure.getActivite()));
					if (StringUtils.isNotBlank(activiteTitle)) {
						heure.setActivite(activiteTitle);
						heure.setBegin(currentDay.withTime(LocalTime.fromMillisOfDay(heure.getBegin().getTime())).toDate());
						heure.setEnd(currentDay.withTime(LocalTime.fromMillisOfDay(heure.getEnd().getTime())).toDate());
						heure.setCreatedBy(user + "");
						heure.setUpdatedBy(user + "");
						heure.setUser(user);
						dao.save(heure);
						
						SalarieActiviteEntity activite = new SalarieActiviteEntity();
						activite.setIntitule(activiteTitle);
						activiteDao.save(activite);
					}
				}
			}
		} else {
			LOG.info("Not user with token : " + token);
		}
	}
	
	@Path("{month}")
	@GET
	public Map<Integer, Map<Integer, List<SalarieHeureEntity>>> loadMonth(@PathParam("month") String month) {
		LocalDate monthAsDate = ISODateTimeFormat.yearMonth().parseLocalDate(month);
		DateTime dayBeginToMindnight = monthAsDate.dayOfMonth().withMinimumValue().toDateTimeAtStartOfDay();
		DateTime dayEndToMindnight = dayBeginToMindnight.plusMonths(1);

		List<SalarieHeureEntity> heures = dao.findByBeginEnd(dayBeginToMindnight.toDate(),
				dayEndToMindnight.toDate());
		
		Map<Long, List<SalarieHeureEntity>> days = new HashMap<>();
		//Init month
		int maxDayInMonth = dayBeginToMindnight.dayOfMonth().getMaximumValue();
		for(int i = 0; i < maxDayInMonth ; i++) {
			days.put(Long.valueOf(dayBeginToMindnight.plusDays(i).getMillis()), new ArrayList<SalarieHeureEntity>());
		}
		for (SalarieHeureEntity heure : heures) {
			LocalDate day = new LocalDate(heure.getBegin().getTime());
			Long dayAsLong = day.toDateTimeAtStartOfDay().getMillis();
			if (days.containsKey(dayAsLong)) {
				List<SalarieHeureEntity> heuresDay = days.get(dayAsLong);
				heuresDay.add(heure);
				days.put(dayAsLong, heuresDay);
			}
		}
		
		Map<Integer, Map<Integer, List<SalarieHeureEntity>>> weeks = new HashMap<>();
		for(Entry<Long, List<SalarieHeureEntity>> entry : days.entrySet()) {
			DateTime keyAsDateTime = new DateTime(entry.getKey());
			Integer week = keyAsDateTime.getWeekOfWeekyear();
			Integer day = keyAsDateTime.getDayOfMonth();
			if(weeks.containsKey(week)) {
				Map<Integer, List<SalarieHeureEntity>> dayOfMonth = weeks.get(week);
				if(dayOfMonth.containsKey(day)) {
					dayOfMonth.get(day).addAll(entry.getValue());
				} else {
					dayOfMonth.put(day, entry.getValue());
				}
			} else {
				Map<Integer, List<SalarieHeureEntity>> dayOfMonth = new HashMap<>();
				dayOfMonth.put(day, entry.getValue());
				weeks.put(week, dayOfMonth);
			}
		}
		return weeks;
	}
}