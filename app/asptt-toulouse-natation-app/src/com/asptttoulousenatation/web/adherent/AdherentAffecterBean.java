package com.asptttoulousenatation.web.adherent;

import java.io.Serializable;

public class AdherentAffecterBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6747068321462780233L;
	private Long affecterAdherent;
	private Long affecterGroupe;
	private Long[] affecterCreneau;
	
	public AdherentAffecterBean() {
		affecterAdherent = -1l;
		affecterGroupe = -1l;
		affecterCreneau = new Long[0];
	}

	public Long getAffecterAdherent() {
		return affecterAdherent;
	}

	public void setAffecterAdherent(Long pAffecterAdherent) {
		affecterAdherent = pAffecterAdherent;
	}

	public Long getAffecterGroupe() {
		return affecterGroupe;
	}

	public void setAffecterGroupe(Long pAffecterGroupe) {
		affecterGroupe = pAffecterGroupe;
	}

	public Long[] getAffecterCreneau() {
		return affecterCreneau;
	}

	public void setAffecterCreneau(Long[] pAffecterCreneau) {
		affecterCreneau = pAffecterCreneau;
	}
}