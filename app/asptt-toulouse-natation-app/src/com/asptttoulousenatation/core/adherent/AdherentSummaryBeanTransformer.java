package com.asptttoulousenatation.core.adherent;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;

public class AdherentSummaryBeanTransformer {

	private static AdherentSummaryBeanTransformer INSTANCE;

	public static AdherentSummaryBeanTransformer getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AdherentSummaryBeanTransformer();
		}
		return INSTANCE;
	}

	private GroupDao groupDao = new GroupDao();
	private SlotDao slotDao = new SlotDao();

	public AdherentSummaryBean get(InscriptionEntity2 entity) {
		AdherentSummaryBean bean = new AdherentSummaryBean();
		bean.setId(entity.getId());
		bean.setNom(entity.getNom());
		bean.setPrenom(entity.getPrenom());
		bean.setDateDeNaissance(entity.getDatenaissance());

		bean.setGroupe(getGroupe(entity.getNouveauGroupe()));
		bean.setCreneaux(getCreneaux(entity.getCreneaux()));
		return bean;
	}
	
	public List<AdherentSummaryBean> get(List<InscriptionEntity2> entities) {
		List<AdherentSummaryBean> results = new ArrayList<AdherentSummaryBean>(
				entities.size());
		for (InscriptionEntity2 entity : entities) {
			results.add(get(entity));
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