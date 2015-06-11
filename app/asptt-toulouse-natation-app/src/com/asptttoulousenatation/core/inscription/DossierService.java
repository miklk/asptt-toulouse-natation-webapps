package com.asptttoulousenatation.core.inscription;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;

@Path("/dossiers")
@Produces("application/json")
public class DossierService {

	private DossierDao dao = new DossierDao();
	
	
	@Path("/find")
	@GET
	@Consumes("application/json")
	public List<DossierUi> find(FindDossierParameters parameters) {
		List<DossierUi> result = Collections.emptyList();
		if(StringUtils.isNotBlank(parameters.getTexteLibre())) {
			//Nom prenom email
			String[] splitGuillemets = parameters.getTexteLibre().split("\"");
			for(String slitGuillemets: splitGuillemets) {
			}
		} else {
			result = DossierTransformer.getInstance().toUi(dao.getAll());
		}
		return result;
	}
}