package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.groupe.SlotTransformer;
import com.asptttoulousenatation.core.groupe.SlotUi;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;

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

	private SlotDao slotDao = new SlotDao();

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
	
	public Set<Long> getCreneauIds(String creneaux) {
		Set<Long> results = new HashSet<Long>();
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
						results.add(Long
								.valueOf(creneauId));
					}
				}
			}
		}
		return results;
	}
}