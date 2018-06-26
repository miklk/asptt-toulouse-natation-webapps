package com.asptt.core.adherent;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.asptt.core.server.dao.club.group.SlotDao;
import com.asptt.core.server.dao.entity.club.group.SlotEntity;

public class AdherentSummaryBeanTransformer {

	private static AdherentSummaryBeanTransformer INSTANCE;

	public static AdherentSummaryBeanTransformer getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AdherentSummaryBeanTransformer();
		}
		return INSTANCE;
	}

	private SlotDao slotDao = new SlotDao();

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