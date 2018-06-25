package com.asptttoulousenatation.core.server.dao.entity.inscription;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
@XmlRootElement
public class DossierFactureEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long dossier;
	
	private DossierFactureEnum statut;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDossier() {
		return dossier;
	}

	public void setDossier(Long dossier) {
		this.dossier = dossier;
	}

	public DossierFactureEnum getStatut() {
		return statut;
	}

	public void setStatut(DossierFactureEnum statut) {
		this.statut = statut;
	}
}