package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;

public class AdherentBeanTransformer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9206273383357950266L;
	
	private static final Logger LOG = Logger.getLogger(AdherentBeanTransformer.class
			.getName());

	private static AdherentBeanTransformer INSTANCE;

	public static AdherentBeanTransformer getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AdherentBeanTransformer();
		}
		return INSTANCE;
	}

	private GroupDao groupDao = new GroupDao();
	private SlotDao slotDao = new SlotDao();

	public AdherentBean get(InscriptionEntity2 entity) {
		AdherentBean bean = new AdherentBean(entity);

		bean.setGroupe(getGroupe(entity.getNouveauGroupe()));
		try {
			bean.setCreneaux(getCreneaux(entity.getCreneaux()));
		} catch(Exception e) {
			LOG.severe("Erreur de créneaux sur l'adhérent " + entity.getNom());
		}
		return bean;
	}

	private GroupUi getGroupe(Long groupeId) {
		final GroupUi groupe;
		if(groupeId != null && groupeId != 0) {
			GroupEntity entity = groupDao.get(groupeId);
			groupe = GroupTransformer.getInstance().toUi(entity);
		} else {
			groupe = null;
		}
		return groupe;
	}

	public Set<SlotUi> getCreneaux(String creneaux) {
		Set<SlotUi> results = new LinkedHashSet<SlotUi>();
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
						results.add(SlotTransformer.getInstance().toUi(slotEntity));
					}
				}
			}
		}
		return results;
	}
}
