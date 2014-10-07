package com.asptttoulousenatation.core.server.dao.entity.inscription;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@XmlRootElement
public class InscriptionNouveauEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207564448323062425L;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
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