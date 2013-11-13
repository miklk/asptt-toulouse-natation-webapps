package com.asptttoulousenatation.core.adherent;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionDao;

@Path("/adherents")
@Produces("application/json")
public class AdherentService {
	
	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;
	
	private InscriptionDao dao = new InscriptionDao();

	@GET
	public List<InscriptionEntity> getAdherents() {
		return dao.getAll();
	}
	
	@Path("{adherent}")
	public AdherentResource getAdherent(@PathParam("adherentId") Long adherentId) {
		return new AdherentResource(adherentId);
	}
}