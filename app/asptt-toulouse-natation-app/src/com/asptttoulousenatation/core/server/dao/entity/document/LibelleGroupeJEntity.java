package com.asptttoulousenatation.core.server.dao.entity.document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
public class LibelleGroupeJEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -250677732974382643L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long libelle;
	private Long groupe;
	
	public LibelleGroupeJEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public Long getLibelle() {
		return libelle;
	}

	public void setLibelle(Long pLibelle) {
		libelle = pLibelle;
	}

	public Long getGroupe() {
		return groupe;
	}

	public void setGroupe(Long pGroupe) {
		groupe = pGroupe;
	}
	
}
