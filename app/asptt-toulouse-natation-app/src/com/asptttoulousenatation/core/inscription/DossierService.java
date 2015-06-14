package com.asptttoulousenatation.core.inscription;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;

@Path("/dossiers")
@Produces("application/json")
public class DossierService {

	private DossierNageurDao dao = new DossierNageurDao();
	
	
	@Path("/find")
	@GET
	@Consumes("application/json")
	public List<DossierResultBean> find(@QueryParam("q") String texteLibre) {
		List<DossierResultBean> result = Collections.emptyList();
		if(StringUtils.isNotBlank(texteLibre)) {
			//Nom prenom email
			String[] splitGuillemets = texteLibre.split("\"");
			for(String slitGuillemets: splitGuillemets) {
			}
		} else {
			result = DossierResultTransformer.getInstance().toUi(dao.getAll());
		}
		return result;
	}
}