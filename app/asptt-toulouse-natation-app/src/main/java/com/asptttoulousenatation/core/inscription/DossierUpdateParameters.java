package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;
import java.util.List;

import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;

public class DossierUpdateParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DossierEntity principal;
	private List<DossierNageurUi> nageurs;
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
}