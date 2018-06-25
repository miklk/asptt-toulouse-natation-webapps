package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;
import java.util.Set;

public class DossierCreneauxParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long nageurId;
	private Set<Long> creneaux;
	
	
	
	public Long getNageurId() {
		return nageurId;
	}
	public void setNageurId(Long nageurId) {
		this.nageurId = nageurId;
	}
	public Set<Long> getCreneaux() {
		return creneaux;
	}
	public void setCreneaux(Set<Long> creneaux) {
		this.creneaux = creneaux;
	}
	
}
