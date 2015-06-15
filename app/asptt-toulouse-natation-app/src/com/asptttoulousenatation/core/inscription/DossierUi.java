package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;

@XmlRootElement
public class DossierUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5602574756341513489L;
	private DossierEntity principal;
	private List<DossierNageurEntity> nageurs;
	
	public DossierUi() {
		nageurs = new ArrayList<DossierNageurEntity>(5);
	}
	
	public void addNageur(DossierNageurEntity nageur) {
		nageurs.add(nageur);
	}
	
	public DossierEntity getPrincipal() {
		return principal;
	}
	public void setPrincipal(DossierEntity principal) {
		this.principal = principal;
	}
	public List<DossierNageurEntity> getNageurs() {
		return nageurs;
	}
	public void setNageurs(List<DossierNageurEntity> nageurs) {
		this.nageurs = nageurs;
	}
}