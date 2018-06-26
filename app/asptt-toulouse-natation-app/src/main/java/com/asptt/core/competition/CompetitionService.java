package com.asptt.core.competition;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.asptt.core.server.dao.competition.CompetitionDao;
import com.asptt.core.server.dao.competition.CompetitionEpreuveDao;
import com.asptt.core.server.dao.entity.competition.CompetitionEntity;
import com.asptt.core.server.dao.entity.competition.CompetitionEpreuveEntity;

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
		CompetitionEntity competition = dao.save(parameters.getCompetition());
		for(CompetitionEpreuveEntity epreuve: parameters.getEpreuves()) {
			epreuve.setCompetition(competition.getId());
			epreuveDao.save(epreuve);
		}
	}
	
	@GET
	public List<CompetitionEntity> list() {
		List<CompetitionEntity> list = new ArrayList<>(dao.getAll());
		return list;
	}
	
	@Path("{competition}")
	@GET
	public List<CompetitionEpreuveEntity> epreuves(@PathParam("competition") Long competition) {
		List<CompetitionEpreuveEntity> epreuves = epreuveDao.findByCompetition(competition);
		return new ArrayList<>(epreuves);
	}
}
