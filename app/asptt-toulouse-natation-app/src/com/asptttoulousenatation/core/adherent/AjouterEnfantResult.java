package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;

public class AjouterEnfantResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7804643549267714599L;
	private InscriptionDossiersUi dossiers;
	private Integer currentIndex;
	
	public AjouterEnfantResult() {
		
	}

	public InscriptionDossiersUi getDossiers() {
		return dossiers;
	}

	public void setDossiers(InscriptionDossiersUi pDossiers) {
		dossiers = pDossiers;
	}

	public Integer getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(Integer pCurrentIndex) {
		currentIndex = pCurrentIndex;
	}
}