package com.asptttoulousenatation.web.adherent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;

public class AdherentListResultBeanTransformer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9206273383357950266L;
	
	private static final Logger LOG = Logger.getLogger(AdherentListResultBeanTransformer.class
			.getName());

	private static AdherentListResultBeanTransformer INSTANCE;

	public static AdherentListResultBeanTransformer getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AdherentListResultBeanTransformer();
		}
		return INSTANCE;
	}

	private GroupDao groupDao = new GroupDao();
	private SlotDao slotDao = new SlotDao();

	private Inscription2Dao inscription2Dao = new Inscription2Dao();
	
	public AdherentListResultBean get2(InscriptionEntity2 entity) {
		AdherentListResultBean bean = new AdherentListResultBean();
		bean.setId(entity.getId());
		bean.setNom(entity.getNom());
		bean.setPrenom(entity.getPrenom());
		bean.setDateNaissance(entity.getDatenaissance());

		bean.setGroupe(getGroupe(entity.getNouveauGroupe()));
		try {
			bean.setCreneaux(getCreneaux(entity.getCreneaux()));
		} catch(Exception e) {
			LOG.severe("Erreur de créneaux sur l'adhérent " + entity.getId());
		}
		bean.setEmail(entity.getEmail());
		bean.setMotdepasse(entity.getMotdepasse());
		if(entity.getInscriptiondt() != null) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			bean.setInscriptionDt(format.format(entity.getInscriptiondt()));
		} else {
			bean.setInscriptionDt("Pas inscrit");
		}
		if(entity.getPrincipal() != null) {
			try {
			InscriptionEntity2 principal = inscription2Dao.get(entity.getPrincipal());
			bean.setEmailPrincipal(principal.getEmail());
			} catch(Exception e) {
				LOG.severe("Erreur principal (" + entity.getPrincipal() + "): " + e.getMessage());
			}
		}
		bean.setCertificat(BooleanUtils.toBoolean(entity.getCertificat()));
		bean.setPaiement(BooleanUtils.toBoolean(entity.getPaiement()));
		bean.setComplet(BooleanUtils.toBoolean(entity.getComplet()));
		return bean;
	}

	public List<AdherentListResultBean> get2(List<InscriptionEntity2> entities) {
		List<AdherentListResultBean> results = new ArrayList<AdherentListResultBean>(
				entities.size());
		for (InscriptionEntity2 entity : entities) {
			results.add(get2(entity));
		}
		return results;
	}

	private String getGroupe(Long groupeId) {
		final String groupe;
		if(groupeId != null && groupeId != 0) {
			GroupEntity entity = groupDao.get(groupeId);
			groupe = entity.getTitle();
		} else {
			groupe = "";
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
						SlotEntity slotEntity = slotDao.get(Long
								.valueOf(creneauId));
						
						String beginDtAsString = new DateTime(slotEntity.getBeginDt().getTime()).plusHours(1).toString("HH:mm");
						String endDtAsString = new DateTime(slotEntity.getEndDt().getTime()).plusHours(1).toString("HH:mm");
						
						String creneauStr = slotEntity.getDayOfWeek()
								+ " "
								+ beginDtAsString
								+ " - "
								+ endDtAsString
								+ " - "
								+ slotEntity.getSwimmingPool();
						results.add(creneauStr);
					}
				}
			}
		}
		return results;
	}
}
