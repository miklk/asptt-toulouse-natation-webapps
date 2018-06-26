package com.asptt.core.page;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/pages")
@Produces("application/json")
public class PageService {
	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;
	
	@Path("{page}")
	public PageResource getPage(@PathParam("page") String menuTitle) {
		return new PageResource(menuTitle);
	}
}