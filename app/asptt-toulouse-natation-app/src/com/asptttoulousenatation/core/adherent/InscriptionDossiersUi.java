package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	private Map<String, String> certificats;
	
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
	
	public InscriptionDossierUi getDossier(Long id) {
		boolean trouve = false;
		Iterator<InscriptionDossierUi> it = dossiers.iterator();
		InscriptionDossierUi dossier = null;
		while(!trouve && it.hasNext()) {
			dossier = it.next();
			if(dossier.getDossier().getId().equals(id)) {
				trouve = true;
			}
		}
		//On peut se tromper de dossier s'il n'est pas présent (dans ce cas c'est le dernier de la liste mais cela ne devrait pas se produire
		return dossier;
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

	public Map<String, String> getCertificats() {
		return certificats;
	}

	public void setCertificats(Map<String, String> certificats) {
		this.certificats = certificats;
	}
	
}