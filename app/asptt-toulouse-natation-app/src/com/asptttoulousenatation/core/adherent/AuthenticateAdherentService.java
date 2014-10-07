package com.asptttoulousenatation.core.adherent;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/inscription")
@Produces("application/json")
public class AuthenticateAdherentService {
	
	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;
	
	@Path("{authenticate}")
	public AuthenticateAdherentResource authenticate(@QueryParam("email") String email, @QueryParam("mdp") String motDePasse) {
		return new AuthenticateAdherentResource(email, motDePasse);
		
	}
}