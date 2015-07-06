package com.asptttoulousenatation.core.groupe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.GroupEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;

@Path("/groupes")
@Produces("application/json")
public class GroupeService {

	private static final Logger LOG = Logger.getLogger(GroupeService.class
			.getName());

	private GroupDao dao = new GroupDao();
	private GroupTransformer transformer = new GroupTransformer();
	
	private SlotDao slotDao = new SlotDao();
	private DossierNageurDao dossierNageurDao = new DossierNageurDao();

	@GET
	public GroupesUi getGroupes(@QueryParam("nouveau") Boolean nouveau) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(GroupEntityFields.INSCRIPTION, Boolean.TRUE,
				Operator.EQUAL));
		if(BooleanUtils.isTrue(nouveau)) {
			criteria.add(new CriterionDao<Boolean>(GroupEntityFields.NOUVEAU, Boolean.TRUE,
					Operator.EQUAL));
		}
		List<GroupEntity> lEntities = dao.find(criteria);
		List<GroupUi> lUis = new GroupTransformer().toUi(lEntities);
		Collections.sort(lUis, new Comparator<GroupUi>() {

			public int compare(GroupUi pO1, GroupUi pO2) {
				return pO1.getTitle().compareTo(pO2.getTitle());
			}
		});
		
		GroupesUi result = new GroupesUi();
		result.setGroups(lUis);
		return result;
	}
	
	@Path("/all")
	@GET
	public GroupesUi getGroupes() {
		List<GroupEntity> lEntities = dao.getAll();
		List<GroupUi> lUis = new GroupTransformer().toUi(lEntities);
		Collections.sort(lUis, new Comparator<GroupUi>() {

			public int compare(GroupUi pO1, GroupUi pO2) {
				return pO1.getTitle().compareTo(pO2.getTitle());
			}
		});
		
		GroupesUi result = new GroupesUi();
		result.setGroups(lUis);
		return result;
	}
	
	@Path("/{groupe}")
	@GET
	public GroupUi getGroupe(@PathParam("groupe") String groupe) {
		final GroupUi result;
		if (StringUtils.isNotBlank(groupe)) {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<String>(GroupEntityFields.TITLE,
					groupe, Operator.EQUAL));
			List<GroupEntity> entities = dao.find(criteria);
			if (CollectionUtils.isNotEmpty(entities)) {
				result = transformer.toUi(entities.get(0));
			} else {
				result = null;
			}
		} else {
			result = null;
		}
		return result;
	}
	
	@Path("/create")
	@POST
	public GroupeCreateResult create(GroupUi action) {
		final GroupeCreateResult result = new GroupeCreateResult();
		if (StringUtils.isNotBlank(action.getTitle())) {
			final GroupEntity entity;
			//Create or update
			if (action.getId() == null) {
				List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteria.add(new CriterionDao<String>(GroupEntityFields.TITLE,
						action.getTitle(), Operator.EQUAL));
				List<GroupEntity> entities = dao.find(criteria);
				if (CollectionUtils.isNotEmpty(entities)) {
					entity = entities.get(0);
					result.setCreation(false);
				} else {
					entity = new GroupEntity();
					result.setCreation(true);
				}
			} else {
				entity = dao.get(action.getId());
				result.setCreation(false);
			}
			entity.setTitle(action.getTitle());
			entity.setDescription(action.getDescription());
			entity.setLicenceFfn(action.isLicenceFfn());
			entity.setInscription(action.isInscription());
			entity.setSeanceunique(action.isSeanceunique());
			entity.setNouveau(action.isNouveau());
			entity.setEnf(action.isEnf());
			entity.setCompetition(action.isCompetition());
			entity.setTarifWeight(action.getTarifWeight());
			entity.setTarif(action.getTarif());
			entity.setTarif2(action.getTarif2());
			entity.setTarif3(action.getTarif3());
			entity.setTarif4(action.getTarif4());
			entity.setTarifUnique(action.getTarifUnique() == 0 ? action.getTarif() : action.getTarifUnique());
			entity.setTarifUnique2(action.getTarifUnique2() == 0 ? action.getTarif2() : action.getTarifUnique2());
			entity.setTarifUnique3(action.getTarifUnique3() == 0 ? action.getTarif3() : action.getTarifUnique3());
			entity.setTarifUnique4(action.getTarifUnique4() == 0 ? action.getTarif4() : action.getTarifUnique4());
			entity.setSecondes(action.isSecondes());
			GroupEntity entityCreated = dao.save(entity);
			result.setGroupe(transformer.toUi(entityCreated));
		} else {
			result.setGroupe(action);
		}
		return result;
	}
	
	@Path("{groupe}")
	@DELETE
	public void delete(@PathParam("groupe") Long groupeId) {
		//Suppression des créneaux
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, groupeId,
				Operator.EQUAL));
		List<SlotEntity> creneaux = slotDao.find(criteria);
		if(CollectionUtils.isNotEmpty(creneaux)) {
			for(SlotEntity creneau: creneaux) {
				slotDao.delete(creneau);
			}
		}
		
		//Suppression des nageurs associés
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.GROUPE, groupeId,
				Operator.EQUAL));
		List<DossierNageurEntity> nageurs = dossierNageurDao.find(criteria);
		if(CollectionUtils.isNotEmpty(nageurs)) {
			for(DossierNageurEntity nageur: nageurs) {
				nageur.setGroupe(null);
				nageur.setCreneaux(null);
				dossierNageurDao.save(nageur);
			}
		}
		dao.delete(groupeId);
	}
}