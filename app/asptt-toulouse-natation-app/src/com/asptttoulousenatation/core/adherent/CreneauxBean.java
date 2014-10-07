package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.asptttoulousenatation.core.shared.club.slot.SlotUi;

public class CreneauxBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8143222537100014245L;
	private List<SlotUi> creneaux;
	
	public CreneauxBean() {
		creneaux = Collections.emptyList();
	}

	public List<SlotUi> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(List<SlotUi> pCreneaux) {
		creneaux = pCreneaux;
	}
}