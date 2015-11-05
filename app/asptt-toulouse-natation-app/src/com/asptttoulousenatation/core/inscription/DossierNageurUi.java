package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;
import java.util.Set;

import com.asptttoulousenatation.core.groupe.GroupUi;
import com.asptttoulousenatation.core.groupe.SlotUi;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;

public class DossierNageurUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DossierNageurEntity nageur;
	private GroupUi groupe;
	private Set<SlotUi> creneaux;
	private boolean hasCertificat;
	private boolean hasPhoto;
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
	public Set<SlotUi> getCreneaux() {
		return creneaux;
	}
	public void setCreneaux(Set<SlotUi> creneaux) {
		this.creneaux = creneaux;
	}
	public boolean isHasPhoto() {
		return hasPhoto;
	}
	public void setHasPhoto(boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}
	
}
