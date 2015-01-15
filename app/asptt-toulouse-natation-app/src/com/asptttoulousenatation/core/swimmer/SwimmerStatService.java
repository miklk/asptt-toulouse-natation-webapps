package com.asptttoulousenatation.core.swimmer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatDataUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatEnum.SwimmerStatDayTime;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;
import com.asptttoulousenatation.server.userspace.admin.entity.SwimmerStatDayTransformer;

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
			statCriteria.add(new CriterionDao<Long>(SwimmerStatEntityFields.DAY,
					dayToMindnight, Operator.EQUAL));
			statCriteria.add(new CriterionDao<Long>(
					SwimmerStatEntityFields.SWIMMER, nageur.getId(),
					Operator.EQUAL));
			List<SwimmerStatEntity> statEntities = dao
					.find(statCriteria);
			SwimmerStatUi dayStat = new SwimmerStatUi();
			dayStat.setAdherent(nageur.getId());
			dayStat.setNom(nageur.getNom());
			dayStat.setPrenom(nageur.getPrenom());
			for (SwimmerStatEntity swimmerStat : statEntities) {
				dayTransformer.update(dayStat, swimmerStat);
			}
			result.addStat(nageur.getId(), dayStat);
		}
		return result;
	}
	
	@PUT
	@Path("{nageur}")
	@Consumes("application/json")
	public SwimmerStatUpdateResult update(@PathParam("nageur") Long nageur, SwimmerStatUpdateAction action) {
		SwimmerStatUpdateResult result = new SwimmerStatUpdateResult();
		SwimmerStatUi stat = action.getStat();
		updateStat(SwimmerStatDayTime.valueOf(action.getDayTime()), action.getDay(), stat);
		return result;
	}

	private void updateStat(SwimmerStatDayTime dayTime, Long day, SwimmerStatUi stat) {
		final SwimmerStatEntity entity;
		if(stat.getId() != null) {
			entity = dao.get(stat.getId());
		} else {
			entity = new SwimmerStatEntity();
		}
		entity.setSwimmer(stat.getAdherent());
		
		SwimmerStatDataUi statData = null;
		switch(dayTime) {
		case MATIN:
			statData = stat.getMorning();
			break;
		case MIDI: statData = stat.getMidday();
		break;
		case SOIR: statData = stat.getNight();
		break;
		case MUSCU: statData = stat.getBodybuilding();
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
		for(SwimmerStatUi stat: action.getSwimmers().values()) {
			if(stat.isSelected()) {
				updateStat(action.getDayTime(), action.getDay(), stat);
			}
		}
		return result;
	}
}
