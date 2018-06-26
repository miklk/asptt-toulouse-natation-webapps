package com.asptt.core.inscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptt.core.server.dao.entity.inscription.DossierEntity;
import com.asptt.core.server.dao.entity.inscription.DossierFactureEntity;

@XmlRootElement
public class DossierUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5602574756341513489L;
	private DossierEntity principal;
	private List<DossierNageurUi> nageurs;
	private DossierFactureEntity facture;
	
	public DossierUi() {
		nageurs = new ArrayList<DossierNageurUi>(5);
	}
	
	public void addNageur(DossierNageurUi nageur) {
		nageurs.add(nageur);
	}
	
	public DossierEntity getPrincipal() {
		return principal;
	}
	public void setPrincipal(DossierEntity principal) {
		this.principal = principal;
	}
	public List<DossierNageurUi> getNageurs() {
		return nageurs;
	}
	public void setNageurs(List<DossierNageurUi> nageurs) {
		this.nageurs = nageurs;
	}

	public DossierFactureEntity getFacture() {
		return facture;
	}

	public void setFacture(DossierFactureEntity facture) {
		this.facture = facture;
	}
	
}