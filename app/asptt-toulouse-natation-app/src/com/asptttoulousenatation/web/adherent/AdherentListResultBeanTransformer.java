package com.asptttoulousenatation.web.adherent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;

public class AdherentListResultBeanTransformer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9206273383357950266L;

	private static AdherentListResultBeanTransformer INSTANCE;

	public static AdherentListResultBeanTransformer getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AdherentListResultBeanTransformer();
		}
		return INSTANCE;
	}

	private GroupDao groupDao = new GroupDao();
	private SlotDao slotDao = new SlotDao();

	public AdherentListResultBean get(InscriptionEntity entity) {
		AdherentListResultBean bean = new AdherentListResultBean();
		bean.setId(entity.getId());
		bean.setNom(entity.getNom());
		bean.setPrenom(entity.getPrenom());
		bean.setDateNaissance(entity.getDatenaissance());

		bean.setGroupe(getGroupe(entity.getNouveauGroupe()));
		bean.setCreneaux(getCreneaux(entity.getCreneaux()));
		bean.setEmail(entity.getEmail());
		bean.setMotdepasse(entity.getMotdepasse());
		return bean;
	}
	
	public AdherentListResultBean get2(InscriptionEntity2 entity) {
		AdherentListResultBean bean = new AdherentListResultBean();
		bean.setId(entity.getId());
		bean.setNom(entity.getNom());
		bean.setPrenom(entity.getPrenom());
		bean.setDateNaissance(entity.getDatenaissance());

		bean.setGroupe(getGroupe(entity.getNouveauGroupe()));
		bean.setCreneaux(getCreneaux(entity.getCreneaux()));
		bean.setEmail(entity.getEmail());
		bean.setMotdepasse(entity.getMotdepasse());
		return bean;
	}

	public List<AdherentListResultBean> get(List<InscriptionEntity> entities) {
		List<AdherentListResultBean> results = new ArrayList<AdherentListResultBean>(
				entities.size());
		for (InscriptionEntity entity : entities) {
			results.add(get(entity));
		}
		return results;
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
						String creneauStr = slotEntity.getDayOfWeek()
								+ " "
								+ (slotEntity.getBegin() / 60)
								+ ":"
								+ StringUtils
										.rightPad(Integer.toString((slotEntity
												.getBegin() % 60)), 2, "0")
								+ " - "
								+ (slotEntity.getEnd() / 60)
								+ ":"
								+ StringUtils.rightPad(Integer
										.toString((slotEntity.getEnd() % 60)),
										2, "0") + " - "
								+ slotEntity.getSwimmingPool();
						results.add(creneauStr);
					}
				}
			}
		}
		return results;
	}
}
