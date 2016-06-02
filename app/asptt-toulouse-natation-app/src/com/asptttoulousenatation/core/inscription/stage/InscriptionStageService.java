package com.asptttoulousenatation.core.inscription.stage;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.asptttoulousenatation.core.server.dao.entity.inscription.stage.DossierStageEntity;
import com.asptttoulousenatation.core.server.dao.inscription.stage.DossierStageDao;

@Path("/inscription-stage")
public class InscriptionStageService {
	
	private static final Logger LOG = Logger.getLogger(InscriptionStageService.class
			.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private DossierStageDao dao = new DossierStageDao();
	
	@Path("inscrire")
	@POST
	@Consumes("application/json")
	public void inscrire(DossierStageEntity dossierStage) {
		dao.save(dossierStage);
	}
}