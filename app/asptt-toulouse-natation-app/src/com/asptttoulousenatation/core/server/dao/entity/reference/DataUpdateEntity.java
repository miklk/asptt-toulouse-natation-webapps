package com.asptttoulousenatation.core.server.dao.entity.reference;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class DataUpdateEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3182065752809718677L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String kind;
	
	@Persistent
	private Boolean updated;
	
	public DataUpdateEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String pKind) {
		kind = pKind;
	}

	public Boolean getUpdated() {
		return updated;
	}

	public void setUpdated(Boolean pUpdated) {
		updated = pUpdated;
	}
}