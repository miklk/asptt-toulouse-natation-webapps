package com.asptt.core.enf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.asptt.core.groupe.SlotUi;

public class EnfGroupeCreneau implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupe;
	private List<SlotUi> creneaux;
	
	public EnfGroupeCreneau() {
		creneaux = new ArrayList<>();
	}
	
	public void addCreneau(SlotUi creneau) {
		creneaux.add(creneau);
	}
	
	public String getGroupe() {
		return groupe;
	}
	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}
	public List<SlotUi> getCreneaux() {
		return creneaux;
	}
	public void setCreneaux(List<SlotUi> creneaux) {
		this.creneaux = creneaux;
	}
}