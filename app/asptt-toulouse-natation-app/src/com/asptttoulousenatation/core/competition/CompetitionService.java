package com.asptttoulousenatation.core.competition;

import java.util.logging.Logger;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.asptttoulousenatation.core.server.dao.competition.CompetitionDao;
import com.asptttoulousenatation.core.server.dao.competition.CompetitionEpreuveDao;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEpreuveEntity;

@Path("/competitions")
@Produces("application/json")
public class CompetitionService {
	private static final Logger LOG = Logger.getLogger(CompetitionService.class
			.getName());

	private CompetitionDao dao = new CompetitionDao();
	private CompetitionEpreuveDao epreuveDao = new CompetitionEpreuveDao();
	
	@PUT
	@Path("/create")
	public void create(CompetitionCreateParameters parameters) {
		dao.save(parameters.getCompetition());
		for(CompetitionEpreuveEntity epreuve: parameters.getEpreuves()) {
			epreuveDao.save(epreuve);
		}
	}
}
