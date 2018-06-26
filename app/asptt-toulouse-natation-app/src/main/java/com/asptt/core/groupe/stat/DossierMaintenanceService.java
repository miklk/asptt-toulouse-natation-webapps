package com.asptt.core.groupe.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;

import com.asptt.core.inscription.DossierService;
import com.asptt.core.server.dao.club.group.GroupDao;
import com.asptt.core.server.dao.club.group.SlotDao;
import com.asptt.core.server.dao.entity.club.group.GroupEntity;
import com.asptt.core.server.dao.entity.club.group.SlotEntity;
import com.asptt.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptt.core.server.dao.entity.field.GroupEntityFields;
import com.asptt.core.server.dao.entity.field.SlotEntityFields;
import com.asptt.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptt.core.server.dao.inscription.DossierNageurDao;
import com.asptt.core.server.dao.search.CriterionDao;
import com.asptt.core.server.dao.search.Operator;

@Path("/dossier-maintenance")
@Produces("application/json")
public class DossierMaintenanceService {
	
	private GroupDao groupeDao = new GroupDao();
	private SlotDao slotDao = new SlotDao();
	private DossierNageurDao dossierNageurDao = new DossierNageurDao();
	
	@Path("creneaux")
	@GET
	public int creneaux() {
		for (GroupEntity group : groupeDao.getAll()) {
			// Creation des creneaux
			Map<Long, Integer> counter = new HashMap<Long, Integer>();
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, group
					.getId(), Operator.EQUAL));
			List<SlotEntity> creneaux = slotDao.find(criteria);
			for (SlotEntity creneau : creneaux) {
				counter.put(creneau.getId(), 0);
			}

			criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<Long>(
					DossierNageurEntityFields.GROUPE, group.getId(),
					Operator.EQUAL));
			criteria.add(new CriterionDao<Long>(
					DossierNageurEntityFields.SAISON, DossierService.NEW_SAISON,
					Operator.EQUAL));
			DossierNageurDao dossierNageurDao = new DossierNageurDao();
			List<DossierNageurEntity> adherents = dossierNageurDao.find(criteria);
			for (DossierNageurEntity adherent : adherents) {
				String creneau = adherent.getCreneaux();
				if (StringUtils.isNotBlank(creneau)) {
					for (String creneauSplit : creneau.split(";")) {
						final String creneauId;
						if (StringUtils.contains(creneauSplit, "_")) {
							creneauId = creneauSplit.split("_")[1];
						} else {
							creneauId = creneauSplit;
						}
						if (StringUtils.isNumeric(creneauId)) {
							Long creneauIdentifier = Long.valueOf(creneauId);
							final int count;
							if (counter.containsKey(creneauIdentifier)) {
								count = counter.get(creneauIdentifier) + 1;
								counter.put(creneauIdentifier, count);
							}

						}
					}
				}
			}

			for (Map.Entry<Long, Integer> creneauEntry : counter.entrySet()) {
				SlotEntity creneauEntity = slotDao.get(creneauEntry.getKey());
				creneauEntity.setPlaceRestante(creneauEntity
						.getPlaceDisponible() - creneauEntry.getValue());
				slotDao.save(creneauEntity);
			}
		}
		return 0;
	}
	
	@Path("groupes")
	@GET
	public int groupes() {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(GroupEntityFields.COMPETITION, Boolean.TRUE, Operator.EQUAL));
		List<GroupEntity> groupes = groupeDao.find(criteria);
		for(GroupEntity groupe: groupes) {
			List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.GROUPE, groupe.getId(), Operator.EQUAL));
			Long total = dossierNageurDao.count(criteriaNageur);
			groupe.setOccupe(total.intValue());
			groupeDao.save(groupe);
		}
		return groupes.size();
	}
}