package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;

@XmlRootElement
public class InscriptionDossiersUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8218965418459608943L;
	private List<InscriptionDossierUi> dossiers;
	private InscriptionDossierUi principal;
	private boolean inconnu;
	private int price;
	
	public InscriptionDossiersUi() {
		dossiers = new ArrayList<InscriptionDossierUi>(5);
		inconnu = true;
		price = 0;
	}
	
	public void addDossier(InscriptionDossierUi pAdherent) {
		dossiers.add(pAdherent);
	}
	
	public void addDossier(Collection<InscriptionEntity2> pAdherents) {
		for(InscriptionEntity2 adherent: pAdherents) {
			dossiers.add(new InscriptionDossierUi(adherent));
		}
	}
	
	public void addPrice(int pPrice) {
		price = price + pPrice;
	}

	public List<InscriptionDossierUi> getDossiers() {
		return dossiers;
	}

	public void setDossiers(List<InscriptionDossierUi> pDossiers) {
		dossiers = pDossiers;
	}

	public boolean isInconnu() {
		return inconnu;
	}

	public void setInconnu(boolean pInconnu) {
		inconnu = pInconnu;
	}

	public InscriptionDossierUi getPrincipal() {
		return principal;
	}

	public void setPrincipal(InscriptionDossierUi pPrincipal) {
		principal = pPrincipal;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int pPrice) {
		price = pPrice;
	}
	
}