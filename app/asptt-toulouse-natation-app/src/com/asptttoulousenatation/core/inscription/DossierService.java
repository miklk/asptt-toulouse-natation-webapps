package com.asptttoulousenatation.core.inscription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

@Path("/dossiers")
@Produces("application/json")
public class DossierService {

	private DossierNageurDao dao = new DossierNageurDao();
	private DossierDao dossierDao = new DossierDao();
	private GroupDao groupeDao = new GroupDao();
	
	
	@Path("/find")
	@GET
	@Consumes("application/json")
	public List<DossierResultBean> find(@QueryParam("query") String texteLibre, @QueryParam("groupe") Long groupe) {
		List<DossierResultBean> result = Collections.emptyList();
		List<DossierNageurEntity> nageurs = new ArrayList<DossierNageurEntity>();
		if(StringUtils.isNotBlank(texteLibre)) {
			/**Nom prenom email
			String[] splitGuillemets = texteLibre.split("\"");
			for(String slitGuillemets: splitGuillemets) {
			}**/
			if(texteLibre.contains("@")) {
				List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteria.add(new CriterionDao<String>(DossierEntityFields.EMAIL,
						texteLibre, Operator.EQUAL));
				List<DossierEntity> entities = dossierDao.find(criteria);
				for(DossierEntity dossier: entities) {
					List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
							1);
					criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
							dossier.getId(), Operator.EQUAL));
					nageurs.addAll(dao.find(criteriaNageur));
				}
			} else {
				List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteriaNageur.add(new CriterionDao<String>(DossierNageurEntityFields.NOM,
						texteLibre.toUpperCase(), Operator.EQUAL));
				nageurs.addAll(dao.find(criteriaNageur));
			}
			
		} else if(groupe != null && groupe > 0) {
			List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.GROUPE,
					groupe, Operator.EQUAL));
			nageurs.addAll(dao.find(criteriaNageur));
		} else {
			nageurs = dao.getAll();
		}
		result = DossierResultTransformer.getInstance().toUi(nageurs);
		return result;
	}
	
	@Path("{dossier}")
	@GET
	public DossierUi findOne(@PathParam("dossier") Long dossierId) {
		DossierUi dossierUi = new DossierUi();
		DossierEntity dossier = dossierDao.get(dossierId);
		dossierUi.setPrincipal(dossier);
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
				dossierId, Operator.EQUAL));
		List<DossierNageurEntity> entities = dao.find(criteria);
		for(DossierNageurEntity entity: entities) {
			dossierUi.addNageur(entity);
		}
		return dossierUi;
	}
	
	@Path("changerGroupe")
	@POST
	public DossierResultBean changerGroupe(DossierNageurEntity nageur) {
		final DossierResultBean result;
		if (nageur.getGroupe() != null) {
			DossierNageurEntity entity = dao.get(nageur.getId());
			GroupEntity groupe = groupeDao.get(nageur.getGroupe());
			if (groupe != null) {
				entity.setGroupe(nageur.getGroupe());
				entity.setCreneaux(null);
				DossierNageurEntity entitySaved = dao.save(entity);
				result = DossierResultTransformer.getInstance().toUi(
						entitySaved);
			} else {
				result = null;
			}
		} else {
			result = null;
		}
		return result;
	}
	
	@Path("creer")
	@POST
	public boolean creer(DossierCreerParameters parameters) {
		boolean result;
		// Test existante du dossier même e-mail
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.EMAIL,
				parameters.getEmail(), Operator.EQUAL));
		List<DossierEntity> entities = dossierDao.find(criteria);
		if (CollectionUtils.isNotEmpty(entities)) {
			result = false;
		} else {
			DossierEntity entity = new DossierEntity();
			entity.setEmail(parameters.getEmail());
			entity.setMotdepasse(parameters.getMdp());
			DossierEntity entityCreated = dossierDao.save(entity);
			DossierNageurEntity nageur = new DossierNageurEntity();
			nageur.setDossier(entityCreated.getId());
			dao.save(nageur);
			result = true;
		}
		return result;
	}
}