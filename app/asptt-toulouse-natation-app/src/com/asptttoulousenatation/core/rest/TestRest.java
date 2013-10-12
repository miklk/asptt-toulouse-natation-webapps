package com.asptttoulousenatation.core.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/test")
public class TestRest {

	@GET
	@Produces("text/plain")
	public String test() {
		return "Yahouuuuu !!! ";
	}
}
