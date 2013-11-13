package com.asptttoulousenatation.core.adherent;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionDao;

public class AdherentResource {

	private InscriptionDao dao = new InscriptionDao();
	private Long adherentId;
	
	
	
	public AdherentResource(Long pAdherentId) {
		super();
		adherentId = pAdherentId;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public InscriptionEntity getAdherent() {
		return dao.getAll().get(0);
	}
	
	@DELETE
	public void deleteAdherent() {
		
	}
	
	@PUT
	public Response updateAdherent() {
		System.out.println("PUT");
		return Response.noContent().build();
	}
}
