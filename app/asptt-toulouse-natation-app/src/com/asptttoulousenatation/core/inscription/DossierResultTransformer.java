package com.asptttoulousenatation.core.inscription;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.server.userspace.admin.entity.AbstractEntityTransformer;

public class DossierResultTransformer extends AbstractEntityTransformer<DossierResultBean, DossierNageurEntity> {

	private static DossierResultTransformer INSTANCE;
	
	private DossierDao dossierDao = new DossierDao();
	private GroupDao groupeDao = new GroupDao();
	private SlotDao creneauDao = new SlotDao();
	
	public static DossierResultTransformer getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new DossierResultTransformer();
		}
		return INSTANCE;
	}
	
	@Override
	public DossierResultBean toUi(DossierNageurEntity pEntity) {
		DossierResultBean bean = new DossierResultBean();
		bean.setNom(pEntity.getNom());
		bean.setPrenom(pEntity.getPrenom());
		bean.setNaissance(pEntity.getNaissance());
		bean.setUpdated(pEntity.getUpdated());
		
		bean.setNageur(pEntity);
		
		//Groupe
		bean.setGroupe(getGroupe(pEntity.getGroupe()));
		
		//Creneaux
		bean.setCreneaux(getCreneaux(pEntity.getCreneaux()));
		
		//Données dossier
		DossierEntity dossier = dossierDao.get(pEntity.getDossier());
		bean.setEmail(dossier.getEmail());
		bean.setMotdepasse(dossier.getMotdepasse());
		bean.setDossierId(dossier.getId());
		bean.setComment(dossier.getComment());
		bean.setEtat(dossier.getStatut());
		return bean;
	}

	private String getGroupe(Long groupeId) {
		final String groupe;
		if(groupeId != null && groupeId != 0) {
			GroupEntity entity = groupeDao.get(groupeId);
			groupe = entity.getTitle();
		} else {
			groupe = "INCONNU";
		}
		return groupe;
	}

	public Set<String> getCreneaux(String creneaux) {
		Set<String> results = new LinkedHashSet<String>();
		if (StringUtils.isNotBlank(creneaux)) {
			String[] creneauSplit = creneaux.split(";");
			for (String creneau : creneauSplit) {
				if (StringUtils.isNotBlank(creneau)) {
					final String creneauId;
					if (StringUtils.contains(creneau, "_")) {
						creneauId = creneau.split("_")[1];
					} else {
						creneauId = creneau;
					}
					if (StringUtils.isNumeric(creneauId)) {
						SlotEntity slotEntity = creneauDao.get(Long
								.valueOf(creneauId));

						if (slotEntity != null) {
							String creneauStr = slotEntity.getDayOfWeek() + " ";
							if (slotEntity.getBeginDt() != null) {
								DateTime beginDt = new DateTime(slotEntity
										.getBeginDt().getTime());
								DateTime endDt = new DateTime(slotEntity
										.getEndDt().getTime());
								creneauStr = creneauStr
										+ beginDt.toString("HH:mm", Locale.FRANCE) + "-"
										+ endDt.toString("HH:mm", Locale.FRANCE);
							} else {
								creneauStr = creneauStr + " - "

								+ slotEntity.getSwimmingPool();

							}
							results.add(creneauStr);
						}
					}
				}
			}
		}
		return results;
	}
	
}