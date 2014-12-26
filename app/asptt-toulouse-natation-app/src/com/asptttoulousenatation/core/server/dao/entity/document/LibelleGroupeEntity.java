package com.asptttoulousenatation.core.server.dao.entity.document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
public class LibelleGroupeEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 251424906258371005L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String intitule;
	
	public LibelleGroupeEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String pIntitule) {
		intitule = pIntitule;
	}
}
