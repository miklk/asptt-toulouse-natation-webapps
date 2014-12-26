package com.asptttoulousenatation.core.document;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.asptttoulousenatation.core.server.dao.document.LibelleGroupeDao;

@Path("/libellegroupes")
@Produces("application/json")
public class LibelleGroupeService {
	
	private LibelleGroupeDao libelleGroupeDao = new LibelleGroupeDao();

	@GET
	@Path("list")
	public LibelleGroupeResult find() {
		LibelleGroupeResult result = new LibelleGroupeResult();
		result.setGroupes(libelleGroupeDao.getAll());
		return result;
	}
}