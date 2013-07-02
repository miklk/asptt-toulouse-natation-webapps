package com.asptttoulousenatation.core.server.dao.entity.club.group;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class GroupEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3111204833945561727L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String title;
	
	@Persistent
	private Boolean licenceFfn;
	
	public GroupEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public Boolean getLicenceFfn() {
		return licenceFfn;
	}

	public void setLicenceFfn(Boolean pLicenceFfn) {
		licenceFfn = pLicenceFfn;
	}
}