package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;
import java.util.List;

import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;

public class DossierUpdateParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DossierEntity principal;
	private List<DossierNageurEntity> nageurs;
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