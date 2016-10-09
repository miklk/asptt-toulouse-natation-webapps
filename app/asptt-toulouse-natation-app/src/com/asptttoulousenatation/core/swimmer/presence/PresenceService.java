package com.asptttoulousenatation.core.swimmer.presence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SwimmerStatEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.PresenceToken;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerStatEntity;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.swimmer.PresenceTokenDao;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerStatDao;
import com.asptttoulousenatation.core.swimmer.DayTimeEnum;

@Path("/presences")
@Produces("application/json")
public class PresenceService {

	private PresenceTokenDao dao = new PresenceTokenDao();
	private DossierNageurDao nageurDao = new DossierNageurDao();
	private SwimmerStatDao swimmerStatDao = new SwimmerStatDao();
	private GroupDao groupDao = new GroupDao();
	
	@Path("/init/{groupe}")
	@GET
	public void init(@PathParam("groupe") String groupe) {
		List<GroupEntity> groupes = groupDao.findByTitle(groupe);
		GroupEntity groupeEntity = groupes.get(0);
		
		//Remove previous week
		dao.deleteByGroupe(groupeEntity.getId());
		
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Long>(
				DossierNageurEntityFields.GROUPE, groupeEntity.getId(), Operator.EQUAL));
		List<DossierNageurEntity> entities = new ArrayList<>(nageurDao.find(criteria));
		DateTime begin = DateTime.now();
		for(DossierNageurEntity nageur : entities) {
			PresenceToken token = new PresenceToken();
			token.setId(UUID.randomUUID().toString());
			token.setSwimmer(nageur.getId());
			token.setBegin(begin.toDate());
			token.setGroupe(groupeEntity.getId());
			dao.save(token);
		}
	}

	@Path("{token}")
	@GET
	public PresenceTokenResult access(@PathParam("token") String token) {
		final PresenceTokenResult result = new PresenceTokenResult();
		PresenceToken presenceToken = dao.findById(token);
		if (presenceToken != null) {
			// Find swimmer
			DossierNageurEntity nageur = nageurDao.get(presenceToken.getSwimmer());
			result.setNom(nageur.getNom());
			result.setPrenom(nageur.getPrenom());
			result.setSwimmer(presenceToken.getSwimmer());
			DateTime begin = new DateTime(presenceToken.getBegin().getTime());
			result.setBegin(begin);
			result.setEnd(begin.plusDays(7));
			// Recherche des données déjà saisies (type TEMP)
			Long dayBeginToMindnight = result.getBegin().withTimeAtStartOfDay().getMillis();
			Long dayEndToMindnight = result.getEnd().withTimeAtStartOfDay().getMillis();
			List<CriterionDao<? extends Object>> statCriteria = new ArrayList<CriterionDao<? extends Object>>(2);
			statCriteria
					.add(new CriterionDao<Long>(SwimmerStatEntityFields.DAY, dayBeginToMindnight, Operator.GREATER_EQ));
			statCriteria.add(new CriterionDao<Long>(SwimmerStatEntityFields.DAY, dayEndToMindnight, Operator.LESS_EQ));
			statCriteria.add(new CriterionDao<Long>(SwimmerStatEntityFields.SWIMMER, nageur.getId(), Operator.EQUAL));
			statCriteria.add(
					new CriterionDao<String>(SwimmerStatEntityFields.TYPE, SwimmerStatEntity.TEMP_TYPE, Operator.NULL));
			List<SwimmerStatEntity> statEntities = swimmerStatDao.find(statCriteria);
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
				case MUSCU:
					index = 3;
					break;
				default://
				}
				LocalDate localDate = new LocalDate(swimmerStat.getDay());
				switch (localDate.getDayOfWeek()) {
				case DateTimeConstants.MONDAY: {
					result.addJour(0, index, swimmerStat.getPresence());
				}
					break;
				case DateTimeConstants.TUESDAY: {
					result.addJour(1, index, swimmerStat.getPresence());
				}
					break;
				case DateTimeConstants.WEDNESDAY: {
					result.addJour(2, index, swimmerStat.getPresence());
				}
					break;
				case DateTimeConstants.THURSDAY: {
					result.addJour(3, index, swimmerStat.getPresence());
				}
					break;
				case DateTimeConstants.FRIDAY: {
					result.addJour(4, index, swimmerStat.getPresence());
				}
					break;
				case DateTimeConstants.SATURDAY: {
					result.addJour(5, index, swimmerStat.getPresence());
				}
					break;
				case DateTimeConstants.SUNDAY: {
					result.addJour(6, index, swimmerStat.getPresence());
				}
					break;
				default:
				}
			}
		}
		return result;
	}
}
