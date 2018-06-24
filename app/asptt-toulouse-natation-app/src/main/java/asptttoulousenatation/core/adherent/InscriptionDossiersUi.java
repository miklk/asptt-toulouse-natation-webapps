package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;

@XmlRootElement
public class InscriptionDossiersUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8218965418459608943L;
	private List<InscriptionDossierUi> nageurs;
	private DossierEntity dossier;
	private boolean inconnu;
	private int price;
	private Map<String, String> certificats;
	
	public InscriptionDossiersUi() {
		nageurs = new ArrayList<InscriptionDossierUi>(5);
		inconnu = true;
		price = 0;
		
	}
	
	public void addDossier(InscriptionDossierUi pAdherent) {
		nageurs.add(pAdherent);
	}
	
	public void addDossier(Collection<DossierNageurEntity> pAdherents) {
		for(DossierNageurEntity adherent: pAdherents) {
			nageurs.add(new InscriptionDossierUi(adherent));
		}
	}
	
	public InscriptionDossierUi getDossier(Long id) {
		boolean trouve = false;
		Iterator<InscriptionDossierUi> it = nageurs.iterator();
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

	
	public boolean isInconnu() {
		return inconnu;
	}

	public void setInconnu(boolean pInconnu) {
		inconnu = pInconnu;
	}

	public List<InscriptionDossierUi> getNageurs() {
		return nageurs;
	}

	public void setNageurs(List<InscriptionDossierUi> nageurs) {
		this.nageurs = nageurs;
	}

	public DossierEntity getDossier() {
		return dossier;
	}

	public void setDossier(DossierEntity dossier) {
		this.dossier = dossier;
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