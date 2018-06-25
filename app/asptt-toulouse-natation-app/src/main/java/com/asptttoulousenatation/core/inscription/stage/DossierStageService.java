package com.asptttoulousenatation.core.inscription.stage;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.asptttoulousenatation.core.server.dao.entity.inscription.stage.DossierStageEntity;
import com.asptttoulousenatation.core.server.dao.inscription.stage.DossierStageDao;

@Path("/dossiers-stage")
@Produces("application/json")
public class DossierStageService {
	
	private static final Logger LOG = Logger.getLogger(DossierStageService.class
			.getName());

	private DossierStageDao dao = new DossierStageDao();
	
	
	@Path("/find")
	@GET
	@Consumes("application/json")
	public List<DossierStageEntity> find(@QueryParam("query") String texteLibre, @QueryParam("groupe") Long groupe, @QueryParam("sansGroupe") Boolean sansGroupe, @QueryParam("dossierStatut") final String dossierStatut, @QueryParam("creneau") final Long creneau, @QueryParam("filter_facture") final Boolean facture, @QueryParam("filter_facture2") final Boolean facture2, @QueryParam("certificat") final Boolean certificat, @QueryParam("certificat2") final Boolean certificat2, @QueryParam("certificatNon") final Boolean certificatNon) {
		return dao.getAll();
	}
}