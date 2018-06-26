package com.asptt.core.util;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.asptt.core.server.dao.club.group.SlotDao;
import com.asptt.core.server.dao.entity.club.group.SlotEntity;

public class CreneauBuilder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9206273383357950266L;
	
	private static final Logger LOG = Logger.getLogger(CreneauBuilder.class
			.getName());

	private static CreneauBuilder INSTANCE;

	public static CreneauBuilder getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CreneauBuilder();
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
