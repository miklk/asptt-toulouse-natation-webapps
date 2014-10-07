package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;

public class InscriptionPriceServiceResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3578369992706330405L;
	private InscriptionDossiersUi dossiers;
	private String price;
	
	public InscriptionPriceServiceResult() {
		
	}

	public InscriptionDossiersUi getDossiers() {
		return dossiers;
	}

	public void setDossiers(InscriptionDossiersUi pDossiers) {
		dossiers = pDossiers;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String pPrice) {
		price = pPrice;
	}
}