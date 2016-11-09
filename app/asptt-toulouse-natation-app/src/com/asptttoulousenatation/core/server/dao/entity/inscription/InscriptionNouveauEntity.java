package com.asptttoulousenatation.core.server.dao.entity.inscription;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
@XmlRootElement
public class InscriptionNouveauEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207564448323062425L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String email;
	
	
	public InscriptionNouveauEntity() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String pEmail) {
		email = pEmail;
	}
}