package com.asptttoulousenatation.core.nageur;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/nageurpresence")
public class NageurPresence {

	@GET
	@Produces("text/plain")
	public String presence() {
		return "presence";
	}
}
