package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;

import com.asptttoulousenatation.core.groupe.GroupUi;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;

public class DossierNageurUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DossierNageurEntity nageur;
	private GroupUi groupe;
	private boolean hasCertificat;
	public DossierNageurEntity getNageur() {
		return nageur;
	}
	public void setNageur(DossierNageurEntity nageur) {
		this.nageur = nageur;
	}
	public GroupUi getGroupe() {
		return groupe;
	}
	public void setGroupe(GroupUi groupe) {
		this.groupe = groupe;
	}
	public boolean isHasCertificat() {
		return hasCertificat;
	}
	public void setHasCertificat(boolean hasCertificat) {
		this.hasCertificat = hasCertificat;
	}
	
}
